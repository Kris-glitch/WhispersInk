package com.libraryrapp.whispersink.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.libraryrapp.whispersink.R
import com.libraryrapp.whispersink.components.EmailTextField
import com.libraryrapp.whispersink.components.Logo
import com.libraryrapp.whispersink.components.PasswordTextField
import com.libraryrapp.whispersink.components.SubmitButton

@Composable
fun LoginScreen(navController: NavController) {

  val showLoginForm = rememberSaveable { mutableStateOf(true) }

  Surface(
      modifier = Modifier
          .fillMaxSize(),
      color = MaterialTheme.colorScheme.tertiary
  ) {
    Logo()
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Image(
          modifier = Modifier
              .weight(1f)
              .fillMaxWidth(),
          painter = painterResource(R.drawable.splash),
          contentDescription = stringResource(R.string.splash)
      )
      Surface(
          modifier = Modifier
              .weight(1f)
              .fillMaxWidth()
              .fillMaxHeight(),
          color = MaterialTheme.colorScheme.surface,
          shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
      ) {
        Column (horizontalAlignment = Alignment.CenterHorizontally){

          LoginForm()

          Row(
              modifier = Modifier.padding(8.dp),
              horizontalArrangement = Arrangement.Center,
              verticalAlignment = Alignment.CenterVertically
          ) {

            val text = if (showLoginForm.value) stringResource(id = R.string.sign_up) else stringResource(id = R.string.login)

            Text(
                text = stringResource(id = R.string.new_user),
                modifier = Modifier.padding(3.dp),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = text,
                modifier = Modifier
                    .clickable {
                      showLoginForm.value = !showLoginForm.value

                    }
                    .padding(start = 5.dp),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium
            )
        }


        }

      }
    }
  }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginForm(
    loading: Boolean = false,
    isCreateAccount: Boolean = false,
    onDone: (String, String) -> Unit = { email, password -> }
) {

  val email = rememberSaveable { mutableStateOf("") }
  val password = rememberSaveable { mutableStateOf("") }
  val passwordVisibility = rememberSaveable { mutableStateOf(false) }
  val passwordFocusRequest = FocusRequester.Default
  val keyboardController = LocalSoftwareKeyboardController.current
  val valid = remember(email.value, password.value) {
    email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()

  }
  val modifier = Modifier
      .height(280.dp)
      .verticalScroll(rememberScrollState())


  Column(
      modifier,
      horizontalAlignment = Alignment.CenterHorizontally
  ) {

    if (isCreateAccount) {
      Text(
          text = stringResource(id = R.string.create_acct),
          modifier = Modifier.padding(8.dp),
          color = MaterialTheme.colorScheme.onSurface,
          style = MaterialTheme.typography.titleLarge)
    } else {
      Text(
          text = stringResource(id = R.string.login),
          modifier = Modifier.padding(8.dp),
          color = MaterialTheme.colorScheme.onSurface,
          style = MaterialTheme.typography.titleLarge
      )
    }
    
    Spacer(modifier = Modifier.height(20.dp))

    EmailTextField(
        emailState = email,
        enabled = !loading,
        onAction = KeyboardActions {
          passwordFocusRequest.requestFocus()
        },
    )

    PasswordTextField(
        modifier = Modifier.focusRequester(passwordFocusRequest),
        passwordState = password,
        labelId = "Password",
        enabled = !loading,
        passwordVisibility = passwordVisibility,
        onAction = KeyboardActions {
          if (!valid) return@KeyboardActions
          onDone(email.value.trim(), password.value.trim())
        })

    Spacer(modifier = Modifier.height(10.dp))

    SubmitButton(
        textId =
        if (isCreateAccount) {
          stringResource(R.string.create_acct)
        } else {
          stringResource(R.string.login)
        },
        loading = loading,
        validInputs = valid
    ){
      onDone(email.value.trim(), password.value.trim())
      keyboardController?.hide()
    }



  }
}

