package dynamicdrillers.soundcast.network

import dynamicdrillers.soundcast.model.Songs

interface ApiWrapper {

    interface CallBack<in T> {
        fun onSuccess(t: T)
        fun onFailure(message: String)
    }

    fun getSongsList(callback: CallBack<Songs>)
}