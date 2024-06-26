package pe.edu.unfv.apppatitas_qbo.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import pe.edu.unfv.apppatitas_qbo.db.entity.PersonaEntity

@Dao
interface PersonaDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertar(persona: PersonaEntity)

    @Update
    suspend fun actualizar(persona: PersonaEntity)

    @Query("DELETE FROM persona")
    suspend fun eliminarTodo()

    @Query("SELECT * FROM persona LIMIT 1")
    fun obtener(): LiveData<PersonaEntity>
}