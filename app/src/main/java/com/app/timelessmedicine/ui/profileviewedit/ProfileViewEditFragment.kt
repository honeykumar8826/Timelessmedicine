package com.app.timelessmedicine.ui.profileviewedit


import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.room.Room
import com.app.timelessmedicine.MyApplication
import com.app.timelessmedicine.R
import com.app.timelessmedicine.constant.AppConstant
import com.app.timelessmedicine.database.AppDatabase
import com.app.timelessmedicine.databinding.FragmentProfileViewEditBinding
import com.app.timelessmedicine.injection.ViewModelFactory
import com.app.timelessmedicine.ui.base.BaseFragment
import com.app.timelessmedicine.ui.home.HomeActivity
import com.app.timelessmedicine.utils.CommonUtils
import com.app.timelessmedicine.utils.SnackbarUtils
import com.app.timelessmedicine.utils.TakeImageUtil
import com.app.timelessmedicine.utils.ValidationUtils
import com.bumptech.glide.Glide
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.fragment_profile_view_edit.*
import kotlinx.android.synthetic.main.image_menu_layout.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class ProfileViewEditFragment : BaseFragment<FragmentProfileViewEditBinding,
        ProfileViewEditViewModel>(),View.OnClickListener {

    var ctx: Context? = null
    private lateinit var profileViewEditModel:ProfileViewEditViewModel
    private lateinit var binding:FragmentProfileViewEditBinding
    private var isEdit = false
    private var profile_image: MultipartBody.Part? = null
    private var isDataAvalaible = false

    override val layoutId: Int
        get() = R.layout.fragment_profile_view_edit

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileViewEditModel = ViewModelProviders.of(this, ViewModelFactory(activity as HomeActivity)).get(ProfileViewEditViewModel::class.java)

        profileViewEditModel.isLoading.observe(this, androidx.lifecycle.Observer {
            if(it){
                progressDialog.showProgress(activity!!, false)
            } else {
                progressDialog.hideProgress()
            }
        })
        binding = viewDataBinding!!
        val db = Room.databaseBuilder((activity as HomeActivity).applicationContext,
            AppDatabase::class.java, "user").allowMainThreadQueries().build()

        var user = db.userDataDao().getUsers()


        profileViewEditModel.errorResponse.observe(this, androidx.lifecycle.Observer {
            onErrorRespose(it, profile_view)
        })
        profileViewEditModel.responseAdd.observe(this, androidx.lifecycle.Observer {
            if(it.response.profile_image != "") {

                Glide.with(activity!!)
                    .load(ApiConstant.IMAGE_BASE_URL + "" + it.response.profile_image).placeholder(R.drawable.placeholder)
                    .into(binding.userProfileImg)
            }
            isDataAvalaible = true
            uniqueGroup.visibility = View.VISIBLE
            referralGroup.visibility = View.VISIBLE
            subscriptionGroup.visibility = View.VISIBLE
            uniqueGroup.requestLayout()
            btnSave.text = "Edit"
            isEdit = true
            tv_email.isEnabled = false
            user = db.userDataDao().getUsers()
            if(user.isNotEmpty()) {
                isDataAvalaible = true
                user.forEach {
                    binding.user = it

                    Glide.with(activity!!).load(ApiConstant.IMAGE_BASE_URL+""+it.profile_image)
                        .placeholder(R.drawable.placeholder).into(binding.userProfileImg)

                }
            }

        })
        profileViewEditModel.response.observe(this, androidx.lifecycle.Observer {
            if(it.response.profile_image != "") {
                Glide.with(activity!!)
                    .load(ApiConstant.IMAGE_BASE_URL + "" + it.response.profile_image).placeholder(R.drawable.placeholder)
                    .into(binding.userProfileImg)
            }
            isDataAvalaible = true
            uniqueGroup.visibility = View.VISIBLE
            referralGroup.visibility = View.VISIBLE
            subscriptionGroup.visibility = View.VISIBLE
            uniqueGroup.requestLayout()
            btnSave.text = "Edit"
            isEdit = true
        })





        profileViewEditModel.injectDagger(activity?.application as MyApplication)


        if(user.isNotEmpty()) {
            isDataAvalaible = true
            user.forEach {
                binding.user = it
                if(it.profile_image != "" || it.profile_image != "") {
                    Glide.with(activity!!).load(ApiConstant.IMAGE_BASE_URL + "" + it.profile_image)
                        .placeholder(R.drawable.placeholder).into(binding.userProfileImg)
                }
            }
        }

        init()
        initControls()
        ctx = activity!!
    }

    fun init(){
        uniqueGroup.visibility = View.INVISIBLE
        referralGroup.visibility = View.INVISIBLE
        subscriptionGroup.visibility = View.INVISIBLE
        isEdit = false
        if(isDataAvalaible) {
            disableViews()
            uniqueGroup.visibility = View.VISIBLE
            referralGroup.visibility = View.VISIBLE
            subscriptionGroup.visibility = View.VISIBLE
            uniqueGroup.requestLayout()
            btnSave.text = "Edit"
            isEdit = true
        } else {
            btnSave.text = "Save"
        }
    }

    fun initControls(){
        btnSave.setOnClickListener(this)
        btn_photo.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.btnSave->{
                if(isEdit){
                    uniqueGroup.visibility = View.INVISIBLE
                    referralGroup.visibility = View.INVISIBLE
                    subscriptionGroup.visibility = View.INVISIBLE
                    uniqueGroup.requestLayout()
                    btnSave.text = "Save"
                    isEdit = false


                     enableViews()
                    tv_email.isEnabled = false

                } else {
                    val first_name = tv_first_name.text.toString()
                    val last_name = tv_last_name.text.toString()
                    val email_id = tv_email.text.toString()

                    if(validate(
                            first_name, last_name, email_id
                        )) {
                        if(!isDataAvalaible) {
                            addAccount()
                        } else {
                            editProfile()
                        }
                    }

                 /*   if(!isDataAvalaible){


                    } else {
                        uniqueGroup.visibility = View.VISIBLE
                        referralGroup.visibility = View.VISIBLE
                        subscriptionGroup.visibility = View.VISIBLE
                        uniqueGroup.requestLayout()
                        btnSave.text = "Edit"
                        isEdit = true
                    }*/

                    /*  tv_first_name.isEnabled = true
                      tv_last_name.isEnabled = true
                      tv_email.isEnabled = true
                      tv_unique.isEnabled = true
                      tv_referral.isEnabled = true
                      tv_subscription.isEnabled = true*/

                }

            }
            R.id.btn_photo->{
                showPhotoDialog()
            }
        }
    }

    fun enableViews(){
        tv_first_name.isEnabled = true
        tv_last_name.isEnabled = true
        tv_email.isEnabled = true
        btn_photo.isEnabled = true
    }

    fun disableViews(){
        tv_first_name.isEnabled = false
        tv_last_name.isEnabled = false
        tv_email.isEnabled = false
        btn_photo.isEnabled = false
    }

    fun addAccount(){

        profileViewEditModel.addAccount(
            first_name = tv_first_name.text.toString(),
            last_name = tv_last_name.text.toString(),
            email_id = tv_email.text.toString(),
            profile_image = profile_image
        )

    }


    fun editProfile(){
            profileViewEditModel.editProfile(
                first_name = tv_first_name.text.toString(),
                last_name = tv_last_name.text.toString(),
                profile_image = profile_image
            )
    }

    fun validate(first_name:String,
                 last_name:String,
                 email_id:String):Boolean{

        if(first_name.isEmpty()){
            SnackbarUtils.displaySnackbar(profile_view, "Enter first name", Toast.LENGTH_SHORT)
            return false
        }
        if(last_name.isEmpty()){
            SnackbarUtils.displaySnackbar(profile_view, "Enter last name", Toast.LENGTH_SHORT)
            return false
        }
        if(email_id.isEmpty()){
            SnackbarUtils.displaySnackbar(profile_view, "Enter email id", Toast.LENGTH_SHORT)
            return false
        }
        if(!ValidationUtils.isValidEmail(email_id)){
            SnackbarUtils.displaySnackbar(profile_view, "Invalid email id", Toast.LENGTH_SHORT)
            return false
        }

        return true
    }

    override fun onActivityResult(requestCode: Int,
                                  resultCode: Int, data: Intent?) {


        if(requestCode == AppConstant.REQUEST_CAMERA_IMAGE_CODE || requestCode == AppConstant.REQUEST_GALLERY_IMAGE_CODE){
            if(data != null){
                val filePath = data.getStringExtra("filePath")
                // val bitmap = MediaStore.Images.Media.getBitmap(ctx.getContentResolver(), Uri.parse(filePath))

                val file = File(filePath)
                if(file.exists()){
                    CropImage.activity(Uri.fromFile(file)).start(ctx!! as HomeActivity)
                }
            }
        }

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            val result = CropImage.getActivityResult(data)
            if(resultCode == Activity.RESULT_OK){
                val uri = result.uri
                val file = File(uri.path)
                val bitmap = MediaStore.Images.Media.getBitmap(activity!!.contentResolver, uri)
                setImageData(file)
                userProfileImg.setImageBitmap(bitmap)
            }
        }

    }

    private fun setImageData(file:File){
        val requestFile = RequestBody.create(MediaType.parse("image"), file)
        profile_image =  MultipartBody.Part.createFormData("profile_image", file.name,
            requestFile)
    }

    fun showPhotoDialog(){
        val dialog = Dialog(activity!!)
        dialog.setContentView(R.layout.image_menu_layout)

        dialog.btn_camera.setOnClickListener {
            val intent = Intent(activity!!, TakeImageUtil::class.java)
            intent.putExtra("from", "camera")
            dialog.dismiss()
            startActivityForResult(intent, AppConstant.REQUEST_CAMERA_IMAGE_CODE)
        }
        dialog.btn_gallery.setOnClickListener {
            val intent = Intent(activity!!, TakeImageUtil::class.java)
            intent.putExtra("from", "gallery")
            dialog.dismiss()
            startActivityForResult(intent, AppConstant.REQUEST_GALLERY_IMAGE_CODE)
        }

        dialog.show()
    }

}
