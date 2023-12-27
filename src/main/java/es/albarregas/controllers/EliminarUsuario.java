/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.albarregas.controllers;

import es.albarregas.beans.Usuario;
import es.albarregas.dao.IUsuarioDAO;
import es.albarregas.daofactory.DAOFactory;
import es.albarregas.models.Utilidades;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Operaciones relativas a eliminar objetos Usuario
 * @author jesus
 */
@WebServlet(name = "EliminarUsuario", urlPatterns = {"/EliminarUsuario"})
public class EliminarUsuario extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DAOFactory daof = DAOFactory.getDAOFactory();
        IUsuarioDAO udao = daof.getUsuarioDAO();
        String url = null;

        if (request.getParameter("boton").equalsIgnoreCase("Realizar")) {
            if (request.getParameterValues("registro") != null) {

                List<Usuario> usuarios = udao.getUsuariosByCondicion(request.getParameterValues("registro"));
                request.getSession().setAttribute("usuariosEliminar", usuarios);
                url = "JSP/usuarios/comun/confirmaEliminar.jsp";

            } else {
                request.setAttribute("error", "Tienes que elegir al menos un usuario");
                url = "JSP/usuarios/comun/listaEliminar.jsp";
            }
        } else if (request.getParameter("boton").equalsIgnoreCase("Confirmar")) {
            List<Usuario> usuarios = (List<Usuario>) request.getSession().getAttribute("usuariosEliminar");
            Iterator itUsuario = usuarios.iterator();
            while (itUsuario.hasNext()) {
                Usuario usuario = new Usuario();
                usuario = (Usuario) itUsuario.next();
                udao.delete(usuario);
            }
            Utilidades.limpiarSesion(request);
            url = "index.jsp";
        } else {
            Utilidades.limpiarSesion(request);
            url = "index.jsp";
        }
        request.getRequestDispatcher(url).forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
