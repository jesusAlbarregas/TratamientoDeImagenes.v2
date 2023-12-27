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
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;

/**
 * Operaciones relativas a añadir objetos Usuario
 * @author jesus
 */
@WebServlet(name = "NuevoUsuario", urlPatterns = {"/NuevoUsuario"})
public class NuevoUsuario extends HttpServlet {

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
        String url = "index.jsp";
        if (request.getParameter("boton").equals("Realizar")) {
            Usuario usuario = new Usuario();
            DAOFactory daof = DAOFactory.getDAOFactory();
            IUsuarioDAO udao = daof.getUsuarioDAO();
            Boolean error = false;
            Enumeration<String> campos = request.getParameterNames();
            // Comprobamos que todos los campos del formulario estén rellenos
            if (!Utilidades.isFormCompleto(campos, request)) {
                DateConverter converter = new DateConverter();
                converter.setPattern("yyyy-MM-dd");
                ConvertUtils.register(converter, Date.class);

                try {
                    BeanUtils.populate(usuario, request.getParameterMap());

                } catch (IllegalAccessException | InvocationTargetException ex) {
                    Logger.getLogger(NuevoUsuario.class.getName()).log(Level.SEVERE, null, ex);
                    error = true;
                }
                if (!error) {
                    udao.add(usuario);
                }
            } else {
                request.setAttribute("error", "Todos los campos son obligatorios");
                url = "JSP/usuarios/comun/nuevo.jsp";
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
