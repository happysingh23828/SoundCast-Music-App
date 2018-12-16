package dynamicdrillers.soundcast.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.kaopiz.kprogresshud.KProgressHUD
import dynamicdrillers.soundcast.R
import dynamicdrillers.soundcast.activities.addmusic.AddMusicActivity
import dynamicdrillers.soundcast.adapters.SongsListAdapter
import dynamicdrillers.soundcast.model.Songs
import dynamicdrillers.soundcast.mvp.Contract
import kotlinx.android.synthetic.main.activity_song_list.*

class SongListActivity : AppCompatActivity(),Contract.SongsActivityView {

    private lateinit var songsActivityPresenter: Contract.SongsActivityPresenter
    lateinit var progressDialog : KProgressHUD


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_list)
        setPresenter(SongsActivityPresenter(this))
        init()
        onClicks()
        progressDialog.show()
        songsActivityPresenter.getSongsList()
    }

    private fun onClicks() {
        add_music.setOnClickListener {
            startActivity(Intent(this,AddMusicActivity::class.java))
        }
    }

    private fun init() {
        songs_list_recyclerView.layoutManager = LinearLayoutManager(this)
        progressDialog = KProgressHUD(this)
        progressDialog.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setLabel("Songs List").
            setCancellable(false).setDetailsLabel("Loading...").setAnimationSpeed(2).setDimAmount(0.5f)
    }

    override fun onSuccessGettingSongsList(songs: Songs) {
        songs_list_recyclerView.adapter = SongsListAdapter(songs.results,this)
        progressDialog.dismiss()
    }

    override fun onErrorGettingSongsList(message: String) {
        progressDialog.dismiss()
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }

    override fun setPresenter(presenter: Contract.SongsActivityPresenter) {
            this.songsActivityPresenter = presenter
    }

    override fun onResume() {
        super.onResume()
        progressDialog.show()
        songsActivityPresenter.getSongsList()
    }
}
