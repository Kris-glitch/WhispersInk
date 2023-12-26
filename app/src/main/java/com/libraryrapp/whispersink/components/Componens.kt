package com.libraryrapp.whispersink.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.libraryrapp.whispersink.R


@Composable
fun Logo() {
  val logo = buildAnnotatedString {
    withStyle(
        style = SpanStyle(
            fontFamily = FontFamily(Font(R.font.barlow_bold)),
            color = MaterialTheme.colorScheme.onSecondary
        )
    ){
      append("Whispers")
    }
    withStyle(
        style = SpanStyle(
            fontFamily = FontFamily(Font(R.font.barlow_bold)),
            color = MaterialTheme.colorScheme.primary
        )
    ){
      append("Ink")
    }
  }
  Text(logo)
}
@Composable
fun EmailTextField(
    modifier: Modifier = Modifier,
    emailState: MutableState<String>,
    labelId: String = "Email",
    enabled: Boolean = true,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default


) {
  CustomTextField(
      modifier = modifier,
      valueState = emailState,
      labelId = labelId,
      enabled = enabled,
      keyboardType = KeyboardType.Email,
      imeAction = imeAction,
      onAction = onAction
  )
}

@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    passwordState: MutableState<String> = mutableStateOf("Password"),
    labelId: String = "",
    enabled: Boolean = true,
    passwordVisibility: MutableState<Boolean> = mutableStateOf(true),
    imeAction: ImeAction = ImeAction.Done,
    onAction: KeyboardActions = KeyboardActions.Default


) {
  val visualTransformation = if (passwordVisibility.value) VisualTransformation.None else
    PasswordVisualTransformation()
  OutlinedTextField(
      value = passwordState.value,
      onValueChange = {
        passwordState.value = it
      },
      label = { Text(text = labelId, color = MaterialTheme.colorScheme.onSurface) },
      singleLine = true,
      textStyle = MaterialTheme.typography.bodyLarge,
      modifier = modifier
          .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
          .fillMaxWidth(),
      enabled = enabled,
      shape = RoundedCornerShape(18.dp),
      keyboardOptions = KeyboardOptions(
          keyboardType = KeyboardType.Password,
          imeAction = imeAction
      ),
      visualTransformation = visualTransformation,
      trailingIcon = { PasswordVisibility(passwordVisibility = passwordVisibility) },
      keyboardActions = onAction,
      colors = TextFieldDefaults.outlinedTextFieldColors(
          focusedBorderColor = MaterialTheme.colorScheme.primary,
          unfocusedBorderColor = MaterialTheme.colorScheme.onSurface)
  )
}

@Composable
fun PasswordVisibility(passwordVisibility: MutableState<Boolean>) {
  val visible = passwordVisibility.value
  IconButton(
      modifier = Modifier.padding(start = 6.dp, end = 6.dp),
      onClick = {
        passwordVisibility.value = !visible
      }) { Icons.Default.Close }

}

@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    valueState: MutableState<String> = mutableStateOf("Email"),
    labelId: String = "",
    enabled: Boolean = true,
    isSingleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
) {

  OutlinedTextField(
      value = valueState.value,
      onValueChange = { valueState.value = it },
      label = { Text(text = labelId, color = MaterialTheme.colorScheme.onSurface) },
      singleLine = isSingleLine,
      textStyle = MaterialTheme.typography.bodyLarge,
      modifier = modifier
          .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
          .fillMaxWidth(),
      enabled = enabled,
      shape = RoundedCornerShape(18.dp),
      keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
      keyboardActions = onAction,
      colors = TextFieldDefaults.outlinedTextFieldColors(
          focusedBorderColor = MaterialTheme.colorScheme.primary,
          unfocusedBorderColor = MaterialTheme.colorScheme.onSurface)
  )


}

@Composable
fun SubmitButton(textId: String,
                 loading: Boolean,
                 validInputs: Boolean,
                 onClick: () -> Unit) {
  Button(
      onClick = onClick,
      modifier = Modifier
          .padding(16.dp)
          .height(50.dp),
      enabled = !loading && validInputs,
      shape = RoundedCornerShape(18.dp),
      colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary, disabledContainerColor = MaterialTheme.colorScheme.secondary)
  ) {
    if (loading) CircularProgressIndicator(modifier = Modifier.size(25.dp))
    else Text(
        text = textId,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurface
        )

  }

}