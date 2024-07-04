package pe.edu.unfv.apppatitas_qbo.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject
import pe.edu.unfv.apppatitas_qbo.R
import pe.edu.unfv.apppatitas_qbo.databinding.FragmentListaMascotasBinding
import pe.edu.unfv.apppatitas_qbo.databinding.FragmentVoluntarioBinding
import pe.edu.unfv.apppatitas_qbo.db.entity.PersonaEntity
import pe.edu.unfv.apppatitas_qbo.viewmodel.PersonaViewModel

class VoluntarioFragment : Fragment() {

    private lateinit var _queue: RequestQueue
    private var _binding: FragmentVoluntarioBinding? = null
    private val binding get()= _binding!!

    private lateinit var _personaViewModel: PersonaViewModel
    private lateinit var _personaEntity: PersonaEntity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _queue = Volley.newRequestQueue(context)
        _personaViewModel = ViewModelProvider(this)[PersonaViewModel::class.java]
        _personaViewModel.obtener()
            .observe(viewLifecycleOwner, Observer { persona ->
                persona?.let {
                    if(persona.esvoluntario == "1"){
                        actualizaFormulario()
                    }else{
                        _personaEntity = persona
                    }
                }
            })

        _binding = FragmentVoluntarioBinding.inflate(inflater, container, false)

        _binding!!.btnRegVoluntario.setOnClickListener{
            if (binding.chkAcepta.isChecked){
                binding.btnRegVoluntario.isEnabled = false
                registrarVoluntarioWS(it)
            }else{
                mostrarMensaje(it, getString(R.string.val_errorTerminos))
            }
        }
        return binding.root
    }

    private fun registrarVoluntarioWS(vista: View) {
        val urlWSVoluntario = "http://www.kreapps.biz/patitas/personavoluntaria.php"
        val parametros = JSONObject()
        parametros.put("idpersona", _personaEntity.id)
        val request = JsonObjectRequest(
            Request.Method.POST,
            urlWSVoluntario,
            parametros,
            { response ->
                if (response.getBoolean("rpta")){
                    val nuevaPersonaEntity = PersonaEntity(
                        _personaEntity.id,
                        _personaEntity.nombres,
                        _personaEntity.apellidos,
                        _personaEntity.email,
                        _personaEntity.celular,
                        _personaEntity.usuario,
                        _personaEntity.password,
                        "1"
                    )
                    Log.e("REGISTRO", _personaEntity.id.toString())
                    _personaViewModel.actualizar(nuevaPersonaEntity)
                    actualizaFormulario()
                }
                mostrarMensaje(vista, response.getString("mensaje"))
                binding.btnRegVoluntario.isEnabled = true
            }, {
                binding.btnRegVoluntario.isEnabled = true
            }
        )
        _queue.add(request)
    }

    private fun actualizaFormulario(){

        binding.tvTextoTerminos.visibility = View.GONE
        binding.btnRegVoluntario.visibility = View.GONE
        binding.chkAcepta.visibility = View.GONE
        binding.tvTituVoluntario.text = getString(R.string.valEsVoluntario)
    }

    fun mostrarMensaje(vista: View, mensaje: String){
        Snackbar.make(vista, mensaje, Snackbar.LENGTH_LONG).show()
    }
}