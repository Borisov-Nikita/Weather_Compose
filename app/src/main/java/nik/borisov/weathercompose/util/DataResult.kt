package nik.borisov.weathercompose.util

sealed class DataResult<T>(
    val data: T? = null,
    val message: String? = null
) {

    class Success<T>(data: T?) : DataResult<T>(data = data)

    class Failure<T>(message: String?) : DataResult<T>(message = message)
}
