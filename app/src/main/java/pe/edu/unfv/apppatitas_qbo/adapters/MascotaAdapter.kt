package pe.edu.unfv.apppatitas_qbo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pe.edu.unfv.apppatitas_qbo.R
import pe.edu.unfv.apppatitas_qbo.model.Mascota

class MascotaAdapter(

    private var lstMascotas: List<Mascota>, private val contex: Context)
    : RecyclerView.Adapter<MascotaAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_mascota, parent, false))
    }

    override fun getItemCount(): Int {
        return lstMascotas.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemMascota = lstMascotas[position]
        holder._tvNomMascota.text = itemMascota.nommascota
        holder._tvLugar.text = itemMascota.lugar
        holder._tvContacto.text = itemMascota.contacto
        holder._tvFecha.text = itemMascota.fechaperdida
        Glide.with(contex).load(itemMascota.urlimagen).into(holder._ivMascota)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val _tvNomMascota: TextView = itemView.findViewById(R.id.tvNomVoluntario)
        val _tvLugar: TextView = itemView.findViewById(R.id.tvApeVoluntario)
        val _tvFecha: TextView = itemView.findViewById(R.id.tvEmail)
        val _tvContacto: TextView = itemView.findViewById(R.id.tvContacto)
        val _ivMascota: ImageView = itemView.findViewById(R.id.ivMascota)
    }
}