package com.example.greenhome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.greenhome.databinding.ActivityAuthBinding
import com.example.greenhome.databinding.ActivitySolicitedBinding

class SolicitedActivity : AppCompatActivity() {

private lateinit var binding: ActivitySolicitedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySolicitedBinding.inflate(layoutInflater)
        val view =binding.root
        setContentView(view)

    }
}