package org.kikermo.tflroadstatus.network.mapper

import okio.IOException
import org.kikermo.tflroadstatus.domain.model.StatusError
import retrofit2.HttpException

internal fun Throwable.toStatusError(): StatusError {
    return when (this) {
        is IOException -> StatusError.NoInternetConnectionError
        is HttpException -> this.toErrorStatus()
        else -> StatusError.UnexpectedError
    }
}

private fun HttpException.toErrorStatus(): StatusError {
    return when (this.code()) {
        404 -> StatusError.ResourceUnavailableError(this.message())
        else -> StatusError.UnexpectedError
    }
}
