package com.itis.bookclub.presentation.signin.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.itis.bookclub.R
import com.itis.bookclub.presentation.themes.OnSurfaceTextAlpha

@Composable
internal fun SignInContent(
    email: String,
    password: String,
    isInvalidCredentials: Boolean,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignUpClick: () -> Unit,
    onSignInClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp)
    ) {
        TitleText()
        CredentialsAndSignIn(
            email = email,
            password = password,
            isInvalidCredentials = isInvalidCredentials,
            onEmailChange = onEmailChange,
            onPasswordChange = onPasswordChange,
            onSignInClick = onSignInClick,
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = buildAnnotatedString {
                append(stringResource(R.string.don_t_have_an_account))

                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = MaterialTheme.typography.bodyMedium.fontWeight
                    ),
                ) {
                    append(stringResource(R.string.sign_up))
                }
            },
            style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = OnSurfaceTextAlpha)
            ),
            modifier = Modifier.fillMaxWidth().clickable { onSignUpClick() }
        )
        Spacer(modifier = Modifier.fillMaxHeight(fraction = 0.74f))
        Text(
            text = stringResource(R.string.terms_of_use_text),
            style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = OnSurfaceTextAlpha)
            ),
            modifier = Modifier
                .padding(bottom = 24.dp),
        )
    }
}
