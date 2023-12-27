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
            <form name="myform" action="${contexto}/EliminarArticulo" method="post"> 
                <h1>Eliminar art&iacute;culos</h1>
                <table>
                    <caption>Elige uno de los candidatos a eliminar</caption>
                    <tr>
                        <th>Elige</th>
                        <th>Denominaci&oacute;n</th>
                        <th>PVP</th>
                        <th>Fotos</th>

                    </tr>
                    <c:forEach items="${sessionScope.articulos}" var="articulo">
                        <tr>
                            <td class="centro"><input type="radio" name="registro" value="${articulo.id}"/></td>
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
