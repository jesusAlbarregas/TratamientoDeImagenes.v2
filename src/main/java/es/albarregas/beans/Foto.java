/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.albarregas.beans;

import java.io.Serializable;


/**
 *
 * @author jesus
 */
public class Foto implements Serializable {
    
    private int id;
    private byte[] foto;
    private String imagen;

    /**
     * Obtiene el identificativo de la foto
     * @return entero con el identificativo
     */
    public int getId() {
        return id;
    }

    /**
     * Asigna el identificativo de la foto
     * @param id entero que se pretende asignar
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el array de bytes de la foto
     * @return array de bytes
     */
    public byte[] getFoto() {
        return foto;
    }

    /**
     * Asigna el array de bytes de la foto
     * @param foto array de bytes que se pretende asignar
     */
    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
    
    /**
     * Obtiene la cadena de caracteres de la foto
     * @return cadena de caracteres
     */
    public String getImagen() {
        return imagen;
    }

    /**
     * Asigna el String de la foto
     * @param imagen cadena que se pretende asignar
     */
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
    
}
