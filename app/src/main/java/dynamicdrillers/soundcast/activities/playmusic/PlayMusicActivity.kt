package dynamicdrillers.soundcast.activities.playmusic

import android.database.Observable
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.SeekBar
import com.kaopiz.kprogresshud.KProgressHUD
import com.squareup.picasso.Picasso
import dynamicdrillers.soundcast.R
import dynamicdrillers.soundcast.model.Result
import kotlinx.android.synthetic.main.activity_play_music.*
import java.util.*
import java.util.concurrent.TimeUnit


class PlayMusicActivity : AppCompatActivity() {

    private lateinit var songsList: ArrayList<Result>
    private var currentPlayingIndex = 0
    private var isPlaying = false
    lateinit var mediaPlayer: MediaPlayer
    private lateinit var handler: Handler
    private var songFullDuration = 0
    lateinit var progressDialog : KProgressHUD
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_music)
        init()
        handleDataIntent()
        onCLicks()
        listeners()
        setContentToScreen(songsList[currentPlayingIndex])
    }

    private fun listeners() {
        mediaPlayer.setOnCompletionListener {
            stopMusic()
        }

        mediaPlayer.setOnPreparedListener {
            progressDialog.dismiss()
            seekbar.max = it.duration-1000
        }

        mediaPlayer.setOnBufferingUpdateListener { mp, percent ->
            seekbar.secondaryProgress = percent
        }

        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser)
                    mediaPlayer.seekTo(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })


        seekbar.setOnTouchListener { v, event ->
            if (mediaPlayer.isPlaying) {
                val playPositionInMilliseconds = songFullDuration / 100 * (v as SeekBar).progress
                mediaPlayer.seekTo(playPositionInMilliseconds)
            }

            return@setOnTouchListener false
        }

    }

    private fun init() {
        handler = Handler()
        mediaPlayer = MediaPlayer()
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        progressDialog = KProgressHUD(this)
        progressDialog.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setLabel("PLease Wait").
            setCancellable(false).setDetailsLabel("Loading Song...").setAnimationSpeed(2).setDimAmount(0.5f)
    }

    private fun onCLicks() {
        play_or_pause.setOnClickListener {
            if (isPlaying) stopMusic() else{
                prepareSong()
                playMusic()
            }
        }
        previous_play.setOnClickListener {
            if(currentPlayingIndex!=0){
                currentPlayingIndex -= 1
                mediaPlayer.reset()
                setContentToScreen(songsList[currentPlayingIndex])
                prepareSong()
                playMusic()
            }
        }

        next_play.setOnClickListener {
            if(currentPlayingIndex+1<songsList.size-1){
                currentPlayingIndex += 1
                mediaPlayer.reset()
                setContentToScreen(songsList[currentPlayingIndex])
                prepareSong()
                playMusic()
            }
        }

        back.setOnClickListener {
            mediaPlayer.stop()
            onBackPressed()
        }
    }

    private fun handleDataIntent() {
        songsList = intent.getParcelableArrayListExtra<Result>(SONGS)
        currentPlayingIndex = intent.getIntExtra(SELECTED_SONG, 0)
    }

    private fun playMusic() {
        play_or_pause.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_pause_circle_filled_white_24dp))
        isPlaying = true
        mediaPlayer.start()
        seekBarDurationUpdate()
    }

    private fun setContentToScreen(result: Result) {
        song_title.text = result.title
        Picasso.get().load(result.thumbnailFile.url).into(song_thumbnail)
        createdAT.text =
                getString(R.string.created_at).plus(result.createdAt.removeRange(9, result.createdAt.length - 1))
    }

    private fun stopMusic() {
        isPlaying = false
        mediaPlayer.pause()
        play_or_pause.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_play_circle_filled_white_24dp))
    }

    private fun seekBarDurationUpdate() {
        seekbar.progress = mediaPlayer.currentPosition
        if (mediaPlayer.isPlaying) {
            val notification = Runnable {
                seekBarDurationUpdate()
            }
            handler.postDelayed(notification, 1000)
        }
    }

    private fun prepareSong() {
        try {
            mediaPlayer.setDataSource(songsList[currentPlayingIndex].musicFile.url)
            mediaPlayer.prepare()
        } catch (e: Exception) {
        }
        songFullDuration = mediaPlayer.duration
    }

    override fun onBackPressed() {
        mediaPlayer.stop()
        super.onBackPressed()
    }

    companion object {
        var SELECTED_SONG = "selected_song_index"
        var SONGS = "songs"
    }
}
