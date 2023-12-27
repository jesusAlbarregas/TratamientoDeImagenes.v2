/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.albarregas.beans;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author jesus
 */
public class Usuario implements Serializable {

    private int id;
    private String nombre;
    private String apellidos;
    private Date fechaNacimiento;
    private String avatar;
    private String avatarPart;
    private String avatarPrivado;
    private byte[] bytesImages;

    /**
     * Obtiene el identificativo del usuario
     * @return entero con el identificativo
     */
    public int getId() {
        return id;
    }

    /**
     * Asigna el identificativo del usuario
     * @param id entero que se pretende asignar
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del usuario
     * @return String con el nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Asigna el nombre del usuario
     * @param nombre String que se pretende asignar
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene los apellidos del usuario
     * @return String con los apellidos
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Asigna los apellidos del usuario
     * @param apellidos String que se pretende asignar
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * Obtiene la fecha de nacimiento del usuario
     * @return Objeto Date con la fecha de nacimiento
     */
    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * Asigna la fecha de nacimiento del usuario
     * @param fechaNacimiento Objeto Date que se pretende asignar
     */
    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * Obtiene el nombre del avatar  del usuario subido mediante la librería FileUpload
     * @return String con el nombre del avatar
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * Asigna el nombre del avatar del usuario
     * @param avatar String que se pretende asignar
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * Obtiene el avatar del usuario subido mediante la clase Part
     * @return String con el nombre del avatar 
     */
    public String getAvatarPart() {
        return avatarPart;
    }

    /**
     * Asigna el nombre del avatar del usuario
     * @param avatarPart String que se pretende asignar
     */
    public void setAvatarPart(String avatarPart) {
        this.avatarPart = avatarPart;
    }

    /**
     * Obtiene el avatar del usuario subido mediante la clase Part y en la parte privada de la aplicación
     * @return String con el nombre del avatar
     */
    public String getAvatarPrivado() {
        return avatarPrivado;
    }

    /**
     * Asigna el nombre del avatar del usuario
     * @param avatarPrivado String que se pretende asignar
     */
    public void setAvatarPrivado(String avatarPrivado) {
        this.avatarPrivado = avatarPrivado;
    }

    /**
     * Obtiene el array de bytes de la imagen del avatar
     * @return Array de bytes de la foto
     */
    public byte[] getBytesImages() {
        return bytesImages;
    }

    /**
     * Asigna el array de bytes del avatar del usuario
     * @param bytesImages Array de bytes que se pretende asignar
     */
    public void setBytesImages(byte[] bytesImages) {
        this.bytesImages = bytesImages;
    }
    
    
}
