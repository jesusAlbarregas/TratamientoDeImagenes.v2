<jsp:directive.page contentType="text/html" pageEncoding="UTF-8"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <c:import url="../../INC/cabecera.jsp">
            <c:param name="titulo" value="Nuevo" />
            <c:param name="estilo" value="${estilo}" />
        </c:import>
    </head>
    <body>
        <div id="principal">
            <h1>Nuevo art&iacute;culo</h1>
            <form name="myform" action="${contexto}/NuevoArticulo" method="post">   

                <fieldset class="index">
                    <legend>Datos del artículo</legend>
                    <p><label for="deno">* Denominación:</label>
                    <input type="text" id="deno" name="denominacion" class="form-control" 
                           maxlength="30" placeholder="Ej. Monitor" value="${param.denominacion}"/>
                    </p>
                    <p><label for="precio">* Precio:</label>
                    <input type="text" id="precio" name="pvp" class="form-control"
                           placeholder="Ej. 124.45" value="${param.pvp}" />
                    </p>
                    

                </fieldset>
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
