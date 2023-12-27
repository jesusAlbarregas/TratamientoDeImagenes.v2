/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.albarregas.beans;

import java.util.List;

/**
 *
 * @author jesus
 */
public class Articulo {
    
    private int id;
    private String denominacion;
    private Double pvp;
    private List<Foto> fotos;

    /**
     * Obtiene el identificativo del artículo
     * @return entero con el identificativo
     */
    public int getId() {
        return id;
    }

    /**
     * Asigna el identificativo del artículo
     * @param id entero que se pretende asignar
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene la denominación del artículo
     * @return String con la denominación
     */
    public String getDenominacion() {
        return denominacion;
    }

    /**
     * Asigna la denominación del artículo
     * @param denominacion String que se pretende asignar
     */
    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    /**
     * Obtiene el precio de venta al público del artículo
     * @return Objeto double con el precio de venta al público
     */
    public Double getPvp() {
        return pvp;
    }

    /**
     * Asigna el precio de venta al público del artículo
     * @param pvp Double que se pretende asignar
     */
    public void setPvp(Double pvp) {
        this.pvp = pvp;
    }

    /**
     * Obtiene la lista de fotos asociadas al artículo
     * @return Lista de objetos Foto
     */
    public List<Foto> getFotos() {
        return fotos;
    }

    /**
     * Asigna la lista de fotos asociadas al artículo
     * @param fotos Lista de objetos Foto que se pretende asignar
     */
    public void setFotos(List<Foto> fotos) {
        this.fotos = fotos;
    }
    
}
