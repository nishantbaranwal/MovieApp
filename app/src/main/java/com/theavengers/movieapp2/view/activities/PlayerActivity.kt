package com.theavengers.movieapp2.view.activities

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import com.ltts.lttsplayer.LTTSPlayerView
import com.ltts.lttsplayer.configuration.PlayerConfig
import com.ltts.lttsplayer.events.Error
import com.ltts.lttsplayer.events.listeners.MediaControllerEvents
import com.ltts.lttsplayer.events.listeners.VideoPlayerEvents
import com.ltts.lttsplayer.playlists.PlaylistItem
import com.ltts.lttsplayer.ui.MediaPlayerControl
import com.theavengers.movieapp2.R
import kotlinx.android.synthetic.main.activity_player.*

class PlayerActivity : AppCompatActivity(),MediaControllerEvents, VideoPlayerEvents.OnPlayerEventListener, VideoPlayerEvents.OnPlayerSetupListener {
    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        if(newConfig?.orientation == Configuration.ORIENTATION_LANDSCAPE){
            val params = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT)
            lttsPlayerContainer.layoutParams = params
            isFullScreen = true
        }
        else{
            val  params = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,800)
            lttsPlayerContainer.layoutParams = params
            isFullScreen = false

        }

    }


    override fun onMediaControlsClick(p0: View?) {
        if(p0?.id == R.id.exo_fullscreen)
            requestedOrientation = if(!isFullScreen)
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            else ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onPlayerError(p0: Error?) {
    }

    override fun onPlayerEvent(p0: Int) {
    }

    override fun onPlayerReady(p0: LTTSPlayerView?) {
        this.videoPlayer = p0
    }

    override fun onPlayerSetupError(p0: Error?) {
    }

    override fun onAudioListReady() {
    }

    private lateinit var playList: ArrayList<PlaylistItem>
    private lateinit var mediaPlayerController:MediaPlayerControl
    private var isFullScreen = false
    private var videoPlayer:LTTSPlayerView?=null
    companion object {
        const val videoUrl:String = "https://html5demos.com/assets/dizzy.mp4"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        initialization()
        initVideoPlayer(playList)
    }

    private fun initialization(){
        playList = ArrayList()
        mediaPlayerController = MediaPlayerControl(this)
        val playListItem = PlaylistItem.Builder().setFile(videoUrl).build()
        playList.add(playListItem)
        mediaPlayerController.setFullscreenButtonListener {
            requestedOrientation = if(!isFullScreen)
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            else ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        }
    }
    private  fun initVideoPlayer( playList: ArrayList<PlaylistItem> ){
        val playerConfig = PlayerConfig.Builder().setPlaylistItems(playList).setAutoStart(true)
            .build()
        lttsPlayerContainer
        lttsPlayerContainer.addSetupListener(this)
        lttsPlayerContainer.addMediaControlEventListener(this)
        lttsPlayerContainer.setMediaPlayerControl(mediaPlayerController)
        lttsPlayerContainer.setup(playerConfig)
    }

    override fun onStop() {
        super.onStop()
        lttsPlayerContainer.stop()
    }

    override fun onPause() {
        super.onPause()
        lttsPlayerContainer.pause()
    }
}
