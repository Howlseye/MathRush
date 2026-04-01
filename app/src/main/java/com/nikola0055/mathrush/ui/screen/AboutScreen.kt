package com.nikola0055.mathrush.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.nikola0055.mathrush.R
import com.nikola0055.mathrush.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    navController: NavController
) {
    val boldText = stringResource(id = R.string.about_text_bold).split("-")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.about),
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }){
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = stringResource(id = R.string.back),
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        )
                    }
                }
            )
        }
    ) { innerpadding ->
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(innerpadding)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = buildAnnotatedString{
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(boldText[0])
                    }
                    append(" ${stringResource(R.string.about_text_normal_1)} ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(boldText[1])
                    }
                },
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Justify,
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )

            HorizontalDivider(
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
            )

            Text(
                text = boldText[2],
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth().padding(16.dp, 12.dp)
            )

            Text(
                text = "1. ${boldText[3]}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp, 0.dp)
            )
            Text(
                text = buildAnnotatedString{
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(boldText[4])
                    }
                    append(" ${stringResource(R.string.about_text_normal_2)} ")
                },
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Justify,
                modifier = Modifier.fillMaxWidth().padding(16.dp, 0.dp)
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "2. ${boldText[5]}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp, 0.dp)
            )
            Text(
                text = stringResource(R.string.about_text_normal_3),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Justify,
                modifier = Modifier.fillMaxWidth().padding(16.dp, 0.dp)
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "3. ${boldText[6]}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp, 0.dp)
            )
            Text(
                text = buildAnnotatedString{
                    append("${stringResource(R.string.about_text_normal_4)} ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(boldText[7])
                    }
                    append(" ${stringResource(R.string.about_text_normal_5)}")
                },
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Justify,
                modifier = Modifier.fillMaxWidth().padding(16.dp, 0.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AboutScreenPreview() {
    AppTheme {
        AboutScreen(rememberNavController())
    }
}