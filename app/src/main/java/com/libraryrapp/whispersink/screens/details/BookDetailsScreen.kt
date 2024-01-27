package com.libraryrapp.whispersink.screens.details

import android.view.LayoutInflater
import android.widget.RatingBar
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.libraryrapp.whispersink.R
import com.libraryrapp.whispersink.components.Logo
import com.libraryrapp.whispersink.components.WhispersTopBar
import com.libraryrapp.whispersink.model.MyBook
import com.libraryrapp.whispersink.navigation.WhispersScreens
import com.libraryrapp.whispersink.screens.ErrorScreen

@Preview(showBackground = true)

@Composable
fun BookDetailsScreen(
    navController: NavController = rememberNavController(),
    bookId: String = "",
    fromList: Boolean = false,
    detailsViewModel: DetailsViewModel = hiltViewModel()
) {

    LaunchedEffect(key1 = bookId, block = {
        detailsViewModel.getBookInfo(bookId)
    })

    val uiState = detailsViewModel.bookUiState.collectAsState().value

    val error = uiState.e

    val book = uiState.data

    var bookSavedText by remember { mutableIntStateOf(R.string.saved_fail) }

    if (book != null) {

        Content(
            book = book,
            fromList = fromList,
            bookSavedText,
            openSettings = { navController.navigate(WhispersScreens.SettingsScreen.route) },
            goBackToHome = { navController.navigate(WhispersScreens.HomeScreen.route) },
            saveBook = {

                if (detailsViewModel.saveBook()) {
                    bookSavedText = R.string.saved_success
                }

            },
            readBook = {
                detailsViewModel.startReadingBook()
            }
        )


    } else if (error != null) {

        ErrorScreen(onRetry = { detailsViewModel.getBookInfo(bookId) })

    } else {

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
        )
        {
            CircularProgressIndicator()
        }
    }


}

@Composable
fun Content(
    book: MyBook,
    fromList: Boolean,
    bookSavedText: Int,
    openSettings: () -> Unit,
    goBackToHome: () -> Unit,
    saveBook: () -> Unit,
    readBook: () -> Unit
) {

    val authors = book.authors

    val categoryList = book.categories

    var bookRating = stringResource(id = R.string.rating_not_available)

    var btnText = stringResource(id = R.string.save)

    var authorsString = stringResource(id = R.string.author_not_available)

    var imageFromUrl = painterResource(id = R.drawable.preview)

    if (!book.photoUrl.isNullOrEmpty()) {
        imageFromUrl = rememberImagePainter(data = book.photoUrl)
    }

    if (!authors.isNullOrEmpty()) {
        authorsString = authors.joinToString(separator = " , ")
    }

    if (book.rating != null) {
        bookRating = book.rating.toString()
    }

    if (fromList) {
        btnText = stringResource(id = R.string.delete)
    }

    var savedClicked by remember {
        mutableStateOf(false)
    }

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.surface
    ) {
        Box(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.surface)
                .fillMaxSize(),
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            )
            {
                Logo()

                WhispersTopBar(
                    title = stringResource(id = R.string.book_details),
                    isHomeScreen = false,
                    onSettingsClicked = {
                        openSettings()
                    },
                    onBackClicked = {
                        goBackToHome()
                    }
                )
            }

            Surface(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .fillMaxHeight(0.7f),
                color = MaterialTheme.colorScheme.onSurface,
                shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
            ) {


                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState(), true)
                        .padding(12.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {

                    Spacer(
                        modifier = Modifier
                            .height(200.dp)
                    )

                    Text(
                        modifier = Modifier
                            .padding(3.dp),
                        text = book.title ?: stringResource(id = R.string.title_not_available),
                        color = MaterialTheme.colorScheme.onSecondary,
                        style = MaterialTheme.typography.titleLarge,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2
                    )

                    Text(
                        modifier = Modifier
                            .padding(3.dp),
                        text = authorsString,
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.titleMedium,

                        )

                    Row(
                        modifier = Modifier.padding(
                            top = 5.dp,
                            end = 3.dp,
                            bottom = 3.dp
                        ),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AndroidView(
                            factory = { context ->
                                LayoutInflater.from(context).inflate(R.layout.rating, null, false)
                            },
                            modifier = Modifier
                        )
                        { view ->

                            val ratingBar = view.findViewById<RatingBar>(R.id.ratingBar)

                            ratingBar.rating = ((book.rating ?: 0).toFloat())
                        }

                        Text(
                            modifier = Modifier
                                .padding(start = 3.dp),
                            text = bookRating,
                            color = MaterialTheme.colorScheme.secondary,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                    Box(
                        modifier = Modifier.padding(3.dp)
                    ) {

                        if (categoryList != null) {

                            for (category in categoryList) {

                                Surface(
                                    modifier = Modifier.padding(3.dp),
                                    shape = RoundedCornerShape(18.dp),
                                    color = MaterialTheme.colorScheme.tertiary
                                ) {
                                    Text(
                                        modifier = Modifier
                                            .padding(5.dp),
                                        text = category,
                                        color = MaterialTheme.colorScheme.onTertiary,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }
                    }

                    Text(
                        modifier = Modifier
                            .padding(3.dp),
                        text = book.description
                            ?: stringResource(id = R.string.desc_not_available),
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(3.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.Bottom
                    ) {

                        Button(
                            onClick = {
                                saveBook()
                                savedClicked = true
                            },
                            modifier = Modifier
                                .padding(3.dp)
                                .height(50.dp),
                            enabled = true,
                            shape = RoundedCornerShape(18.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                disabledContainerColor = MaterialTheme.colorScheme.secondary
                            )
                        ) {

                            Text(
                                text = btnText,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        Button(
                            onClick = { readBook() },
                            modifier = Modifier
                                .padding(end = 6.dp)
                                .height(50.dp),
                            enabled = true,
                            shape = RoundedCornerShape(18.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                disabledContainerColor = MaterialTheme.colorScheme.secondary
                            )
                        ) {

                            Text(
                                text = stringResource(id = R.string.read),
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    if (savedClicked) {

                        Snackbar(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 6.dp),
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(3.dp),
                                text = stringResource(id = bookSavedText)
                            )

                        }
                    }

                }
            }

            Image(
                painter = imageFromUrl,
                contentDescription = null,
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(18.dp)
                    )
                    .fillMaxWidth(0.5f)
                    .padding(top = 110.dp)
                    .align(Alignment.TopCenter),
                contentScale = ContentScale.Crop
            )
        }
    }
}


