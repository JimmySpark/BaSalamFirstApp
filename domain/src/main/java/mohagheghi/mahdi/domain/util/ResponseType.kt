package mohagheghi.mahdi.domain.util

sealed class ResponseType<T>(
    val products: T? = null
) {
    class Success<T>(products: T) : ResponseType<T>(products)
    class EmptyList<T> : ResponseType<T>()
    sealed class Error<T> {
        class NetworkError<T> : ResponseType<T>()
        class ResponseError<T> : ResponseType<T>()
    }
}
