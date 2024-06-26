package pe.edu.unfv.apppatitas_qbo.repository

import androidx.lifecycle.LiveData
import pe.edu.unfv.apppatitas_qbo.db.dao.PersonaDao
import pe.edu.unfv.apppatitas_qbo.db.entity.PersonaEntity

class PersonaRepository(private val personaDao: PersonaDao) {

    suspend fun insertar(personaEntity: PersonaEntity){
        personaDao.insertar(personaEntity)
    }

    suspend fun actualizar(personaEntity: PersonaEntity){
        personaDao.actualizar(personaEntity)
    }

    suspend fun eliminarTodo(){
        personaDao.eliminarTodo()
    }

    fun obtener(): LiveData<PersonaEntity>{
        return personaDao.obtener()
    }
}