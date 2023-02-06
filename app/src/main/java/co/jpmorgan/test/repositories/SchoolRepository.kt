package co.jpmorgan.test.repositories

import co.jpmorgan.test.models.School
import co.jpmorgan.test.models.SchoolDetail
import co.jpmorgan.test.utils.Result

interface SchoolRepository {
    suspend fun fetchSchoolList() : Result<List<School>>
    suspend fun fetchSchoolDetail(dbn : String) : Result<SchoolDetail>
}