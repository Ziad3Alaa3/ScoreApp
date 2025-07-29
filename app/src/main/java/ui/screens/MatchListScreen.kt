package ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import model.MatchItem
import network.MatchApi
import viewmodel.MatchState
import viewmodel.MatchViewModel


@Composable
fun MatchListScreen(viewModel: MatchViewModel = hiltViewModel()) {
    val state = viewModel.state.collectAsState().value

    when (state) {
        is MatchState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is MatchState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = state.massage,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }

        is MatchState.GroupedSuccess -> {
            val selectedLeague = rememberSaveable { mutableStateOf("All") }
            val expanded = rememberSaveable { mutableStateOf(false) }

            val groupedMatches = state.groupedData

            // استخراج كل الدوريات
            val allLeagues = groupedMatches.values
                .flatten()
                .map { it.competition }
                .distinct()








            // قائمة اختيار الدوري
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = selectedLeague.value,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expanded.value = true }
                        .padding(12.dp),
                    style = MaterialTheme.typography.titleMedium
                )

                DropdownMenu(
                    expanded = expanded.value,
                    onDismissRequest = { expanded.value = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("All") },
                        onClick = {
                            selectedLeague.value = "All"
                            expanded.value = false
                        }
                    )
                    allLeagues.forEach { league ->
                        DropdownMenuItem(
                            text = { Text(league) },
                            onClick = {
                                selectedLeague.value = league
                                expanded.value = false
                            }
                        )
                    }
                }
            }

            LazyColumn {
                groupedMatches.forEach { (day, matchesForDay) ->
                    // ترتيب الماتشات حسب الدوري
                    val matchesByLeague = matchesForDay
                        .filter { selectedLeague.value == "All" || it.competition == selectedLeague.value }
                        .groupBy { it.competition }

                    if (matchesByLeague.isNotEmpty()) {
                        // عنوان اليوم
                        item {
                            Text(
                                text = "🗓️ $day",
                                modifier = Modifier.padding(start = 16.dp, top = 12.dp, bottom = 4.dp),
                                style = MaterialTheme.typography.titleLarge
                            )
                        }

                        matchesByLeague.forEach { (league, matches) ->
                            item {
                                Text(
                                    text = "🏆 $league",
                                    modifier = Modifier.padding(start = 24.dp, top = 8.dp),
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }

                            items(matches) { match ->
                                MatchItem(
                                    title = match.title,
                                    competition = match.competition,
                                    imageUrl = match.thumbnail,
                                    date = match.date,
                                    isFavorite = match.isFavorite,
                                    onFavoriteClick = { viewModel.toggleFavorite(match) },

                                   team1Name = match.team1Name?:"Barcelona",
                                      team2Name = match.team2Name?:"Real Madrid",
                                      team1Logo = match.team1Logo?:"https://upload.wikimedia.org/wikipedia/en/4/47/FC_Barcelona_%28crest%29.svg",
                                        team2Logo = match.team2Logo?:"https://upload.wikimedia.org/wikipedia/en/5/56/Real_Madrid_CF.svg",
                                    matchTime = match.matchTime?:"22:00",
                                    score = match.score ?: "0 - 0",




                                )
                            }

                        }
                    }
                }
            }
        }

        else -> {}
    }
}


