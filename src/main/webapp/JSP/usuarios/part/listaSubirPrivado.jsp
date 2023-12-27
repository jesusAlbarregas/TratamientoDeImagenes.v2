<jsp:directive.page contentType="text/html" pageEncoding="UTF-8"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="es">
    <head>
        <c:import url="../../../INC/cabecera.jsp">
            <c:param name="titulo" value="Actualizar" />
            <c:param name="estilo" value="${estilo}" />
        </c:import>
    </head>
    <body>
        <div id="principal">
            <form name="myform" action="${contexto}/SubirPrivadoAvatarUsuario" method="post">
                <h1>Actualizar usuarios</h1>
                <table>
                    <caption>Elige uno entre los candidatos para subir avatar privado</caption>
                    <tr>
                        <th>Elige</th>
                        <th>Nombre</th>
                        <th>Apellidos</th>
                        <th>Fecha nacimiento</th>

                    </tr>
                    <c:forEach items="${requestScope.usuarios}" var="usuario">
                        <tr>
                            <td class="centro"><input type="radio" name="registro" value="${usuario.id}"/></td>
                            <td class="resto"><c:out value="${usuario.nombre}"/></td>
                            <td class="resto"><c:out value="${usuario.apellidos}"/></td>
                            <td class="resto"><fmt:formatDate type="date" dateStyle="long" value="${usuario.fechaNacimiento}"/></td>


                        </tr>
                    </c:forEach>
                </table>
                <br>
                <p class="error"><c:out value="${requestScope.error}" default="" /></p>

                <div class="flex">

                    <input class="boton" type="submit" value="Realizar" name="boton" />
                    <input class="boton" type="submit" value="Cancelar" name="boton" />

                </div>

            </form>
        </div>
    </body>
</html>
