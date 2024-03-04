package io.nerdythings.dummy.app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

class MainActivity : ComponentActivity() {

    // The first rookie mistake. Never-ever create a ViewModel directly.
    // https://developer.android.com/topic/libraries/architecture/viewmodel/viewmodel-factories
    /**
     * 18 : ViewModel should not be instantiated directly in the activity .
     * ViewModel should be retrieved via ViewModel Provider to correctly handle
     * lifecycle states and to share data between different components
     * such as activities and fragments .
     */
    private val viewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         * 24 : Could potentially crash due to Network On MainThread Exception .
         * The :: load State method usually loads data from network or database which
         * cannot be performed on Main Thread , so it should be executed within a coroutine scope .
         */
        // Passing Activity into ViewModel, breaking more rules
        viewModel.prepareText(this)
        /**
         * 32 : Activity context being passed to the ViewModel
         * in the prepare Text (this ) method .
         * It might cause memory leaks if not handled properly .
         * ViewModel should never have a reference to an activity ,
         * use application context instead .
         */
        setContent {
            // Ideally, it should be collectAsStateWithLifecycle
            val state by viewModel.uiState.collectAsState()
            // MaterialTheme is not used here, neither in MainScreen
            Surface(
                modifier = Modifier.fillMaxSize(),
                // Color could be part of resources / theme
                color = Color(0xFFFFFFFF)
            ) {
                MainScreen(state, viewModel::loadState, ::openNerdyThings)
            }
        }
    }

    private fun openNerdyThings() {
        val url = "https://www.youtube.com/@Nerdy.Things"
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setData(Uri.parse(url))
        startActivity(intent)
    }
}
