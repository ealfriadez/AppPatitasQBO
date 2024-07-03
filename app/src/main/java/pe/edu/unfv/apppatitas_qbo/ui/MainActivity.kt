package pe.edu.unfv.apppatitas_qbo.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import pe.edu.unfv.apppatitas_qbo.R
import pe.edu.unfv.apppatitas_qbo.databinding.ActivityMainBinding
import pe.edu.unfv.apppatitas_qbo.viewmodel.PersonaViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var _navView: NavigationView
    private lateinit var _personaViewModel: PersonaViewModel
    private lateinit var _tvNombres: TextView
    private lateinit var _tvCorreo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        _navView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navVoluntario, R.id.navListaMascotas
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        _navView.setupWithNavController(navController)
        mostrarInfoAutenticacion()
    }

    private fun mostrarInfoAutenticacion() {
        _tvNombres = _navView.getHeaderView(0)
            .findViewById(R.id.tvNombres)
        _tvCorreo = _navView.getHeaderView(0)
            .findViewById(R.id.tvCorreo)
        _personaViewModel = ViewModelProvider(this)
            .get(PersonaViewModel::class.java)
        _personaViewModel.obtener().observe(this, Observer{ persona ->
            persona?.let {
                _tvNombres.text = persona.nombres.uppercase()
                _tvCorreo.text = persona.email
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val idItem = item.itemId
        if (idItem == R.id.action_salir){
                startActivity(Intent(this, LoginActivity::class.java))
            finish()
            }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}