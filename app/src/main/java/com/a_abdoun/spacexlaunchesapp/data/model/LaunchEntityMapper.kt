package com.a_abdoun.spacexlaunchesapp.data.local

import com.a_abdoun.spacexlaunchesapp.data.model.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun LaunchEntity.toLaunch(): Launch {
    val gson = Gson()
    return Launch(
        id = id,
        name = name,
        date_utc = date_utc,
        date_unix = date_unix,
        date_local = date_local,
        date_precision = date_precision,
        upcoming = upcoming,
        success = success,
        failures = gson.fromJson(failures, object : TypeToken<List<Failure>>() {}.type),
        details = details,
        flight_number = flight_number,
        crew = gson.fromJson(crew, object : TypeToken<List<String>>() {}.type),
        ships = gson.fromJson(ships, object : TypeToken<List<String>>() {}.type),
        capsules = gson.fromJson(capsules, object : TypeToken<List<String>>() {}.type),
        payloads = gson.fromJson(payloads, object : TypeToken<List<String>>() {}.type),
        launchpad = launchpad,
        rocket = rocket,
        auto_update = auto_update,
        tbd = tbd,
        launch_library_id = launch_library_id,
        static_fire_date_utc = static_fire_date_utc,
        static_fire_date_unix = static_fire_date_unix,
        net = net,
        window = window,
        fairings = gson.fromJson(fairings, Fairings::class.java),
        cores = gson.fromJson(cores, object : TypeToken<List<Core>>() {}.type),
        links = gson.fromJson(links, Links::class.java)
    )
}
