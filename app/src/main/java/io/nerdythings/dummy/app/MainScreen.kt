package io.nerdythings.dummy.app

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen(
    uiState: MainViewModel.UiState,
    loadData: () -> Unit,
    floatingAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (uiState) {
                is MainViewModel.UiState.Loading -> Text(text = "Loading")
                is MainViewModel.UiState.Error -> Text(text = "Error")
                is MainViewModel.UiState.Loaded ->
                    Text(text = "Success ${uiState.label} ${uiState.user.hashCode()}")
            }
            Button(onClick = loadData) {
                Text(text = "Reload data")
            }
        }
        FloatingActionButton(
            onClick = {
                floatingAction.invoke()
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Image(
                painterResource(id = R.mipmap.ic_launcher),
                contentDescription = "Favorite"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MaterialTheme {
        MainScreen(MainViewModel.UiState.Loading, {}, {})
    }
}