/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.albarregas.controllers;

import es.albarregas.beans.Articulo;
import es.albarregas.beans.Foto;
import es.albarregas.dao.IArticuloDAO;
import es.albarregas.daofactory.DAOFactory;

import java.io.IOException;
import java.util.Base64;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Operaciones relativas para actualizar objetos Articulo
 * @author jesus
 */
@WebServlet(name = "ActualizarArticulo", urlPatterns = {"/ActualizarArticulo"})
public class ActualizarArticulo extends HttpServlet {

    /**
     * Maneja el método HTTP <code>GET</code>.
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
     * Maneja el método HTTP <code>POST</code>.
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
        IArticuloDAO adao = daof.getArticuloDAO();
        Articulo articulo = null;
        String url = "index.jsp";

        if (request.getParameter("boton").equalsIgnoreCase("Realizar")) {
            if (request.getParameterValues("registro") != null) {

                articulo = adao.getArticuloById(Integer.parseInt(request.getParameter("registro")));
               
                Iterator<Foto> itFotos = articulo.getFotos().iterator();
                while(itFotos.hasNext()) {
                    Foto foto = itFotos.next();
                    foto.setImagen(Base64.getEncoder().encodeToString(foto.getFoto()));
                }
                
                request.getSession().setAttribute("articulo", articulo);

                url = "JSP/articulos/actualizar.jsp";
            } else {
                request.setAttribute("error", "Tienes que elegir un artículo");
                url = "JSP/articulos/listaActualizar.jsp";
            }
        } 

        request.getRequestDispatcher(url).forward(request, response);
    }

    /**
     * Devuelve una breve descripción del servlet.
     *
     * @return una cadena que contiene la descripción del servlet
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
