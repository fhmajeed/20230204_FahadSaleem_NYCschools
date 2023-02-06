package co.jpmorgan.test.repositories

import co.jpmorgan.test.models.School
import co.jpmorgan.test.models.SchoolDetail
import retrofit2.http.GET
import retrofit2.http.Query

interface SchoolApi {
    @GET("s3k6-pzi2.json") // this is the end point that we need for this project to get the api response.
    suspend fun getSchoolList() : List<School>

    @GET("f9bf-2cp4.json") // this is the end point that we need for this project to get the api response.
    suspend fun getSchoolDetail(@Query("dbn") dbn: String) : List<SchoolDetail>
}