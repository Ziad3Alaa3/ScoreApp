package ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import utils.getDayLabel

@Composable
fun MatchItem(
    title: String,
    competition: String,
    imageUrl: String,
    date: String,
    isFavorite: Boolean = false,
    onFavoriteClick: () -> Unit = {},
    team1Name: String? = null,
    team2Name: String? = null,
    team1Logo: String? = null,
    team2Logo: String? = null,
    matchTime: String? = null,
    score: String? = null,

) {
    // روابط صور افتراضية لو ما فيش لوجو
    val defaultLogo = "file:///android_asset/default_logo.png"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            // 🏆 اسم الدوري + أيقونة المفضلة
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "\uD83C\uDFC6 $competition",
                    style = MaterialTheme.typography.labelLarge
                )

                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = if (isFavorite) "إزالة من المفضلة" else "إضافة إلى المفضلة",
                    tint = if (isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.clickable { onFavoriteClick() }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 🥅 الشعارات + الأسماء + النتيجة أو الوقت
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    AsyncImage(
                        model = team1Logo ?: defaultLogo,
                        contentDescription = "Team 1 Logo",
                        modifier = Modifier.size(40.dp),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = team1Name ?: "Unknown",
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Text(
                    text = score ?: matchTime ?: "TBD",
                    style = MaterialTheme.typography.headlineSmall
                )

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    AsyncImage(
                        model = team2Logo ?: defaultLogo,
                        contentDescription = "Team 2 Logo",
                        modifier = Modifier.size(40.dp),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = team2Name ?: "Unknown",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 📝 اسم الماتش
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            // 🕒 التاريخ
            Text(
                text = "\uD83D\uDD52 ${getDayLabel(date)}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
