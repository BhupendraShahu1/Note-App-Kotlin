package com.example.notes.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notes.DAO.UserDao
import com.example.notes.Entity.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class UserDataBase : RoomDatabase() {
    abstract fun getDAO(): UserDao

    companion object {
        private const val DATABASE_NAME = "UserData"

        @Volatile
        private var INSTANCE: UserDataBase? = null
        fun getInstance(context: Context): UserDataBase? {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDataBase::class.java,
                    "UserDataBAse"
                ).build()
                INSTANCE = instance
                return instance
            }


//                synchronized(UserDataBase::class.java) {
//                    if (INSTANCE == null) {
//                        INSTANCE =
//                            Room.databaseBuilder(context, UserDataBase::class.java, DATABASE_NAME)
//                                .build()
//                    }
//                }
//            }
//            return INSTANCE
        }
    }
}