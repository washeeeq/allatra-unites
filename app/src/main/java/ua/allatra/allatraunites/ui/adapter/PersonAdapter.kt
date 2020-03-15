package ua.allatra.allatraunites.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.person_row.view.*
import ua.allatra.allatraunites.R

class PersonAdapter(var personList: MutableList<String>):
    RecyclerView.Adapter<PersonAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.person_row, parent, false
        )
    )

    override fun getItemCount() = personList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: String) {
            itemView.txtPerson.text = item
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(personList[position])
}