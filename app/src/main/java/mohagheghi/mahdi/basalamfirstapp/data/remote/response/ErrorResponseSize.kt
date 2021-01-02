package mohagheghi.mahdi.basalamfirstapp.data.remote.response

data class ErrorResponseSize(
    val errors: List<Error>
) {
    data class Error(
        val messages: Messages
    ) {
        data class Messages(
            val size: List<String>
        )
    }
}