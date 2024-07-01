package pe.edu.unfv.apppatitas_qbo.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pe.edu.unfv.apppatitas_qbo.model.Mascota

class MascotaAdapter(
    private var lstMascotas: List<Mascota>, private val contex: Context)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }
}