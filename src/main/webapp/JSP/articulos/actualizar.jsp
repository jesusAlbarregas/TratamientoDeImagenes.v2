<jsp:directive.page contentType="text/html" pageEncoding="UTF-8"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
            <h1>Actualizar art&iacute;culo</h1>
            <form name="myform" action="${contexto}/SubirFotosArticulo" method="post" enctype="multipart/form-data">   

                <fieldset class="index">
                    <legend>Datos del art&iacute;culo</legend>
                    <p><label for="ident">Id: </label><c:out value="${sessionScope.articulo.id}"/></p>
                    <p><label for="deno">* Denominaci&oacute;n:</label>
                        <input type="text" id="deno" name="denominacion" maxlength="30" class="form-control" value="${sessionScope.articulo.denominacion}"/>
                    </p>
                    <p><label for="pvp">* PVP:</label>
                        <input type="text" id="pvp" name="pvp" class="form-control" value="${sessionScope.articulo.pvp}"/>
                    </p>
                    
                    <p>
                    <c:forEach items="${sessionScope.articulo.fotos}" var="foto">
                    <img src="data:image/png;base64,${foto.imagen}" 
                         alt="imagen articulo" title="Imagen artículo" width="50" height="50" />
                    </c:forEach>
                    </p>
                    <p>
                    <label for="ft">* Fotos (png y como máximo 50 KB): </label>
                    <%-- Podemos seleccionar más de una imagen a la vez en un mismo input de tipo file a través del atributo multiple
                         Así el nombre del control no puede ser simple y tiene que ser el nombre de un array, en nuestro caso fotos[] --%>
                    <input id="ft" type="file" name="fotos[]" multiple class="form-control">
                    </p>
                </fieldset>
                <br/>
                <p class="error"><c:out value="${requestScope.error}" default="" /></p>
                <div class="b1">
                    <input class="boton" type="submit" value="Actualizar" name="boton" />
                </div>
                <div class="b2">
                    <input class="boton" type="submit" value="Cancelar" name="boton" />
                </div>
                
            </form>
        </div>
    </body>
</html>
