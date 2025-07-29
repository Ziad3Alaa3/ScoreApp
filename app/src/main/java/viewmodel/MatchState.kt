package viewmodel

import android.service.autofill.FieldClassification
import model.MatchItem
import model.MatchResponse
import java.util.Objects


sealed class MatchState {
    object Loading : MatchState()
    // Represents the state of the match data
    data class Success(val data: MatchResponse) : MatchState()
    // Represents a successful data fetch with the match data
        data class Error(val massage: String) : MatchState()
    // Represents an error state with an error message

    data class GroupedSuccess(val groupedData: Map<String, List<MatchItem>>) : MatchState()
    // Represents a successful data fetch with grouped match data

}