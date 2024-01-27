package com.libraryrapp.whispersink.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.libraryrapp.whispersink.R
import com.libraryrapp.whispersink.model.MyBook



@Composable
fun Logo() {
    val logo = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                fontFamily = FontFamily(Font(R.font.barlow_bold)),
                color = MaterialTheme.colorScheme.onSecondary
            )
        ) {
            append("Whispers")
        }
        withStyle(
            style = SpanStyle(
                fontFamily = FontFamily(Font(R.font.barlow_bold)),
                color = MaterialTheme.colorScheme.primary
            )
        ) {
            append("Ink")
        }
    }
    Text(modifier = Modifier.padding(start = 12.dp), text = logo)
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
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface
        )
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    valueState: MutableState<String> = mutableStateOf("Email"),
    labelId: String = "",
    enabled: Boolean = true,
    isSingleLine: Boolean = true,
    trailingIcon: @Composable (() -> Unit) = {},
    keyboardType: KeyboardType = KeyboardType.Email,
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
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = imeAction),
        keyboardActions = onAction,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface
        ),
        trailingIcon = trailingIcon
    )


}

@Composable
fun SubmitButton(
    textId: String,
    loading: Boolean,
    validInputs: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(16.dp)
            .height(50.dp),
        enabled = !loading && validInputs,
        shape = RoundedCornerShape(18.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            disabledContainerColor = MaterialTheme.colorScheme.secondary
        )
    ) {
        if (loading) CircularProgressIndicator(modifier = Modifier.size(25.dp))
        else Text(
            text = textId,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )

    }

}

