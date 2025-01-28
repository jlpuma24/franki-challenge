package com.frankichallenge.presentation.screens.compose.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.frankichallenge.R
import com.frankichallenge.data.model.WeatherResponse
import com.frankichallenge.presentation.extensions.kelvinToCelsius
import com.frankichallenge.presentation.extensions.toWeatherIconUrl

@Composable
fun WeatherScreen(weather: WeatherResponse) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = weather.weather.firstOrNull()?.icon?.toWeatherIconUrl(),
                error = painterResource(R.drawable.error_placeholder)
            ),
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .padding(12.dp)
        )

        BasicText(
            text = weather.cityName,
            style = MaterialTheme.typography.h4.copy(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        BasicText(
            text = weather.weather.joinToString(", ") { it.main },
            style = MaterialTheme.typography.h6.copy(
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        BasicText(
            text = weather.main.temperature.kelvinToCelsius(),
            style = MaterialTheme.typography.h5.copy(
                color = Color.Red,
                fontSize = 18.sp
            ),
        )
    }
}
