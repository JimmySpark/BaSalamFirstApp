package mohagheghi.mahdi.basalamfirstapp.data.remote.response

data class ErrorResponseServer(
    val errors: List<Error>
) {
    data class Error(
        val messages: Messages
    ) {
        data class Messages(
            val message: List<String>
        )
    }
}