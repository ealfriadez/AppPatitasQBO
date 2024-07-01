package pe.edu.unfv.apppatitas_qbo.ui

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
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

class LoginActivity : AppCompatActivity() {

    private lateinit var _queue: RequestQueue
    private lateinit var _preferences: SharedPreferences
    private lateinit var _personaViewModel: PersonaViewModel

    private lateinit var _etUsuario: EditText
    private lateinit var _etPassword: EditText
    private lateinit var _btnLogin: Button
    private lateinit var _btnRegistrar: Button
    private lateinit var _chkRecordar: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        _queue = Volley.newRequestQueue(this)
        _preferences = getSharedPreferences("appPatitas", MODE_PRIVATE)
        _personaViewModel = ViewModelProvider(this).get(PersonaViewModel::class.java)

        _etUsuario = findViewById(R.id.etUsuario)
        _etPassword = findViewById(R.id.etPassword)
        _btnLogin = findViewById(R.id.btnLogin)
        _btnRegistrar = findViewById(R.id.btnRegistrar)
        _chkRecordar = findViewById(R.id.chkRecordar)

        if(verificarValorSharedPreferences()){
            _chkRecordar.isChecked = true
            _personaViewModel.obtener()
                .observe(this, Observer { persona ->
                    persona?.let {
                        _etUsuario.setText(persona.usuario)
                        _etPassword.setText(persona.password)
                    }
                })
        }else{
            _personaViewModel.eliminarTodo()
        }

        _chkRecordar.setOnClickListener {
            setearValoresDeRecordar(it)
        }

        _btnLogin.setOnClickListener {
            _btnLogin.isEnabled = false
            if(validarUsuarioPassword()){
                autenticarUsuarioWS(it, _etUsuario.text.toString(), _etPassword.text.toString())
            }else{
                mostrarMensaje(it, "Ingrese usuario y password")
            }
        }
        _btnRegistrar.setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
            finish()
        }
    }

    private fun setearValoresDeRecordar(vista: View) {
        if(vista is CheckBox){
            val checked: Boolean = vista.isChecked
            when(vista.id){
                R.id.chkRecordar -> {
                    if(!checked){
                        if(verificarValorSharedPreferences()){
                            _personaViewModel.eliminarTodo()
                            _etUsuario.setText("")
                            _etPassword.setText("")
                            _preferences.edit().remove("recordarDatos").apply()
                        }
                    }
                }
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
                val personaEntity = PersonaEntity(
                    response.getString("idpersona").toInt(),
                    response.getString("nombres"),
                    response.getString("apellidos"),
                    response.getString("email"),
                    response.getString("celular"),
                    response.getString("usuario"),
                    response.getString("password"),
                    response.getString("esvoluntario")
                )
                if(verificarValorSharedPreferences()){
                    _personaViewModel.actualizar(personaEntity)
                }else{
                    _personaViewModel.insertar(personaEntity)
                    if(_chkRecordar.isChecked){
                        _preferences.edit().putBoolean("recordarDatos", true).apply()
                    }
                }
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }else{
                mostrarMensaje(vista, response.getString("mensaje"))
            }
            _btnLogin.isEnabled = true
        }, {
            Log.e("LOGIN", it.toString())
            _btnLogin.isEnabled = true
        })
        _queue.add(request)
    }

    fun verificarValorSharedPreferences(): Boolean{
        return _preferences.getBoolean("recordarDatos", false)
    }

    fun validarUsuarioPassword(): Boolean{
        var respuesta = true
        if (_etUsuario.text.toString().trim().isEmpty()){
            _etUsuario.isFocusableInTouchMode = true
            _etUsuario.requestFocus()
            respuesta = false
        }else if (_etPassword.text.toString().trim().isEmpty()){
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