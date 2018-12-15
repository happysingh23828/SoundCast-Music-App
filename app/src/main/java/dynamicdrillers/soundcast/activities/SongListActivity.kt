package dynamicdrillers.soundcast.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import dynamicdrillers.soundcast.R
import dynamicdrillers.soundcast.adapters.SongsListAdapter
import dynamicdrillers.soundcast.model.Songs
import dynamicdrillers.soundcast.mvp.Contract
import kotlinx.android.synthetic.main.activity_song_list.*

class SongListActivity : AppCompatActivity(),Contract.SongsActivityView {

    private lateinit var songsActivityPresenter: Contract.SongsActivityPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_list)
        setPresenter(SongsActivityPresenter(this))
        init()
        onClicks()
        songsActivityPresenter.getSongsList()
    }

    private fun onClicks() {
        add_music.setOnClickListener {

        }
    }

    private fun init() {
        songs_list_recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onSuccessGettingSongsList(songs: Songs) {
        songs_list_recyclerView.adapter = SongsListAdapter(songs.results,this)
    }

    override fun onErrorGettingSongsList(message: String) {
    }

    override fun setPresenter(presenter: Contract.SongsActivityPresenter) {
            this.songsActivityPresenter = presenter
    }
}
