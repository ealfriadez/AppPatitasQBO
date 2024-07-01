package pe.edu.unfv.apppatitas_qbo.ui

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject
import pe.edu.unfv.apppatitas_qbo.R
import pe.edu.unfv.apppatitas_qbo.db.entity.PersonaEntity
import pe.edu.unfv.apppatitas_qbo.viewmodel.PersonaViewModel

class RegistroActivity : AppCompatActivity() {

    private lateinit var _queue: RequestQueue

    private lateinit var _etNombreReg: EditText
    private lateinit var _etApellidoReg: EditText
    private lateinit var _etCorreoReg: EditText
    private lateinit var _etNumeroReg: EditText
    private lateinit var _etUsurarioReg: EditText
    private lateinit var _etPasswordReg: EditText
    private lateinit var _btnRegistrarUsario: Button
    private lateinit var _btnVolverLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        _queue = Volley.newRequestQueue(this)

        _etNombreReg = findViewById(R.id.etNombreReg)
        _etApellidoReg = findViewById(R.id.etApellidoReg)
        _etCorreoReg = findViewById(R.id.etCorreoReg)
        _etNumeroReg = findViewById(R.id.etNumeroReg)
        _etUsurarioReg = findViewById(R.id.etUsuarioReg)
        _etPasswordReg = findViewById(R.id.etPasswordReg)
        _btnRegistrarUsario = findViewById(R.id.btnRegistrarUsuario)
        _btnVolverLogin = findViewById(R.id.btnVolverLogin)

        _btnVolverLogin.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        _btnRegistrarUsario.setOnClickListener{
            _btnRegistrarUsario.isEnabled = false
            if(validarFormulario(it)){
                registrarUsuarioWS(it)
            }else{
                _btnRegistrarUsario.isEnabled = true
            }
        }
    }

    private fun registrarUsuarioWS(vista: View) {
        val urlWsRegistro = "http://www.kreapps.biz/patitas/persona.php"
        val parametros = JSONObject()
        parametros.put("nombres", _etNombreReg.text.toString())
        parametros.put("apellidos", _etApellidoReg.text.toString())
        parametros.put("email", _etCorreoReg.text.toString())
        parametros.put("celular", _etNumeroReg.text.toString())
        parametros.put("usuario", _etUsurarioReg.text.toString())
        parametros.put("password", _etPasswordReg.text.toString())
        val request = JsonObjectRequest(Request.Method.PUT, urlWsRegistro, parametros,
            { response ->
                if (response.getBoolean("rpta")){
                    setearControles()
                }
                mostrarMensaje(vista, response.getString("mensaje"))
                _btnRegistrarUsario.isEnabled = true
            }, {
                Log.e("REGISTRO", it.message.toString())
                _btnRegistrarUsario.isEnabled = true
            }
        )
        _queue.add(request)
    }

    private fun validarFormulario(vista: View): Boolean{
        var respuesta = true
        when{
            _etNombreReg.text.toString().trim().isEmpty() -> {
                _etNombreReg.isFocusableInTouchMode = true
                _etNombreReg.requestFocus()
                mostrarMensaje(vista, "Ingrese nombres")
                respuesta = false
            }
            _etApellidoReg.text.toString().trim().isEmpty() -> {
                _etApellidoReg.isFocusableInTouchMode = true
                _etApellidoReg.requestFocus()
                mostrarMensaje(vista, "Ingrese apellidos")
                respuesta = false
            }
            _etCorreoReg.text.toString().trim().isEmpty() -> {
                _etCorreoReg.isFocusableInTouchMode = true
                _etCorreoReg.requestFocus()
                mostrarMensaje(vista, "Ingrese correo")
                respuesta = false
            }
            _etNumeroReg.text.toString().trim().isEmpty() -> {
                _etNumeroReg.isFocusableInTouchMode = true
                _etNumeroReg.requestFocus()
                mostrarMensaje(vista, "Ingrese numero telefonico")
                respuesta = false
            }
            _etUsurarioReg.text.toString().trim().isEmpty() -> {
                _etUsurarioReg.isFocusableInTouchMode = true
                _etUsurarioReg.requestFocus()
                mostrarMensaje(vista, "Ingrese usuario")
                respuesta = false
            }
            _etPasswordReg.text.toString().trim().isEmpty() -> {
                _etPasswordReg.isFocusableInTouchMode = true
                _etPasswordReg.requestFocus()
                mostrarMensaje(vista, "Ingrese password")
                respuesta = false
            }
        }
        return respuesta
    }

    private fun setearControles(){
        _etNombreReg.setText("")
        _etApellidoReg.setText("")
        _etCorreoReg.setText("")
        _etNumeroReg.setText("")
        _etUsurarioReg.setText("")
        _etPasswordReg.setText("")
    }

    fun mostrarMensaje(vista: View, mensaje: String){
        Snackbar.make(vista, mensaje, Snackbar.LENGTH_LONG).show()
    }
}