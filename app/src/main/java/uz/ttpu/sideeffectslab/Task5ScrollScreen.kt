package uz.ttpu.sideeffectslab

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

@Composable
fun Task5ScrollScreen() {
    val listState = rememberLazyListState()

    val showButtonDerived by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 0 }
    }

    val showButtonDirect = listState.firstVisibleItemIndex > 0

    var derivedRecompositions by remember { mutableIntStateOf(0) }
    var directRecompositions by remember { mutableIntStateOf(0) }

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .map { index -> index > 0 }
            .distinctUntilChanged()
            .filter { it }
            .collect {
                Log.d("Task5_Log", "[ANALYTICS EVENT] User scrolled past item 0!")
            }
    }

    SideEffect {
        directRecompositions++
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Task 5: derivedStateOf & snapshotFlow",
            style = MaterialTheme.typography.titleMedium
        )

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text("Direct Read Recompositions: $directRecompositions")
                DerivedCounter(showButtonDerived) { derivedRecompositions++ }
                Text("Derived State Recompositions: $derivedRecompositions")
            }
        }

        Box(modifier = Modifier.weight(1f)) {
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize()
            ) {
                items(100) { index ->
                    Text(
                        text = "Item #$index",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                    Divider()
                }
            }

            this@Column.AnimatedVisibility(visible = showButtonDerived) {
                Button(
                    onClick = { },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Scroll to Top")
                }
            }
        }
    }
}

@Composable
fun DerivedCounter(show: Boolean, onRecompose: () -> Unit) {
    SideEffect {
        onRecompose()
    }
}