package com.itis.bookclub.presentation.signup.composable

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.itis.bookclub.R

@Composable
internal fun TitleText(modifier: Modifier = Modifier) {
    Spacer(modifier = modifier.height(20.dp))
    Text(
        text = stringResource(id = R.string.sign_up),
        style = MaterialTheme.typography.headlineSmall.copy(
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold
        ),
        modifier = modifier.padding(vertical = 16.dp)
    )
    Text(
        text = stringResource(R.string.create_new_account),
        style = MaterialTheme.typography.headlineMedium.copy(
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold
        ),
        modifier = modifier.padding(vertical = 16.dp)
    )
}