package com.fathoor.cityzen.presentation.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.fathoor.cityzen.R

@Composable
fun DetailItem(
    modifier: Modifier = Modifier,
    id: Int,
    firstName: String,
    lastName: String,
    nationality: String,
    position: String,
    number: Int,
    image: String,
    highlight: Boolean,
    onBackClick: () -> Unit,
    onUpdateHighlight: (Int) -> Unit
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(600.dp)
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp))
        ) {
            AsyncImage(
                model = image,
                contentDescription = firstName,
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
            )
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = null,
                modifier = modifier
                    .padding(start = 16.dp, top = 24.dp)
                    .clickable { onBackClick() }
            )
            FloatingActionButton(
                onClick = {
                    onUpdateHighlight(id)
                },
                modifier = modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 16.dp, end = 16.dp),
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = if (highlight) Icons.Filled.Star else Icons.Outlined.Star,
                    contentDescription = stringResource(R.string.highlight),
                    tint = if (highlight) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onPrimary
                )
            }
        }
        Card(
            modifier = modifier
                .fillMaxWidth()
                .heightIn(min = 200.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary,
            )
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "$firstName $lastName",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.background,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 48.sp
                )
                Text(
                    text = number.toString(),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.background,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = modifier
                        .padding(vertical = 8.dp)
                )
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.position),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.background,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal
                    )
                    Text(
                        text = position,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.background,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.nationality),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.background,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal
                    )
                    Text(
                        text = nationality,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.background,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}