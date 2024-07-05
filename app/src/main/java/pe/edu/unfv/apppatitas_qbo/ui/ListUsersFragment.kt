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
import pe.edu.unfv.apppatitas_qbo.model.Dob
import pe.edu.unfv.apppatitas_qbo.model.Location
import pe.edu.unfv.apppatitas_qbo.model.Name
import pe.edu.unfv.apppatitas_qbo.model.Picture
import pe.edu.unfv.apppatitas_qbo.model.Street
import pe.edu.unfv.apppatitas_qbo.model.Usuario

class ListUsersFragment : Fragment() {

    private lateinit var _queue: RequestQueue
    private var _binding: FragmentListUsersBinding? = null
    private val binding get()= _binding!!
    var userList = arrayListOf<Usuario>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
            _queue = Volley.newRequestQueue(context)

            _binding = FragmentListUsersBinding.inflate(inflater, container, false)
            val _rvListUsers: RecyclerView = binding.rvListUsers
            _rvListUsers.layoutManager = LinearLayoutManager(context)
            listarUsuariosWSNewApi(binding.rvListUsers.context)
            return binding.root
    }

    private fun listarUsuariosWSNewApi(context: Context) {
        val urlWsLista = "https://randomuser.me/api/?results=10"
        val _request = JsonObjectRequest(Request.Method.GET, urlWsLista, null, {
            result ->
                val jsonArray = result.getJSONArray("results")
                for (i in 0 until  jsonArray.length()){

                    val jsonObjetLocationStreet = jsonArray.getJSONObject(i).getJSONObject("location").getJSONObject("street")
                    val _street = Street(
                        jsonObjetLocationStreet.getInt("number"),
                        jsonObjetLocationStreet.getString("name")
                    )

                    val jsonObjetLocation = jsonArray.getJSONObject(i).getJSONObject("location")
                    val _location = Location(
                        _street,
                        jsonObjetLocation.getString("city"),
                        jsonObjetLocation.getString("state"),
                        jsonObjetLocation.getString("country")
                    )

                    val jsonObjetName = jsonArray.getJSONObject(i).getJSONObject("name")
                    val _name = Name(
                        jsonObjetName.getString("title"),
                        jsonObjetName.getString("first"),
                        jsonObjetName.getString("last")
                    )

                    val jsonObjetDob = jsonArray.getJSONObject(i).getJSONObject("dob")
                    val _dob = Dob(
                        jsonObjetDob.getString("date"),
                        jsonObjetDob.getInt("age")
                    )

                    val jsonObjetPicture = jsonArray.getJSONObject(i).getJSONObject("picture")
                    val _picture = Picture(
                        jsonObjetPicture.getString("large")
                    )

                    val jsonObject = jsonArray.getJSONObject(i)
                    val _usuario = Usuario(
                        jsonObject.getString("gender"),
                        _name,
                        _location,
                        jsonObject.getString("email"),
                        _dob,
                        jsonObject.getString("phone"),
                        jsonObject.getString("cell"),
                        _picture,
                        jsonObject.getString("nat")
                    )

                    userList.add(_usuario)
                    Log.d("LISTAR", userList.toString())
                }
            binding.rvListUsers.adapter = UsuarioAdapter(userList, context)
        }, { err ->
            Log.e("LISTAR", err.message.toString())
        })
        _queue.add(_request)
    }

}