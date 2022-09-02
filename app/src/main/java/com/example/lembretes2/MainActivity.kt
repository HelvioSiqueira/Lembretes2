package com.example.lembretes2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lembretes2.adicionar.LembreteFormFragment
import com.example.lembretes2.databinding.ActivityMainBinding
import com.example.lembretes2.lista.ListLembretesFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        showListFragment()

        binding.fabAdd.setOnClickListener {
            LembreteFormFragment.newInstance().open(supportFragmentManager)
        }

    }

    private fun showListFragment(){
        val fragment = ListLembretesFragment.newInstance()

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.layout, fragment, ListLembretesFragment.TAG_LISTA)
            .commit()
    }
}