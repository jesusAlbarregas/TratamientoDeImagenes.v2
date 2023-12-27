<jsp:directive.page contentType="text/html" pageEncoding="UTF-8"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <c:import url="../../../INC/cabecera.jsp">
            <c:param name="titulo" value="Nuevo" />
            <c:param name="estilo" value="${estilo}" />
        </c:import>
    </head>
    <body>
        <div id="principal">
            <h1>Nuevo usuario</h1>
            <form name="myform" action="${contexto}/NuevoUsuario" method="post">   

                <fieldset class="index">
                    <legend>Datos del usuario</legend>
                    <p><label for="nombre">* Nombre:</label>
                        <input type="text" id="nombre" name="nombre" maxlength="15" 
                               placeholder="Ej. Jesús" class="form-control" value="${param.nombre}"/>
                    </p>
                    <p><label for="ape">* Apellidos:</label>
                    <input type="text" id="ape" name="apellidos" maxlength="30" 
                           placeholder="Ej. Pérez García" class="form-control" value="${param.apellidos}"/>
                    </p>
                    <p>
                    <label for="fn">* Fecha de nacimiento: </label>
                    <input id="fn" type="date" name="fechaNacimiento" 
                           placeholder="dd-mm-aaaa" class="form-control" value="${param.fechaNacimiento}">
                    </p>

                </fieldset>
                <br/>
                <p class="error"><c:out value="${requestScope.error}" default="" /></p>
                <div class="flex"> 

                    <input class="boton" type="submit" value="Realizar" name="boton" />
                    <input class="boton" type="submit" value="Cancelar" name="boton" />

                </div>
                
            </form>
        </div>
    </body>
</html>
