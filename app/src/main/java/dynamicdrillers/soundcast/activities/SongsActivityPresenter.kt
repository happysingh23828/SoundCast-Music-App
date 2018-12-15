package dynamicdrillers.soundcast.activities

import dynamicdrillers.soundcast.model.Songs
import dynamicdrillers.soundcast.mvp.Contract
import dynamicdrillers.soundcast.network.ApiCaller
import dynamicdrillers.soundcast.network.ApiWrapper

class SongsActivityPresenter(private val view : Contract.SongsActivityView) : Contract.SongsActivityPresenter {

    var apiCaller = ApiCaller()
    override fun getSongsList() {
        apiCaller.getSongsList(object : ApiWrapper.CallBack<Songs>{
            override fun onSuccess(t: Songs) {
                view.onSuccessGettingSongsList(t)
            }

            override fun onFailure(message: String) {
                view.onErrorGettingSongsList(message)
            }
        })
    }
}