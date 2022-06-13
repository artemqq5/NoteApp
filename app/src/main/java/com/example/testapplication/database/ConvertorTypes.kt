package com.example.testapplication.database

import androidx.room.TypeConverter
import com.example.testapplication.helperDate.DateParsing
import java.util.*

class ConvertorTypes {

    @TypeConverter
    fun fromUUID(uuid: UUID): String {
        return uuid.toString()
    }

    @TypeConverter
    fun toUUID(str: String): UUID {
        return UUID.fromString(str)
    }

    @TypeConverter
    fun fromDate(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun toDate(strDate: Long): Date {
        return Date(strDate)
    }

}