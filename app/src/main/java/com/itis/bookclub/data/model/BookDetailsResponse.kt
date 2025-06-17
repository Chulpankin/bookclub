import kotlinx.serialization.Serializable

@Serializable
data class BookDetailsResponse(
    val description: Description?,
    val title: String?,
    val covers: List<Long>,
    val key: String?,
)

@Serializable
data class Description(
    val value: String?
)