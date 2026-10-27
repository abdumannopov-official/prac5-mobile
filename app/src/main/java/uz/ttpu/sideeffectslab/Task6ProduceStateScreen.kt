package uz.ttpu.sideeffectslab

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

sealed interface ImageState {
    object Loading : ImageState
    data class Success(val url: String) : ImageState
    data class Error(val message: String) : ImageState
}

suspend fun loadFakeNetworkImage(url: String): ImageState {
    delay(500)
    return if (url.endsWith(".png")) {
        ImageState.Success("Successfully loaded image from: $url")
    } else {
        ImageState.Error("Failed to load: URL must end with .png")
    }
}

@Composable
fun Task6ProduceStateScreen() {
    var currentUrl by remember { mutableStateOf("https://example.com/avatar.png") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Task 6: produceState",
            style = MaterialTheme.typography.titleMedium
        )

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { currentUrl = "https://example.com/avatar.png" }) {
                Text("PNG URL (Success)")
            }
            Button(onClick = { currentUrl = "https://example.com/photo.jpg" }) {
                Text("JPG URL (Error)")
            }
        }

        Divider()

        ImageLoaderDisplay(url = currentUrl)
    }
}

@Composable
fun ImageLoaderDisplay(url: String) {
    val imageState by produceState<ImageState>(
        initialValue = ImageState.Loading,
        key1 = url
    ) {
        value = ImageState.Loading
        value = loadFakeNetworkImage(url)

        awaitDispose {
            Log.d("Task6_Log", "[DISPOSE] Producer cancelled for URL: $url")
        }
    }

    Card(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            when (val state = imageState) {
                is ImageState.Loading -> CircularProgressIndicator()
                is ImageState.Success -> Text(state.url, color = Color(0xFF2E7D32))
                is ImageState.Error -> Text(state.message, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}

