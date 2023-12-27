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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Operaciones relativas al almacenaje de imágenes en la base de datos
 * @author jesus
 */
@WebServlet(name = "SubirFotosArticulo", urlPatterns = {"/SubirFotosArticulo"})
public class SubirFotosArticulo extends HttpServlet {

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

        DAOFactory daof = DAOFactory.getDAOFactory();
        IArticuloDAO adao = daof.getArticuloDAO();
        Articulo articulo = new Articulo();
        ArrayList<Foto> fotos = new ArrayList<>();

        // Declaramos algunas variables booleans necesarias para el proceso
        Boolean error = false;
        Boolean cancelar = false;
        Boolean errorTipo = false;
        Boolean errorSize = false;

        // Obtenemos el usuario pasado por sesión
        articulo = (Articulo) request.getSession().getAttribute("articulo");

        // Para almacenar las imágenes el la base de datos solamente se ha realizado utilizando la librería fileupload
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);

        List<FileItem> items = null;
        try {
            items = upload.parseRequest(request);
            Iterator<FileItem> it = items.iterator();
            // Realizamos un primer recorrido en busca de posibles errores
            while (it.hasNext() && !cancelar) {
                FileItem uploaded = it.next();
                if (uploaded.isFormField()) {
                    String key = uploaded.getFieldName();
                    String valor = "";
                    valor = uploaded.getString("UTF-8");
                    switch (key) {
                        case "boton":
                            if (valor.equals("Cancelar")) {
                                cancelar = true;
                            }
                            break;
                        case "denominacion":

                        case "pvp":
                            if (valor.equals("")) {
                                error = true;
                            }
                    }
                } else if (uploaded.getName().equals("")) {
                    error = true;
                } else if (!uploaded.getContentType().equalsIgnoreCase("image/png")) {
                    errorTipo = true;
                } else if (uploaded.getSize() > 51200) {
                    errorSize = true;
                }
            }
            if (!cancelar) {
                // En el caso de que no se haya pulsado Cancelar comprobamos que no haya algún campo vacío
                if (!error) {
                    // En el caso de que alguna imagen no sea del tipo permitido
                    if (!errorTipo) {
                        // En el caso de que alguna imagen sobrepase el tamaño permitido
                        if (!errorSize) {
                            // Recorremos la lista con ayuda de iterator
                            Iterator<FileItem> itDos = items.iterator();
                            while (itDos.hasNext() && !error) {

                                FileItem uploaded = itDos.next();
                                if (!uploaded.isFormField()) {

                                    Foto foto = new Foto();
                                    /* 
                                     * A través del método get de un objeto de la clase FileItem se realiza el proceso de almacenar la imagen
                                     * como un array de bytes en la base de datos. Para que esto funcione el campo de la tabla donde la vamos 
                                     * a almacenar tiene que ser del tipo blob
                                     */
                                    foto.setFoto(uploaded.get());
                                    fotos.add(foto);

                                }
                                /*
                                 * A partir de aquí y en caso de no existir errores se procede a tratar los demás campos del formulario
                                 */
                                String key = uploaded.getFieldName();
                                String valor = "";
                                if (!key.equals("fotos[]")) {
                                    valor = uploaded.getString("UTF-8");
                                }
                                switch (key) {

                                    case "denominacion":
                                        articulo.setDenominacion(valor);
                                        break;
                                    case "pvp":
                                        articulo.setPvp(Double.parseDouble(valor));
                                        break;

                                }

                            }

                            articulo.setFotos(fotos);
                            adao.update(articulo);
                            Utilidades.limpiarSesion(request);
                        } else {
                            request.setAttribute("error", "Alguna imagen sobrepasa el tamaño permitido de 50 KB");
                            url = "/JSP/articulos/actualizar.jsp";
                        }
                    } else {
                        request.setAttribute("error", "Alguna imagen no tiene el formato adecuado");
                        url = "/JSP/articulos/actualizar.jsp";

                    }
                } else {
                    request.setAttribute("error", "Todos los campos son obligatorios");
                    url = "/JSP/articulos/actualizar.jsp";
                }
            }
        } catch (FileUploadException ex) {
            request.setAttribute("error", "No se han podido leer los campos del formulario");
            url = "JSP/notificaciones/error.jsp";
        }
        request.getRequestDispatcher(url).forward(request, response);
    }

}
