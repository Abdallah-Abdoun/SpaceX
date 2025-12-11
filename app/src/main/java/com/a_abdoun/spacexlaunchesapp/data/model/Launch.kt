package com.a_abdoun.spacexlaunchesapp.data.model

data class Launch(
    val id: String,
    val name: String,
    val date_utc: String,
    val date_unix: Long,
    val date_local: String,
    val date_precision: String,
    val upcoming: Boolean,
    val success: Boolean?,
    val failures: List<Failure>,
    val details: String?,
    val flight_number: Int,
    val crew: List<String>,
    val ships: List<String>,
    val capsules: List<String>,
    val payloads: List<String>,
    val launchpad: String,
    val rocket: String,
    val auto_update: Boolean,
    val tbd: Boolean,
    val launch_library_id: String?,
    val static_fire_date_utc: String?,
    val static_fire_date_unix: Long?,
    val net: Boolean,
    val window: Int?,
    val fairings: Fairings?,
    val cores: List<Core>,
    val links: Links
)

data class Failure(
    val time: Int,
    val altitude: Int?,
    val reason: String
)

data class Fairings(
    val reused: Boolean?,
    val recovery_attempt: Boolean?,
    val recovered: Boolean?,
    val ships: List<String>
)

data class Core(
    val core: String?,
    val flight: Int?,
    val block: Int?,
    val reused: Boolean?,
    val gridfins: Boolean?,
    val legs: Boolean?,
    val landing_attempt: Boolean?,
    val landing_success: Boolean?,
    val landing_type: String?,
    val landpad: String?
)

data class Links(
    val patch: Patch,
    val reddit: Reddit,
    val flickr: Flickr,
    val presskit: String?,
    val webcast: String?,
    val youtube_id: String?,
    val article: String?,
    val wikipedia: String?
)

data class Patch(
    val small: String?,
    val large: String?
)

data class Reddit(
    val campaign: String?,
    val launch: String?,
    val media: String?,
    val recovery: String?
)

data class Flickr(
    val small: List<String>,
    val original: List<String>
)
