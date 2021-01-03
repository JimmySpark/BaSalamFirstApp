package mohagheghi.mahdi.basalamfirstapp.data.model

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    val errors: List<Error>
) {
    data class Error(
        val messages: Messages
    ) {
        data class Messages(
            @SerializedName("size") val message: List<String>
        )
    }
}