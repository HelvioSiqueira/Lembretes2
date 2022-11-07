# App de Lembretes usando RecyclerView

O app consiste em uma lista de tarefas com funções(até o momento) de adicionar, mover e excluir as tarefas. Nele a parte mais importante do código é
sem dúvida o RecyclerView que adapta lista da forma que queremos e deixa mais fácil implementar funções como mover e excluir com um gesto cada item da lista.
Vamos ao código:

## RecyclerView

A classe RecycleView(arquivo LembreteAdapter) obrigatoriamente tem que ter 3 métodos ```onCreateViewHolder()```, ```onBindViewHolder()``` e o ```getItemCount()```

### onCreateViewHolder()

```
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_lembrete, parent, false)

        return VH(v)
    }
```
Esse método será o responsável por criar a instância do ViewHolder(Método que inicializa os atributos do lembrete sem precisar ficar chamando o findViewById ou usar binding de views) a partir do layout que representa cada item da lista.

### onBindViewHolder()

```
    override fun onBindViewHolder(holder: VH, pos: Int){
        val (titulo, texto, prioridade) = lembretes[pos]

        holder.txtTitle.text = titulo
        holder.txtText.text = texto

        holder.itemView.setBackgroundColor(setCor(prioridade))
        holder.icone.setImageDrawable(icones.getDrawable(numPrioridade(prioridade)))
    }
```
É onde a lista é realmente preenchida com as informações do objeto

### getItemCount()

```
override fun getItemCount(): Int = lembretes.size
```

Define a quantidade de itens(lembretes) que a lista terá(Nesse casso será infinita)

## Ações com gestos

Outra funcionalidade importante do app é a capacidade de realizar ações com gestos: deslizar pra esquerda pra excluir e reordenar a lista movendo os lembretes
pra cima ou pra baixo

```
    private fun initSwipeGesture(){

        val swipe = object: ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT){

            override fun onMove(
                recyclerView: RecyclerView ,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {

                val from: Int = viewHolder.absoluteAdapterPosition
                val to: Int = target.absoluteAdapterPosition

                Collections.swap(lembretes, from, to)
                adapter.notifyItemMoved(from, to)

                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.absoluteAdapterPosition
                lembretes.removeAt(position)
                adapter.notifyItemRemoved(position)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipe)
        itemTouchHelper.attachToRecyclerView(rvLembretes)
    }
```
O ItemTouchHelper é responsavel por permitir os gestos na RecyclerView. Com ele podemos configurar as direções que poderão ser movidas para cada ação, (se o ```ItemTouchHelper.UP or ItemTouchHelper.DOWN``` fosse 0 então os lembretes não poderiam ser movidos) e as ações que terão cada movimento: Reordenar com o ```onMove()``` e excluir com o ```onSwiped()```.
