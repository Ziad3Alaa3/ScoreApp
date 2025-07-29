package network

import model.MatchResponse
import retrofit2.http.GET


interface MatchApi {


    @GET("video-api/v3/")
    suspend fun getMatches(): MatchResponse

}