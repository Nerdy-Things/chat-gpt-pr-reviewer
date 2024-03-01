package io.nerdythings.dummy.app

import android.content.Context
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
class MainViewModel {

    private val retrofitService = UserProfileRepository()

    val uiState = MutableStateFlow<UiState>(UiState.Loading)

    fun prepareText(context: Context) {
        labelProvider = fun(userProfile: UserProfile): String {
            return if (userProfile.followersCount > 1000) {
                context.getString(R.string.celebrity)
            } else {
                context.getString(R.string.user)
            }
        }
    }

    fun loadState() {
        GlobalScope.launch {
            uiState.emit(UiState.Loading)
            try {
                val userProfile = retrofitService.service.getUserProfile().userProfile
                uiState.emit(UiState.Loaded(userProfile, labelProvider(userProfile)))
            } catch (e: Exception) {
                uiState.emit(UiState.Error)
            }
        }
    }

    sealed class UiState {
        data object Loading : UiState()

        data object Error : UiState()

        class Loaded(val user: UserProfile, val label: String) : UiState()
    }

    companion object {
        lateinit var labelProvider: (UserProfile) -> String
    }
}