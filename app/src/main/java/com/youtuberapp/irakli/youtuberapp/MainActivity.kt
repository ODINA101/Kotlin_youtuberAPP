package com.youtuberapp.irakli.youtuberapp
import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.android.youtube.player.*

import com.youtuberapp.irakli.youtuberapp.R
import com.google.android.youtube.player.YouTubePlayerFragment




class MainActivity : YouTubeBaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
    }
}