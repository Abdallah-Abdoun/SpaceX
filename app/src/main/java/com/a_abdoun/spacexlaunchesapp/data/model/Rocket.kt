package com.a_abdoun.spacexlaunchesapp.data.model

data class Rocket(
    val id: String,
    val name: String,
    val description: String,
    val type: String,
    val active: Boolean,
    val stages: Int,
    val boosters: Int,
    val cost_per_launch: Long,
    val success_rate_pct: Int,
    val first_flight: String,
    val country: String,
    val company: String,
    val wikipedia: String,
    val height: Dimension,
    val diameter: Dimension,
    val mass: Mass,
    val flickr_images: List<String>,
    val first_stage: FirstStage,
    val second_stage: SecondStage,
    val engines: Engines,
    val landing_legs: LandingLegs,
    val payload_weights: List<PayloadWeight>
)

data class Dimension(
    val meters: Double?,
    val feet: Double?
)

data class Mass(
    val kg: Int,
    val lb: Int
)

data class PayloadWeight(
    val id: String,
    val name: String,
    val kg: Int,
    val lb: Int
)

data class Thrust(
    val kN: Int,
    val lbf: Int
)

data class ISP(
    val sea_level: Int,
    val vacuum: Int
)

data class FirstStage(
    val thrust_sea_level: Thrust,
    val thrust_vacuum: Thrust,
    val reusable: Boolean,
    val engines: Int,
    val fuel_amount_tons: Double,
    val burn_time_sec: Int?
)

data class CompositeFairing(
    val height: Dimension,
    val diameter: Dimension
)

data class Payloads(
    val option_1: String,
    val composite_fairing: CompositeFairing
)

data class SecondStage(
    val thrust: Thrust,
    val payloads: Payloads,
    val reusable: Boolean,
    val engines: Int,
    val fuel_amount_tons: Double,
    val burn_time_sec: Int?
)

data class Engines(
    val isp: ISP,
    val thrust_sea_level: Thrust,
    val thrust_vacuum: Thrust,
    val number: Int,
    val type: String,
    val version: String,
    val layout: String?,
    val engine_loss_max: Int?,
    val propellant_1: String,
    val propellant_2: String,
    val thrust_to_weight: Double
)

data class LandingLegs(
    val number: Int,
    val material: String?

)