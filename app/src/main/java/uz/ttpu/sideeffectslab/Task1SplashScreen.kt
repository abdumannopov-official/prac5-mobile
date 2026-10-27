package uz.ttpu.sideeffectslab

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun Task1SplashScreen() {
    var version by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(500)
            version++
        }
    }

    val onTimeoutLambda = {
        Log.d("Task1_Log", "Callback fired with version: v$version")
    }

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(text = "Parent Version Counter: v$version", style = androidx.compose.material3.MaterialTheme.typography.headlineSmall)
        Text(text = "Wait 2 seconds after screen opens to see Logcat outputs.")

        Divider()

        Text(text = "Variant 1: Direct lambda capture (Stale)")
        BadLandingScreen(onTimeout = onTimeoutLambda)

        Divider()

        Text(text = "Variant 2: Using rememberUpdatedState (Fresh)")
        GoodLandingScreen(onTimeout = onTimeoutLambda)
    }
}

@Composable
fun BadLandingScreen(onTimeout: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(2000)
        Log.d("Task1_Log", "[BadVariant] Executing onTimeout:")
        onTimeout()
    }
    Text("Bad Screen active")
}

@Composable
fun GoodLandingScreen(onTimeout: () -> Unit) {
    val currentOnTimeout by rememberUpdatedState(onTimeout)

    LaunchedEffect(Unit) {
        delay(2000)
        Log.d("Task1_Log", "[GoodVariant] Executing currentOnTimeout:")
        currentOnTimeout()
    }
    Text("Good Screen active")
}