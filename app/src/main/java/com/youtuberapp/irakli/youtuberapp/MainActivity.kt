package com.youtuberapp.irakli.youtuberapp
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import okhttp3.*
import java.io.IOException


class MainActivity : AppCompatActivity() {
    var nextPageToken: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var recyclerView  = findViewById<RecyclerView>(R.id.videosrecycler)
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



        fetchJson("https://www.googleapis.com/youtube/v3/search?order=date&part=snippet&type=videos&channelId=UCtz3sMM1qPIm1WrbhaJexaA&maxResults=10&key=AIzaSyDyIXJHb0clHIW60pG8OR_G8XRuaUtVqHk",null,false)


    }

        lateinit var myitems:items


    fun fetchJson(url:String, lastItems: items?, add: Boolean) {
        println("Attempting to Fetch JSON")


        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()

                val gson = GsonBuilder().create()
                var homeFeed = gson.fromJson(body, items::class.java)



/* if(add) {
     if (lastItems != null) {
         lastItems.items = lastItems!!.items.plus(gson.fromJson(body, items::class.java).items)
         lastItems!!.nextPageToken = gson.fromJson(body, items::class.java).nextPageToken
         println("new elements aded")

     }
     val homeFeed = lastItems
 }*/
                var viewManager = LinearLayoutManager(this@MainActivity)
                var  layoutManager = viewManager
                runOnUiThread {
                    var recyclerView  = findViewById<RecyclerView>(R.id.videosrecycler)
              if(lastItems != null) {
             println("new items aded ")
                  myitems.items = myitems.items.plus(lastItems.items)
                  println(myitems.items.size)

                  viewManager.scrollToPositionWithOffset(myitems.items.size - 13, 0)

                   }
                    recyclerView.layoutManager = layoutManager

                   if(add) {
                       myitems.nextPageToken = lastItems!!.nextPageToken

                   }
                    if(!add) {
                        myitems = homeFeed
                        recyclerView.adapter = MyAdapter(myitems)

                    }


                    recyclerView.adapter.notifyDataSetChanged()
                   // recyclerView.adapter.notifyDataSetChanged()

                    var loading = true
                    recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                            super.onScrolled(recyclerView, dx, dy)
                            var aa = viewManager.findFirstVisibleItemPosition()
                            if(dy > 0) //check for scroll down
                            {
                                var visibleItemCount = viewManager.childCount
                                var totalItemCount = viewManager.itemCount
                                var pastVisiblesItems = viewManager.findFirstVisibleItemPosition()

                                if (loading)
                                {

                                    if ( (visibleItemCount + pastVisiblesItems) == totalItemCount)
                                    {
                                        loading = false
                                        println("Last Item Wow !")
                                        //Do pagination.. i.e. fetch new data
                                                var next = MyAdapter(homeFeed).nextPage()


                                            fetchJson("https://www.googleapis.com/youtube/v3/search?order=date&pageToken=$next&part=snippet&type=videos&channelId=UCtz3sMM1qPIm1WrbhaJexaA&maxResults=10&key=AIzaSyDyIXJHb0clHIW60pG8OR_G8XRuaUtVqHk", homeFeed, true)



                                    }
                                }
                            }
                        }
                    })


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

        fun nextPage(): String {
            return myDataset.nextPageToken
        }

        fun lastItems():items {
            return myDataset
        }


        fun addItems(nItems:items) {
            myDataset.items.plus(nItems)
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
            val video = myDataset.items.get(position)
            holder!!.videotitle.text = video.snippet.title
            Picasso.get().load(video.snippet.thumbnails.default.url).into(holder.thumb)

        }






    }



}

class items(var nextPageToken:String, var items: List<Video>)

class Video(var id:Id,val snippet:Snippet)



class Id(val videoId:String)
class Snippet(val title: String,val thumbnails:Thumbnails)

class Thumbnails(val default:def)

class def(val url:String)









