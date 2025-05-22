package com.example.shoppinglist

// Importiere grundlegende Android- und Jetpack Compose-Komponenten
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.shoppinglist.ui.theme.ShoppingListTheme // eigenes App-Theme

// Die MainActivity ist der Einstiegspunkt der App
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // setContent setzt den Compose-Inhalt für die Aktivität
        setContent {
            // Theme sorgt für einheitliche Farben, Schriftarten usw.
            ShoppingListTheme {
                // Surface stellt den Hintergrundbereich dar
                Surface(modifier = Modifier.fillMaxSize()) {
                    // Unsere selbst definierte UI-Funktion
                    ShoppingListApp()
                }
            }
        }
    }
}

@Composable
fun ShoppingListApp() {
    // Zustand für das Eingabefeld (Text, der gerade eingegeben wird)
    var newItem by remember { mutableStateOf("") }

    // Zustand für die Liste aller Artikel
    var items by remember { mutableStateOf(listOf<String>()) }

    // Layout-Spalte mit Abstand zum Rand
    Column(modifier = Modifier.padding(16.dp)) {

        // Eingabefeld für neue Artikel
        OutlinedTextField(
            value = newItem, // aktueller Text
            onValueChange = { newItem = it }, // bei Änderung: Text aktualisieren
            label = { Text("Artikel eingeben") }, // Beschriftung
            modifier = Modifier.fillMaxWidth() // Eingabefeld nimmt ganze Breite ein
        )

        // Abstand nach dem Textfeld
        Spacer(modifier = Modifier.height(8.dp))

        // Button zum Hinzufügen eines Artikels zur Liste
        Button(
            onClick = {
                if (newItem.isNotBlank()) {
                    // Wenn das Eingabefeld nicht leer ist:
                    items = items + newItem // Artikel zur Liste hinzufügen
                    newItem = "" // Eingabefeld leeren
                }
            }
        ) {
            Text("Hinzufügen") // Text im Button
        }

        // Abstand zwischen Button und Liste
        Spacer(modifier = Modifier.height(16.dp))

        // Liste der Artikel mit LazyColumn (leistungsfähig bei vielen Elementen)
        LazyColumn {
            // Für jedes Element in der Liste: zeige ein Text-Element
            items(items) { item ->
                Text(
                    text = item,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            // Wenn auf den Artikel geklickt wird: entferne ihn aus der Liste
                            items = items - item
                        }
                        .padding(8.dp) // Innenabstand um den Text
                )
            }
        }
    }
}
