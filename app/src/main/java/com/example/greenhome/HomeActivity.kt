package com.example.greenhome

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.greenhome.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth

enum class ProviderType(){
    BASIC
}

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //setup
        val bundle =intent.extras
        val email = bundle?.getString("email")
        val provider = bundle?.getString( "Provider")
        setup(email ?:"", provider ?:"")

        //Guardado de sesion
        val prefs=getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.putString("email", email)
        //prefs.putString("provider", provider)
        prefs.apply()
        solicitedPickup()
    }


    private fun setup(email: String, provider: String){
        title="Inicio"
        binding.emailtextView.text=email
        binding.providertextView.text=provider

        binding.logOutButton.setOnClickListener{
            //borrar los datos de sesion
            val prefs=getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
            prefs.clear()
            prefs.apply()
            //finaliza sesion de firebase
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }

    }
    private fun solicitedPickup()
    {
        binding.solicitarButton.setOnClickListener{
            showSolicited()
        }
    }
    private fun showSolicited() {
        val solicitedActivity = Intent(this, SolicitedActivity::class.java).apply {
            putExtra("email", binding.emailtextView.text.toString())
        }
        startActivity(solicitedActivity)
    }

}