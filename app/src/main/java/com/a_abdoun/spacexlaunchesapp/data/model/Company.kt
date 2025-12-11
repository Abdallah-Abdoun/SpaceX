package com.a_abdoun.spacexlaunchesapp.data.model

data class CompanyInfo(
    val name: String,
    val founder: String,
    val founded: Int,
    val employees: Int,
    val launch_sites: Int,
    val test_sites: Int,
    val ceo: String,
    val cto: String,
    val coo: String,
    val cto_propulsion: String,
    val valuation: Long?,
    val headquarters: Headquarters,
    val links: CompanyLinks,
    val summary: String
)

data class Headquarters(val address:String, val city:String, val state:String)
data class CompanyLinks(val website:String, val flickr:String?, val twitter:String?, val elon_twitter:String?)
