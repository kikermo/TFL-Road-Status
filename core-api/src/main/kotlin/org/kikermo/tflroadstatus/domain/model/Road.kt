package org.kikermo.tflroadstatus.domain.model

data class Road(
    val id: String,
    val displayName: String,
    val severityStatus: String,
    val severityStatusDescription: String?,
)
