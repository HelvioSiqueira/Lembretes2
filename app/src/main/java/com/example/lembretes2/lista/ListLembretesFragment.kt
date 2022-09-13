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
import com.example.lembretes2.adapter.LembreteAdapter
import com.example.lembretes2.databinding.ListLembretesFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListLembretesFragment : BaseFragment<ListLembretesFragmentBinding>() {

    private val viewModel: ListLembretesViewModel by viewModel()
    private val lembreteAdapter by lazy { LembreteAdapter(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycleView()
        initSwipeGesture()

        if (viewModel.getLembretes().value == null) {
            search()
        }

        viewModel.getLembretes().observe(viewLifecycleOwner, Observer { lembretes ->
            lembreteAdapter.lembretes = lembretes

            Log.d("HSV", lembretes.joinToString(separator = ","))
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

                Log.d("HSV", lembreteAdapter.lembretes[to].toString())

                //Collections.swap(lembreteAdapter.lembretes, from, to)
                lembreteAdapter.notifyItemMoved(from, to)

                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.absoluteAdapterPosition

                viewModel.delete(lembreteAdapter.lembretes[position])

                lembreteAdapter.notifyItemRemoved(position)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipe)
        itemTouchHelper.attachToRecyclerView(binding.rvLembretes)
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ListLembretesFragmentBinding =
        ListLembretesFragmentBinding.inflate(inflater, container, false)

    fun search(term: String = "") {
        viewModel.search(term)
    }

    companion object {
        const val TAG_LISTA = "tagLista"

        fun newInstance() = ListLembretesFragment()
    }
}

