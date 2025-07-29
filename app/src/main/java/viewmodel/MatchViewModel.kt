package viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import model.MatchItem
import model.MatchResponse
import repository.MatchRepository
import utils.getDayLabel
import javax.inject.Inject
import java.text.SimpleDateFormat
import java.util.*



@HiltViewModel
class MatchViewModel @Inject constructor(
    private val repository: MatchRepository
) : ViewModel() {

    private val _state = MutableStateFlow<MatchState>(MatchState.Loading)
    val state = _state.asStateFlow()

    init {
        loadMatches()
    }

    private fun parseDate(dateStr: String): Date {
        return try {
            val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault())
            format.parse(dateStr) ?: Date(0)
        } catch (e: Exception) {
            Date(0)
        }
    }

    // دالة لتجميع الماتشات حسب اليوم (label)
    private fun groupMatchesByDay(matches: List<MatchItem>): Map<String, List<MatchItem>> {
        return matches.groupBy { match ->
            getDayLabel(match.date)
        }
    }

    private fun loadMatches() {
        viewModelScope.launch {
            try {
                val result = repository.getMatches().response // List<MatchItem>

                val sortedMatches = result
                    .filter { it.date.isNotEmpty() }
                    .sortedBy { match -> parseDate(match.date).time }

                // نجمع البيانات حسب اليوم
                val groupedMatches = groupMatchesByDay(sortedMatches)

                // نحدث الحالة بالحالة GroupedSuccess مع البيانات المجمعة
                _state.value = MatchState.GroupedSuccess(groupedData = groupedMatches)

            } catch (e: Exception) {
                _state.value = MatchState.Error(e.message ?: "An error occurred")
            }
        }
    }

    fun toggleFavorite(match: MatchItem) {
        val currentState = _state.value
        if (currentState is MatchState.GroupedSuccess) {
            // بما إن الحالة دلوقتي GroupedSuccess، لازم نحدث البيانات جوه القوائم المجمعة
            val updatedGroupedData = currentState.groupedData.mapValues { entry ->
                entry.value.map {
                    if (it == match) it.copy(isFavorite = !it.isFavorite)
                    else it
                }
            }

            _state.value = MatchState.GroupedSuccess(groupedData = updatedGroupedData)
        }
    }
}
