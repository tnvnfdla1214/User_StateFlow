package com.example.user_stateflow

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule  {

    @Provides
    fun providesUserDao(userDatabase: UserDatabase):UserDao = userDatabase.userDao()

    @Provides
    @Singleton
    fun providesUserDatabase(@ApplicationContext context: Context):UserDatabase
            = Room.databaseBuilder(context,UserDatabase::class.java,"UserDatabase").build()

    @Provides
    fun providesUserRepository(userDao: UserDao) : UserRepository
            = UserRepository(userDao)
}