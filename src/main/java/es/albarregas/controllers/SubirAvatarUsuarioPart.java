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
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;

/**
 * Operaciones relativas a la subida de imágenes al servidor utilizando la clase Part
 * @author jesus
 */
@MultipartConfig
@WebServlet(name = "SubirAvatarUsuarioPart", urlPatterns = {"/SubirAvatarUsuarioPart"})
public class SubirAvatarUsuarioPart extends HttpServlet {

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

        // Comprobamos que el usuario no haya pulsado Cancelar
        if (!request.getParameter("boton").equalsIgnoreCase("Cancelar")) {
            Part filePart = request.getPart("avatarPart");
            if (filePart.getName().length() != 0) {
                Enumeration<String> campos = request.getParameterNames();
                while (campos.hasMoreElements()) {
                    String nombre = campos.nextElement();

                    if (request.getParameter(nombre).length() == 0) {
                        error = true;

                    }
                }
            } else {
                error = true;
            }
            if (!error) {
                // Comprobamos que sea del formato adecuado
                if (filePart.getContentType().equals("image/png") || filePart.getContentType().equals("image/jpeg")) {
                    // Comprobamos que tenga el tamaño permitido
                    if (filePart.getSize() < 102400) {
                        // Obtenemos la extensión
                        String extension = ".jpg";
                        if (filePart.getContentType().equals("image/png")) {
                            extension = ".png";
                        }
                        // Obtenemos el nombre del fichero
                        nombreFichero.append("part-AvatarN").append(String.valueOf(usuario.getId())).append(extension);
                        filePath = dirImagen + nombreFichero.toString();
                        // Escribimos el fichero en el servidor
                        filePart.write(filePath);

                    } else {
                        request.setAttribute("error", "La imagen sobrepasa el tamaño permitido");
                        url = "/JSP/usuarios/part/actualizar.jsp";
                        error = true;
                    }
                } else {
                    request.setAttribute("error", "La imagen no tiene el formato adecuado");
                    url = "/JSP/usuarios/part/actualizar.jsp";
                    error = true;
                }
            } else {
                request.setAttribute("error", "Todos los campos son obligatorios");
                url = "/JSP/usuarios/part/actualizar.jsp";
                error = true;
            }
            // Comprobamos que no haya habido errores al tratar la imagen
            if (!error) {
                // Realizamos el tratamiento de los otros campos del formulario
                DateConverter converter = new DateConverter();
                converter.setPattern("yyyy-MM-dd");
                ConvertUtils.register(converter, Date.class);
                try {
                    BeanUtils.populate(usuario, request.getParameterMap());
                    usuario.setAvatarPart(nombreFichero.toString());
                    usuario.setAvatar(usuario.getAvatar());
                    // Actualizamos el usuario
                    udao.update(usuario);
                    // Eliminamos el atributo de sesión
                    Utilidades.limpiarSesion(request);
                    
                } catch (IllegalAccessException | InvocationTargetException ex) {
                    Logger.getLogger(SubirAvatarUsuarioPart.class.getName()).log(Level.SEVERE, null, ex);

                }
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
