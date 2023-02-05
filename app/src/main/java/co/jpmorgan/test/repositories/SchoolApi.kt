package co.jpmorgan.test.repositories

import co.jpmorgan.test.models.School
import retrofit2.http.GET

interface SchoolApi {
    @GET("s3k6-pzi2.json") // this is the end point that we need for this project to get the api response.
    suspend fun getSchoolList() : List<School>
}