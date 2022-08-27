package com.drdlx.youtubeclienttryout

import android.os.Bundle
import android.widget.FrameLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.compiler.plugins.kotlin.EmptyFunctionMetrics.composable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.drdlx.youtubeclienttryout.mainScreen.view.MainScreenView
import com.drdlx.youtubeclienttryout.mainScreen.viewmodel.MainScreenViewModel
import com.drdlx.youtubeclienttryout.ui.theme.YoutubeClientTryoutTheme
import com.drdlx.youtubeclienttryout.videoScreen.VideoScreen
import androidx.fragment.app.FragmentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val navController = rememberNavController()

            YoutubeClientTryoutTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(navController = navController, startDestination = "mainScreen") {
                        composable("mainScreen") {
                            val mainScreenViewModel = MainScreenViewModel()
                            MainScreenView(
                                uiState = mainScreenViewModel.uiState,
                                changeSearchQueryValue = mainScreenViewModel::changeSearchQueryValue,
                                startSearching = mainScreenViewModel::search,
                                openVideo = { videoId -> navController.navigate("video/${videoId}") }
                            )
                        }
                        composable(
                            "video/{videoId}",
                            arguments = listOf(navArgument("videoId") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val videoId = backStackEntry.arguments?.getString("videoId")
                            VideoScreen(
                                videoId = videoId,
                            )
                        }
                    }
                }
            }
        }
    }

}
