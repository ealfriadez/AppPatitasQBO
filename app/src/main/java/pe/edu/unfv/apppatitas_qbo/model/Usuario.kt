package pe.edu.unfv.apppatitas_qbo.model

data class Usuario(

    val gender: String,
    val name: Name,
    val location: Location,
    val email: String,
    val dob: Dob,
    val phone: String,
    val cell: String,
    val picture: Picture,
    val nat: String
)
