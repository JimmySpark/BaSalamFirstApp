package mohagheghi.mahdi.basalamfirstapp.view.util

import mohagheghi.mahdi.basalamfirstapp.data.util.ResponseType

interface ResponseState {
    fun onResponse(response: ResponseType)
}