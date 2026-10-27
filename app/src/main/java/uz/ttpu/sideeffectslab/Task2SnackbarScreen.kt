package uz.ttpu.sideeffectslab

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun Task2SnackbarScreen() {
    val snackbarHostState = remember { SnackbarHostState() }

    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Task 2: rememberCoroutineScope",
                style = MaterialTheme.typography.titleMedium
            )

            Button(
                onClick = {
                    scope.launch {
                        Log.d("Task2_Log", "Showing snackbar...")
                        val result = snackbarHostState.showSnackbar(
                            message = "Hello! This is a Snackbar.",
                            actionLabel = "Dismiss",
                            duration = SnackbarDuration.Short
                        )
                        Log.d("Task2_Log", "Snackbar dismissed with result: $result")
                    }
                }
            ) {
                Text("Show Snackbar")
            }

            Text(
                text = "Check steps:\n" +
                        "1. Tap 'Show Snackbar'.\n" +
                        "2. While visible, switch to Task 1 tab and come back.\n" +
                        "3. Tap rapidly twice to observe queue/replacement behavior.",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}