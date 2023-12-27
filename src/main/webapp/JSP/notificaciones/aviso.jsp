<jsp:directive.page contentType="text/html" pageEncoding="UTF-8"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <c:import url="../../INC/cabecera.jsp">
            <c:param name="titulo" value="Notificación" />
            <c:param name="estilo" value="${estilo}" />
        </c:import>
    </head>
    <body>
        <div id="principal">
            <h1>Notificaci&oacute;n</h1>
            <form name="myform" action="${contexto}/FrontController" method="post">   

                <fieldset style="padding-left: 20px;">
                    
                    <c:out value="${requestScope.aviso}"/>
                    

                </fieldset>
                <br/>
                <div class="flex">
                    <input class="boton" type="submit" value="Inicio" name="boton" />
                </div>
                
                
            </form>
        </div>
    </body>
</html>
