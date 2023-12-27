/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.albarregas.dao;

import es.albarregas.beans.Articulo;
import es.albarregas.beans.Foto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jesus
 */
public class FotoDAO implements IFotoDAO {
    
    @Override
    public List<Articulo> getFotosByCondicion(String[] registros) {
        Connection conexion = null;
        List<Articulo> articulos = null;
        // Construimos la cláusula where de la forma: where idFoto in (1, 2)
        StringBuilder clausulaWhere = null;

        clausulaWhere = new StringBuilder(" WHERE f.idFoto IN (");
        for (String registro : registros) {

            clausulaWhere.append(registro);
            clausulaWhere.append(",");
        }
        clausulaWhere.replace(clausulaWhere.length() - 1, clausulaWhere.length(), ")");

        String sql = "SELECT * FROM articulos AS a INNER JOIN fotos AS f USING(idArticulo)" + clausulaWhere.toString();
        ArrayList<Foto> fotos = null;

        try {
            conexion = ConnectionFactory.getConnection();
            Statement sentencia = conexion.createStatement();
            articulos = new ArrayList<>();
            ResultSet resultado = sentencia.executeQuery(sql);
            int anterior = -1;
            Articulo articulo = null;
            while (resultado.next()) {
                if (anterior != resultado.getInt("idArticulo")) {
                    if (anterior != -1) {
                        articulo.setFotos(fotos);
                        articulos.add(articulo);
                    }
                    articulo = new Articulo();
                    anterior = resultado.getInt("idArticulo");
                    articulo.setId(resultado.getInt("idArticulo"));
                    articulo.setDenominacion(resultado.getString("denominacion"));
                    articulo.setPvp(resultado.getDouble("pvp"));
                    fotos = new ArrayList<>();
                }
                if (resultado.getBytes("foto") != null) {
                    Foto foto = new Foto();
                    foto.setId(resultado.getInt("idFoto"));
                    foto.setFoto(resultado.getBytes("foto"));
                    fotos.add(foto);
                }
            }

            articulo.setFotos(fotos);
            articulos.add(articulo);

        } catch (SQLException ex) {
            Logger.getLogger(ArticuloDAO.class.getName()).log(Level.SEVERE, null, ex);
            articulos = null;

        } finally {
            this.closeConnection();
        }

        return articulos;
    }
        
    @Override
    public void delete(Foto foto) {
        Connection conexion = null;
        String sqlFoto = "DELETE FROM fotos WHERE idFoto=?";
        
        PreparedStatement preparada;
        try {
            conexion = ConnectionFactory.getConnection();
            conexion.setAutoCommit(false);
            preparada = conexion.prepareStatement(sqlFoto);

            preparada.setInt(1, foto.getId());
            preparada.executeUpdate();
            conexion.commit();
        } catch (SQLException ex) {
            try {
                conexion.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(FotoDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(FotoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.closeConnection();
        }
    }
    
    @Override
    public void closeConnection() {
        ConnectionFactory.closeConnection();
    }

    
}
