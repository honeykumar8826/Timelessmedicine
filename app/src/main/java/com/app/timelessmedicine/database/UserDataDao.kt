package com.app.timelessmedicine.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.app.timelessmedicine.database.model.User

@Dao
interface UserDataDao {

    @get:Query("SELECT * FROM user")
    val all: List<User>

    @Insert
    fun insertAll(vararg users: User)

    @Query("Select * FROM user")
    fun getUsers():List<User>

    @Update
    fun updateUser(user:User)
}