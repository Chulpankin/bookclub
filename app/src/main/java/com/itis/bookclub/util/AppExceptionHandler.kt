package com.itis.bookclub.util

import retrofit2.HttpException

class AppExceptionHandler {

    fun handleException(ex: Throwable): Throwable {
        return when (ex) {
            is HttpException -> {
                when (ex.code()) {
                    400 -> AppException.BadRequest(
                        message = "Invalid request parameters",
                        cause = ex
                    )
                    401 -> AppException.Unauthorized(
                        message = "Authentication required",
                        cause = ex
                    )
                    404 -> AppException.NotFound(
                        message = "Requested resource not found",
                        cause = ex
                    )
                    500 -> AppException.InternalServerError(
                        message = "Server encountered an error",
                        cause = ex
                    )
                    else -> AppException.UnexpectedError(
                        message = "Unexpected server error (${ex.code()})",
                        cause = ex
                    )
                }
            }
            is NullPointerException -> AppException.EmptyResponse(
                message = "Received empty response from server",
                cause = ex
            )
            else -> AppException.UnexpectedError(
                message = "An unexpected error occurred: ${ex.message ?: "No details"}",
                cause = ex
            )
        }
    }
}