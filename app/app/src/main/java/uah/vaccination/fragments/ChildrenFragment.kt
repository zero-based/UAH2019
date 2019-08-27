package uah.vaccination.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.dialog_add_child.view.*
import kotlinx.android.synthetic.main.fragment_children.*
import kotlinx.android.synthetic.main.fragment_children.view.*
import uah.vaccination.ChildrenAdapter
import uah.vaccination.R
import uah.vaccination.models.Child
import java.util.*


class ChildrenFragment : Fragment() {
    lateinit var dialogView: View
    lateinit var adapter: ChildrenAdapter
    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        db.collection("parents")
            .document(auth.currentUser!!.email.toString())
            .get().addOnSuccessListener {
                val childrenDocs = it.data!!["children"] as ArrayList<HashMap<String, Object>>
                Log.d("lol", childrenDocs.toString())
                val children = arrayListOf<Child>()
                childrenDocs.forEach { doc ->
                    children.add(
                        Child(
                            name = doc["name"].toString(),
                            dateOfBirth = doc["dateOfBirth"] as Timestamp
                        )
                    )
                }

                adapter = ChildrenAdapter(children)
                children_recycler_view.adapter = adapter
                children_recycler_view.layoutManager = LinearLayoutManager(context)
            }
        return inflater.inflate(R.layout.fragment_children, container, false)
    }


    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.fab.setOnClickListener {

            dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_child, null)
            val dialogBuilder =
                AlertDialog.Builder(context).setView(dialogView).setTitle("إضافة طفل")
            val dialog = dialogBuilder.show()

            dialogView.sign_up_button.setOnClickListener {
                if (validateInput()) {
                    val child = Child(
                        name = dialogView.name_edit_text.text.toString(),
                        surname = dialogView.surname_edit_text.text.toString(),
                        dateOfBirth = Timestamp(Date(dialogView.dateOfBirth_edit_text.text.toString()))
                    )
                    Log.d("lol", child.dateOfBirth.toString())
                    addNewChild(child)
                    adapter.children.add(child)
                    adapter.notifyItemInserted(adapter.children.lastIndex)
                    dialog.dismiss()
                }
            }

        }
    }

    private fun validateInput(): Boolean {
        val isNameEmpty = dialogView.name_edit_text.text.isNullOrEmpty()
        val isSurnameEmpty = dialogView.surname_edit_text.text.isNullOrEmpty()
        val isDateOfBirthEmpty = dialogView.dateOfBirth_edit_text.text.isNullOrEmpty()

        if (!isNameEmpty &&
            !isSurnameEmpty &&
            !isDateOfBirthEmpty
        )
            return true
        else {
            if (isNameEmpty) dialogView.name_edit_text.error = "Required Field."
            if (isSurnameEmpty) dialogView.surname_edit_text.error = "Required Field."
            if (isDateOfBirthEmpty) dialogView.dateOfBirth_edit_text.error = "Required Field."
        }
        return false
    }

    private fun addNewChild(child: Child) {
        db.collection("parents").document(auth.currentUser!!.email.toString())
            .update("children", FieldValue.arrayUnion(child))
    }
}
