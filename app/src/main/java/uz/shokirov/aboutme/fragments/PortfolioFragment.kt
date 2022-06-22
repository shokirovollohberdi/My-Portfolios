package uz.shokirov.aboutme.fragments

import android.Manifest
import android.app.DownloadManager
import android.content.Context.DOWNLOAD_SERVICE
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import coil.load
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.firestore.FirebaseFirestore
import uz.shokirov.aboutme.R
import uz.shokirov.aboutme.adapter.OnClick
import uz.shokirov.aboutme.adapter.PortfolioAdapter
import uz.shokirov.aboutme.databinding.BottomSheetBinding
import uz.shokirov.aboutme.databinding.FragmentPortfolioBinding
import uz.shokirov.aboutme.model.Portfolios


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PortfolioFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PortfolioFragment : Fragment() {
    lateinit var binding: FragmentPortfolioBinding
    lateinit var firebaseFirestore: FirebaseFirestore
    lateinit var list: ArrayList<Portfolios>

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
        binding = FragmentPortfolioBinding.inflate(layoutInflater)

        firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseFirestore.collection("portfolio").get().addOnCompleteListener {
            if (it.isSuccessful) {
                list = ArrayList()
                var result = it.result
                result?.forEach { queryDocumentSnapshot ->
                    val portfolio = queryDocumentSnapshot.toObject(Portfolios::class.java)
                    list.add(portfolio)
                }
                var adapter = PortfolioAdapter(list, object : OnClick {
                    override fun click(portfolios: Portfolios, position: Int) {
                        var bottomSheetDialog = BottomSheetDialog(context!!, R.style.SheetDialog)
                        var item = BottomSheetBinding.inflate(layoutInflater)
                        bottomSheetDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                        bottomSheetDialog.setContentView(item.root)
                        item.tvName.text = portfolios.name
                        item.screenshotImahge.load(portfolios.screenShotUrl)
                        item.tvSize.text = "Download(${portfolios.size}MB)"
                        item.cardDownload.setOnClickListener {
                            download(portfolios.apkUrl)
                        }
                        bottomSheetDialog.show()
                    }
                })
                binding.rv.adapter = adapter
            }
        }


        return binding.root
    }

    private fun download(apkUrl: String?) {

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            // this will request for permission when user has not granted permission for the app
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                1
            )
        } else {
            //Download Script
            val downloadManager =
                requireActivity().baseContext.getSystemService(DOWNLOAD_SERVICE) as DownloadManager
            val uri: Uri = Uri.parse("$apkUrl")
            val request = DownloadManager.Request(uri)
            request.setVisibleInDownloadsUi(true)
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                uri.lastPathSegment
            )
            downloadManager.enqueue(request)
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PortfolioFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PortfolioFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}