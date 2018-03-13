package com.youtuberapp.irakli.youtuberapp

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import com.youtuberapp.irakli.youtuberapp.R.id.container
import okhttp3.*
import java.io.IOException

/**
 * Created by root on 3/13/18.
 */


class videosFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.home_fragment,container,false)
    }

    @Override
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
               fetchJson("https://www.googleapis.com/youtube/v3/search?order=date&part=snippet&type=videos&channelId=UCtz3sMM1qPIm1WrbhaJexaA&maxResults=50&key=AIzaSyDyIXJHb0clHIW60pG8OR_G8XRuaUtVqHk")

    }

    lateinit var myitems: MyAdapter.items
    var sec = 0
    fun fetchJson(url:String) {
        println("Attempting to Fetch JSON")


        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()

                val gson = GsonBuilder().create()
                var myitems = gson.fromJson(body, MyAdapter.items::class.java)


                activity.runOnUiThread(java.lang.Runnable {
                    var recyclerView  = view!!.findViewById<RecyclerView>(R.id.videosrecycler)

                    println("new items aded ")

                    var viewManager = LinearLayoutManager(view!!.context)
                    var  layoutManager = viewManager


                    // viewManager.scrollToPositionWithOffset(myitems.items.size - 49, 0)

                    recyclerView.layoutManager = layoutManager


                    recyclerView.adapter = MyAdapter(myitems)
                    recyclerView!!.adapter.notifyDataSetChanged()






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
                                        sec++
                                        recyclerView!!.adapter.notifyDataSetChanged()

                                        var next:String = MyAdapter(myitems).nextPage()






                                        fetchJson("https://www.googleapis.com/youtube/v3/search?order=date&pageToken=$next&part=snippet&type=videos&channelId=UCtz3sMM1qPIm1WrbhaJexaA&maxResults=50&key=AIzaSyDyIXJHb0clHIW60pG8OR_G8XRuaUtVqHk")



                                    }
                                }
                            }
                        }
                    })


                })
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



        class items(var nextPageToken:String, var items: List<Video>)

        class Video(var id:Id,val snippet:Snippet)



        class Id(val videoId:String)
        class Snippet(val title: String,val thumbnails:Thumbnails)

        class Thumbnails(val default:def)

        class def(val url:String)





    }


}