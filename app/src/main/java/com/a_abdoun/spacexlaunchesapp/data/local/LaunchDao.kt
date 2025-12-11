package com.a_abdoun.spacexlaunchesapp.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface LaunchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(launches: List<LaunchEntity>)

    @Query("SELECT * FROM launches ORDER BY date_unix DESC")
    fun getAllLaunches(): Flow<List<LaunchEntity>>

    @Query("SELECT * FROM launches WHERE id = :id")
    fun getLaunchById(id: String): Flow<LaunchEntity?>
}
