<jsp:directive.page contentType="text/html" pageEncoding="UTF-8"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <c:import url="../../INC/cabecera.jsp">
            <c:param name="titulo" value="Listado" />
            <c:param name="estilo" value="${estilo}" />
        </c:import>
    </head>
    <body>
        <div id="principal">
            <h1>Listado de art&iacute;culos</h1>
            <c:forEach items="${articulos}" var="articulo">
            <table id="articulos">
                
                <tr>
                    <th>Id</th>
                    <th>Denominación</th>
                    <th>Precio</th>
                    
                </tr>
                <tr>
                    <td class="centro"><c:out value="${articulo.id}"/></td>
                    <td class="resto"><c:out value="${articulo.denominacion}"/></td>
                    <td class="resto numero"><fmt:formatNumber type="currency" value="${articulo.pvp}"/></td>
                </tr>
            
                    <tr>
                        <td colspan="3">
                            <c:set var="bandera" value="0"/>
                <c:forEach items="${articulo.fotos}" var="foto">
                    
                    <img src="data:image/png;base64,${foto.imagen}" 
                         alt="imagen articulo" title="Imagen artículo" width="50" height="50" />
                    <c:set var="bandera" value="1"/>
                </c:forEach>
                    <c:if test="${bandera==0}">
                        <c:out value="Este artículo no dispone de fotos"/>
                    </c:if>
                    </td>
                    </tr>
            </table>
            </c:forEach>
            <form name="myform" action="${contexto}/FrontController" method="post">   


                <div class="flex">
                    <input class="boton" type="submit" value="Inicio" name="boton" />
                </div>


            </form>
        </div>
    </body>
</html>
