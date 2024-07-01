package pe.edu.unfv.apppatitas_qbo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pe.edu.unfv.apppatitas_qbo.R
import pe.edu.unfv.apppatitas_qbo.model.Mascota

class MascotaAdapter(
    private var lstMascotas: List<Mascota>, private val contex: Context)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var _tvNomMascota: TextView
    private lateinit var _tvLugar: TextView
    private lateinit var _tvFecha: TextView
    private lateinit var _tvContacto: TextView
    private lateinit var _ivMascota: ImageView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_mascota, parent, false))
    }

    override fun getItemCount(): Int {
        return lstMascotas.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemMascota = lstMascotas[position]


    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        _tvNomMascota = itemView.findViewById(R.id.tvNomMascota)
        _tvLugar: TextView = itemView.findViewById(R.id.tvLugar)
        val tvFecha: TextView = itemView.findViewById(R.id.tvFecha)
        val tvContacto: TextView = itemView.findViewById(R.id.tvContacto)
        val ivMascota: TextView = itemView.findViewById(R.id.ivMascota)
    }
}