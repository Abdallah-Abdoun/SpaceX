package com.a_abdoun.spacexlaunchesapp.data.network

import com.a_abdoun.spacexlaunchesapp.data.model.CompanyInfo
import com.a_abdoun.spacexlaunchesapp.data.model.Launch
import com.a_abdoun.spacexlaunchesapp.data.model.Rocket
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface SpaceXApiService {
    @GET("v4/launches")
    suspend fun getAllLaunches(): List<Launch>

    @GET("v4/rockets")
    suspend fun getAllRockets(): List<Rocket>

    @GET("v4/company")
    suspend fun getCompanyInfo(): CompanyInfo

    @GET("v4/rockets/{id}")
    suspend fun getRocketById(
        @Path("id") id: String
    ): Rocket



    companion object {
        fun create(): SpaceXApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.spacexdata.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(SpaceXApiService::class.java)
        }
    }
}
