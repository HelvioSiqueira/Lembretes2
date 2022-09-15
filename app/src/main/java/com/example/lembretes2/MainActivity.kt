package com.example.lembretes2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import com.example.lembretes2.adicionar.LembreteFormFragment
import com.example.lembretes2.databinding.ActivityMainBinding
import com.example.lembretes2.lista.ListLembretesFragment

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener,
    MenuItem.OnActionExpandListener {

    private lateinit var binding: ActivityMainBinding

    private var lastSearchTerm: String = ""
    private var searchView: SearchView? = null

    private val listFragment: ListLembretesFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.layout) as ListLembretesFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        showListFragment()

        binding.fabAdd.setOnClickListener {
            LembreteFormFragment.newInstance().open(supportFragmentManager)
        }

    }

    private fun showListFragment() {
        val fragment = ListLembretesFragment.newInstance()

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.layout, fragment, ListLembretesFragment.TAG_LISTA)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.lembrete, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        searchItem?.setOnActionExpandListener(this)
        searchView = searchItem?.actionView as SearchView
        searchView?.queryHint = getString(R.string.hint_search)
        searchView?.setOnQueryTextListener(this)

        if (lastSearchTerm.isNotEmpty()) {
            Handler().post {
                val query = lastSearchTerm
                searchItem.expandActionView()
                searchView?.setQuery(query, true)
                searchView?.clearFocus()
            }
        }
        return true
    }

    override fun onQueryTextSubmit(p0: String?) = true

    override fun onQueryTextChange(newText: String?): Boolean {
        lastSearchTerm = newText ?: ""
        listFragment.search(lastSearchTerm)

        return true
    }

    override fun onMenuItemActionExpand(p0: MenuItem?) = true

    override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
        lastSearchTerm = ""
        listFragment.clearSearch()

        return true
    }
}