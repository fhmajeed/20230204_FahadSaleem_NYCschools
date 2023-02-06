package co.jpmorgan.test.utils
/**
 * Generic sealed classes for generic response handling at view
 */
sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}