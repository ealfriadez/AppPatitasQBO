package pe.edu.unfv.apppatitas_qbo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pe.edu.unfv.apppatitas_qbo.db.PatitasRoomDatabase
import pe.edu.unfv.apppatitas_qbo.db.entity.PersonaEntity
import pe.edu.unfv.apppatitas_qbo.repository.PersonaRepository

class PersonaViewModel(application: Application): AndroidViewModel(application) {

    private val repository: PersonaRepository

    init{
        val personaDao = PatitasRoomDatabase.getDataBase(application).personaDao()
        repository = PersonaRepository(personaDao)
    }

    fun insertar(personaEntity: PersonaEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertar(personaEntity)
    }

    fun actualizar(personaEntity: PersonaEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.actualizar(personaEntity)
    }

    fun eliminarTodo() = viewModelScope.launch(Dispatchers.IO) {
        repository.eliminarTodo()
    }

    fun obtener(): LiveData<PersonaEntity>{
        return repository.obtener()
    }
}