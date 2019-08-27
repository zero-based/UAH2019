package uah.vaccination.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uah.vaccination.R
import uah.vaccination.models.Parent

class ParentsAdapter(val parents: ArrayList<Parent>) :
    RecyclerView.Adapter<ParentsAdapter.ParentHolder>(), Filterable {

    private var filteredParents = parents

    override fun getItemCount() = filteredParents.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ParentHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_parent, parent, false))

    override fun onBindViewHolder(holder: ParentHolder, position: Int) {
        val parent = filteredParents[position]
        holder.emailTextView.text = parent.email
    }

    override fun getFilter() = filteringStrategy

    private val filteringStrategy: Filter = object : Filter() {

        override fun performFiltering(p0: CharSequence?): FilterResults {
            val query = p0.toString()
            val filteredList = if (query.isNotEmpty()) {
                parents.filter { it.email!!.contains(query, true) } as ArrayList<Parent>
            } else {
                parents
            }
            return FilterResults().also { it.values = filteredList }
        }

        @Suppress("UNCHECKED_CAST")
        override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
            filteredParents = p1!!.values as ArrayList<Parent>
            notifyDataSetChanged()
        }

    }

    inner class ParentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var emailTextView: TextView = itemView.findViewById(R.id.email_text_view)
    }

}