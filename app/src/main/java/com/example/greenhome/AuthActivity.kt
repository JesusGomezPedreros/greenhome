package com.example.greenhome

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.greenhome.databinding.ActivityAuthBinding
import com.example.greenhome.databinding.ActivityHomeBinding
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth


class AuthActivity : AppCompatActivity() {
    //Binding se utiliza para remplazar id 'kotlin-android-extensions' ya que se dejara de utilizar
    //crear private para binding
    private lateinit var binding: ActivityAuthBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        val view =binding.root
        setContentView(view)

        //setup
        setup()
        session()

    }

    override fun onStart() {
        super.onStart()
        binding.authLayout.visibility=View.VISIBLE
    }
    private fun session(){
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email=prefs.getString("email", null)
        if(email !=null){
            binding.authLayout.visibility = View.INVISIBLE
            showHome(email)
        }

    }
    private fun setup(){
        title="Auntenticacion"
        binding.singUpButton.setOnClickListener {

            if (binding.emailEditText.text.isNotEmpty() && binding.passwordEditText.text.isNotEmpty()){
                 FirebaseAuth.getInstance().createUserWithEmailAndPassword(binding.emailEditText.text.toString(),binding.passwordEditText.text.toString()).addOnCompleteListener {
                     if (it.isSuccessful){
                        showHome(it.result?.user?.email ?:"")//,ProviderType.BASIC)
                     }else{
                         showAlert()
                     }
                 }
            }
        }
        binding.loginButton.setOnClickListener {
            if (binding.emailEditText.text.isNotEmpty() && binding.passwordEditText.text.isNotEmpty()){
                FirebaseAuth.getInstance().signInWithEmailAndPassword(binding.emailEditText.text.toString(),binding.passwordEditText.text.toString()).addOnCompleteListener {
                    if (it.isSuccessful){
                        showHome(it.result?.user?.email ?:"")//,ProviderType.BASIC)
                    }else{
                        showAlert()
                    }
                }
            }
        }
    }

    private fun showAlert(){
        val builder =AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando el usuario")
        builder.setPositiveButton("Aceptar",null )
        val dialog:AlertDialog=builder.create()
        dialog.show()

    }

    private fun showHome(email: String){
        val homeIntent = Intent(this, HomeActivity::class.java).apply {
            putExtra("email", email)
            //putExtra("provider", provider.name)
        }
        startActivity(homeIntent)
    }
}


