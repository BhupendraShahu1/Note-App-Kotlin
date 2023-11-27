package com.example.notes.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.notes.Entity.UserEntity

@Dao
interface UserDao {
    @Insert
    fun insertData(userEntity: UserEntity)

    @Query("SELECT * FROM  notesData ORDER BY id DESC")
    fun getAllUserData(): LiveData<List<UserEntity>>

    @Query("select * from notesData where tittle LIKE :search")
//    @Query("Select * from notesData where tittle like :search" )
    fun searchTittle(search: String): LiveData<List<UserEntity>>

    @Update
    fun updateText(userEntity: UserEntity)

    @Delete
    fun deleteText(userEntity: UserEntity)

}