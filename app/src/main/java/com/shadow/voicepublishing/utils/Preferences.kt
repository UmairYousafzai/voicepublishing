package com.shadow.voicepublishing.utils

import androidx.datastore.preferences.core.*


const val USER_ID = "user_id"
const val USER_EMAIL = "email"
const val USER_PHONE_NUMBER = "phone_number"
const val USER_FULL_NAME = "fullName"
const val IS_LOGIN = "is.login"
const val IS_SUBSCRIBE = "is.subscribe"

val FULL_NAME = stringPreferencesKey(USER_FULL_NAME)
val USER__ID = stringPreferencesKey(USER_ID)
val EMAIL = stringPreferencesKey(USER_EMAIL)
val PHONE_NUMBER = stringPreferencesKey(USER_PHONE_NUMBER)
val IS__LOGIN = booleanPreferencesKey(IS_LOGIN)
val IS__SUBSCRIBE = booleanPreferencesKey(IS_SUBSCRIBE)
