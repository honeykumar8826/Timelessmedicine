package com.app.timelessmedicine.injection

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.app.timelessmedicine.database.AppDatabase
import com.app.timelessmedicine.ui.profileviewedit.ProfileViewEditViewModel

class ViewModelFactory(private val activity: AppCompatActivity): ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(ProfileViewEditViewModel::class.java)) {

            val db = Room.databaseBuilder(activity.applicationContext, AppDatabase::class.java, "user").allowMainThreadQueries().build()
            @Suppress("UNCHECKED_CAST")
            return ProfileViewEditViewModel(db.userDataDao()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}