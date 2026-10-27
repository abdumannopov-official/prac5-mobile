package uz.ttpu.sideeffectslab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                MainScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Task 1", "Task 2", "Task 3", "Task 4", "Task 5", "Task 6")

    Scaffold(
        topBar = {
            ScrollableTabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title) }
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues).fillMaxSize().padding(16.dp)) {
            when (selectedTab) {
                0 -> Task1SplashScreen()
                1 -> Task2SnackbarScreen()
                2 -> Task3LifecycleScreen()
                3 -> Task4SideEffectScreen()
                4 -> Task5ScrollScreen()
                5 -> Task6ProduceStateScreen()
            }
        }
    }
}