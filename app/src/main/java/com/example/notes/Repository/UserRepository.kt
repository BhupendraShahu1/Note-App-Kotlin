package com.example.notes.Repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.notes.DataBase.UserDataBase
import com.example.notes.Entity.UserEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class UserRepository {

    companion object {
        private var userDatabase: UserDataBase? = null
        fun initaliseDB(context: Context): UserDataBase? {
            return UserDataBase.getInstance(context)

        }

        fun insert(context: Context, user: UserEntity) {
            userDatabase = initaliseDB(context)
            CoroutineScope(IO).launch {
                userDatabase?.getDAO()?.insertData(user)
            }
        }

        fun search(context: Context, search: String) :LiveData<List<UserEntity>>?{
            userDatabase = initaliseDB(context)
            CoroutineScope(IO).launch {
                userDatabase?.getDAO()?.searchTittle(search)
            }
            return userDatabase?.getDAO()?.searchTittle(search)
        }

        fun update(context: Context, user: UserEntity) {
            userDatabase = initaliseDB(context)
            CoroutineScope(IO).launch {
                userDatabase?.getDAO()?.updateText(user)
            }
        }

        fun delete(context: Context, user: UserEntity) {
            userDatabase = initaliseDB(context)
            CoroutineScope(IO).launch {
                userDatabase?.getDAO()?.deleteText(user)
            }
        }

        fun getAllUserData(context: Context): LiveData<List<UserEntity>>? {
            userDatabase = initaliseDB(context)
            return userDatabase?.getDAO()?.getAllUserData()
        }
    }

}
//class UserRepository(private val userDao: UserDao) {
//    val readAllData: LiveData<List<UserEntity>> = userDao.getAllUserData()
//    suspend fun addUser(userEntity: UserEntity) {
//        userDao.insertData(userEntity)
//    }
//}