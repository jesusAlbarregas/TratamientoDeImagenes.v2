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
import es.albarregas.models.Utilidades;

import java.io.IOException;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Operaciones relativas a eliminar objetos Articulo
 * @author jesus
 */
@WebServlet(name = "EliminarArticulo", urlPatterns = {"/EliminarArticulo"})
public class EliminarArticulo extends HttpServlet {

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
        IArticuloDAO adao = daof.getArticuloDAO();
        String url = null;

        if (request.getParameter("boton").equalsIgnoreCase("Realizar")) {
            if (request.getParameterValues("registro") != null) {

                List<Articulo> articulos = adao.getArticulosByCondicion(request.getParameterValues("registro"));
                if (articulos != null) {
                    Iterator<Articulo> itArticulo = articulos.iterator();
                    Iterator<Foto> itFoto;
                    while (itArticulo.hasNext()) {
                        Articulo articulo = itArticulo.next();
                        if (!articulo.getFotos().isEmpty()) {
                            itFoto = articulo.getFotos().iterator();
                            while (itFoto.hasNext()) {
                                Foto foto = itFoto.next();
                                foto.setImagen(Base64.getEncoder().encodeToString(foto.getFoto()));
                            }
                        }
                    }
                } else {
                    request.setAttribute("aviso", "No disponemos de artículos en este momento");
                    url = "/JSP/notificaciones/aviso.jsp";
                }
                request.getSession().setAttribute("articulosEliminar", articulos);
                url = "JSP/articulos/confirmaEliminar.jsp";

            } else {
                request.setAttribute("error", "Tienes que elegir al menos un usuario");
                url = "JSP/articulos/listaEliminar.jsp";
            }
        } else if (request.getParameter("boton").equalsIgnoreCase("Confirmar")) {
            List<Articulo> articulos = (List<Articulo>) request.getSession().getAttribute("articulosEliminar");
            Iterator<Articulo> itArticulo = articulos.iterator();
            while (itArticulo.hasNext()) {
                Articulo articulo = new Articulo();
                articulo = (Articulo) itArticulo.next();
                adao.delete(articulo);
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
