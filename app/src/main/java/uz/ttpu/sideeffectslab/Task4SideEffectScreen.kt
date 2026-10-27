package uz.ttpu.sideeffectslab

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

object Analytics {
    var userType: String = "Guest"
}

@Composable
fun Task4SideEffectScreen() {
    var userType by remember { mutableStateOf("Guest") }
    var sideEffectCount by remember { mutableIntStateOf(0) }
    var dummyStateForRecomposition by remember { mutableIntStateOf(0) }

    SideEffect {
        Analytics.userType = userType
        sideEffectCount++
        Log.d(
            "Task4_Log",
            "[SideEffect #$sideEffectCount] Updated Analytics.userType = '${Analytics.userType}'"
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Task 4: SideEffect & Analytics",
            style = MaterialTheme.typography.titleMedium
        )

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Analytics.userType: ${Analytics.userType}")
                Text("SideEffect executed count: $sideEffectCount")
            }
        }

        Button(
            onClick = {
                userType = if (userType == "Guest") "Admin" else "Guest"
            }
        ) {
            Text("Toggle User Type ($userType)")
        }

        Button(
            onClick = {
                dummyStateForRecomposition++
            }
        ) {
            Text("Trigger Recomposition Without Changing userType ($dummyStateForRecomposition)")
        }

        Text(
            text = "Observe:\n" +
                    "SideEffect runs after EVERY successful recomposition, even if userType didn't change, " +
                    "ensuring the external library always gets the committed value.",
            style = MaterialTheme.typography.bodySmall
        )
    }
}