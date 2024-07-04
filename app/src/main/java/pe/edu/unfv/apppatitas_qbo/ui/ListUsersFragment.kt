package pe.edu.unfv.apppatitas_qbo.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import pe.edu.unfv.apppatitas_qbo.adapters.UsuarioAdapter
import pe.edu.unfv.apppatitas_qbo.databinding.FragmentListUsersBinding
import pe.edu.unfv.apppatitas_qbo.model.Usuario

class ListUsersFragment : Fragment() {

    private lateinit var _queue: RequestQueue
    private var _binding: FragmentListUsersBinding? = null
    private val binding get()= _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
            _queue = Volley.newRequestQueue(context)

            _binding = FragmentListUsersBinding.inflate(inflater, container, false)
            val _rvListUsers: RecyclerView = binding.rvListUsers
            _rvListUsers.layoutManager = LinearLayoutManager(context)
            listarUsuariosWS(binding.rvListUsers.context)
            return binding.root
    }

    private fun listarUsuariosWS(context: Context) {
        val lstUsuaruios: ArrayList<Usuario> = ArrayList()
        val urlWsLista = "https://reqres.in/api/users"
        val request = JsonObjectRequest(Request.Method.GET, urlWsLista, null, {
            result ->
                val jsonArray = result.getJSONArray("data")
                for (i in 0 until jsonArray.length()){
                    val jsonObj = jsonArray.getJSONObject(i)

                    val _usuario = Usuario(
                        jsonObj.getString("email"),
                        jsonObj.getString("first_name"),
                        jsonObj.getString("last_name"),
                        jsonObj.getString("avatar")
                    )
                    lstUsuaruios.add(_usuario)
                    Log.d("LISTAR", lstUsuaruios.toString())
                }
            binding.rvListUsers.adapter = UsuarioAdapter(lstUsuaruios, context)
        }, { err ->
                Log.e("LISTAR", err.message.toString())
        })

        _queue.add(request)
    }
}