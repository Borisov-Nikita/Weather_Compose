package nik.borisov.weathercompose.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import nik.borisov.weathercompose.util.DataResult
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

interface NetworkResponse {

    suspend fun <T, E> safeNetworkCall(
        call: suspend () -> Response<T>,
        mapper: (T) -> E
    ): DataResult<E> {
        return withContext(Dispatchers.IO) {
            try {
                val response = call()
                if (response.isSuccessful) {
                    val body = response.body()
                    body?.let {
                        DataResult.Success(data = mapper(body))
                    } ?: getFailureResult("Response body is empty")
                } else {
                    getFailureResult("${response.code()}: ${response.message()}")
                }
            } catch (e: HttpException) {
                getFailureResult(e.message ?: "Something went wrong")
            } catch (e: IOException) {
                getFailureResult(e.message ?: "Check your network connection")
            } catch (e: Exception) {
                getFailureResult(e.message ?: "Something went wrong")
            }
        }
    }

    private fun handleResponseErrorBody(errorBody: ResponseBody) {
        //TODO
    }

    private fun <T> getFailureResult(errorMessage: String): DataResult.Failure<T> {
        return DataResult.Failure(message = errorMessage)
    }
}