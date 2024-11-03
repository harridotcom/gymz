package com.example.gymz.others

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.gymz.objs.Client
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.time.LocalDate

class ClientRepository {
    var database = FirebaseDatabase.getInstance().getReference("Clients")

    fun addClient(client: Client, context: Context) {
        database.child(client.name).setValue(client).addOnSuccessListener {
            Toast.makeText(context, "Successfully Added", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(context, "Error! Try Later", Toast.LENGTH_SHORT).show()
        }
    }

    fun loadClients(onDataChange: (List<Client>) -> Unit, onCancelled: (String) -> Unit) {
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val clients = mutableListOf<Client>()
                for (clientSnapshot in snapshot.children) {
                    val client = clientSnapshot.getValue(Client::class.java)
                    if (client != null) {
                        clients.add(client)
                    }
                }
                onDataChange(clients)
            }

            override fun onCancelled(error: DatabaseError) {
                onCancelled(error.message)
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateClientMembership(client: Client, context: Context) {
        val updates = hashMapOf<String, Any>(
            "startDate" to LocalDate.now().toString(),
            "endDate" to LocalDate.now().plusMonths(1).toString()
        )

        database.child(client.name).updateChildren(updates).addOnSuccessListener {
            Toast.makeText(context, "Updated Membership", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(context, "Error! Try Later", Toast.LENGTH_SHORT).show()
        }
    }
}