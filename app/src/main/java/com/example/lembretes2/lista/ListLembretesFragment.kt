package com.example.lembretes2.lista

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lembretes2.BaseFragment
import com.example.lembretes2.Lembrete
import com.example.lembretes2.R
import com.example.lembretes2.adapter.LembreteAdapter
import com.example.lembretes2.databinding.ListLembretesFragmentBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.coroutines.CoroutineContext

//---------------------------------------------------------------------
//---------------------------------------------------------------------
//---------------------------------------------------------------------
//Continuar implementação do swipe (move)
//---------------------------------------------------------------------
//---------------------------------------------------------------------
//---------------------------------------------------------------------

class ListLembretesFragment : BaseFragment<ListLembretesFragmentBinding>() {

    private val viewModel: ListLembretesViewModel by viewModel()
    private val lembreteAdapter by lazy { LembreteAdapter(requireContext()) }

    //Uma lista para ser manipulada a posição dos lembrete
    private var listaLembretes = mutableListOf<Lembrete>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycleView()
        initSwipeGesture()

        obterLembretes()
    }

    override fun onResume() {
        super.onResume()


        Log.d("HSV", "Resume: ${listaLembretes.joinToString(separator = "----")}")
    }

    private fun obterLembretes() {
        if (viewModel.getLembretes().value == null) {
            search()
        }

        viewModel.getLembretes().observe(viewLifecycleOwner, Observer { lembretes ->
            lembreteAdapter.lembretes = lembretes
            listaLembretes = lembretes.toMutableList()
        })
    }

    private fun setupRecycleView() = with(binding) {
        rvLembretes.apply {
            adapter = lembreteAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun initSwipeGesture() {
        val swipe = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.LEFT
        ) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {

                val from: Int = viewHolder.absoluteAdapterPosition
                val to: Int = target.absoluteAdapterPosition

                //Manipulando a posição dos lembrete
                val posAlvo = listaLembretes[to].position

                listaLembretes[to].position = listaLembretes[from].position
                listaLembretes[from].position = posAlvo

                Log.d("HSV", "Posição: ${listaLembretes[from].position}")

                Collections.swap(listaLembretes, from, to)
                lembreteAdapter.notifyItemMoved(from, to)

                lembreteAdapter.lembretes = listaLembretes

                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.absoluteAdapterPosition

                viewModel.delete(lembreteAdapter.lembretes[position])
                listaLembretes.remove(lembreteAdapter.lembretes[position])
                showMessageLembreteDeleted()
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipe)
        itemTouchHelper.attachToRecyclerView(binding.rvLembretes)
    }

    private fun showMessageLembreteDeleted() {
        Snackbar.make(
            requireView(),
            getString(R.string.message_lembrete_deleted),
            Snackbar.LENGTH_LONG
        ).setAction(R.string.undo) {
            viewModel.undoDelete()
        }.show()
    }

    //Quando atualiza a posição dos lembretes no banco de dados quando ele entrar em onStop
    override fun onStop() {
        super.onStop()

        Log.d("HSV", "Pausado: ${listaLembretes.joinToString(separator = "----")}")
        viewModel.move(*listaLembretes.toTypedArray())
        //setupRecycleView()
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ListLembretesFragmentBinding =
        ListLembretesFragmentBinding.inflate(inflater, container, false)

    fun search(term: String = "") {
        viewModel.search(term)
    }

    fun clearSearch() {
        viewModel.search("")
    }

    companion object {
        const val TAG_LISTA = "tagLista"

        fun newInstance() = ListLembretesFragment()
    }
}

