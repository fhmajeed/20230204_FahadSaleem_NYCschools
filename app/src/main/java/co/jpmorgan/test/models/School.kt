package co.jpmorgan.test.models

import com.google.gson.annotations.SerializedName

data class School(
    @SerializedName("dbn") val dbn: String,

    @SerializedName("school_name")
    var schoolName: String,

    @SerializedName("school_email")
    var schoolEmail: String,

    @SerializedName("website")
    var website: String,

    @SerializedName("phone_number")
    var phoneNumber: String
)

