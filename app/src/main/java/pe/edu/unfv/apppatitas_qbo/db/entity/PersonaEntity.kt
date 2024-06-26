package pe.edu.unfv.apppatitas_qbo.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "persona")
data class PersonaEntity (

    @PrimaryKey
    var id: String,
    var nombres: String,
    var apellidos: String,
    var email: String,
    var celular: String,
    var usuario: String,
    var password: String,
    var esvoluntario: String
)