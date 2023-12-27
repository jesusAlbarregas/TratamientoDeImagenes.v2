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
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jesus
 */
public class ArticuloDAO implements IArticuloDAO {

    @Override
    public Boolean add(Articulo articulo) {
        Connection conexion = null;
        Boolean retorno = true;
        String sql = "INSERT INTO articulos (denominacion,pvp) VALUES (?,?)";

        try {
            conexion = ConnectionFactory.getConnection();
            conexion.setAutoCommit(false);
            PreparedStatement preparada = conexion.prepareStatement(sql);
            preparada.setString(1, articulo.getDenominacion());
            preparada.setDouble(2, articulo.getPvp());
            preparada.executeUpdate();
            conexion.commit();
        } catch (SQLException ex) {
            try {
                conexion.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(ArticuloDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(ArticuloDAO.class.getName()).log(Level.SEVERE, null, ex);
            retorno = false;
        } finally {
            this.closeConnection();
        }
        return retorno;
    }

    @Override
    public Boolean update(Articulo articulo) {
        Connection conexion = null;
        Boolean retorno = true;
        String sql = "UPDATE articulos SET denominacion=?,pvp=? WHERE idArticulo=?";
        PreparedStatement preparada;

        try {
            conexion = ConnectionFactory.getConnection();
            conexion.setAutoCommit(false);
            preparada = conexion.prepareStatement(sql);
            preparada.setString(1, articulo.getDenominacion());
            preparada.setDouble(2, articulo.getPvp());
            preparada.setInt(3, articulo.getId());
            preparada.executeUpdate();
            Iterator<Foto> it = articulo.getFotos().iterator();
            sql = "INSERT INTO fotos (idArticulo,foto) VALUES (?,?)";
            while (it.hasNext()) {
                Foto foto = it.next();
                preparada = null;
                preparada = ConnectionFactory.getConnection().prepareStatement(sql);
                preparada.setInt(1, articulo.getId());
                preparada.setBytes(2, foto.getFoto());
                preparada.executeUpdate();
            }
            conexion.commit();
        } catch (SQLException ex) {
            try {
                conexion.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(ArticuloDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(ArticuloDAO.class.getName()).log(Level.SEVERE, null, ex);
            retorno = false;
        } finally {
            this.closeConnection();
        }

        return retorno;
    }

    @Override
    public List<Articulo> getArticulos() {
        Connection conexion = null;
        List<Articulo> articulos = null;
        String sql = "SELECT * FROM articulos AS a LEFT JOIN fotos AS f USING(idArticulo)";
        Statement sentencia;
        try {
            conexion = ConnectionFactory.getConnection();
            sentencia = conexion.createStatement();

            ResultSet resultado = sentencia.executeQuery(sql);
            int anterior = -1;
            Articulo articulo = null;
            articulos = new ArrayList<>();
            ArrayList<Foto> fotos = null;
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
            if (articulo != null) {
                articulo.setFotos(fotos);
                articulos.add(articulo);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ArticuloDAO.class.getName()).log(Level.SEVERE, null, ex);
            articulos = null;

        } finally {
            this.closeConnection();
        }

        return articulos;
    }

    @Override
    public Articulo getArticuloById(int id) {
        Connection conexion = null;
        Articulo articulo = null;
        String sql = "SELECT * FROM articulos AS a LEFT JOIN fotos AS f USING(idArticulo) WHERE a.idArticulo=?";
        PreparedStatement preparada;
        try {
            conexion = ConnectionFactory.getConnection();
            preparada = conexion.prepareStatement(sql);
            preparada.setInt(1, id);
            ResultSet resultado = preparada.executeQuery();
            int anterior = -1;
            List<Foto> fotos = new ArrayList<>();
            while (resultado.next()) {
                if (anterior != resultado.getInt("idArticulo")) {
                    articulo = new Articulo();
                    anterior = resultado.getInt("idArticulo");
                    articulo.setId(resultado.getInt("idArticulo"));
                    articulo.setDenominacion(resultado.getString("denominacion"));
                    articulo.setPvp(resultado.getDouble("pvp"));
                }
                if (resultado.getBytes("foto") != null) {
                    Foto foto = new Foto();
                    foto.setId(resultado.getInt("idFoto"));
                    foto.setFoto(resultado.getBytes("foto"));
                    fotos.add(foto);
                }
            }

            articulo.setFotos(fotos);

        } catch (SQLException ex) {
            Logger.getLogger(ArticuloDAO.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            this.closeConnection();
        }

        return articulo;
    }

    @Override
    public List<Articulo> getArticulosByCondicion(String[] registros) {
        Connection conexion = null;
        List<Articulo> articulos = null;
        // Construimos la cláusula where de la forma: where id in (1, 2)
        StringBuilder clausulaWhere = null;

        clausulaWhere = new StringBuilder(" WHERE a.idArticulo IN (");
        for (String registro : registros) {

            clausulaWhere.append(registro);
            clausulaWhere.append(",");
        }
        clausulaWhere.replace(clausulaWhere.length() - 1, clausulaWhere.length(), ")");

        String sql = "SELECT * FROM articulos AS a LEFT JOIN fotos AS f USING(idArticulo)" + clausulaWhere.toString();
        List<Foto> fotos = null;

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
    public void delete(Articulo articulo) {
        Connection conexion = null;
        String sqlArt = "DELETE FROM articulos WHERE idArticulo=?";

        PreparedStatement preparada;
        try {
            conexion = ConnectionFactory.getConnection();
            conexion.setAutoCommit(false);
            preparada = conexion.prepareStatement(sqlArt);

            preparada.setInt(1, articulo.getId());
            preparada.executeUpdate();
            conexion.commit();
        } catch (SQLException ex) {
            try {
                conexion.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(ArticuloDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.closeConnection();
        }
    }

    @Override
    public void closeConnection() {
        ConnectionFactory.closeConnection();
    }

}
