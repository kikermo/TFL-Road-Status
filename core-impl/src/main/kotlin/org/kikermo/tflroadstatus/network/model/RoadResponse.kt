package org.kikermo.tflroadstatus.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RoadResponse(
    @SerialName("id") val id: String?,
    @SerialName("displayName") val displayName: String?,
    @SerialName("statusSeverity") val statusSeverity: String?,
    @SerialName("statusSeverityDescription") val statusSeverityDescription: String?,
    @SerialName("bounds") val bounds: String?,
    @SerialName("envelope") val envelope: String?,
    @SerialName("url") val url: String?,
)
