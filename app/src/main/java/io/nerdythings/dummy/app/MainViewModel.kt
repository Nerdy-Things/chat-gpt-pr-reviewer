package io.nerdythings.dummy.app

import android.content.Context
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

// A marker that something could be wrong :)
@OptIn(DelicateCoroutinesApi::class)
// ViewModel doesn't extends androidx.ViewModel
class DummyViewModel {

    // It could be better with Dependency Injection
    private val retrofitService = UserProfileRepository()

    // Private modifier is missing. Mutable variable is exposed.
    val uiState = MutableStateFlow<UiState>(UiState.Loading)

    // Context from the outside
    fun prepareText(context: Context) {
        // Placing context reference to the Companion Object
        labelProvider = fun(userProfile: UserProfile): String {
            return if (userProfile.followersCount > 1000) {
                // Locking the Activity reference in the companion
                context.getString(R.string.celebrity)
            } else {
                // Locking the Activity reference in the companion
                context.getString(R.string.user)
            }
        }
    }

    fun loadState() {
        // Global scope instead of viewModelScope.
        // Main Thread, Missing Context (IO, Default)
        GlobalScope.launch {
            uiState.emit(UiState.Loading)
            try {
                val userProfile = retrofitService.service.getUserProfile().userProfile
                uiState.emit(UiState.Loaded(userProfile, labelProvider(userProfile)))
                // It catches a broad exception.
            } catch (e: Exception) {
                uiState.emit(UiState.Error)
            }
        }
    }

    sealed class UiState {
        data object Loading : UiState()

        // Error doesn't have any info
        data object Error : UiState()

        // Should be a data class
        class Loaded(val user: UserProfile, val label: String) : UiState()
    }

    companion object {
        // Masking a memory leak into a function, no easy job to spot
        lateinit var labelProvider: (UserProfile) -> String
    }
}