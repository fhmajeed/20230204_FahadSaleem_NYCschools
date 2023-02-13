package co.jpmorgan.test.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.jpmorgan.test.models.School
import co.jpmorgan.test.models.SchoolDetail
import co.jpmorgan.test.repositories.SchoolRepository
import co.jpmorgan.test.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SchoolViewModel @Inject constructor(private val schoolRepository: SchoolRepository) :
    ViewModel() {

    private val _schoolListState = MutableStateFlow(SchoolState<List<School>>())
    val schoolState = _schoolListState.asStateFlow()

    private val _schoolDetailSate = MutableStateFlow(SchoolState<SchoolDetail>())
    val schoolDetailSate = _schoolDetailSate.asStateFlow()


    init {
        getSchoolList()
    }

    fun getSchoolList() {
        _schoolListState.update { currentValue ->
            currentValue.copy(isLoading = true)
        }
        viewModelScope.launch {
            when (val result = schoolRepository.fetchSchoolList()) {
                is Result.Success -> {
                    _schoolListState.update { currentValue ->
                        currentValue.copy(
                            data = result.data,
                            isLoading = false
                        )
                    }
                }
                is Result.Error -> {
                    _schoolListState.update { currentValue ->
                        currentValue.copy(
                            error = result.exception.message.toString(),
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    fun getSchoolDetail(dbn: String) {
        _schoolDetailSate.update { currentValue ->
            currentValue.copy(isLoading = true)
        }
        viewModelScope.launch {
            when (val result = schoolRepository.fetchSchoolDetail(dbn = dbn)) {
                is Result.Success -> {
                    _schoolDetailSate.update { currentValue ->
                        currentValue.copy(
                            data = result.data,
                            isLoading = false
                        )
                    }
                }
                is Result.Error -> {
                    _schoolDetailSate.update { currentValue ->
                        currentValue.copy(
                            error = result.exception.message.toString(),
                            isLoading = false
                        )
                    }
                }
            }
        }
    }
}

data class SchoolState<out T : Any>(
    val data: T? = null,
    val isLoading: Boolean = false,
    val error: String = ""
)
