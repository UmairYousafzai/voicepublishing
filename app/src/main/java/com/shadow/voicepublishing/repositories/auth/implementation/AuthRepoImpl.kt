package com.shadow.voicepublishing.repositories.auth.implementation

import android.util.Log

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.shadow.voicepublishing.models.netwrok.auth.User
import com.shadow.voicepublishing.repositories.auth.abstraction.AuthRepository
import com.shadow.voicepublishing.repositories.data.abstraction.DataStoreRepository
import com.shadow.voicepublishing.utils.CONSTANTS.USERS
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class AuthRepoImpl @Inject constructor(private val dataStore: DataStoreRepository) : AuthRepository {
    private val TAG= "AuthRepoImpl"
    val auth: FirebaseAuth by lazy { Firebase.auth }

    override fun signUp(user: User,onSuccess:(Boolean,String)-> Unit) {

        checkEmailExist(user.email){ flag,message->
            if (flag){
                onSuccess.invoke(flag,message)
            }else
            {
                auth.createUserWithEmailAndPassword(user.email, user.password).addOnCompleteListener { task->

                    if (task.isSuccessful) {
                        val uID = auth.currentUser?.uid
                        if (!uID.isNullOrEmpty()){
                            user.userID = uID
                            setUserData(user,onSuccess)
                        }else
                        {
                            Log.d(TAG, "signUp: failed because id is empty or null ")
                            onSuccess.invoke(false,"Sign up failed")
                        }
                    }
                }
                    .addOnFailureListener {
                        it.message?.let { it1 -> onSuccess.invoke(false, it1) }
                    }
            }

        }



    }

    override fun signIn(email: String, password: String, onSuccess: (Boolean, String) -> Unit) {

        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener {task->
                if (task.isSuccessful)
                {
                    getUserAndStore(task.result.user?.uid)
                    onSuccess.invoke(true,"")
                }else
                {
                    task.exception?.message?.let { onSuccess.invoke(false, it) }
                }
            }
            .addOnFailureListener {
                it.message?.let { it1 -> onSuccess.invoke(false, it1) }
            }
    }

    private fun getUserAndStore(uid: String?) {
        if (uid != null) {
            FirebaseFirestore.getInstance().collection(USERS).document(uid).get()
                .addOnSuccessListener {

                    val model = it.toObject(User::class.java)
                    model?.let {
                       saveUserToDataStore(it)
                    }

                }
                .addOnFailureListener {
                    it.printStackTrace()
                }
        }
    }

    private fun saveUserToDataStore(it: User) {
        CoroutineScope(Dispatchers.IO).launch{
            dataStore.saveUserInfo(it)
        }
    }

    private fun setUserData(user: User,onSuccess: (Boolean,String) -> Unit) {

        FirebaseFirestore.getInstance().collection(USERS).document(user.userID).set(user)
            .addOnSuccessListener {
                onSuccess.invoke(true,"Sign up successful")
            }
            .addOnFailureListener {
                it.message?.let { it1 -> onSuccess.invoke(false, it1) }
            }
    }

     fun checkEmailExist(value: String, onSuccess: (Boolean,String) -> Unit) {
        auth.fetchSignInMethodsForEmail(value)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    val isNewUser = task.result.signInMethods?.isEmpty()
                    if (isNewUser == true) {
                        onSuccess.invoke(false,"Already Exist")

                    } else {
                        onSuccess.invoke(true,"Already Exist")
                    }
                } else {
                    onSuccess.invoke(true,"Auth Failed")
                }


            }
            .addOnFailureListener {
                when (it) {
                    is FirebaseAuthUserCollisionException -> {
                        onSuccess.invoke(true, "Email already exists")
                    }
                    else -> {
                        onSuccess.invoke(false, "An error occurred: ${it.message}")
                    }
                }
            }
    }

}