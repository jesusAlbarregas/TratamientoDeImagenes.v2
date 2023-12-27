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
import java.util.ArrayList;
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

import org.json.JSONArray;

/**
 * Operaciones relativas a actualizar el avatar del usuario utilizando Ajax
 * @author jesus
 */
@MultipartConfig
@WebServlet(name = "AvatarAjax", urlPatterns = {"/AvatarAjax"})
public class AvatarAjax extends HttpServlet {

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

        StringBuilder nombreFichero = new StringBuilder();
        String filePath = null;

        // Obtenemos el directorio donde se almacenarán las imágenes
        String dirImagen = request.getServletContext().getRealPath("/imagenes/avatares/");
        // Obtenenmos el usuario que va a cambiar de avatar
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
        // Declaramos la lista para enviar datos al cliente
        List<String> retorno = new ArrayList<>();
        // Declaramos la factoria para FileUpload
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        // Declaramos la lista que albergará los objeto FileItem
        List<FileItem> items = null;
        try {
            items = upload.parseRequest(request);
            if (items != null) {
                for (FileItem item : items) {
                    FileItem uploaded = item;
                    if (!uploaded.isFormField()) { // No es campo de formulario, guardamos el fichero en algún sitio
                        if (uploaded.getContentType().equals("image/png") || uploaded.getContentType().equals("image/jpeg")) {
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

                                uploaded.write(fichero);

                                usuario.setAvatar(nombreFichero.toString());

                                udao.update(usuario);
                                retorno.add("success");
                                retorno.add("Avatar modificado correctamente");

                            } else {
                                retorno.add("error");
                                retorno.add("La imagen sobrepasa el tamaño permitido de 100 KB");
                            }
                        } else {
                            retorno.add("error");
                            retorno.add("La imagen no tiene el formato adecuado");
                        }
                    }
                }
            } else {
                retorno.add("error");
                retorno.add("No se ha introducido ningún fichero");
            }
        } catch (FileUploadException ex) {
            retorno.add("error");
            retorno.add("No se han podidos leer los campos del formulario");
        } catch (Exception ex) {
            retorno.add("error");
            retorno.add("No se ha podido subir la imagen al servidor");
        }

        
        // Declaramos el array JSON
        JSONArray arrayJSON = new JSONArray(retorno);
        response.setContentType("application/json");
        response.getWriter().print(arrayJSON);
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
