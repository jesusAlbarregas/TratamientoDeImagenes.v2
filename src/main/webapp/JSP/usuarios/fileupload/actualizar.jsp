<jsp:directive.page contentType="text/html" pageEncoding="UTF-8"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <c:import url="../../../INC/cabecera.jsp">
            <c:param name="titulo" value="Actualizar" />
            <c:param name="estilo" value="${estilo}" />
        </c:import>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Actualizar FileUpload</title>
        <link rel="stylesheet" type="text/css" href="${estilo}">
        <%@include file="/INC/metas.inc"%>
    </head>
    <body>
        <div id="principal">
            <h1>Actualizar usuario mediante la librería FileUpload</h1>
            <form name="myform" action="${contexto}/SubirAvatarUsuarioFileUpload" method="post" enctype="multipart/form-data">   

                <fieldset class="index">
                    <legend>Datos del usuario</legend>
                    <p><label for="ident">Id: </label><c:out value="${sessionScope.usuario.id}"/></p>
                    <p><label for="nombre">* Nombre:</label>
                        <input type="text" id="nombre" name="nombre" maxlength="15" class="form-control" value="${sessionScope.usuario.nombre}"/>
                    </p>
                    <p><label for="ape">* Apellidos:</label>
                        <input type="text" id="ape" name="apellidos" maxlength="30" class="form-control" value="${sessionScope.usuario.apellidos}"/>
                    </p>
                    <p>
                    <label for="fn">* Fecha de nacimiento: </label>
                    <input id="fn" type="date" name="fechaNacimiento" placeholder="dd-mm-aaaa" class="form-control" value="${sessionScope.usuario.fechaNacimiento}">
                    </p>
                    <p>
                    <img src='<c:url value="/imagenes/avatares/${sessionScope.usuario.avatar}"/>' 
                         alt="avatar del usuario" title="Avatar del usuario" width="50" height="50" />
                    </p>
                    <p>
                    <label for="av">* Avatar (png o jpg y como máximo 100 KB): </label>
                    <input id="av" type="file" name="avatar" class="form-control">
                    </p>
                </fieldset>
                <br/>
                <p class="error"><c:out value="${requestScope.error}" default="" /></p>
                <div class="flex"> 

                    <input class="boton" type="submit" value="Actualizar" name="boton" />
                    <input class="boton" type="submit" value="Cancelar" name="boton" />

                </div>
                
            </form>
        </div>
    </body>
</html>
