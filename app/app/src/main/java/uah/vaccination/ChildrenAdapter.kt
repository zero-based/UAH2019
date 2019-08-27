package uah.vaccination

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uah.vaccination.models.Child

class ChildrenAdapter(val children: ArrayList<Child>) :
    RecyclerView.Adapter<ChildrenAdapter.ChildHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ChildHolder(LayoutInflater.from(parent.context).inflate(R.layout.child_item, parent, false))

    override fun getItemCount(): Int {
        return children.size
    }

    override fun onBindViewHolder(holder: ChildHolder, position: Int) {
        holder.childName.text = children[position].name
        holder.childBirthDate.text = children[position].dateOfBirth!!.toDate().toLocaleString()
    }

    inner class ChildHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var childName: TextView = itemView.findViewById(R.id.child_name_text_view)
        internal var childBirthDate: TextView = itemView.findViewById(R.id.child_birth_date_text_view)

    }
}