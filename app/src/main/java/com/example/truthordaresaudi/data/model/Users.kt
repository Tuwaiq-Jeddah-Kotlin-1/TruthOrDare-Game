package com.example.truthordaresaudi.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Users(
    var fullName: String = "",
    var email: String = "",
    var uid: String = ""
) : Parcelable
