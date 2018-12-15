package dynamicdrillers.soundcast.mvp

import dynamicdrillers.soundcast.model.Songs

class Contract {

    interface SongsActivityView : BaseView<SongsActivityPresenter>{
        fun onSuccessGettingSongsList(songs: Songs)
        fun onErrorGettingSongsList(message : String)
    }

    interface SongsActivityPresenter {
        fun getSongsList()
    }
}