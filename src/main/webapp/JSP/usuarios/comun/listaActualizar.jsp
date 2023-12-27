<jsp:directive.page contentType="text/html" pageEncoding="UTF-8"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
            <form name="myform" action="${contexto}/ActualizarUsuario" method="post"> 
                <h1>Actualizar usuarios</h1>
                <table>
                    <caption>Elige uno de los candidatos a actualizar</caption>
                    <tr>
                        <th style="width: 5%;">Elige</th>
                        <th style="width: 15%;">Nombre</th>
                        <th style="width: 25%;">Apellidos</th>
                        <th style="width: 20%;">Fecha nacimiento</th>
                        <th style="width: 35%;">Avatar</th>
                    </tr>
                    <c:forEach items="${sessionScope.usuarios}" var="usuario">
                        <tr>
                            <td class="centro"><input type="radio" name="registro" value="${usuario.id}"/></td>
                            <td class="resto"><c:out value="${usuario.nombre}"/></td>
                            <td class="resto"><c:out value="${usuario.apellidos}"/></td>
                            <td class="resto"><fmt:formatDate type="date" dateStyle="medium" value="${usuario.fechaNacimiento}"/></td>
                            <c:set var="imagen" value="${usuario.avatar}"/>
                            <c:if test="${fn:contains(libreria, 'part')}">
                                <c:set var="imagen" value="${usuario.avatarPart}"/>
                            </c:if>
                            <td class="centro"><img src="<c:url value='/imagenes/avatares/${imagen}'/>" alt="avatar usuario" title="Avatar de usuario" width="50" height="50"/></td>
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
