<jsp:directive.page contentType="text/html" pageEncoding="UTF-8"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contexto" value="${pageContext.request.contextPath}" scope="application"/>
<c:url var="estilo" value="/CSS/estilo.css" scope="application"/>
<!DOCTYPE html>
<html lang="es">
    <head>
        <c:import url="INC/cabecera.jsp">
            <c:param name="titulo" value="Gestión de imágenes" />
            <c:param name="estilo" value="${estilo}" />
        </c:import>
    </head>
    <body>
        <div id="principal">
            <h1>Tratamiento de Im&aacute;genes</h1>
            <div id="secundario">
                <form action="${contexto}/FrontController" method="post">
                    <fieldset class="contiene">
                        <legend style="color: green">Imágenes en el servidor sobre la tabla usuarios</legend>
                        <fieldset class="comun">
                            <legend style="color: blue">Operaciones comunes</legend>
                            <p><input type="submit" name="boton" value="A. Nuevo" class="enlace" 
                                      title="FrontController&#10;comun/nuevo.jsp&#10;NuevoUsuario" /></p>
                            <p><input type="submit" name="boton" value="B. Eliminar" class="enlace" 
                                      title="FrontController&#10;comun/listaEliminar.jsp&#10;EliminarUsuario&#10;comun/confirmaEliminar.jsp&#10;EliminarUsuario" /></p>
                        </fieldset>
                        <div class="flota">
                            <fieldset class="dentro">
                                <legend>Librer&iacute;a commons.fileupload</legend>

                                <p><input type="submit" name="boton" value="a. Actualizar" class="enlace" 
                                          title="FrontController&#10;comun/listaActualizar.jsp&#10;ActualizarUsuario&#10;fileupload/actualizar.jsp&#10;SubirAvatarUsuarioFileUpload" /></p>
                                <p><input type="submit" name="boton" value="b. Visualizar" class="enlace" 
                                          title="FrontController&#10;fileupload/listado.jsp&#10;FrontController" /></p>
                                <p><input type="submit" name="boton" value="c. Cambiar avatar con ajax" class="enlace" 
                                          title="FrontController&#10;comun/listaActualizar.jsp&#10;ActualizarUsuario&#10;fileupload/actualizarAvatarAjax.jsp&#10;AvatarAjax"/></p>

                            </fieldset>
                        </div>
                        <div class="flota">
                            <fieldset class="dentro">
                                <legend>Clase Part</legend>

                                <p><input type="submit" name="boton" value="1. Actualizar" class="enlace" 
                                          title="FrontController&#10;comun/listaActualizar.jsp&#10;ActualizarUsuario&#10;part/actualizar.jsp&#10;SubirAvatarUsuarioPart" /></p>
                                <p><input type="submit" name="boton" value="2. Visualizar" class="enlace" 
                                          title="FrontController&#10;part/listado.jsp&#10;FrontController" /></p>
                                <p><input type="submit" name="boton" value="3. Subir privado" class="enlace" 
                                          title="FrontController&#10;part/listaSubirPrivado.jsp&#10;SubirPrivadoAvatarUsuario" /></p>
                                <p><input type="submit" name="boton" value="4. Ver privado" class="enlace" 
                                          title="FrontController&#10;comun/listadoUsuariosConAvatarPrivado.jsp&#10;FrontController" /></p>
                            </fieldset>
                        </div>
                    </fieldset>
                    <br>
                    <fieldset class="contiene">
                        <legend style="color: green">Im&aacute;genes en la base de datos sobre la tabla art&iacute;culos y fotos</legend>
                        <div class="b1" style="text-align: left;">
                             
                        <p><input type="submit" name="boton" value="I. Crear artículo" class="enlace" 
                                  title="FrontController&#10;nuevo.jsp&#10;NuevoArticulo" /></p>
                        <p><input type="submit" name="boton" value="II. Actualizar un artículo" class="enlace" 
                                  title="FrontController&#10;listaActualizar.jsp&#10;ActualizarArticulo&#10;actualizar.jsp&#10;SubirFotosArticulo" /></p>
                        <p><input type="submit" name="boton" value="III. Ver todos los artículos" class="enlace" 
                                  title="FrontController&#10;listado.jsp&#10;FrontController" /></p>
                        </div>
                        <div class="b2" style="text-align: left;">
                        <p><input type="submit" name="boton" value="IV. Eliminar artículos" class="enlace" 
                                  title="FrontController&#10;listaEliminar.jsp&#10;EliminarArticulo&#10;confirmaEliminar.jsp&#10;EliminarArticulo" /></p>
                        <p><input type="submit" name="boton" value="V. Eliminar fotos de un art&iacute;culo" class="enlace" 
                                  title="FrontController&#10;listaEliminarFotos.jsp&#10;EliminarFotos&#10;confirmaEliminarFotos.jsp&#10;EliminarFotos" /></p>
                        </div>
                    </fieldset>
                </form>

            </div>
        </div>
    </body>
</html>
