/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.albarregas.controllers;

import es.albarregas.beans.Usuario;
import es.albarregas.dao.IUsuarioDAO;
import es.albarregas.daofactory.DAOFactory;
import java.io.File;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * Operaciones relativas a la subida de imágenes a la parte privada de la aplicación utilizando la clase Part 
 * @author jesus
 */
@MultipartConfig
@WebServlet(name = "SubirPrivadoAvatarUsuario", urlPatterns = {"/SubirPrivadoAvatarUsuario"})
public class SubirPrivadoAvatarUsuario extends HttpServlet {

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

                url = "JSP/usuarios/part/subirAvatarPrivado.jsp";

            } else {
                request.setAttribute("error", "Tienes que elegir un usuario");
                url = "JSP/usuarios/part/listaSubirPrivado.jsp";
            }
        } else if (request.getParameter("boton").equalsIgnoreCase("Subir")) {
            Part filePart = request.getPart("avatarPrivado");

            StringBuilder nombreFichero = new StringBuilder();
            String dirImagen = request.getServletContext().getRealPath("/WEB-INF/avatares/");
            String filePath = null;
            usuario = (Usuario) request.getSession().getAttribute("usuario");
            if (filePart.getName().length() != 0) {
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
                        nombreFichero.append("privado-AvatarN").append(String.valueOf(usuario.getId())).append(extension);
                        filePath = dirImagen + nombreFichero.toString();
                        try {
                            // Escribimos el fichero en el servidor
                            filePart.write(filePath);
                            // Actualizamos el objeto usuario
                            usuario.setAvatarPrivado(nombreFichero.toString());
                            udao.update(usuario);
                        } catch (IOException ex) {

                            File directorio = new File(dirImagen);
                            if (!directorio.exists()) {
                                directorio.mkdir();
                            }
                            Logger.getLogger(SubirPrivadoAvatarUsuario.class.getName()).log(Level.SEVERE, null, ex);
                            request.setAttribute("error", "No se ha podido subir la imagen del avatar al servidor. Por favor vuelva a intentarlo.");
                            url = "/JSP/notificaciones/error.jsp";
                        }

                    } else {
                        request.setAttribute("error", "La imagen sobrepasa el tamaño permitido");
                        url = "/JSP/usuarios/part/subirAvatarPrivado.jsp";

                    }
                } else {
                    request.setAttribute("error", "La imagen no tiene el formato adecuado");
                    url = "/JSP/usuarios/part/subirAvatarPrivado.jsp";

                }
            } else {
                request.setAttribute("error", "El campo avatar es obligatorio");
                url = "/JSP/usuarios/part/subirAvatarPrivado.jsp";
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
