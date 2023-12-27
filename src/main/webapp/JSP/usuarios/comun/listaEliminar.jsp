<jsp:directive.page contentType="text/html" pageEncoding="UTF-8"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <c:import url="../../../INC/cabecera.jsp">
            <c:param name="titulo" value="Eliminar" />
            <c:param name="estilo" value="${estilo}" />
        </c:import>
    </head>
    <body>
        <div id="principal">
            <form name="myform" action="${contexto}/EliminarUsuario" method="post"> 
                <h1>Eliminar usuarios</h1>
                <table>
                    <caption>Elige uno o varios de los candidatos a eliminar</caption>
                    <thead>
                    <tr>
                        <th style="width: 5%;">Elige</th>
                        <th style="width: 15%;">Nombre</th>
                        <th style="width: 25%;">Apellidos</th>
                        <th style="width: 20%;">Fecha nacimiento</th>
                        <th colspan="2" style="width: 35%;">Avatares</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${sessionScope.usuarios}" var="usuario">
                        <tr>
                            <td class="centro"><input type="checkbox" name="registro" value="${usuario.id}"/></td>
                            <td class="resto"><c:out value="${usuario.nombre}"/></td>
                            <td class="resto"><c:out value="${usuario.apellidos}"/></td>
                            <td class="resto"><fmt:formatDate type="date" dateStyle="medium" value="${usuario.fechaNacimiento}"/></td>

                            <td class="centro"><img src="<c:url value='/imagenes/avatares/${usuario.avatar}'/>" alt="avatar usuario" title="Avatar de usuario" width="50" height="50"/></td>
                            <td class="centro"><img src="<c:url value='/imagenes/avatares/${usuario.avatarPart}'/>" alt="avatar usuario" title="Avatar de usuario" width="50" height="50"/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
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
