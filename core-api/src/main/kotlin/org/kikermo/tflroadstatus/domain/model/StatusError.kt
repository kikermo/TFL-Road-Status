package org.kikermo.tflroadstatus.domain.model

sealed class StatusError : Exception() {
    object UnexpectedError : StatusError()
    data class ResourceUnavailableError(
        val nonAvailableMessage: String?,
    ) : StatusError()

    object NoInternetConnectionError : StatusError()
}
