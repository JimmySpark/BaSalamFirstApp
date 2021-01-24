package mohagheghi.mahdi.basalamfirstapp.util

sealed class UiState<T>(
    val products: T? = null,
    val errorMessage: String? = null
) {
    class Success<T>(products: T) : UiState<T>(products = products)
    class EmptyList<T> : UiState<T>()
    class Error<T>(errorMessage: String) : UiState<T>(errorMessage = errorMessage)
}