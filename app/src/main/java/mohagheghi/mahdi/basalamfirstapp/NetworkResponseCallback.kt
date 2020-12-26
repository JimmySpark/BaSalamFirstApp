package mohagheghi.mahdi.basalamfirstapp

interface NetworkResponseCallback {
    fun onNetworkRequestSuccess()
    fun onNetworkRequestFailure(t: Throwable)
}