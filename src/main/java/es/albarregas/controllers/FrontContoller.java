/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.albarregas.controllers;

import es.albarregas.beans.Articulo;
import es.albarregas.beans.Usuario;
import es.albarregas.beans.Foto;
import es.albarregas.dao.IArticuloDAO;
import es.albarregas.dao.IUsuarioDAO;
import es.albarregas.daofactory.DAOFactory;
import es.albarregas.models.Utilidades;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controlador inicial de cualquier operación a realizar en la aplicación
 * @author jesus
 */
@WebServlet(name = "FrontController", urlPatterns = {"/FrontController"})
public class FrontContoller extends HttpServlet {

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
        String url = ".";
        Utilidades.limpiarSesion(request);
        request.getRequestDispatcher(url).forward(request, response);
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
        if (request.getParameter("boton") != null && !request.getParameter("boton").equalsIgnoreCase("Inicio")) {
            DAOFactory daof = DAOFactory.getDAOFactory();
            IUsuarioDAO udao = daof.getUsuarioDAO();
            IArticuloDAO adao = daof.getArticuloDAO();
            List<Usuario> usuarios;
            List<Articulo> articulos;
            /*
        * Dependiendo de la opción elegida en el index.jsp realizaremos unas tareas u otras.
        * Si la opción empieza por "A." crearemos un nuevo usuario donde las imágenes residirán en un directorio del servidor utilizando fileupload o Part
        *   - Disponemos de una tabla "usuarios" donde almacenaremos: id de usuario, nombre, apellidos, fecha de nacimiento, 
        *     avatar (cuando subamos la imagen con fileupload) y avatarPart (cuando la subamos mediante Part)
        *   - Los campos avartar y avatarPart serán diferentes para almacenar el nombre de la imagen de cada modo de subida
        *   - Las imágenes se nombrarán como AvatarN + id de usuario en el caso de fileupload y part-AvatarN + id de usuario en el caso de utilizar Part
        * Si la opción empieza por "B." mostraremos todos los usuarios y elegiremos cual o cuales queremos eliminar
        * Si la opción empieza por "a." actualizaremos el usuario y subiremos su avatar al servidor utilizando la librería fileupload
        * Si la opción empieza por "1." actualizaremos el usuario y subiremos su avatar al servidor utilizando la clase javax.servlet.http.Part
        * En los dos últimos casos habrá: 
        *   - Que leer todos los usuarios que se encuentran en la base de datos para visualizarlos y a partir de ellos elegir uno que actualizar
        *   - Que guardar la opción elegida en sesión para conservarlo en sucesivos componentes
        * Si la opción empieza por "b." o "1." cambiaremos el avatar de un usuario en concreto para lo que tendremos que introducir el usuario a modificar
        *   - Si empieza por "b." o "2." leeremos todos los usuarios pero en la salida tendremos que distinguir:
        *       - Si empieza por "b." visualizaremos el campo avatar de la tabla usuarios
        *       - Si empieza por "2." visualizaremos el campo avatarPart de la tabla usuarios 
        *   - Si empieza por "1." utilizaremos la clase javax.servlet.http.Part
        * en ambos casos guardaremos la opción elegida en sesión para consevarlo en sucesivos componentes
        * Si la opción empieza por "I." crearemos un artículo donde las imágenes resirirán en la base de datos utilizando fileupload
        * Si la opción empieza por "III." visualizaremos todos los artículos con sus respectivas imágenes almacenadas en la base de datos, para lo cual:
        *   - Tenemos una tabla "artículos" donde almacenaremos: id del artículo, denominación y precio
        *   - Una tabla "fotos" donde almacenaremos id de la foto, id del artículo al que hacen referencia y foto la fotografía en sí
        * Para poder visualizar la foto en una vista .jsp necesitamos convertir un array de bytes a String para lo cual:
        *   - En el bean Foto crearemos un atributo del tipo String que no se encuentra en la tabla "fotos" y lo llamaremos Imagen
        *   - Convertimos el array de bytes a String mediante la sentencia Base64.getEncoder().encodeToString(foto.getFoto()), donde foto.getFoto() 
        *     es el array de bytes leído de la base de datos.
        * Si no se cumple ninguna de las condiciones anteriores es que vamos a añadir una foto a un artículo existente
             */
            String opcion = request.getParameter("boton");
            String numeroOpcion = opcion.substring(0, opcion.indexOf("."));

            switch (numeroOpcion) {
                // Nuevo usuario
                case "A":
                    url = "JSP/usuarios/comun/nuevo.jsp";
                    break;
                // Visualizar usuarios con imagen de avatar que se encuentra en un directorio privado de la aplicación
                case "4":
                    usuarios = udao.getUsuarios();
                    // Comprobamos que existen datos
                    if (usuarios != null) {
                        response.setContentType("image/png");
                        StringBuilder fichero;
                        StringBuilder nombre;
                        String ruta = request.getServletContext().getRealPath("/WEB-INF/avatares/");

                        List<Usuario> users = new ArrayList<>();
                        // Recorremos la lista de usuarios cargados después de la lectura de la base de datos
                        Iterator<Usuario> itUser = usuarios.iterator();
                        while (itUser.hasNext()) {
                            fichero = new StringBuilder();
                            nombre = new StringBuilder();
                            Usuario user = itUser.next();
                            // Comprobamos que el usuario disponga de avatar privado 
                            if (user.getAvatarPrivado() != null) {
                                nombre.append(user.getAvatarPrivado());
                                fichero.append(ruta).append(nombre);
                                // Obtenemos el array de bytes de la imagen
                                user.setBytesImages(Utilidades.extractBytes(fichero.toString()));
                                // Guardamos en el atributo la imagen que posteriormente se visualizará en la vista
                                user.setAvatarPrivado(Base64.getEncoder().encodeToString(user.getBytesImages()));
                            }
                            // Añadimos el usuario a la lista que se enviará por petición
                            users.add(user);

                        }
                        request.setAttribute("userPrivado", users);
                        url = "JSP/usuarios/comun/listadoUsuariosConAvatarPrivado.jsp";
                    } else {
                        request.setAttribute("aviso", "No existen usuarios almacenados");
                        url = "JSP/notificaciones/aviso.jsp";
                    }
                    break;
                // Eliminar un usuario con imágen del avatar en el servidor
                case "B":

                // Actualizar un usuario con imagen en el servidor con la librería FileUpload
                case "a":

                // Visualizar usuarios con imagen en el servidor con la librería FileUpload
                case "b":

                // Actualizar el avatar de un usuario con la imagen en el servidor con la librería FileUpload y utilizando Ajax
                case "c":

                // Actualizar un usuario con imagen en el servidor con la clase Part
                case "1":

                // Visualizar usuarios con imagen en el servidor con la clase Part
                case "2":

                // Visualizar usuarios para subir el avatar a la zona privada de la aplicación
                case "3":
                    // Leemos todos los registros de usuarios y los almacenamos en petición
                    usuarios = (List<Usuario>) udao.getUsuarios();
                    // Comprobamos que existen datos
                    if (usuarios != null) {
                        // Creamos un atributo de sesión para almacenar la herramienta con la que se subirá la imagen al servidor
                        String libreria = (opcion.startsWith("a.") || opcion.startsWith("b.") || opcion.startsWith("c.")) ? "fileupload" : "part";
                        // Introducimos la libería a utilizar en un atributo de sesión
                        request.getSession().setAttribute("libreria", libreria);
                        // Actualizar usuarios
                        switch (numeroOpcion) {
                            // Listar usuarios para Actualizar con FileUpload
                            case "a":
                            // Listar usuarios para Actualizar avatar utilizando Ajax
                            case "c":

                            // Listar usuarios para Actualizar con Part    
                            case "1":
                                url = "JSP/usuarios/comun/listaActualizar.jsp";
                                // Creamos un atributo de sesión con los datos de los usuarios
                                request.getSession().setAttribute("usuarios", usuarios);
                                // Almacenamos en sesión la opcion elegida
                                request.getSession().setAttribute("numeroOpcion", numeroOpcion);
                                break;
                            // Listar usuarios para Visualizar con FileUpload
                            case "b":
                                // Creamos un atributo de petición con los datos de los usuarios
                                request.setAttribute("usuarios", usuarios);
                                url = "JSP/usuarios/fileupload/listado.jsp";
                                break;
                            // Listar usuarios para Visualizar con Part    
                            case "2":
                                // Creamos un atributo de petición con los datos de los usuarios
                                request.setAttribute("usuarios", usuarios);
                                url = "JSP/usuarios/part/listado.jsp";
                                break;
                            case "B":
                                // Listar usuarios para Eliminar
                                // Creamos un atributo de sesión con los datos de los usuarios
                                request.getSession().setAttribute("usuarios", usuarios);
                                url = "JSP/usuarios/comun/listaEliminar.jsp";
                                break;
                            // Listar para subir avatar a la zona privada
                            case "3":
                                // Creamos un atributo de petición con los datos de los usuarios
                                request.setAttribute("usuarios", usuarios);
                                url = "JSP/usuarios/part/listaSubirPrivado.jsp";
                                break;
                        }

                    } else {
                        request.setAttribute("aviso", "No existen usuarios almacenados");
                        url = "JSP/notificaciones/aviso.jsp";
                    }
                    break;
                // Nuevo artículo con imagen en la base de datos
                case "I":
                    url = "JSP/articulos/nuevo.jsp";
                    break;
                // Actualizar artículos con imagen en la base de datos
                case "II":
                // Visualizar artículos con imágenes en la base de datos   
                case "III":
                // Eliminar artículos con imagenes en la base de datos
                case "IV":
                // Eliminar fotos de un artículo
                case "V":
                    articulos = (List<Articulo>) adao.getArticulos();
                    if (!articulos.isEmpty()) {
                        Iterator<Articulo> itArticulo = articulos.iterator();
                        Iterator<Foto> itFoto;
                        while (itArticulo.hasNext()) {
                            Articulo articulo = itArticulo.next();
                            if (!articulo.getFotos().isEmpty()) {
                                itFoto = articulo.getFotos().iterator();
                                while (itFoto.hasNext()) {
                                    Foto foto = itFoto.next();
                                    foto.setImagen(java.util.Base64.getEncoder().encodeToString(foto.getFoto()));
                                }
                            }
                        }
                        request.getSession().setAttribute("articulos", articulos);
                        switch (numeroOpcion) {
                            case "II":
                                // Actualizar artículo
                                url = "JSP/articulos/listaActualizar.jsp";
                                break;
                            case "III":
                                // Visualizar artículos
                                url = "JSP/articulos/listado.jsp";
                                break;
                            case "IV":
                                // Eliminar artículos
                                url = "JSP/articulos/listaEliminar.jsp";
                                break;
                            case "V":
                                // Eliminar fotos
                                url = "JSP/articulos/listaEliminarFotos.jsp";

                        }
                    } else {
                        request.setAttribute("aviso", "No disponemos de artículos en este momento");
                        url = "JSP/notificaciones/aviso.jsp";
                    }
            }
        } else {
            url = "index.jsp";
        }

        request.getRequestDispatcher(url).forward(request, response);

    }

}
