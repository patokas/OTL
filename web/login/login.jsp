<%-- 
    Document   : admin
    Created on : 26-dic-2013, 16:08:09
    Author     : patricio alberto
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Iniciar Sesión</title>
        <meta charset="utf-8">
        <!-- Stylesheets -->
        <link href="http://fonts.googleapis.com/css?family=Droid+Sans:400,700" rel="stylesheet">
        <link rel="stylesheet" href="css/styleLogin.css">

        <!-- Optimize for mobile devices -->
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>  
    </head>
    <body>

        <!-- TOP BAR -->
        <div id="top-bar">

            <div class="page-full-width">

                <a href="#" class="round button dark ic-left-arrow image-left "> Ir al Sitio Oficial</a>

            </div> <!-- end full-width -->	

        </div> <!-- end top-bar -->

        <!-- HEADER -->
        <div id="header">

            <div class="page-full-width cf">

                <div id="login-intro" class="fl">

                    <h1>INICIAR SESIÓN</h1>
                    <h5>Ingrese sus datos de admin</h5>

                </div> <!-- login-intro -->

                <!-- Change this image to your own company's logo -->
                <!-- The logo will automatically be resized to 39px height. -->
                <a href="#" id="company-branding" class="fr"><img src="images/company-logo.bmp" alt="Blue Hosting" /></a>

            </div> <!-- end full-width -->	

        </div> <!-- end header -->

        <!-- MAIN CONTENT -->
        <div id="content">            

            <form action="LoginServlet" method="POST" id="login-form">

                <fieldset>                    
                    <p>
                        <label for="login-username">username</label>
                        <input type="text" id="login-username" name="username" required="true" maxlength="40" class="round full-width-input" autofocus />
                    </p>

                    <p>
                        <label for="login-password">password</label>
                        <input type="password" id="login-password" name="password" required="true"  maxlength="20" class="round full-width-input" />
                    </p>
                    <c:if test="${msgErrorLogin != null}" >
                        <p><font color="red" ><c:out value="${msgErrorLogin}" /></font></p>
                    </c:if>

                    <input type="submit" name="btnLogin" class="button round blue image-right ic-right-arrow" value="LOG IN"/>
                    <p></p>
                    <p>He <a href="#">olvidado mi password</a>.</p>
                </fieldset>

                <br/><div class="information-box round">Ingrese sus datos y luego click en LOG IN.</div>

            </form>

        </div> <!-- end content -->

        <!-- FOOTER -->
        <div id="footer">

            <p>&copy; Copyright 2014 <a href="#">ON THE LIST! LTD</a>. Todos los derechos reservados.</p>

        </div> <!-- end footer -->

    </body>
</html>