<jsp:directive.page contentType="text/html" pageEncoding="UTF-8"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
            <h1>Listado de usuarios con todos los avatares</h1>
            <table>
                
                <thead>
                    <tr>
                        <th rowspan="2">Id</th>
                        <th rowspan="2">Nombre</th>
                        <th rowspan="2">Apellidos</th>
                        <th rowspan="2">Fecha de nacimiento</th>
                        <th colspan="3">Tipos de avatares</th>
                    </tr>
                    <tr>
                        <th>Upload</th>
                        <th>Part</th>
                        <th>Privado</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${requestScope.userPrivado}" var="usuario">
                        <tr>
                            <td class="centro"><c:out value="${usuario.id}"/></td>
                            <td class="resto"><c:out value="${usuario.nombre}"/></td>
                            <td class="resto"><c:out value="${usuario.apellidos}"/></td>
                            <td class="resto"><fmt:formatDate type="date" dateStyle="long" value="${usuario.fechaNacimiento}"/></td>
                            <td class="centro"><img src="<c:url value='/imagenes/avatares/${usuario.avatar}'/>" alt="avatar usuario" title="Avatar de usuario" width="50" height="50"/></td>
                            <td class="centro"><img src="<c:url value='/imagenes/avatares/${usuario.avatarPart}'/>" alt="avatar usuario" title="Avatar de usuario" width="50" height="50"/></td>
                            <c:set var="defecto" value="default.png" />
                                <c:choose>
                                <c:when test="${usuario.avatarPrivado == null}">
                                    <td class="centro"><img src="<c:url value='/imagenes/avatares/privado-default.png'/>" alt="avatar privado" title="Avatar" width="50" height="50"/></td>
                                    </c:when>
                                    <c:otherwise>
                                    <td class="centro"><img src="<c:out value="data:image/png;base64,${usuario.avatarPrivado}"/>" alt="avatar privado" title="Avatar" width="50" height="50" /></td>
                                    </c:otherwise>
                                </c:choose>    

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
