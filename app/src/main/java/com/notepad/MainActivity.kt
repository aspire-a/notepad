package com.notepad

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import com.notepad.data.datasource.local.roomdb.entity.NotesEntity
import com.notepad.navigation.Route
import com.notepad.screen.add.AddScreen
import com.notepad.screen.add.AddViewModel
import com.notepad.screen.delete.DeletePopUp
import com.notepad.screen.delete.DeleteViewModel
import com.notepad.screen.detail.DetailScreen
import com.notepad.screen.detail.DetailViewModel
import com.notepad.screen.list.ListScreen
import com.notepad.screen.list.ListUiEvent
import com.notepad.screen.list.ListViewModel
import com.notepad.screen.list.SortItem
import com.notepad.ui.theme.NotepadTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val backStackEntry by navController.currentBackStackEntryAsState()
            val currentScreenRoute: Route? = Route.getRoute(backStackEntry?.destination?.route)
            val listViewModel: ListViewModel by viewModels()

            NotepadTheme {

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),

                    topBar = {

                        if (currentScreenRoute?.hasTopBar == true) {
                            TopAppBar(
                                title = { Text(text = "Notepad") },
                                colors = TopAppBarDefaults.mediumTopAppBarColors(
                                    containerColor = Color.Cyan
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(color = Color.Blue),
                                actions = {
                                    SortItem(onClick = {
                                        listViewModel.onEvent(ListUiEvent.SortNotes(it))
                                    })
                                }

                            )
                        }

                    },

                    floatingActionButton = {
                        if (currentScreenRoute?.hasFAB == true) {
                            FloatingActionButton(onClick = {
                                navController.handleNavigation(
                                    Route.ADD.name, null
                                )
                            }) {
                                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
                            }
                        }
                    }


                ) { paddingValues ->

                    NavHost(
                        modifier = Modifier.padding(paddingValues),
                        navController = navController,
                        startDestination = Route.LIST.name
                    ) {
                        composable(Route.LIST.name) {

                            ListScreen(
                                uiStateFlow = listViewModel.uiState,
                                uiEventFlow = listViewModel.uiEvent,
                                onNavigate = { route, data ->
                                    navController.handleNavigation(route, data)
                                },
                                showToast = {
                                    Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT)
                                        .show()
                                },
                                onEvent = {
                                    listViewModel.onEvent(it)
                                },
                                showDialog = { route, data ->
                                    navController.handleDialogNavigation(route, data)
                                },
                            )
                        }


                        dialog(Route.DELETE.name) { currentStackEntry ->

                            val deleteViewModel: DeleteViewModel by viewModels()
                            val noteEntity: NotesEntity? =
                                currentStackEntry.savedStateHandle.get<NotesEntity>("DeleteData")

                            DeletePopUp(
                                uiEventFlow = deleteViewModel.uiEvent,
                                onNavigate = { route, data ->
                                    navController.handleNavigation(route, data)
                                },
                                showToast = {
                                    Toast.makeText(baseContext, it, Toast.LENGTH_SHORT).show()
                                },
                                onEvent = {
                                    deleteViewModel.onEvent(it)
                                },
                                closeDialog = {
                                    navController.popBackStack()
                                },
                                noteEntity = noteEntity,
                            )
                        }


                        composable(Route.ADD.name) {

                            val addViewModel: AddViewModel by viewModels()

                            AddScreen(uiStateFlow = addViewModel.uiState,
                                uiEventFlow = addViewModel.uiEvent,
                                onNavigate = { route, data ->
                                    navController.handleNavigation(route, data)
                                },
                                showToast = {
                                    Toast.makeText(baseContext, it, Toast.LENGTH_SHORT).show()
                                },
                                onEvent = {
                                    addViewModel.onEvent(it)
                                })
                        }

                        composable(Route.DETAIL.name) { currentStackEntry ->

                            val detailViewModel: DetailViewModel by viewModels()

                            val noteId: Long? =
                                currentStackEntry.savedStateHandle.get<Long>("DetailData")

                            DetailScreen(

                                noteId = noteId,
                                uiStateFlow = detailViewModel.uiState,
                                uiEventFlow = detailViewModel.uiEvent,
                                onNavigate = { route, data ->
                                    navController.handleNavigation(route, data)
                                },
                                showToast = {
                                    Toast.makeText(baseContext, it, Toast.LENGTH_SHORT).show()
                                },
                                onEvent = {
                                    detailViewModel.onEvent(it)
                                })
                        }
                    }
                }
            }
        }
    }


    private fun NavHostController.handleNavigation(
        route: String, data: Map<String, Any?>?
    ) {

        navigate(
            route = route
        ) {}
        getBackStackEntry(route = route).apply {
            data?.forEach { (key, value) ->
                savedStateHandle.set(
                    key = key, value = value
                )
            }
        }
    }

    private fun NavHostController.handleDialogNavigation(
        dialogRoute: String, data: Map<String, Any?>?
    ) {
        navigate(
            route = dialogRoute
        )
        getBackStackEntry(route = dialogRoute).apply {
            data?.forEach { (key, value) ->
                savedStateHandle.set(
                    key = key, value = value
                )
            }
        }
    }
}

