package com.shadow.voicepublishing.repositories.data.implementation


import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.shadow.voicepublishing.repositories.data.abstraction.DataStoreRepository
import com.shadow.voicepublishing.models.netwrok.auth.User
import com.shadow.voicepublishing.utils.EMAIL
import com.shadow.voicepublishing.utils.FULL_NAME
import com.shadow.voicepublishing.utils.IS__LOGIN
import com.shadow.voicepublishing.utils.IS__SUBSCRIBE
import com.shadow.voicepublishing.utils.PHONE_NUMBER
import com.shadow.voicepublishing.utils.USER__ID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepoImpl @Inject constructor(private val dataStore: DataStore<Preferences>) : DataStoreRepository {

    override suspend fun putString(key: String, value: String) {
        val preferencesKey = stringPreferencesKey(key)
        dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    override suspend fun putLong(key: String, value: Long) {
        val preferencesKey = longPreferencesKey(key)
        dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    override suspend fun putInt(key: String, value: Int) {
        val preferencesKey = intPreferencesKey(key)
        dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    override suspend fun putBoolean(key: String, value: Boolean) {
        val preferencesKey = booleanPreferencesKey(key)
        dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    override suspend fun getBoolean(key: String): Boolean {
        val preferencesKey = booleanPreferencesKey(key)
        val preferences = dataStore.data.first()
        return preferences[preferencesKey] ?: false
    }

    override suspend fun getString(key: String): String {
        val preferencesKey = stringPreferencesKey(key)
        val preferences = dataStore.data.first()
        return preferences[preferencesKey] ?: ""
    }




    override suspend fun getInt(key: String): Int? {
        val preferencesKey = intPreferencesKey(key)
        val preferences = dataStore.data.first()
        return preferences[preferencesKey]
    }



    override suspend fun saveUserInfo(user: User) {
        dataStore.edit { preferences ->
            preferences[USER__ID] = user.userID
            preferences[FULL_NAME] = user.name
            preferences[EMAIL] = user.email
            preferences[PHONE_NUMBER] = user.phoneNumber

        }
    }

    override suspend fun getUser(): User {
        val preferences = dataStore.data.first()
        return User(
            preferences[FULL_NAME] ?: "",
            preferences[PHONE_NUMBER] ?: "",
            preferences[EMAIL] ?: "",
            preferences[USER__ID] ?: "",

        )
    }

    override val user: Flow<User>
        get() = dataStore.data.map { preferences ->
            User(
                preferences[FULL_NAME] ?: "",
                preferences[PHONE_NUMBER] ?: "",
                preferences[EMAIL] ?: "",
                preferences[USER__ID] ?: "",

                )
        }

    override val isAlreadyLogin: Flow<Boolean>
        get() = dataStore.data.map { preferences ->
            preferences[IS__LOGIN] ?: false
        }


    override val isSubscribe: Flow<Boolean>
        get() = dataStore.data.map { preferences ->
            preferences[IS__SUBSCRIBE] ?: false
        }




    override suspend fun getIsAlreadyLogin(): Boolean {
        val preferences = dataStore.data.first()
        return preferences[IS__LOGIN] ?: false
    }
    override suspend fun getIsSubscribe(): Boolean {
        val preferences = dataStore.data.first()
        return preferences[IS__SUBSCRIBE] ?: false
    }
    override suspend fun clearData() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

}