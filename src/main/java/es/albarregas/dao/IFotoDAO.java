/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.albarregas.dao;

import es.albarregas.beans.Articulo;
import es.albarregas.beans.Foto;
import java.util.List;

/**
 *
 * @author jesus
 */
public interface IFotoDAO {
    
    /**
     * Obtiene las fotos cuyo id está dentro de un rango de valores
     * @param registros Valores de los diferentes id que se pretenden obtener
     * @return Lista de objetos fotos
     */
    public List<Articulo> getFotosByCondicion (String[] registros);
    
    /**
     * Elimina una foto de un artículo
     * @param foto Objeto foto que se pretende eliminar
     */
    public void delete(Foto foto);
    
    /**
     * Abandona el hilo de la conexión a la base de datos
     */
    public void closeConnection();
    
}
