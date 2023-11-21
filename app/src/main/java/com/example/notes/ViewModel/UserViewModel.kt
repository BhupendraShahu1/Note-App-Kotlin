package com.example.notes.ViewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.notes.Entity.UserEntity
import com.example.notes.Repository.UserRepository

class UserViewModel(application: Application) : AndroidViewModel(application) {

    fun insert(context: Context, user: UserEntity) {
        UserRepository.insert(context, user)
    }

    fun update(context: Context, user: UserEntity) {
        UserRepository.update(context, user)
    }

    fun delete(context: Context, user: UserEntity) {
        UserRepository.delete(context, user)
    }

    fun search(context: Context, tittle: String): LiveData<List<UserEntity>>? {
        return UserRepository.search(context, tittle)
    }

    fun getAllUserData(context: Context): LiveData<List<UserEntity>>? {
        return UserRepository.getAllUserData(context)
    }
}
//class UserViewModel(application: Application):AndroidViewModel(application){
//    private val readAllData:LiveData<List>
//}