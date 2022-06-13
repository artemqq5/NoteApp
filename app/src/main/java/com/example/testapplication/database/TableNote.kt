package com.example.testapplication.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.*


@Entity(tableName = "table_notes")
@Parcelize
data class TableNote(
    @PrimaryKey val uid: UUID = UUID.randomUUID(),
    val title: String? = null,
    val description: String? = null,
    val date: Date? = null
): Parcelable
