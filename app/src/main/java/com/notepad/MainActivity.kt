package com.notepad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.notepad.navigation.Route
import com.notepad.screen.add.AddScreen
import com.notepad.screen.detail.DetailScreen
import com.notepad.screen.list.ListScreen
import com.notepad.screen.list.ListViewModel
import com.notepad.ui.theme.NotepadTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val backStackEntry by navController.currentBackStackEntryAsState()
            NotepadTheme {

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) { paddingValues ->

                    NavHost(
                        modifier = Modifier
                            .padding(paddingValues),
                        navController = navController,
                        startDestination = Route.LIST.name
                    ) {
                        composable(Route.LIST.name) {

                            val listViewModel: ListViewModel by viewModels()

                            ListScreen(uiStateFlow = listViewModel.uiState,
                                uiEventFlow = listViewModel.uiEvent,
                                onNavigate = { route, data ->
                                    navController.handleNavigation(route, data)
                                },
                                onEvent = {
                                    listViewModel.onEvent(it)
                                }
                            )
                        }

                        /*
                        composable(Route.ADD.name) {
                            AddScreen(onValueChange =)
                        }*/
                        composable(Route.DETAIL.name) {currentStackEntry ->
                            val text: String? = currentStackEntry.savedStateHandle.get<String>("DetailData")
                            DetailScreen(textField = text?:"", onValueChange = {})
                        }

                    }
                }
            }
        }
    }


    private fun NavHostController.handleNavigation(
        route: String,
        data: Map<String, Any?>?
    ) {

        navigate(
            route = route
        ) {}
        getBackStackEntry(route = route).apply {
            data?.forEach { (key, value) ->
                savedStateHandle.set(
                    key = key,
                    value = value
                )
            }
        }


    }
}

