package com.kivous.quotesapp

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.kivous.quotesapp.screens.QuoteDetails
import com.kivous.quotesapp.screens.QuoteListScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        CoroutineScope(Dispatchers.IO).launch {
            DataManager.loadAssetFromFile(applicationContext)
        }
        setContent {
            App()
        }
    }

}

@Composable
fun App() {
    if (DataManager.currentPage.value == Pages.LISTING) {
        if (DataManager.isDataLoad.value) {
            QuoteListScreen(data = DataManager.data) {
                DataManager.switchPages(it)

            }
        }
    } else {
        DataManager.currentQuote?.let { QuoteDetails(quote = it) }
    }

}

enum class Pages {
    LISTING,
    DETAILS
}
