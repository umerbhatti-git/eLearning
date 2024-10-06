package com.example.elearning.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.example.elearning.models.Result
import com.example.elearning.models.UserModel
import com.example.elearning.room.courses.CoursesDao
import com.example.elearning.room.user.UserDao

@Database(entities = [Result::class, UserModel::class], version = 1)
abstract class MyDatabase : RoomDatabase() {
    abstract fun coursesDao(): CoursesDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: MyDatabase? = null

        fun getDatabase(context: Context): MyDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyDatabase::class.java,
                    "my_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
