package co.jpmorgan.test.repositories

import co.jpmorgan.test.models.School
import javax.inject.Inject
import co.jpmorgan.test.utils.Result

class SchoolRepositoryImp @Inject constructor(private val schoolApi: SchoolApi) : SchoolRepository{

    override suspend fun fetchSchoolList(): Result<List<School>> {
        return try {
            val list : List<School> = schoolApi.getSchoolList()
            Result.Success(list)
        }catch (e:Exception){
            Result.Error(Exception("Error Processing Request =$e"))
        }
    }

}