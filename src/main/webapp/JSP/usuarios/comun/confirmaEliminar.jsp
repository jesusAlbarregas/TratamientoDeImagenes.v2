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
            <h1>Usuarios elegidos para eliminar</h1>
            <table>
                
                <tr>
                    <th>Id</th>
                    <th>Nombre</th>
                    <th>Apellidos</th>
                    <th>Fecha nacimiento</th>
                    <th>Avatares</th>
                </tr>
                <c:forEach items="${sessionScope.usuariosEliminar}" var="usuario">
                    <tr>
                        <td class="centro"><c:out value="${usuario.id}"/></td>
                        <td class="resto"><c:out value="${usuario.nombre}"/></td>
                        <td class="resto"><c:out value="${usuario.apellidos}"/></td>
                        <td class="resto"><fmt:formatDate type="date" dateStyle="long" value="${usuario.fechaNacimiento}"/></td>
                        
                        <td class="centro">
                            <img src="<c:url value='/imagenes/avatares/${usuario.avatar}'/>" alt="avatar usuario" title="Avatar de usuario" width="50" height="50"/>
                            <img src="<c:url value='/imagenes/avatares/${usuario.avatarPart}'/>" alt="avatar usuario" title="Avatar de usuario" width="50" height="50"/>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            
            <form name="myform" action="${contexto}/EliminarUsuario" method="post"> 


                <div class="flex"> 

                    <input class="boton" type="submit" value="Confirmar" name="boton" />
                    <input class="boton" type="submit" value="Cancelar" name="boton" />

                </div>


            </form>
        </div>
    </body>
</html>
