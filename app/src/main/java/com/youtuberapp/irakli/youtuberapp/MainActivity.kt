/*




class MainActivity : AppCompatActivity()  {
    var nextPageToken: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        )


        var tabLayout = findViewById<TableLayout>(R.id.tabLayout)
        var viewPager = findViewById<ViewPager>(R.id.pager)

        var adapter = ViewPager(this)


       // viewPager.adapter = adapter


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

        lateinit var myitems:items
        var sec = 0







}





*//*
*/

