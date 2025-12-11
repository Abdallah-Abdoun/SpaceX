package com.a_abdoun.spacexlaunchesapp.data.local

import androidx.room.*

@Entity(tableName = "launches")
data class LaunchEntity(
    @PrimaryKey val id: String,
    val name: String,
    val date_utc: String,
    val date_unix: Long,
    val date_local: String,
    val date_precision: String,
    val upcoming: Boolean,
    val success: Boolean?,
    val details: String?,
    val flight_number: Int,
    val launchpad: String,
    val rocket: String,
    val auto_update: Boolean,
    val tbd: Boolean,
    val launch_library_id: String?,
    val static_fire_date_utc: String?,
    val static_fire_date_unix: Long?,
    val net: Boolean,
    val window: Int?,

    val failures: String,
    val crew: String,
    val ships: String,
    val capsules: String,
    val payloads: String,
    val cores: String,
    val fairings: String?,
    val links: String
)
