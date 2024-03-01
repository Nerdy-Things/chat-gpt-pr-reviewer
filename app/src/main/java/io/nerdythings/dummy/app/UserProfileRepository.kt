package io.nerdythings.dummy.app

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class RetrofitService {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val service: GitHubService by lazy {
        retrofit.create(GitHubService::class.java)
    }

    interface GitHubService {
        @GET(JSON_URL)
        suspend fun listColors(): Map<String, List<Int>>
    }
    companion object {
        private const val BASE_URL = "https://raw.githubusercontent.com"
        private const val JSON_URL =
            "$BASE_URL/Nerdy-Things/04-android-network-inspector/master/colors.json"
    }

}