package io.nerdythings.dummy.app

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class DummyViewModel {

    val retrofitService = RetrofitService()

    val uiState = MutableStateFlow<UiState>(UiState.Loading)

    fun loadState() {
        GlobalScope.launch {
            uiState.emit(UiState.Loading)
            try {
                val colors = retrofitService.service.listColors()
                uiState.emit(UiState.Loaded(colors))
            } catch (e: Exception) {
                uiState.emit(UiState.Error)
            }
        }
    }

    sealed class UiState {
        data object Loading : UiState()
        data object Error : UiState()
        data class Loaded(val map: Map<String, List<Int>>) : UiState()
    }
}