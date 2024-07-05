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
import pe.edu.unfv.apppatitas_qbo.model.Usuario

class UsuarioAdapter(
    private var lstUsuarios: List<Usuario>, private val context: Context)
    : RecyclerView.Adapter<UsuarioAdapter.ViewHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioAdapter.ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            return ViewHolder(layoutInflater.inflate(R.layout.item_user, parent, false))
        }

        override fun getItemCount(): Int {
            return lstUsuarios.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val itemUsuario = lstUsuarios[position]
            holder._tvNomVoluntario.text = itemUsuario.name.title.plus(" " + itemUsuario.name.first.plus(" " + itemUsuario.name.last.plus(" - ".plus(itemUsuario.dob.age))))
            holder._tvApeVoluntario.text = itemUsuario.location.street.name.toString().plus(" ".plus(itemUsuario.location.street.number))
            holder._tvEmail.text = itemUsuario.location.city.plus("/".plus(itemUsuario.location.state).plus("/".plus(itemUsuario.location.country)))
            holder._tvTelefono.text = itemUsuario.phone.plus("/".plus(itemUsuario.cell))
            holder._tvEmail_.text = itemUsuario.email
            holder._tvNat.text = itemUsuario.nat
            Glide.with(context).load(itemUsuario.picture.large).into(holder._ivMascota)
        }

        class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            val _tvNomVoluntario = itemView.findViewById<TextView>(R.id.tvNomVoluntario)
            val _tvApeVoluntario = itemView.findViewById<TextView>(R.id.tvApeVoluntario)
            val _tvEmail = itemView.findViewById<TextView>(R.id.tvEmail)
            val _tvTelefono = itemView.findViewById<TextView>(R.id.tvTelefono)
            val _tvEmail_ = itemView.findViewById<TextView>(R.id.tvEmail_)
            val _tvNat= itemView.findViewById<TextView>(R.id.tvNat)
            val _ivMascota= itemView.findViewById<ImageView>(R.id.ivMascota)
        }
}