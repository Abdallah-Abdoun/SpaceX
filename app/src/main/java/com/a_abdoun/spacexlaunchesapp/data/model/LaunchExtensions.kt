package com.a_abdoun.spacexlaunchesapp.data.model

import com.a_abdoun.spacexlaunchesapp.data.local.LaunchEntity
import com.google.gson.Gson

fun Launch.toEntity(): LaunchEntity {
    val gson = Gson()

    return LaunchEntity(
        id = id,
        name = name,
        date_utc = date_utc,
        date_unix = date_unix,
        date_local = date_local,
        date_precision = date_precision,
        upcoming = upcoming,
        success = success,
        details = details,
        flight_number = flight_number,
        launchpad = launchpad,
        rocket = rocket,
        auto_update = auto_update,
        tbd = tbd,
        launch_library_id = launch_library_id,
        static_fire_date_utc = static_fire_date_utc,
        static_fire_date_unix = static_fire_date_unix,
        net = net,
        window = window,
        failures = gson.toJson(failures),
        crew = gson.toJson(crew),
        ships = gson.toJson(ships),
        capsules = gson.toJson(capsules),
        payloads = gson.toJson(payloads),
        cores = gson.toJson(cores),
        fairings = gson.toJson(fairings),
        links = gson.toJson(links)
    )
}
