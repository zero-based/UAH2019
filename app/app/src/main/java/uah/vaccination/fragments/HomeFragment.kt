package uah.vaccination.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import uah.vaccination.R
import uah.vaccination.adapters.ParentsAdapter
import uah.vaccination.models.Parent

class HomeFragment : Fragment() {

    private lateinit var adapter: ParentsAdapter
    private var parents = arrayListOf<Parent>()
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        firestore.collection("parents")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    parents.add(document.toObject(Parent::class.java))
                }
            }

        val rootView = inflater.inflate(R.layout.fragment_home, container, false)
        val parentsRecyclerView = rootView.findViewById(R.id.parents_recycler_view) as RecyclerView // Add this
        val searchEditText = rootView.findViewById(R.id.search_edit_text) as EditText // Add this

        adapter = ParentsAdapter(parents)
        parentsRecyclerView.adapter = adapter
        parentsRecyclerView.layoutManager = LinearLayoutManager(activity)

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) = Unit
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                adapter.filter.filter(s.toString())
            }
        })

        return rootView
    }

}
