<jsp:directive.page contentType="text/html" pageEncoding="UTF-8"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <c:import url="../../INC/cabecera.jsp">
            <c:param name="titulo" value="Eliminar" />
            <c:param name="estilo" value="${estilo}" />
        </c:import>
    </head>
    <body>
        <div id="principal">
            <h1>Art&iacute;culos elegidos para eliminar</h1>
            <table>

                <tr>
                    <th>Id</th>
                    <th>Denominaci&oacute;n</th>
                    <th>PVP</th>
                    <th>Fotos</th>

                </tr>
                <c:forEach items="${sessionScope.articulosEliminar}" var="articulo">
                    <tr>
                        <td class="centro"><c:out value="${articulo.id}"/></td>
                        <td class="resto"><c:out value="${articulo.denominacion}"/></td>
                        <td class="resto numero"><fmt:formatNumber type="currency" value="${articulo.pvp}"/></td>

                        <c:choose>
                            <c:when test="${fn:length(articulo.fotos) != 0}">
                                <td class="centro">
                                    <c:forEach items="${articulo.fotos}" var="foto">
                                        <img src="data:image/png;base64,${foto.imagen}" 
                                             alt="imagen articulo" title="Imagen artículo" width="50" height="50" />
                                    </c:forEach>
                                </td>
                            </c:when>
                            <c:otherwise>
                                <td class="resto">Sin fotos</td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                </c:forEach>
            </table>

            <form name="myform" action="${contexto}/EliminarArticulo" method="post"> 

                <div class="flex"> 

                    <input class="boton" type="submit" value="Confirmar" name="boton" />
                    <input class="boton" type="submit" value="Cancelar" name="boton" />

                </div>

            </form>
        </div>
    </body>
</html>
