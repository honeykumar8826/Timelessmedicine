package com.app.timelessmedicine.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.timelessmedicine.database.model.User

@Database(entities = [User::class], version = 1, exportSchema = true)
abstract class AppDatabase:RoomDatabase() {
    abstract fun userDataDao():UserDataDao
}