package com.example.gym

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.example.gym.ui.theme.GymTheme
import com.example.gymz.others.ClientFactory
import com.example.gymz.others.ClientRepository
import com.example.gymz.others.Navigation
import com.example.gymz.vms.AuthViewModel
import com.example.gymz.vms.ClientViewModel

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        var authViewModel = AuthViewModel()
        val clientRepository = ClientRepository()
        val clientFactory = ClientFactory(clientRepository)
        var clientViewModel = ViewModelProvider(this, clientFactory)[ClientViewModel::class.java]
        setContent {
            Navigation(
                authViewModel = authViewModel,
                clientViewModel = clientViewModel
            )
        }
    }
}
