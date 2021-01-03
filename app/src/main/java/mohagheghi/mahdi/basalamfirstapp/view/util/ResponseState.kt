package mohagheghi.mahdi.basalamfirstapp.view.util

import mohagheghi.mahdi.basalamfirstapp.view.repository.ResponseType

interface ResponseState {
    fun onResponse(response: ResponseType)
}