package com.example.gymz.others

import RegisterPage
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gym.others.AddClient
import com.example.gym.pages.RenewMembership
import com.example.gymz.vms.AuthViewModel
import com.example.gymz.pages.HomePage
import com.example.gymz.pages.LoginPage
import com.example.gym.pages.MembershipOverClients
import com.example.gymz.pages.SeeClients
import com.example.gymz.vms.ClientViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel,
    clientViewModel: ClientViewModel
) {
    var navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login"){
            LoginPage(navController = navController, authViewModel = authViewModel)
        }
        composable("register"){
            RegisterPage(navController = navController, authViewModel = authViewModel)
        }
        composable("home"){
            HomePage(navController = navController, authViewModel = authViewModel)
        }
        composable("addClient"){
            AddClient(clientViewModel = clientViewModel)
        }
        composable("seeClient"){
            SeeClients(clientViewModel = clientViewModel, onBackClick = { Unit })
        }
        composable("membershipOverClients"){
            MembershipOverClients(clientViewModel = clientViewModel, onBackClick = { Unit }, navController = navController)
        }
        composable("renewMembership/{name}"){
                backStackEntry ->
            val name = backStackEntry.arguments?.getString("name")
            if (name != null) {
                RenewMembership(name = name, clientViewModel = clientViewModel)
            }
        }
    }
}