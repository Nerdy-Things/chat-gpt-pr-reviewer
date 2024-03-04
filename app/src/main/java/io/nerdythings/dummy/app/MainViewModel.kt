package io.nerdythings.dummy.app

import android.content.Context
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

// A marker that something could be wrong :)
@OptIn(DelicateCoroutinesApi::class)
// ViewModel doesn't extend androidx.ViewModel
class MainViewModel {
    /**
     * 9 : " UserProfile Repository ()" instance is created directly in the ViewModel .
     * Should use dependency injection for better test ability and maintain ability .
     */
    // It could be better with Dependency Injection
    private val retrofitService = UserProfileRepository()

    // Private modifier is missing. Mutable variable is exposed.
    val uiState = MutableStateFlow<UiState>(UiState.Loading)

    // Context from the outside
    fun prepareText(context: Context) {
        // Placing context reference to the Companion Object
        labelProvider = fun (userProfile: UserProfile): String {
            return if (userProfile.followersCount > 1000) {
                // And here we have a memory leak of Activity
                context.getString(R.string.celebrity)
            } else {
                // And here we have a memory leak of Activity
                context.getString(R.string.user)
            }
            /**
             * 30 - 34 : Pot entially leaking the context if it 's an Activity context .
             * Context should be used carefully and not held longer than necessary to prevent memory leaks .
             * In this case , context might be held longer than necessary through the label Provider .
             */
        }
    }

    fun loadState() {
        // Global scope instead of viewModelScope.
        // Main Thread, Missing Context (IO, Default)
        /**
         * 35 : Global Scope used .
         * In case the ViewModel is destroyed before this coroutine finishes ,
         * it can lead to memory leaks as it will keep the ViewModel
         * instance alive until the coroutine completed .
         */
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
        /**
         * 40 : Any error in the network call will only emit " Ui State .Error ",
         * no debugging information or differentiation between types of errors .
         */
        data object Error : UiState()

        // Should be a data class
        class Loaded(val user: UserProfile, val label: String) : UiState()
    }

    companion object {
        // Masking a memory leak into a function, no easy job to spot
        /**
         * 47 : label Provider is defined in a companion object , which is essentially a static
         * field that holds an instance to a function that uses context .
         * If the context is an Activity , this can lead to serious memory leaks .
         */
        /**
         * 49 : No safe call or null check before using label
         * Provider which will throw a null exception
         * if it is not initialized before calling load State ().
         */
        lateinit var labelProvider: (UserProfile) -> String
    }
}