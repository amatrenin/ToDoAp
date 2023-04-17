package com.awddrhz.todoap.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.room.RoomDatabase
import com.awddrhz.todoap.data.room.AppDatabase
import com.awddrhz.todoap.data.room.RoomRepository
import com.awddrhz.todoap.data.room.RoomRepositoryImpl
import com.awddrhz.todoap.data.room.RoomRepositoryImpl.Companion.DATABASE_NAME
import com.awddrhz.todoap.data.sharedPrefs.PrefRepository
import com.awddrhz.todoap.data.sharedPrefs.PrefRepositoryImpl
import com.awddrhz.todoap.data.sharedPrefs.PrefRepositoryImpl.Companion.PREFS_NAME
import com.awddrhz.todoap.room.ToDoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Singleton
    @Provides
    fun providesRoomDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
    context,
    AppDatabase::class.java, DATABASE_NAME
    )
    .allowMainThreadQueries()
    .fallbackToDestructiveMigration()
    .build()

    @Singleton
    @Provides
    fun providesAppDataBase(appDataBase: AppDatabase) = appDataBase.toDoDao()


    @Singleton
    @Provides
    fun providesSharedPreferenses(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)


    @Singleton
    @Provides
    fun providesRoomRepository(toDoDao: ToDoDao): RoomRepository = RoomRepositoryImpl(toDoDao)

    @Singleton
    @Provides
    fun providesSharedPrefs(sharedPreferences: SharedPreferences): PrefRepository = PrefRepositoryImpl(sharedPreferences)
}
