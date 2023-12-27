<jsp:directive.page contentType="text/html" pageEncoding="UTF-8"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="es">
    <head>
        <c:import url="../../../INC/cabecera.jsp">
            <c:param name="titulo" value="Listado" />
            <c:param name="estilo" value="${estilo}" />
        </c:import>
    </head>
    <body>
        <div id="principal">
            <h1>Usuarios con avatar subido con Part</h1>
            <table>
                <thead>
                <tr>
                    <th>Id</th>
                    <th>Nombre</th>
                    <th>Apellidos</th>
                    <th>Fecha nacimiento</th>
                    <th>Avatar</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${requestScope.usuarios}" var="usuario">
                    <tr>
                        <td class="centro"><c:out value="${usuario.id}"/></td>
                        <td class="resto"><c:out value="${usuario.nombre}"/></td>
                        <td class="resto"><c:out value="${usuario.apellidos}"/></td>
                        <td class="resto"><fmt:formatDate type="date" dateStyle="long" value="${usuario.fechaNacimiento}"/></td>
                        
                        <td class="centro"><img src="<c:url value='/imagenes/avatares/${usuario.avatarPart}'/>" alt="avatar usuario" title="Avatar de usuario" width="50" height="50"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <form name="myform" action="${contexto}/FrontController" method="post">   


                <div class="flex">
                    <input class="boton" type="submit" value="Inicio" name="boton" />
                </div>


            </form>
        </div>
    </body>
</html>
