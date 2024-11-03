package com.example.gym

import MyWorker
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.gymz.others.ClientFactory
import com.example.gymz.others.ClientRepository
import com.example.gymz.others.Navigation
import com.example.gymz.vms.AuthViewModel
import com.example.gymz.vms.ClientViewModel
import java.util.Calendar
import java.util.concurrent.TimeUnit

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

            val initialDelay = calculateInitialDelay()
            val workRequest = PeriodicWorkRequestBuilder<MyWorker>(24, TimeUnit.HOURS)
                .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
                .build()

            WorkManager.getInstance(this).enqueue(workRequest)
        }
    }
}

fun calculateInitialDelay(): Long {
    val calendar = Calendar.getInstance()
    val now = Calendar.getInstance()


    calendar.set(Calendar.HOUR_OF_DAY, 7)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)

    if (calendar.before(now)) {
        calendar.add(Calendar.DATE, 1)
    }

    return calendar.timeInMillis - now.timeInMillis
}