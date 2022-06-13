package com.example.testapplication.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [TableNote::class], version = 2)
@TypeConverters(ConvertorTypes::class)
abstract class NoteDataBase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}