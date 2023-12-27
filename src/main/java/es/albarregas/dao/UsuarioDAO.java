/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.albarregas.dao;

import es.albarregas.beans.Usuario;
import es.albarregas.models.Utilidades;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jesus
 */
public class UsuarioDAO implements IUsuarioDAO {

    @Override
    public void add(Usuario usuario) {
        Connection conexion = null;
        String sql = "INSERT INTO usuarios (nombre,apellidos,fechaNacimiento) VALUES (?,?,?)";
        try {
            conexion = ConnectionFactory.getConnection();
            conexion.setAutoCommit(false);

            PreparedStatement preparada = conexion.prepareStatement(sql);
            preparada.setString(1, usuario.getNombre());
            preparada.setString(2, usuario.getApellidos());
            preparada.setDate(3, Utilidades.utilDateToSqlDate(usuario.getFechaNacimiento()));

            preparada.executeUpdate();
            conexion.commit();
        } catch (SQLException ex) {
            try {
                conexion.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            this.closeConnection();
        }
    }

    @Override
    public List<Usuario> getUsuarios() {
        Connection conexion = null;
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        try {
            conexion = ConnectionFactory.getConnection();
            Statement sentencia = conexion.createStatement();

            try (ResultSet resultado = sentencia.executeQuery(sql)) {
                while (resultado.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setId(resultado.getInt("id"));
                    usuario.setNombre(resultado.getNString("nombre"));
                    usuario.setApellidos(resultado.getString("apellidos"));
                    usuario.setFechaNacimiento(resultado.getDate("fechaNacimiento"));
                    usuario.setAvatar(resultado.getString("avatar"));
                    usuario.setAvatarPart(resultado.getString("avatarPart"));
                    usuario.setAvatarPrivado(resultado.getString("avatarPrivado"));
                    lista.add(usuario);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.closeConnection();
        }
        if (lista.isEmpty()) {
            lista = null;
        }
        return lista;
    }

    @Override
    public Usuario getUsuarioById(int id) {
        Connection conexion = null;
        Usuario usuario = null;
        String sql = "SELECT * FROM usuarios WHERE id=?";
        PreparedStatement preparada;
        try {
            conexion = ConnectionFactory.getConnection();
            preparada = conexion.prepareStatement(sql);
            preparada.setInt(1, id);
            ResultSet resultado = preparada.executeQuery();
            if (resultado.next()) {
                usuario = new Usuario();
                usuario.setId(id);
                usuario.setNombre(resultado.getString("nombre"));
                usuario.setApellidos(resultado.getString("apellidos"));
                usuario.setFechaNacimiento(resultado.getDate("fechaNacimiento"));
                usuario.setAvatar(resultado.getString("avatar"));
                usuario.setAvatarPart(resultado.getString("avatarPart"));
                usuario.setAvatarPrivado(resultado.getString("avatarPrivado"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.closeConnection();
        }

        return usuario;
    }

    @Override
    public void update(Usuario usuario) {
        Connection conexion = null;
        String sql = "UPDATE usuarios SET nombre=?,apellidos=?,fechaNacimiento=?,avatar=?,avatarPart=?,avatarPrivado=? WHERE id=?";

        PreparedStatement preparada;
        try {
            conexion = ConnectionFactory.getConnection();
            conexion.setAutoCommit(false);
            preparada = conexion.prepareStatement(sql);
            preparada.setString(1, usuario.getNombre());
            preparada.setString(2, usuario.getApellidos());
            preparada.setDate(3, Utilidades.utilDateToSqlDate(usuario.getFechaNacimiento()));
            preparada.setString(4, usuario.getAvatar());
            preparada.setString(5, usuario.getAvatarPart());
            if (usuario.getAvatarPrivado() != null) {
                preparada.setString(6, usuario.getAvatarPrivado());
            } else {
                preparada.setNull(6, Types.VARCHAR);
            }
            preparada.setInt(7, usuario.getId());
            preparada.executeUpdate();
            conexion.commit();
        } catch (SQLException ex) {
            try {
                conexion.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.closeConnection();
        }

    }

    @Override
    public List<Usuario> getUsuariosByCondicion(String[] registros) {
        Connection conexion = null;
        List<Usuario> usuarios = null;
        // Construimos la cláusula where de la forma: where id in (1, 2)
        StringBuilder clausulaWhere = null;

        clausulaWhere = new StringBuilder(" WHERE id IN (");
        for (String registro : registros) {

            clausulaWhere.append(registro);
            clausulaWhere.append(",");
        }
        clausulaWhere.replace(clausulaWhere.length() - 1, clausulaWhere.length(), ")");

        String sql = "SELECT * FROM usuarios" + clausulaWhere.toString();

        try {
            conexion = ConnectionFactory.getConnection();
            Statement sentencia = conexion.createStatement();
            usuarios = new ArrayList<>();
            try (ResultSet resultado = sentencia.executeQuery(sql)) {
                while (resultado.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setId(resultado.getInt("id"));
                    usuario.setNombre(resultado.getNString("nombre"));
                    usuario.setApellidos(resultado.getString("apellidos"));
                    usuario.setFechaNacimiento(resultado.getDate("fechaNacimiento"));
                    usuario.setAvatar(resultado.getString("avatar"));
                    usuario.setAvatarPart(resultado.getString("avatarPart"));
                    usuario.setAvatarPrivado(resultado.getString("avatarPrivado"));
                    usuarios.add(usuario);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.closeConnection();
        }
        return usuarios;
    }

    @Override
    public void delete(Usuario usuario) {
        Connection conexion = null;
        String sql = "DELETE FROM usuarios WHERE id=?";

        PreparedStatement preparada;
        try {
            conexion = ConnectionFactory.getConnection();
            conexion.setAutoCommit(false);
            preparada = conexion.prepareStatement(sql);

            preparada.setInt(1, usuario.getId());
            preparada.executeUpdate();
            conexion.commit();
        } catch (SQLException ex) {
            try {
                conexion.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex1);
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
