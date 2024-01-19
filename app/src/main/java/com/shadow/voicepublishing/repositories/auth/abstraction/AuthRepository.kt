package com.shadow.voicepublishing.repositories.auth.abstraction

import android.provider.ContactsContract.CommonDataKinds.Email
import com.shadow.voicepublishing.models.netwrok.auth.User


interface AuthRepository {

    fun signUp(user: User,onSuccess:(Boolean,String)->Unit)

    fun signIn(email: String, password:String,onSuccess: (Boolean, String) -> Unit)
    fun resetPassword(email: String, onSuccess: (Boolean) -> Unit)
}