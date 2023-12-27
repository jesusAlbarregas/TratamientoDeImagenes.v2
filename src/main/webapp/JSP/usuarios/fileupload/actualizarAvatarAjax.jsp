<jsp:directive.page contentType="text/html" pageEncoding="UTF-8"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <c:import url="../../../INC/cabecera.jsp">
            <c:param name="titulo" value="Actualizar con Ajax" />
            <c:param name="estilo" value="${estilo}" />
        </c:import>
        <script type="text/javascript" src="https://code.jquery.com/jquery-1.12.0.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/notify/0.4.2/notify.min.js"></script>

    </head>
    <body>
        <div id="principal">

            <h1>Actualizar el avatar mediante Ajax</h1>
            <div id="mensaje"></div>
            <fieldset style="padding-left: 20px;">

                <legend>Datos del usuario</legend>
                <table>
                    <tr>
                        <td>Nombre</td>
                        <td><c:out value="${usuario.nombre}" /></td>

                        <td rowspan="3">
                            <img src='<c:url value="/imagenes/avatares/${usuario.avatar}"/>' 
                                 alt="avatar del usuario" title="Avatar del usuario" width="100" height="100" id="imagenAvatar"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Apellidos</td>
                        <td><c:out value="${usuario.apellidos}" /></td>
                    </tr>
                    <tr>
                        <td>Fecha de nacimiento</td>
                        <td><c:out value="${usuario.fechaNacimiento}" /></td>
                    </tr>
                </table>




            </fieldset>
            <form id="modAvatar">
                <fieldset>
                    <legend>Cambio de avatar</legend>
                    <div id="secundario">

                        <img src='<c:url value="/imagenes/avatares/${usuario.avatar}"/>' 
                             alt="avatar del usuario" title="Avatar del usuario" width="100" height="100" id="previa"/>
                        <br>

                        <label for="imagenAvatar">Avatar (png o jpg y como máximo 100 KB): </label>
                        <input id="ficheroAvatar" type="file" name="avatar" class="form-control">
                    </div>
                    <br>
                </fieldset>
                <div class="b1">
                    <button class="boton" name="boton" id="cambiarAvatar">
                        <div>Cambiar</div>
                    </button>
                </div>
                <div class="b2">
                    <button class="boton" name="boton" id="cambiarAvatar" onclick="location.href = 'FrontController'">
                        <div>Cancelar</div>
                    </button>
                </div>

            </form>
        </div>
        <script type="text/javascript" src="${contexto}/js/cambioAvatar.js"></script>
    </body>
</html>
