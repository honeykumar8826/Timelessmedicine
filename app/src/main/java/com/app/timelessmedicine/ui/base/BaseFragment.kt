package com.app.timelessmedicine.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.app.timelessmedicine.utils.ErrorUtil
import com.app.timelessmedicine.utils.ProgressDialogUtils

abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel>:Fragment() {


    private var mRootView: View? = null
    var viewDataBinding: T? = null
    private set
    private var mViewModel: V? = null

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
/*
    abstract fun getBindingVariable(): Int
*/

    /**
     * @return layout resource id
     */
    @get:LayoutRes
    abstract val layoutId: Int
    /**
     * Override for set view model
     *
     * @return view model instance
     */
    var viewModel:V? = null
        set

    override fun onCreate(savedInstanceState: Bundle?) {
      //  performDependencyInjection()
        super.onCreate(savedInstanceState)
        mViewModel = viewModel
        setHasOptionsMenu(false)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        mRootView = viewDataBinding!!.root
        return mRootView
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // mViewDataBinding!!.setVariable(getBindingVariable(), mViewModel)
        //comment by me
       // viewDataBinding!!.lifecycleOwner = this
        viewDataBinding!!.executePendingBindings()
    }

    val progressDialog = ProgressDialogUtils().getInstance()

/*
    fun getBaseActivity(): BaseActivity? {
        return mActivity
    }

    fun getViewDataBinding(): T? {
        return mViewDataBinding
    }

    fun hideKeyboard() {
        if (mActivity != null) {
            mActivity!!.hideKeyboard()
        }
    }

    fun isNetworkConnected(): Boolean {
        return mActivity != null && mActivity!!.isNetworkConnected()
    }

    fun openActivityOnTokenExpire() {
        if (mActivity != null) {
            mActivity!!.openActivityOnTokenExpire()
        }
    }*/

  /*  private fun performDependencyInjection() {
        AndroidSupportInjection.inject(this)
    }*/

    interface Callback {

        fun onFragmentAttached()

        fun onFragmentDetached(tag: String)
    }

    fun onErrorRespose(it: Throwable, view: View) {
        progressDialog.hideProgress()
        ErrorUtil.handlerGeneralError(context, view, it)
    }
}