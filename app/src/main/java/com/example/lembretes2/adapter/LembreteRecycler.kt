package com.example.lembretes2.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.res.TypedArray
import android.transition.AutoTransition
import android.transition.TransitionManager
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.lembretes2.databinding.ItemLembreteBinding
import com.example.lembretes2.Lembrete
import com.example.lembretes2.R

//A classe LembreteAdapter necessita de uma subclasse do tipo RecyclerView.Adapter que deve ser do
//tipo VH(ViewHolder)

//Uso o ctx para poder ter acesso aos arquivo de layout array
class LembreteAdapter(ctx: Context) : RecyclerView.Adapter<LembreteAdapter.VH>() {

    inner class VH(val binding: ItemLembreteBinding) :
        RecyclerView.ViewHolder(binding.root)

    //Objeto TypedArray que só será iniciado quando for usado a primeira vez
    private val icones: TypedArray by lazy {
        ctx.resources.obtainTypedArray(R.array.icones)
    }

    private val differCallback = object : DiffUtil.ItemCallback<Lembrete>() {
        override fun areItemsTheSame(oldItem: Lembrete, newItem: Lembrete): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: Lembrete, newItem: Lembrete): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.position == newItem.position &&
                    oldItem.titulo == newItem.titulo &&
                    oldItem.texto == newItem.texto &&
                    oldItem.prioridade == newItem.prioridade &&
                    oldItem.data == newItem.data
        }
    }

    private val differ = AsyncListDiffer(this, differCallback)

    var lembretes: List<Lembrete>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {

        val vh = VH(ItemLembreteBinding.inflate(LayoutInflater.from(parent.context), parent, false))

        vh.binding.cardOculto

        vh.binding.btnSeta.setOnClickListener {
            if (vh.binding.cardOculto.visibility == View.VISIBLE) {

                vh.binding.btnSeta.setImageResource(R.drawable.ic_baseline_expand_more_24)

                TransitionManager.beginDelayedTransition(vh.binding.card, AutoTransition())
                vh.binding.cardOculto.visibility = View.GONE

                vh.binding.txtTexto.maxLines = 1
            } else {
                vh.binding.btnSeta.setImageResource(R.drawable.ic_baseline_expand_less_24)

                TransitionManager.beginDelayedTransition(vh.binding.card, AutoTransition())
                vh.binding.cardOculto.visibility = View.VISIBLE

                vh.binding.txtTexto.maxLines = 5
            }
        }

        return vh
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val lembrete = lembretes[position]

        holder.binding.apply {
            txtTitulo.text = lembrete.titulo
            txtTexto.text = lembrete.texto
            icone.setImageDrawable(icones.getDrawable(numPrioridade(lembrete.prioridade)))
            dataAdd.text = lembrete.data
        }

        holder.binding.card.setBackgroundColor(setCor(lembrete.prioridade))
    }

    override fun getItemCount() = lembretes.size

    //Função que retorna a cor que terá o itemView dependendo da "prioridade"
    private fun setCor(prioridade: String): Int {
        return when (prioridade) {
            "Urgente" -> Color.parseColor("#EF5350")
            "Importante" -> Color.parseColor("#42A5F5")
            "Flexivel" -> Color.parseColor("#66BB6A")
            else -> Color.WHITE
        }
    }

    //Transforma a prioridade em um inteiro para usa-lo no obtainTypedArray
    private fun numPrioridade(prioridade: String): Int {
        val ic = when (prioridade) {
            "Urgente" -> 0
            "Importante" -> 1
            "Flexivel" -> 2
            else -> 3
        }
        return ic
    }
}