package mohagheghi.mahdi.basalamfirstapp.util

interface ResponseState {
    fun onShowLoading(isLoading: Boolean)
    fun onHideLoading()
    fun onError(errorCode: Int, message: String)
    fun onFailure(t: Throwable)
    fun onEmptyList(isEmpty: Boolean)
}