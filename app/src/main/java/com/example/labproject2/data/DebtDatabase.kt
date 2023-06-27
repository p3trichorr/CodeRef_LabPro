package com.example.labproject2.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [Debts::class],
    version = 1
)

abstract class DebtDatabase : RoomDatabase() {
    abstract fun debtsDao(): DebtsDAO

    companion object {
        private const val DATABASE_NAME = "debt_list.db"

        @Volatile
        private var instance: DebtDatabase? = null

        fun getInstance(context: Context): DebtDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): DebtDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                DebtDatabase::class.java,
                DATABASE_NAME
            ).build()
        }

    }
}