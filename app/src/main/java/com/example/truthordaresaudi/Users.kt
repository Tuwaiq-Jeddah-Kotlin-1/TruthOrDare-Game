package com.example.truthordaresaudi

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Users(
    var fullName: String = "",
    var email: String = "",
    var uId: String = ""
) : Parcelable
