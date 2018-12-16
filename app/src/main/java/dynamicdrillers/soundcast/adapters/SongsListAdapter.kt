package dynamicdrillers.soundcast.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import dynamicdrillers.soundcast.R
import dynamicdrillers.soundcast.activities.playmusic.PlayMusicActivity
import dynamicdrillers.soundcast.activities.playmusic.PlayMusicActivity.Companion.SELECTED_SONG
import dynamicdrillers.soundcast.activities.playmusic.PlayMusicActivity.Companion.SONGS
import dynamicdrillers.soundcast.model.Result
import kotlinx.android.synthetic.main.adapter_song_list_item.view.*

class SongsListAdapter(private var songsList : ArrayList<Result>, var context : Context) : RecyclerView.Adapter<SongsListAdapter.SongsListViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SongsListViewHolder {
        return SongsListViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.adapter_song_list_item,p0,false))
    }

    override fun getItemCount(): Int {
        return songsList.size
    }

    override fun onBindViewHolder(viewHolder: SongsListViewHolder, position: Int) {
        viewHolder.bind(songsList[position],position)
    }


    inner class  SongsListViewHolder(var view : View): RecyclerView.ViewHolder(view) {
        fun bind(result : Result,position: Int){
            view.title.text = result.title
            view.createdAt.text = context.getString(R.string.created_at).plus(result.createdAt.removeRange(9,result.createdAt.length-1))
            if(result.thumbnailFile.url!="")
            Picasso.get().load(result.thumbnailFile.url).into(view.thumbnail)
            onClicks(position)
        }

        private fun onClicks(position: Int) {
           view.setOnClickListener {
               val intent = Intent(context,PlayMusicActivity::class.java)
               intent.putParcelableArrayListExtra(SONGS, songsList)
               intent.putExtra(SELECTED_SONG,position)
               context.startActivity(intent)
           }
        }
    }
}