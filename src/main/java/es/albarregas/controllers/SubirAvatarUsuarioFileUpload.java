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

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
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
 * Operaciones relativas a la subida de imágenes al servidor utilizando la libería FileUpload
 * @author jesus
 */
@MultipartConfig
@WebServlet(name = "SubirAvatarUsuarioFileUpload", urlPatterns = {"/SubirAvatarUsuarioFileUpload"})
public class SubirAvatarUsuarioFileUpload extends HttpServlet {

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

        // Obtenemos el usuario pasado por sesión
        usuario = (Usuario) request.getSession().getAttribute("usuario");
        // Obtenemos el directorio donde se almacenarán las imágenes
        String dirImagen = request.getServletContext().getRealPath("/imagenes/avatares/");
        // Declaramos una variable booleana que pondremos a true en caso de error
        Boolean error = false;
        // Declaramos otra variable booleana que pondremos a true en el caso de que el usuario haya pulsado Cancelar
        Boolean cancelar = false;
        // Declaramos la variable donde se almacenará el nombre del fichero
        StringBuilder nombreFichero = new StringBuilder();
        // Declaramos la variable necesaria para crear un objeto File
        String filePath = null;

        // Declaramos la factoría
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        // Declaramos la lista de objetos FileItem
        List<FileItem> items = null;
        // Vamos a realizar una primera iteración de la lista para comprobar que no se ha pulsado Cancelar o algún campo está vacío
        try {
            items = upload.parseRequest(request);
            // Recorremos la lista con ayuda de iterator
            Iterator<FileItem> it = items.iterator();
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
                        case "nombre":

                        case "apellidos":

                        case "fechaNacimiento":
                            if (valor.equals("")) {
                                error = true;
                            }
                    }
                } else if (uploaded.getName().equals("")) {
                    error = true;
                }
            }
            if (!cancelar) {
                // En el caso de que no se haya pulsado Cancelar comprobamos que no haya algún campo vacío
                if (!error) {

                    // Recorremos la lista con ayuda de iterator
                    Iterator<FileItem> itDos = items.iterator();
                    while (itDos.hasNext() && !error) {

                        // Obtenemos un objeto FileItem
                        FileItem uploaded = itDos.next();

                        // En el caso de que no se un control normal
                        if (!uploaded.isFormField()) {
                            
                            // Comprobamos que el fichero tenga la extensión permitida
                            if (uploaded.getContentType().equals("image/png") || uploaded.getContentType().equals("image/jpeg")) {
                                // Comprobamos que el fichero no sea mayor de lo permitido
                                if (uploaded.getSize() < 102400) {
                                    // Obtenemos la extensión
                                    String extension = ".jpg";
                                    if (uploaded.getContentType().equals("image/png")) {
                                        extension = ".png";
                                    }
                                    // Obtenemos el nombre del fichero como AvatarN + identificativo del usuario
                                    nombreFichero.append("AvatarN").append(String.valueOf(usuario.getId())).append(extension);
                                    filePath = dirImagen + nombreFichero.toString();
                                    // Obtenemos el objeto File a partir de la variable anterior
                                    File fichero = new File(filePath);
                                    try {
                                        // Escribimos el fichero en el servidor
                                        uploaded.write(fichero);
                                        // Alcenamos el nombre en el objeto usuario
                                        usuario.setAvatar(nombreFichero.toString());
                                        // Guardamos en avatarPart lo mismo que había
                                        usuario.setAvatarPart(usuario.getAvatarPart());
                                    } catch (Exception ex) {
                                        request.setAttribute("error", "No se ha podido almacenar la imagen en el servidor");
                                        url = "/JSP/notificaciones/error.jsp";
                                        error = true;
                                    }
                                } else {
                                    request.setAttribute("error", "La imagen sobrepasa el tamaño permitido");
                                    url = "/JSP/usuarios/fileupload/actualizar.jsp";
                                    error = true;
                                }
                            } else {
                                request.setAttribute("error", "La imagen no tiene el formato adecuado");
                                url = "/JSP/usuarios/fileupload/actualizar.jsp";
                                error = true;
                            }
                        }
                        // En el caso de que no se haya producido error en la entrada de la imagen procedemos a gestionar los otros campos
                        if (!error) {
                            // Obtenemos el nombre de cada campo de entrada
                            String nombre = uploaded.getFieldName();
                            // Declaramos la variable que almacenará el valor del campo de entrada
                            String valor = null;
                            // Filtramos los campos que contienen el fichero que se pretende subir
                            if (!nombre.equals("avatar") && !nombre.equals("avatarPart")) {
                                // Obtenemos el valor del campo de entrada
                                valor = uploaded.getString("UTF-8");
                            }

                            // Ahora dependiendo del nombre del control lo almacenaremos en el atrubuto correspondiente del objeto usuario
                            switch (nombre) {
                                case "nombre":
                                    usuario.setNombre(valor);
                                    break;
                                case "apellidos":
                                    usuario.setApellidos(valor);
                                    break;
                                case "fechaNacimiento":
                                    usuario.setFechaNacimiento(Utilidades.stringToDate(valor));
                            }

                        }
                    }
                    if (!error) {
                        udao.update(usuario);
                        Utilidades.limpiarSesion(request);
                        
                    }

                } else {
                    request.setAttribute("error", "Todos los campos son obligatorios");
                    url = "/JSP/usuarios/fileupload/actualizar.jsp";
                }

            }
        } catch (FileUploadException ex) {
            request.setAttribute("error", "No se han podido leer los campos del formulario");
            url = "/JSP/notificaciones/error.jsp";
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
