<jsp:directive.page contentType="text/html" pageEncoding="UTF-8"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="es">
    <head>
        <c:import url="../../INC/cabecera.jsp">
            <c:param name="titulo" value="Eliminar" />
            <c:param name="estilo" value="${estilo}" />
        </c:import>
        <script type="text/javascript">
            function mostrar(capa) {
                var objDiv = document.getElementById(capa);

                if (objDiv.style.display === "block") {
                    objDiv.style.display = "none";
                } else {
                    objDiv.style.display = "block";
                }
            }
        </script>
    </head>
    <body>
        <div id="principal">
            <h1>Eliminar fotos de art&iacute;culos</h1>
            <h3>Pulsa sobre el art&iacute;culo para ver sus fotos</h3>
            <form name="myform" action="${contexto}/EliminarFotos" method="post"> 

                <c:forEach items="${sessionScope.articulos}" var="articulo">
                    <div id="secundario">
                        <p>
                            <a class="enlace" onclick="mostrar('${articulo.id}')"><c:out value="${articulo.denominacion}"/></a>
                        </p>
                        <div id="${articulo.id}" class="resuelto">
                            <c:set var="bandera" value="0"/>
                            <c:forEach var="foto" items="${articulo.fotos}">

                                <span><input type="checkbox" name="registro" value="${foto.id}"/></span>
                                <span><img src="data:image/png;base64,${foto.imagen}" 
                                           alt="imagen articulo" title="Imagen artículo" width="50" height="50" /></span>
                                    <c:set var="bandera" value="1"/>



                            </c:forEach>
                            <c:if test="${bandera==0}">
                                <span>Este art&iacute;culo no dispone de fotos</span>
                            </c:if>
                        </div>
                    </div>
                </c:forEach>

                <div class="limpiar"></div>
                <p class="error"><c:out value="${requestScope.error}" default="" /></p>

                <div class="flex"> 

                    <input class="boton" type="submit" value="Realizar" name="boton" />
                    <input class="boton" type="submit" value="Cancelar" name="boton" />

                </div>

            </form>
        </div>
    </body>
</html>
