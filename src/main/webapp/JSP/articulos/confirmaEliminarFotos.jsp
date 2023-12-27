<jsp:directive.page contentType="text/html" pageEncoding="UTF-8"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
            <h1>Fotos elegidas de los art&iacute;culos para eliminar</h1>
            <table>
                
                <tr>
                    
                    <th>Denominaci&oacute;n</th>
                    
                    <th>Fotos</th>
                    
                </tr>
                <c:forEach items="${sessionScope.fotosEliminar}" var="articulo">
                    <tr>
                        
                        <td class="resto"><c:out value="${articulo.denominacion}"/></td>
                        
                        <td class="centro">
                            <c:forEach items="${articulo.fotos}" var="foto">
                                <img src="data:image/png;base64,${foto.imagen}" 
                                     alt="imagen articulo" title="Imagen artículo" width="50" height="50" />
                            </c:forEach>
                        </td>
                    </tr>
                </c:forEach>
            </table>

            <form name="myform" action="${contexto}/EliminarFotos" method="post"> 


                <div class="flex"> 

                    <input class="boton" type="submit" value="Confirmar" name="boton" />
                    <input class="boton" type="submit" value="Cancelar" name="boton" />

                </div>



            </form>
        </div>
    </body>
</html>
