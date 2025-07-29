package repository

import model.MatchResponse


interface MatchRepository {

    suspend fun getMatches(): MatchResponse
}