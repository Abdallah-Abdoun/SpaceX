package com.a_abdoun.spacexlaunchesapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.a_abdoun.spacexlaunchesapp.data.local.Converters
import com.a_abdoun.spacexlaunchesapp.data.local.LaunchDao
import com.a_abdoun.spacexlaunchesapp.data.local.LaunchEntity

@Database(
    entities = [LaunchEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun launchDao(): LaunchDao
}
