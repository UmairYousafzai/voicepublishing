package com.shadow.voicepublishing.repositories.data.abstraction

import com.shadow.voicepublishing.models.netwrok.auth.User
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun putString(key: String, value: String)
    suspend fun putLong(key: String, value: Long)
    suspend fun putInt(key: String, value: Int)
    suspend fun putBoolean(key: String, value: Boolean)
    suspend fun getBoolean(key: String): Boolean
    suspend fun getString(key: String): String
//    suspend fun getLong(key: String): Long
    suspend fun getInt(key: String): Int?

    suspend fun saveUserInfo(user: User)
    suspend fun getUser(): User
    suspend fun getIsSubscribe(): Boolean
    suspend fun getIsAlreadyLogin(): Boolean
    suspend fun clearData()


    val user: Flow<User>
    val isAlreadyLogin: Flow<Boolean>
    val isSubscribe: Flow<Boolean>

}