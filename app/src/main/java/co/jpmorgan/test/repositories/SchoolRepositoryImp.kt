package co.jpmorgan.test.repositories

import co.jpmorgan.test.models.School
import co.jpmorgan.test.models.SchoolDetail
import javax.inject.Inject
import co.jpmorgan.test.utils.Result

class SchoolRepositoryImp @Inject constructor(private val schoolApi: SchoolApi) : SchoolRepository {

    override suspend fun fetchSchoolList(): Result<List<School>> {
        return try {
            val list: List<School> = schoolApi.getSchoolList()
            Result.Success(list)
        } catch (e: Exception) {
            println(e)
            Result.Error(Exception("Network Error"))
        }
    }

    override suspend fun fetchSchoolDetail(dbn: String): Result<SchoolDetail> {
        return try {
            val schoolDetail = schoolApi.getSchoolDetail(dbn).first()
            Result.Success(schoolDetail)
        } catch (e: Exception) {
            println(e)
            Result.Error(Exception("Network Error"))
        }
    }

}