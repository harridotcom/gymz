package com.example.gymz.vms

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gymz.objs.Client
import com.example.gymz.others.ClientRepository

class ClientViewModel(val clientRepository: ClientRepository): ViewModel() {

    private val _clientsList = MutableLiveData<List<Client>>()
    val clientsList: LiveData<List<Client>> get() = _clientsList

    fun addClient(client: Client, context: Context) = clientRepository.addClient(client, context)

    fun loadClients(){
        clientRepository.loadClients(
            onDataChange = {
                    clients ->
                _clientsList.value = clients
            },
            onCancelled = {
                errorMessage ->
//                Toast.makeText(this, "Error Try Later", Toast.LENGTH_SHORT).show()
            }
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateClientMembership(client: Client, context: Context){
        clientRepository.updateClientMembership(client, context)
    }
}