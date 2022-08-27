package com.drdlx.youtubeclienttryout.videoScreen

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


@Composable
fun VideoScreen(
    videoId: String?,
) {
    Column() {
        ComposableLifecycle(videoId = videoId)
    }

}

@Composable
fun ComposableLifecycle(
    videoId: String?,
    lifeCycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
) {
    val context = LocalContext.current
    val player = YouTubePlayerView(context)
    Column {
        AndroidView(factory = {
            player
        })
    }

    DisposableEffect(lifeCycleOwner) {
        lifeCycleOwner.lifecycle.addObserver(player)
        player.also {
            it.addYouTubePlayerListener( object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    super.onReady(youTubePlayer)
                    if (videoId != null) {
                        youTubePlayer.loadVideo(videoId, 0f)
                    }
                }
            }
            )
        }
        onDispose {
            lifeCycleOwner.lifecycle.removeObserver(player)
        }
    }
}
