package com.nads.kingshan.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import androidx.test.filters.SmallTest
import com.nads.kingshan.KingShanFakeRepo
import com.nads.kingshan.ui.kingshanfail.KingShanFail
import com.nads.kingshan.ui.kingshanwin.KingShanWin

import com.nads.kingshan.ui.main.MainViewModel
import com.nads.kingshan.ui.theme.KingShanTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

//@HiltAndroidTest
@SmallTest
class MainComposeActivityTest{

    @get:Rule(order = 2)
    val composeTestRule = createComposeRule()
//    @get:Rule(order = 0)
//    var hiltRule = HiltAndroidRule(this)
    @get:Rule(order = 1)
    val instantTaskExecutorRule = InstantTaskExecutorRule()
//    @Inject
    lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
//        hiltRule.inject()

    }
    @Test
    fun myTest() {
        
        composeTestRule.setContent {
           viewModel = MainViewModel(KingShanFakeRepo(), SavedStateHandle())
            viewModel.getVehicles()
            viewModel.getPlanets()
            viewModel.getToken()
            KingShanTheme() {
                 VehicleScreen(
                     paddingValues = PaddingValues(9.dp),
                     viewModel = viewModel ,
                     onNavigate = { /*TODO*/ }
                 )
            }
        }

        composeTestRule.onNodeWithText("Donlon").assertIsDisplayed()
//
//        composeTestRule.onNodeWithText("Welcome").assertIsDisplayed()
    }
    @Test
    fun myTestKingShanWin() {

        composeTestRule.setContent {
            viewModel = MainViewModel(KingShanFakeRepo(), SavedStateHandle())
            KingShanTheme() {
                KingShanWin(
                    viewModel = viewModel ,
                    onNavigate = { /*TODO*/ },
                    inner_padding = PaddingValues(9.dp)
                )
            }
        }

        composeTestRule.onNodeWithText("Success").assertIsDisplayed()
//
//        composeTestRule.onNodeWithText("Welcome").assertIsDisplayed()
    }
    @Test
    fun myTestKingShanFail() {

        composeTestRule.setContent {
            viewModel = MainViewModel(KingShanFakeRepo(), SavedStateHandle())
            KingShanTheme() {
                KingShanFail(
                    viewModel = viewModel ,
                    onNavigate = { /*TODO*/ },
                    inner_padding = PaddingValues(9.dp)
                )
            }
        }

        composeTestRule.onNodeWithText("Try Again").assertIsDisplayed()
//
//        composeTestRule.onNodeWithText("Welcome").assertIsDisplayed()
    }

}