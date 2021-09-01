package fr.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import fr.todoapp.ui.theme.MyApplicationTheme

class StartActivity : ComponentActivity() {

    private var auth: FirebaseAuth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            MyApplicationTheme {

                Greeting(auth.currentUser?.email)
                
                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                }
            }
            
        }






    }
}

@Composable
fun Greeting(name: String?) {

    Text(text = "Hello $name!")
    CircularProgressIndicator()
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    lateinit var auth: FirebaseAuth

    MyApplicationTheme {
        Greeting("")
    }
}