package mohagheghi.mahdi.basalamfirstapp.view.util

import mohagheghi.mahdi.basalamfirstapp.data.repository.ResponseType

interface ResponseState {
    fun onResponse(response: ResponseType)
}