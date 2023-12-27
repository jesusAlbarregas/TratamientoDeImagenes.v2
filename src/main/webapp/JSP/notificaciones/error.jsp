<jsp:directive.page contentType="text/html" pageEncoding="UTF-8"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <c:import url="../../INC/cabecera.jsp">
            <c:param name="titulo" value="Error" />
            <c:param name="estilo" value="${estilo}" />
        </c:import>
    </head>
    <body>
        <div id="principal">
            <form name="myform" action="${contexto}/FrontController" method="post">   

                <fieldset style="padding-left: 20px;">
                    <legend class="error">Error</legend>
                    <p class="error"><c:out value="${requestScope.error}"/></p>
                    

                </fieldset>
                <br/>
                <div class="flex">
                    <input class="boton" type="submit" value="Inicio" name="boton" />
                </div>
                
                
            </form>
        </div>
    </body>
</html>
