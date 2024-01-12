package com.shadow.voicepublishing.models.netwrok.auth

import java.io.Serializable

data class User(
    var name :String ="",
    var phoneNumber :String ="",
    var email :String ="",
    var userID :String ="",
    var password :String ="",
):Serializable
