package repository

import model.MatchResponse
import network.MatchApi
import javax.inject.Inject

class MatchRepositoryImpl @Inject constructor(
    private val api: MatchApi) : MatchRepository
{
    override suspend fun getMatches(): MatchResponse {
        return api.getMatches()
    }



}