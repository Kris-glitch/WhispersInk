package com.libraryrapp.whispersink.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.libraryrapp.whispersink.components.Logo
import com.libraryrapp.whispersink.components.WhispersTopBar
import com.libraryrapp.whispersink.R
import com.libraryrapp.whispersink.components.BookCard
import com.libraryrapp.whispersink.components.TabButton
import com.libraryrapp.whispersink.components.WhispersSearchBar
import com.libraryrapp.whispersink.model.MyBook
import com.libraryrapp.whispersink.navigation.WhispersScreens

@Composable
fun HomeScreen(navController: NavController) {

    var savedTabColor by remember {
        mutableStateOf(Color.LightGray)
    }
    var newTabColor by remember {
        mutableStateOf(Color.LightGray)
    }
    var freeTabColor by remember {
        mutableStateOf(Color.LightGray)
    }
    var paidTabColor by remember {
        mutableStateOf(Color.LightGray)
    }

    var listOfBooks = emptyList<MyBook>()



    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.surface
    ) {


        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Logo()

            WhispersTopBar(
                title = stringResource(id = R.string.books_catalogue),
                isHomeScreen = true,
                onSettingsClicked = {
                    navController.navigate(WhispersScreens.SettingsScreen.name)
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            WhispersSearchBar(
                modifier = Modifier.weight(1f),
                onSearch = {book ->
                    //TODO: Search the book

                }
            )

            Spacer(modifier = Modifier.height(60.dp))

            Surface(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .fillMaxHeight(),
                color = MaterialTheme.colorScheme.onSurface,
                shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally)
                {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TabButton(textId = stringResource(id = R.string.my_list), savedTabColor) {
                            savedTabColor = Color(0xFFE7B34D)
                            newTabColor = Color.LightGray
                            freeTabColor = Color.LightGray
                            paidTabColor = Color.LightGray
                            //TODO: Open user reading list
                        }
                        TabButton(textId = stringResource(id = R.string.newest_list), newTabColor) {
                            newTabColor = Color(0xFFE7B34D)
                            savedTabColor = Color.LightGray
                            freeTabColor = Color.LightGray
                            paidTabColor = Color.LightGray
                            //TODO: Open new list
                        }
                        TabButton(textId = stringResource(id = R.string.free_list), freeTabColor) {
                            freeTabColor = Color(0xFFE7B34D)
                            savedTabColor = Color.LightGray
                            newTabColor = Color.LightGray
                            paidTabColor = Color.LightGray
                            //TODO: Open free list
                        }
                        TabButton(textId = stringResource(id = R.string.paid_list), paidTabColor) {
                            paidTabColor = Color(0xFFE7B34D)
                            savedTabColor = Color.LightGray
                            newTabColor = Color.LightGray
                            freeTabColor = Color.LightGray
                            //TODO: Open paid list
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    LazyColumn(
                        modifier = Modifier,
                        state = rememberLazyListState(),
                        contentPadding = PaddingValues(8.dp),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        content = {
                            items(items = listOfBooks) { book ->
                                BookCard(book = book) {title ->

                                    //TODO: Go to book details
                                }
                            }

                    })
                }

            }
        }

    }



}