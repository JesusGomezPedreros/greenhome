package com.example.greenhome

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.greenhome.databinding.ActivityAuthBinding
import com.example.greenhome.databinding.ActivityHomeBinding
import com.example.greenhome.databinding.ActivitySolicitedBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.time.days

class SolicitedActivity : AppCompatActivity() {

    private val db= FirebaseFirestore.getInstance()
    private lateinit var binding: ActivitySolicitedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySolicitedBinding.inflate(layoutInflater)
        val view =binding.root
        setContentView(view)
        val bundle =intent.extras
        val email = bundle?.getString("email")
        setup(email ?:"" )
    }
    override fun onStart() {
        super.onStart()
        binding.emailtextView2.visibility= View.INVISIBLE
    }
   private fun saveAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Guardado")
        builder.setMessage("se ha guardado  correctamente la solicitud de recogida")
        builder.setPositiveButton("Aceptar",null )
        val dialog: AlertDialog =builder.create()
        dialog.show()
    }

    private fun setup(email: String){
        title="Agendar"
        binding.emailtextView2.text = email
        binding.solicitarbutton.setOnClickListener {
            db.collection("user").document().set(
                    hashMapOf(
                            "Recogida" to  false,
                            "Email" to binding.emailtextView2.text.toString(),
                            "Material" to binding.materialEditText.text.toString(),
                            "Direccion" to binding.addresEditText.text.toString(),
                            "Numero de contacto" to binding.numberEditText.text.toString(),
                            "Nombre de contacto" to binding.nameEditText.text.toString(),
                            "Tipo de material" to binding.tipeEditText.text.toString(),
                            "Fecha de solicitud" to binding.calendarView.date.toString(),

                    )
            )
        }
    }


}