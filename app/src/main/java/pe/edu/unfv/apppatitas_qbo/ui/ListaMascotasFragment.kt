package pe.edu.unfv.apppatitas_qbo.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import pe.edu.unfv.apppatitas_qbo.R
import pe.edu.unfv.apppatitas_qbo.adapters.MascotaAdapter
import pe.edu.unfv.apppatitas_qbo.databinding.FragmentListaMascotasBinding
import pe.edu.unfv.apppatitas_qbo.model.Mascota

class ListaMascotasFragment : Fragment() {

    private lateinit var _queue: RequestQueue
    private var _binding: FragmentListaMascotasBinding? = null
    private val binding get()= _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _queue = Volley.newRequestQueue(context)

        _binding = FragmentListaMascotasBinding.inflate(inflater, container, false)
        val _rvMascota: RecyclerView = binding.rvMascota
        _rvMascota.layoutManager = LinearLayoutManager(context)
        listarMascotasPerdidasWS(binding.rvMascota.context)
        return binding.root
    }

    private fun listarMascotasPerdidasWS(context: Context) {
        val lstMascota: ArrayList<Mascota> = ArrayList()
        val urlWsLista = "http://www.kreapps.biz/patitas/mascotaperdida.php"
        val request = JsonArrayRequest(
            Request.Method.GET,
            urlWsLista,
            null,
            { response ->
                for (i in 0 until response.length()){
                    val itemJson = response.getJSONObject(i)
                    lstMascota.add(
                        Mascota(
                            itemJson["nommascota"].toString(),
                            itemJson["fechaperdida"].toString(),
                            itemJson["urlimagen"].toString(),
                            itemJson["lugar"].toString(),
                            itemJson["contacto"].toString()
                        )
                    )
                }
                binding.rvMascota.adapter = MascotaAdapter(lstMascota, context)
            }, {
                Log.e("LISTAR", it.toString())
            })
        _queue.add(request)
    }
}