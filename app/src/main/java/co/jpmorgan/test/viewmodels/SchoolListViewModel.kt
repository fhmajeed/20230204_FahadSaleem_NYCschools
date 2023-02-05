package co.jpmorgan.test.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.jpmorgan.test.models.School
import co.jpmorgan.test.repositories.SchoolApi
import co.jpmorgan.test.repositories.SchoolRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import co.jpmorgan.test.utils.Result

@HiltViewModel
class SchoolListViewModel @Inject constructor(private val schoolRepository: SchoolRepository) : ViewModel() {

    private val _listOfSchoolLiveData = MutableLiveData<List<School>?>()
    val listOfSchoolLiveData = _listOfSchoolLiveData

    private val _showProgress = MutableLiveData<Boolean>()
    val showProgress = _showProgress

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage = _errorMessage

    init {
        getSchoolList()
    }

    fun getSchoolList() {
        viewModelScope.launch {
            _showProgress.postValue(true)
            when (val result = schoolRepository.fetchSchoolList()) {
                is Result.Success -> {
                    _listOfSchoolLiveData.postValue(result.data)
                }
                is Result.Error -> {
                    _errorMessage.postValue(result.exception.toString())
                }
            }
            _showProgress.postValue(false)
        }
    }
}