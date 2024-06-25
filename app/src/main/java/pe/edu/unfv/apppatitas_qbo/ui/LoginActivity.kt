package pe.edu.unfv.apppatitas_qbo.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject
import pe.edu.unfv.apppatitas_qbo.R

class LoginActivity : AppCompatActivity() {

    private lateinit var _queue: RequestQueue

    private lateinit var _etUsuario: EditText
    private lateinit var _etPassword: EditText
    private lateinit var _btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        _queue = Volley.newRequestQueue(this)

        _etUsuario = findViewById(R.id.etUsuario)
        _etPassword = findViewById(R.id.etPassword)
        _btnLogin = findViewById(R.id.btnLogin)

        _btnLogin.setOnClickListener {
            _btnLogin.isEnabled = false
            if(validarUsuarioPassword()){
                autenticarUsuarioWS(it, _etUsuario.text.toString(), _etPassword.text.toString())
            }else{
                mostrarMensaje(it, "Ingrese usuario y password")
            }
        }
    }

    private fun autenticarUsuarioWS(vista: View, _etUsuario: String, _etPassword: String) {
        val urlWsLogin = "http://www.kreapps.biz/patitas/login.php"
        val parametros = JSONObject()
        parametros.put("usuario", _etUsuario)
        parametros.put("password", _etPassword)
        val request = JsonObjectRequest(Request.Method.POST, urlWsLogin, parametros, {response ->
            if (response.getBoolean("rpta")){
                startActivity(Intent(this, MainActivity::class.java))
            }else{
                mostrarMensaje(vista, response.getString("mensaje"))
            }
        }, {
            Log.e("LOGIN", it.toString())
            _btnLogin.isEnabled = true
        })
    }

    fun validarUsuarioPassword(): Boolean{
        var respuesta = true
        if (_etUsuario.text.toString().trim().isNullOrEmpty()){
            _etUsuario.isFocusableInTouchMode = true
            _etUsuario.requestFocus()
            respuesta = false
        }else if (_etPassword.text.toString().trim().isNullOrEmpty()){
            _etPassword.isFocusableInTouchMode = true
            _etPassword.requestFocus()
            respuesta = false
        }
        return respuesta
    }

    fun mostrarMensaje(vista: View, mensaje: String){
        Snackbar.make(vista, mensaje, Snackbar.LENGTH_LONG).show()
    }
}