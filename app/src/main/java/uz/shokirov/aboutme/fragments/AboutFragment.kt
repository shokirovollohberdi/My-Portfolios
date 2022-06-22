package uz.shokirov.aboutme.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.firestore.FirebaseFirestore
import uz.shokirov.aboutme.R
import uz.shokirov.aboutme.adapter.SkillsAdapter
import uz.shokirov.aboutme.databinding.FragmentAboutBinding
import uz.shokirov.aboutme.model.Skills

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AboutFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AboutFragment : Fragment() {
    lateinit var binding: FragmentAboutBinding
    lateinit var firebaseFirestore: FirebaseFirestore
    lateinit var list: ArrayList<Skills>
    lateinit var skillsAdapter: SkillsAdapter

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAboutBinding.inflate(layoutInflater)

        firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseFirestore.collection("skills").get().addOnCompleteListener {
            if (it.isSuccessful) {
                list = ArrayList()
                val result = it.result
                result?.forEach { queryDocumentSnapshot ->
                    val skill = queryDocumentSnapshot.toObject(Skills::class.java)
                    list.add(skill)
                }
                skillsAdapter = SkillsAdapter(list)
                binding.rv.adapter = skillsAdapter

            }
        }


        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AboutFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AboutFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}