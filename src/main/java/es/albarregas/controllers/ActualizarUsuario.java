/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.albarregas.controllers;

import es.albarregas.beans.Usuario;
import es.albarregas.dao.IUsuarioDAO;
import es.albarregas.daofactory.DAOFactory;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Operaciones relativas a actualizar objetos Usuario 
 * @author jesus
 */
@WebServlet(name = "ActualizarUsuario", urlPatterns = {"/ActualizarUsuario"})
public class ActualizarUsuario extends HttpServlet {

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
        Usuario usuario = null;
        String url = "index.jsp";

        if (request.getParameter("boton").equalsIgnoreCase("Realizar")) {
            if (request.getParameterValues("registro") != null) {

                usuario = udao.getUsuarioById(Integer.parseInt(request.getParameter("registro")));
                request.getSession().setAttribute("usuario", usuario);
                String numOpcion = (String) request.getSession().getAttribute("numeroOpcion");
                if (numOpcion.equalsIgnoreCase("c")) {
                    url = "JSP/usuarios/fileupload/actualizarAvatarAjax.jsp";
                } else {
                    url = (request.getSession().getAttribute("libreria").equals("part")) ? "JSP/usuarios/part/actualizar.jsp" : "JSP/usuarios/fileupload/actualizar.jsp";
                }
            } else {
                request.setAttribute("error", "Tienes que elegir un usuario");
                url = "JSP/usuarios/comun/listaActualizar.jsp";
            }
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
