package co.jpmorgan.test.repositories

import co.jpmorgan.test.models.School
import co.jpmorgan.test.models.SchoolDetail
import co.jpmorgan.test.utils.Result
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class SchoolRepositoryImpTest {

    private lateinit var schoolApi: SchoolApi
    private lateinit var schoolRepositoryImp: SchoolRepositoryImp

    @BeforeEach
    fun setUp() {
        schoolApi = mockk(relaxed = true)
        schoolRepositoryImp = SchoolRepositoryImp(schoolApi)
    }

    @Test
    fun `fetch school list with success response`() = runBlocking {
        val list = arrayListOf(buildSchool())

        coEvery { schoolApi.getSchoolList() } returns list

        val result = schoolRepositoryImp.fetchSchoolList()

        coVerify(exactly = 1) { schoolApi.getSchoolList() }

        Assertions.assertSame(list, (result as Result.Success).data)
    }

    @Test
    fun `fetch school list returns error`() = runBlocking {
        coEvery { schoolApi.getSchoolList() } throws Exception()

        val result = schoolRepositoryImp.fetchSchoolList()

        coVerify(exactly = 1) { schoolApi.getSchoolList() }

        Assertions.assertSame("Network Error", (result as Result.Error).exception.message)
    }

    @Test
    fun `fetch school detail success response`() = runBlocking {
        val list = arrayListOf(buildSchoolDetail())

        coEvery { schoolApi.getSchoolDetail("1") } returns list

        val result = schoolRepositoryImp.fetchSchoolDetail("1")

        coVerify(exactly = 1) { schoolApi.getSchoolDetail("1") }

        Assertions.assertSame(list[0], (result as Result.Success).data)
    }

    @Test
    fun `fetch school detail returns error`() = runBlocking {
        coEvery { schoolApi.getSchoolDetail("1") } throws Exception()

        val result = schoolRepositoryImp.fetchSchoolDetail("1")

        coVerify(exactly = 1) { schoolApi.getSchoolDetail("1") }

        Assertions.assertSame("Network Error", (result as Result.Error).exception.message)
    }

    companion object {
        fun buildSchool() = School(
            dbn = "1",
            schoolName = "ISS",
            schoolEmail = "fahad.saleem@ISS.com",
            website = "www.ISS.com",
            phoneNumber = "0990078601"
        )

        fun buildSchoolDetail() = SchoolDetail(
            dbn = "1",
            schoolName = "ISS",
            readingSATScore = "10",
            mathSATScore = "100",
            writingSATScore = "1000"
        )
    }
}