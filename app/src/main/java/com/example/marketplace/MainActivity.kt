package com.example.marketplace

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.marketplace.ui.theme.MarketplaceTheme
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : ComponentActivity() {

    // Instancia de Firestore
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inicializa Firestore
        db = FirebaseFirestore.getInstance()

        setContent {
            MarketplaceTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    UserForm(
                        onAddUser = { name, email ->
                            addUserToFirestore(name, email)  // Llama a la función para agregar usuario
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    // Función para agregar un usuario a Firestore
    private fun addUserToFirestore(name: String, email: String) {
        // Crea un mapa con los datos del usuario
        val user = hashMapOf(
            "name" to name,
            "email" to email
        )

        // Agrega el usuario a la colección "users" en Firestore
        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d("Firestore", "Usuario agregado con ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Error al agregar usuario", e)
            }
    }

    private fun addUsersToFirestore(name: String, email: String, rol: String, phone: Number) {
        // Crea un mapa con los datos del usuario
        val user = hashMapOf(
            "nombre" to name,
            "email" to email,
            "rol" to rol,
            "telefono" to phone
        )

        db.collection("usuarios")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d("Firestore", "Usuario agregado con ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Error al agregar usuario", e)
            }
    }
}

@Composable
fun UserForm(onAddUser: (String, String) -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = {
            onAddUser("Juan Perez", "juan.perez@example.com")
        },
        modifier = modifier
    ) {
        Text("Agregar Usuario")
    }
}

@Preview(showBackground = true)
@Composable
fun UserFormPreview() {
    MarketplaceTheme {
        UserForm(onAddUser = { _, _ -> })
    }
}
