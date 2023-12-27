<jsp:directive.page contentType="text/html" pageEncoding="UTF-8"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <c:import url="../../../INC/cabecera.jsp">
            <c:param name="titulo" value="Actualizar" />
            <c:param name="estilo" value="${estilo}" />
        </c:import>
    </head>
    <body>
        <div id="principal">
            <h1>Subir un avatar a la parte privada de la aplicaci&oacute;n</h1>
            <form name="myform" action="${contexto}/SubirPrivadoAvatarUsuario" method="post" enctype="multipart/form-data">   

                <fieldset class="index">
                    <legend>Datos del usuario</legend>
                    <p><label for="ident">Id: </label><c:out value="${sessionScope.usuario.id}"/></p>
                    <p><label for="nombre">Nombre:</label>
                        <c:out value="${sessionScope.usuario.nombre}"/>
                    </p>
                    <p><label for="ape">Apellidos:</label>
                        <c:out value="${sessionScope.usuario.apellidos}"/>
                    </p>
                    <p>
                    <label for="fn">Fecha de nacimiento: </label>
                    <fmt:formatDate type="date" dateStyle="long" value="${sessionScope.usuario.fechaNacimiento}"/>
                    </p>
                    
                    <p>
                    <label for="av">* Avatar (png o jpg y como máximo 100 KB): </label>
                    <input id="av" type="file" name="avatarPrivado" class="form-control">
                    </p>
                </fieldset>
                <br/>
                <p class="error"><c:out value="${requestScope.error}" default="" /></p>
                <div class="flex"> 

                    <input class="boton" type="submit" value="Subir" name="boton" />
                    <input class="boton" type="submit" value="Cancelar" name="boton" />

                </div>
                
            </form>
        </div>
    </body>
</html>
