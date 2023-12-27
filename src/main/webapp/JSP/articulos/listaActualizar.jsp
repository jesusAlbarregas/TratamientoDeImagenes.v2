<jsp:directive.page contentType="text/html" pageEncoding="UTF-8"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="es">
    <head>
        <c:import url="../../INC/cabecera.jsp">
            <c:param name="titulo" value="Actualizar" />
            <c:param name="estilo" value="${estilo}" />
        </c:import>
    </head>
    <body>
        <div id="principal">
            <h1>Actualizar art&iacute;culos</h1>
            <form name="myform" action="${contexto}/ActualizarArticulo" method="post"> 
            <table>
                <caption>Elige uno de los candidatos a actualizar</caption>
                <tr>
                    <th>Elige</th>
                    <th>Denominaci&oacute;n</th>
                    <th>PVP</th>

                </tr>
                <c:forEach items="${sessionScope.articulos}" var="articulo">
                    <tr>
                        <td class="centro"><input type="radio" name="registro" value="${articulo.id}"/></td>
                        <td class="resto"><c:out value="${articulo.denominacion}"/></td>
                        
                        <td class="resto numero"><fmt:formatNumber type="currency" value="${articulo.pvp}"/></td>
                        
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