@Composable
fun TabButton(
    textId: String,
    btnColor: Color,
    onClick: () -> Unit
) {

    Button(
        onClick = {
            onClick()
        },
        modifier = Modifier
            .padding(5.dp)
            .height(50.dp),
        enabled = true,
        shape = RoundedCornerShape(18.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = btnColor,
        )
    ) {
        Text(
            text = textId,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface
        )

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WhispersTopBar(
    title: String = "",
    isHomeScreen: Boolean = false,
    onBackClicked: () -> Unit = {},
    onSettingsClicked: () -> Unit = {}

) {


    TopAppBar(
        title = {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleLarge
            )
        },

        navigationIcon = {
            if (!isHomeScreen) {
                IconButton(onClick = onBackClicked) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(
                            id = R.string.back
                        ),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        },

        actions = {
            IconButton(onClick = onSettingsClicked) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = stringResource(
                        id = R.string.back
                    ),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    )

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun WhispersSearchBar(
    modifier: Modifier,
    onSearch: (String) -> Unit = {}
) {
    Column {
        val searchQueryState = rememberSaveable { mutableStateOf("") }
        val keyboardController = LocalSoftwareKeyboardController.current
        val valid = remember(searchQueryState.value) {
            searchQueryState.value.trim().isNotEmpty()

        }

        CustomTextField(
            valueState = searchQueryState,
            labelId = stringResource(id = R.string.search),
            enabled = true,
            onAction = KeyboardActions {
                if (!valid) return@KeyboardActions
                onSearch(searchQueryState.value.trim())
                searchQueryState.value = ""
                keyboardController?.hide()
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(id = R.string.search),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            })
    }
}

@Preview(showBackground = true)
@Composable
fun BookCard(
    modifier: Modifier = Modifier,
    book: MyBook = MyBook(
        title = "The Google story",
        authors = listOf("David A. Vise, Mark Malseed"),
        photoUrl = "https://books.google.com/books?id=zyTCAlFPjgYC&printsec=frontcover&img=1&zoom=2&edge=curl&source=gbs_api",
        categories = listOf("Business & Economics", "Entrepreneurship"),
        publishedDate = "2005-11-15",
        rating = 3.5,
        description = "Here is the story behind one of the most remarkable Internet successes of our time. Based on scrupulous research and extraordinary access successes of our time. Based on scrupulous research and extraordinary access  to Google, ...",
        pageCount = "207",
        startedReading = null,
        finishedReading = null,
        userId = null,
        googleBookId = "zyTCAlFPjgYC",
        inMyList = true
    ),
    onBookCardClicked: (String) -> Unit = {},
    deleteFromList: (String) -> Unit = {}
) {

    val authors = book.authors

    var authorsString = stringResource(id = R.string.author_not_available)

    var bookRating = stringResource(id = R.string.rating_not_available)

    var imageFromUrl = painterResource(id = R.drawable.preview)

    var read by remember {
        mutableIntStateOf(R.string.start_reading)
    }

    if (book.startedReading == true) {
        read = R.string.continue_reading
    }

    if (!book.photoUrl.isNullOrEmpty()) {
        imageFromUrl = rememberImagePainter(data = book.photoUrl)
    }

    if (!authors.isNullOrEmpty()) {
        authorsString = authors.joinToString(separator = " , ")
    }

    if (book.rating != null) {
        bookRating = book.rating.toString()
    }


    Card(
        modifier = modifier
            .padding(12.dp)
            .fillMaxWidth()
            .clickable { onBookCardClicked(book.googleBookId) },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp)
    ) {
        Column( modifier = modifier.height(200.dp)) {
            Row(
                modifier = modifier,
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Top
            ) {
                Image(
                    modifier = modifier
                        .fillMaxSize()
                        .weight(0.4f)
                        .clip(RoundedCornerShape(18.dp))
                        .shadow(elevation = 1.dp, shape = RoundedCornerShape(18.dp)),
                    painter = imageFromUrl,
                    contentDescription = stringResource(id = R.string.book_cover),
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = modifier
                        .padding(8.dp)
                        .weight(0.6f),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        modifier = modifier
                            .padding(3.dp),
                        text = book.title ?: stringResource(id = R.string.title_not_available),
                        color = MaterialTheme.colorScheme.onSecondary,
                        style = MaterialTheme.typography.titleSmall,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2
                    )

                    Text(
                        modifier = modifier
                            .padding(3.dp),
                        text = authorsString,
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.bodyMedium,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2
                    )

                    Row(
                        modifier = modifier.padding(
                            top = 5.dp,
                            start = 3.dp,
                            end = 3.dp,
                            bottom = 3.dp
                        ),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = modifier.size(10.dp),
                            tint = MaterialTheme.colorScheme.primary,
                            imageVector = Icons.Default.Star,
                            contentDescription = stringResource(
                                id = R.string.book_rating
                            )
                        )
                        Text(
                            modifier = modifier
                                .padding(start = 3.dp),
                            text = bookRating,
                            color = MaterialTheme.colorScheme.secondary,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Text(
                        modifier = modifier
                            .padding(top = 12.dp, start = 3.dp, end = 3.dp, bottom = 3.dp),
                        text = book.description ?: stringResource(id = R.string.desc_not_available),
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.bodySmall,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 8
                    )


                }
            }
        }


        if (book.inMyList == true) {
            Row(
                modifier = Modifier.padding(top = 6.dp)
            ) {
                Surface(
                    modifier = Modifier.weight(0.5f),
                    color = MaterialTheme.colorScheme.primary
                ) {
                    Text(
                        modifier = modifier
                            .clickable {
                                //Read
                            }
                            .padding(6.dp),
                        text = stringResource(id = read),
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }

                Surface(
                    modifier = Modifier.weight(0.5f),
                    color = Color.LightGray
                ){
                    Text(
                        modifier = modifier
                            .clickable {
                                deleteFromList(book.googleBookId)
                            }
                            .padding(6.dp),
                        text = stringResource(id = R.string.remove),
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

    }
}

@Composable
fun FilterRadioButtons(
    onSelect: (Int) -> Unit
) {

    val radioOptions = listOf(R.string.reading,R.string.not_reading,R.string.finished)
    var selectedOption by remember { mutableStateOf(R.string.reading) }

    Column(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        radioOptions.forEach { selected ->

            Row(verticalAlignment = Alignment.CenterVertically) {

                RadioButton(
                    selected = (selected == selectedOption),
                    onClick = {
                        selectedOption = selected
                        onSelect(selectedOption)
                    },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.primary,
                        unselectedColor = MaterialTheme.colorScheme.secondary
                    )
                )
                Text(
                    text = stringResource(id = selected),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 8.dp),
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }

    }
}










