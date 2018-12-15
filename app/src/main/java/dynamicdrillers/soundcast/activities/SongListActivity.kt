package dynamicdrillers.soundcast.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import dynamicdrillers.soundcast.R
import dynamicdrillers.soundcast.model.Songs
import dynamicdrillers.soundcast.mvp.Contract

class SongListActivity : AppCompatActivity(),Contract.SongsActivityView {

    private lateinit var songsActivityPresenter: Contract.SongsActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_list)
        setPresenter(SongsActivityPresenter(this))
        songsActivityPresenter.getSongsList()
    }

    override fun onSuccessGettingSongsList(songs: Songs) {

    }

    override fun onErrorGettingSongsList(message: String) {
    }

    override fun setPresenter(presenter: Contract.SongsActivityPresenter) {
            this.songsActivityPresenter = presenter
    }
}
