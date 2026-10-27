package uz.ttpu.sideeffectslab

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

@Composable
fun Task3LifecycleScreen() {
    var isObserverVisible by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Task 3: DisposableEffect & Lifecycle Observer",
            style = MaterialTheme.typography.titleMedium
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Show Observed Component:")
            Switch(
                checked = isObserverVisible,
                onCheckedChange = { isObserverVisible = it }
            )
        }

        Divider()

        if (isObserverVisible) {
            ObservedComponent()
        } else {
            Text(
                text = "Component is hidden (Removed from Composition)",
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}

@Composable
fun ObservedComponent(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START || event == Lifecycle.Event.ON_STOP) {
                Log.d("Task3_Log", "[EVENT] Lifecycle Event fired: $event")
            }
        }

        Log.d("Task3_Log", "[ADD] Adding LifecycleObserver")
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            Log.d("Task3_Log", "[REMOVE] Removing LifecycleObserver in onDispose")
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "I am actively observing Lifecycle events (ON_START / ON_STOP)",
            modifier = Modifier.padding(16.dp)
        )
    }
}