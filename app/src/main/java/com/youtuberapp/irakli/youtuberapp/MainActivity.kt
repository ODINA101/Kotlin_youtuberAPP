package com.youtuberapp.irakli.youtuberapp
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.google.gson.GsonBuilder
import com.youtuberapp.irakli.youtuberapp.R.attr.layoutManager
import okhttp3.*
import java.io.IOException


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

/*

        val youTubePlayerView = fragmentManager
                .findFragmentById(R.id.videp) as YouTubePlayerFragment


       val onInitializedListener = object : YouTubePlayer.OnInitializedListener {

            override fun onInitializationSuccess(provider: YouTubePlayer.Provider, youTubePlayer: YouTubePlayer, b: Boolean) {

                youTubePlayer.loadVideo("Hce74cEAAaE")

                youTubePlayer.play()
            }

            override fun onInitializationFailure(provider: YouTubePlayer.Provider, youTubeInitializationResult: YouTubeInitializationResult) {

            }
        }
  youTubePlayerView.initialize("AIzaSyC4SmRsNPyo1k0tJx6pkWvflEBusgWRFV4", onInitializedListener)
*/



        fetchJson()



    }


    fun fetchJson() {
        println("Attempting to Fetch JSON")

        val url = "https://www.googleapis.com/youtube/v3/search?order=date&part=snippet&type=videos&channelId=UCtz3sMM1qPIm1WrbhaJexaA&maxResults=5&key=AIzaSyDyIXJHb0clHIW60pG8OR_G8XRuaUtVqHk"

        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()
                println(body)

                val gson = GsonBuilder().create()

                val homeFeed = gson.fromJson(body,items::class.java)

                runOnUiThread {
                    var recyclerView  = findViewById<RecyclerView>(R.id.videosrecycler)

                    recyclerView.adapter = MyAdapter(homeFeed)
                    var viewManager = LinearLayoutManager(this@MainActivity)

                         // use a linear layout manager
                        var  layoutManager = viewManager
recyclerView.layoutManager = layoutManager
                         // specify an viewAdapter (see also next example)
                }
            }

            override fun onFailure(call: Call?, e: IOException?) {
                println("Failed to execute request")
            }
        })
    }


    class MyAdapter(private val myDataset: items) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var cardView = itemView.findViewById<CardView>(R.id.mCardview)
            var videotitle = itemView.findViewById<TextView>(R.id.mVideoTitle)!!
            var thumb = itemView.findViewById<ImageView>(R.id.imageView)
}
        
        
        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyAdapter.ViewHolder? {
            // create a new view
            val singleitem = LayoutInflater.from(parent?.context).inflate(R.layout.single_item, parent, false)
            return MyAdapter.ViewHolder(singleitem)
        }

        override fun getItemCount(): Int {
          return myDataset.items.size
        }

        override fun onBindViewHolder(holder: MyAdapter.ViewHolder?, position: Int) {
            //holder!!.videotitle.text = myDataset.videos[position].name
            val video = myDataset.items.get(position)
            holder!!.videotitle.text = video.snippet.title
            

        }






    }




}

class items(val items: List<Video>)

class Video(var id:Id,val snippet:Snippet)



class Id(val videoId:String)
class Snippet(val title: String,thumbnails:thumbnails)

 class thumbnails(val default:def)

class def(val url:String)







