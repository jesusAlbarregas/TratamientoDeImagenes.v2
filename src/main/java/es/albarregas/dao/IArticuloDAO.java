/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.albarregas.dao;

import es.albarregas.beans.Articulo;

import java.util.List;

/**
 *
 * @author jesus
 */
public interface IArticuloDAO {
    
    /**
     * Añade un artículo
     * @param articulo Objeto artículo que se pretende añadir
     * @return Objeto Boolean, TRUE si todo ha ido bien y FALSE en caso contrario
     */
    public Boolean add(Articulo articulo);
    
    /**
     * Actualiza un artículo
     * @param articulo Objeto artículo que se pretende actualizar
     * @return Objeto Boolean, TRUE si todo ha ido bien y FALSE en caso contrario
     */
    public Boolean update(Articulo articulo);
    
    /**
     * Obtiene todos los artículos y sus fotos asociadas
     * @return Lista de objetos artículo
     */
    public List<Articulo> getArticulos();
    
    /**
     * Obtiene un artículo y sus fotos asociadas a partir de un id
     * @param id Identificativo del artículo que se pretende obtener
     * @return Objeto artículo con sus fotos asociadas
     */
    public Articulo getArticuloById(int id);
    
    /**
     * Obtiene los artículos cuyo id está dentro de un rango de valores
     * @param registros Valores de los diferentes id que se pretenden obtener
     * @return Lista de objetos artículos
     */
    public List<Articulo> getArticulosByCondicion (String[] registros);
    
    /**
     * Elimina un artículo y sus fotos asociadas
     * @param articulo Objeto artículo que se pretende eliminar
     */
    public void delete(Articulo articulo);
    
    /**
     * Abandona el hilo de la conexión a la base de datos
     */
    public void closeConnection();
    
}
