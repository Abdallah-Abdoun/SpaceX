package com.a_abdoun.spacexlaunchesapp.data.repository

import android.content.Context
import android.net.ConnectivityManager
import com.a_abdoun.spacexlaunchesapp.data.local.LaunchDao
import com.a_abdoun.spacexlaunchesapp.data.local.LaunchEntity
import com.a_abdoun.spacexlaunchesapp.data.model.toEntity
import com.a_abdoun.spacexlaunchesapp.data.network.SpaceXApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class LaunchRepository(
    private val apiService: SpaceXApiService,
    private val dao: LaunchDao,
    private val context: Context
) {

    fun getAllLaunches(): Flow<List<LaunchEntity>> = flow {
        val local = dao.getAllLaunches().first()
        emit(local)

        if (isConnectedToInternet()) {
            try {
                val remote = apiService.getAllLaunches()
                val entities = remote.map { it.toEntity() }
                dao.insertAll(entities)
                emit(entities)
            } catch (e: Exception) {
                e.printStackTrace()
                // fallback: emit local again if API fails
                emit(local)
            }
        }
    }

    fun getLaunchById(id: String): Flow<LaunchEntity?> {
        return dao.getLaunchById(id)
    }

    private fun isConnectedToInternet(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo?.isConnectedOrConnecting == true
    }
}
