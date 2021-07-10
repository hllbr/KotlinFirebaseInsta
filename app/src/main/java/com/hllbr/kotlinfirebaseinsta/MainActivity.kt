package com.hllbr.kotlinfirebaseinsta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth :  FirebaseAuth

    private lateinit var email : String
    private lateinit var password : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Authtentication operation
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        if(currentUser != null){
            val intent = Intent(applicationContext,FeedActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    fun signinClicked(view : View){
        email = userEmailText.text.toString()
        password = passwordText.text.toString()
        if (!email.isEmpty() && !password.isEmpty()){
            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task ->
                if(task.isSuccessful){
                    //User Signed In
                Toast.makeText(applicationContext,"Welcome ${auth.currentUser!!.email.toString()}",Toast.LENGTH_LONG).show()
                val intent = Intent(applicationContext,FeedActivity::class.java)
                    startActivity(intent)
                    finish()
                }

            }.addOnFailureListener { exception->
                Toast.makeText(applicationContext,exception.localizedMessage.toString(),Toast.LENGTH_LONG).show()
            }

        }else{
            Toast.makeText(applicationContext,"mail address or password not set",Toast.LENGTH_LONG).show()
        }
    }
    fun  signupClicked(view : View){
        email = userEmailText.text.toString()
        password = passwordText.text.toString()
        if(email.isNotEmpty() && !password.isNotEmpty()){
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->
                if(task.isSuccessful){
                    //user Sign Up
                    val intent = Intent(applicationContext,FeedActivity::class.java)
                    startActivity(intent)
                    finish()//back key is bloked for this operation
                    //this method used activity destroyed
                }
            }.addOnFailureListener { exception->
                if (exception != null){
                    Toast.makeText(applicationContext,exception.localizedMessage.toString(),Toast.LENGTH_LONG).show()
                }

            }
        }else{
            Toast.makeText(applicationContext,"mail address or password not set",Toast.LENGTH_LONG).show()
        }

    }
}