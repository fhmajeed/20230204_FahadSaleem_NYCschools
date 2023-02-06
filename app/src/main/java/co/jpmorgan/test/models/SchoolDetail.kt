package co.jpmorgan.test.models

import com.google.gson.annotations.SerializedName

data class SchoolDetail(
    @SerializedName("dbn") var dbn: String?,
    @SerializedName("school_name") val schoolName: String,
    @SerializedName("sat_critical_reading_avg_score") val readingSATScore: String,
    @SerializedName("sat_math_avg_score") val mathSATScore: String,
    @SerializedName("sat_writing_avg_score") val writingSATScore: String
)
