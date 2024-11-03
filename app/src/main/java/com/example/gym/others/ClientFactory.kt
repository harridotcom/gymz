package com.example.gymz.others

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gymz.vms.ClientViewModel

class ClientFactory(val clientRepository: ClientRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ClientViewModel(clientRepository = clientRepository) as T
    }
}