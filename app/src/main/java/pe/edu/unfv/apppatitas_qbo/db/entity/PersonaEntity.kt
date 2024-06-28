package pe.edu.unfv.apppatitas_qbo.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "persona")
data class PersonaEntity (

    @PrimaryKey
    val id: Int,
    val nombres: String,
    val apellidos: String,
    val email: String,
    val celular: String,
    val usuario: String,
    val password: String,
    val esvoluntario: String
)