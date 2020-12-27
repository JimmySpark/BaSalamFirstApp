package mohagheghi.mahdi.basalamfirstapp.helper

interface NetworkResponseCallback {
    fun onNetworkRequestSuccess()
    fun onNetworkRequestFailure(t: Throwable)
}