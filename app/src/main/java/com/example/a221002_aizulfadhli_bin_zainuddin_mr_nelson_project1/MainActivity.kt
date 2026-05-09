package com.example.a221002_aizulfadhli_bin_zainuddin_mr_nelson_project1


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a221002_aizulfadhli_bin_zainuddin_mr_nelson_project1.ui.theme.AppTheme
import com.example.a221002_aizulfadhli_bin_zainuddin_mr_nelson_project1.ui.theme.HistoryScreen
import com.example.a221002_aizulfadhli_bin_zainuddin_mr_nelson_project1.ui.theme.HomeScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            //Add theme from Theme.kt
            AppTheme {
                //Initialize the Controller and ViewModel
                val navController = rememberNavController()
                val viewModel: CareGoViewModel = viewModel()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //The NavHost defines the "Map"
                    NavHost(
                        navController = navController,
                        startDestination = CareGoScreen.Home.name
                    ) {


                        // SCREEN 1: HOME (DASHBOARD)
                        composable(route = CareGoScreen.Home.name) {
                            HomeScreen(
                                onStartLogClick = {
                                    navController.navigate(CareGoScreen.Start.name)
                                },
                                onViewHistoryClick = {
                                    navController.navigate(CareGoScreen.History.name)
                                }
                            )
                        }


                        //SCREEN 2: START (SDG INFO)
                        composable(route = CareGoScreen.Start.name) {
                            StartScreen(
                                onNextButtonClicked = {
                                    navController.navigate(CareGoScreen.Entry.name)
                                }
                            )
                        }

                        //SCREEN 3: ENTRY (FORM)
                        composable(route = CareGoScreen.Entry.name) {
                            EntryScreen(
                                viewModel = viewModel,
                                onNextButtonClicked = {
                                    viewModel.saveEntry() //call viewmodel here so the data move to history
                                    navController.navigate(CareGoScreen.Summary.name)
                                }
                            )
                        }

                        // SCREEN 4: SUMMARY (REPORT)
                        composable(route = CareGoScreen.Summary.name) {
                            SummaryScreen(
                                viewModel = viewModel,
                                onRestartButtonClicked = {
                                    //Go back to home
                                    navController.popBackStack(CareGoScreen.Home.name, inclusive = false)
                                }
                            )
                        }

                        // SCREEN 5: HISTORY (THE LIST)
                        composable(route = CareGoScreen.History.name) {
                            val uiState by viewModel.uiState.collectAsState()

                            HistoryScreen(
                                historyList = uiState.healthHistory,
                                onBackClick = {
                                    navController.popBackStack(CareGoScreen.Home.name, inclusive = false)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
