<%-- 
    Document   : admin
    Created on : 26-dic-2013, 16:08:09
    Author     : patricio alberto
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>OTL - Admin</title>

        <!-- imperio css -->
        <link rel="stylesheet" type="text/css" href="css/bootstrap.css" />
        <link rel="stylesheet" type="text/css" href="css/bootstrap-responsive.css" />
        <link rel="stylesheet" type="text/css" href="css/imperio.css" />

        <!-- Bootstrap core CSS -->
        <link href="css/bootstrap.css" rel="stylesheet">

        <!-- Add custom CSS here -->
        <link href="css/sb-admin.css" rel="stylesheet">
        <link rel="stylesheet" href="font-awesome/css/font-awesome.min.css">
        <script type="text/javascript" src="http://code.jquery.com/jquery-1.6.min.js"></script>  

        <!-- JavaScript -->
        <script src="js/jquery-1.10.2.js"></script>
        <script src="js/bootstrap.js"></script>

        <!-- Page Specific Plugins -->
        <script src="js/tablesorter/jquery.tablesorter.js"></script>
        <script src="js/tablesorter/tables.js"></script>
    </head>
</head>

<body>

    <div id="wrapper">

        <!-- Collect the nav links, forms, and other content for toggling -->
        <c:import var="menu" url="/mainMenu.jsp" />
        <c:out value="${menu}" escapeXml="false" />
        <!-- /.navbar-collapse -->

        <div id="page-wrapper">

            <div class="row">
                <div class="col-lg-12">
                    <h1>Mantenedor <small> Admin</small></h1>
                    <ol class="breadcrumb">
                        <li><a href="AdminMainServlet"><i class="fa fa-table"></i> DataTable</a></li>
                        <li class="active"><i class="fa fa-edit"></i> Agregar</li>
                    </ol>
                    <c:if test="${msg != null}" >
                        <div class="alert alert-info alert-dismissable">
                            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                            <td><strong><c:out value="${msg}" /></strong></td>
                        </div>
                    </c:if>
                    <c:if test="${msgOk != null}" >
                        <div class="alert alert-dismissable alert-success">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong><c:out value="${msgOk}" /></strong>
                        </div>
                    </c:if>
                    <c:if test="${msgErrorPwd1 != null }" >
                        <div class="alert alert-dismissable alert-danger">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong><c:out value="${msgErrorPwd1}" /></strong></br>
                        </div>
                    </c:if>
                    <c:if test="${msgErrorPwd2 != null}" >
                        <div class="alert alert-dismissable alert-danger">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong><c:out value="${msgErrorPwd2}" /></strong></br>
                        </div>
                    </c:if>
                    <c:if test="${msgErrorUsername != null}" >
                        <div class="alert alert-dismissable alert-danger">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong><c:out value="${msgErrorUsername}" /></strong></br>
                        </div>
                    </c:if>
                    <c:if test="${msgErrorEmail != null}" >
                        <div class="alert alert-dismissable alert-danger">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong><c:out value="${msgErrorEmail}" /></strong></br>
                        </div>
                    </c:if>
                    <c:if test="${msgErrorDup != null}" >
                        <div class="alert alert-dismissable alert-danger">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong><c:out value="${msgErrorDup}" /></strong></br>
                        </div>
                    </c:if>
                    <c:if test="${msgErrorType != null}" >
                        <div class="alert alert-dismissable alert-danger">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong><c:out value="${msgErrorType}" /></strong></br>
                        </div>
                    </c:if>
                </div>
                <div class="col-lg-4">
                    <form role="form" action="AdminAddServlet" method="POST" name="formAdd">       
                        <c:choose>
                            <c:when test="${msgErrorUsername == null}">
                                <div class="form-group">
                                    <label>Username </label>
                                    <input class="form-control" required="true" maxlength="30" name="username" value="<c:out value="${admin.username}" />">
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">Username </label>
                                    <input class="form-control" required="true" maxlength="30" name="username" id="inputError" value="<c:out value="${admin.username}" />">
                                </div>
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${msgErrorEmail == null}">
                                <div class="form-group input-group">
                                    <span class="input-group-addon">@</span>
                                    <input type="email" required="true" name="email" maxlength="254" class="form-control" placeholder="Email" value="<c:out value="${admin.email}" />" >
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="form-group input-group has-error">
                                    <span class="input-group-addon" for="inputError">@</span>
                                    <input type="email" required="true" name="email" maxlength="254" id="inputError" maxlength="50" class="form-control" placeholder="Email" value="<c:out value="${admin.email}" />" >
                                </div>
                            </c:otherwise>
                        </c:choose>
                        <div class="form-group">
                            <label>Permisos</label>
                            <select class="form-control" name="type_admin">
                                <option value="333" selected>Nivel 3: Acceso a tarjetas y clientes</option>
                                <option value="555">Nivel 5: Acceso a eventos, promos y regalos</option>
                                <option value="777">Nivel 7: Acceso a todo</option>
                            </select>                                
                        </div>
                        <c:if test="${msgErrorPwd1 == null && msgErrorPwd2 == null }" >
                            <div class="form-group">
                                <label>Password (min 6 caracteres) </label>
                                <input class="form-control" type="password" maxlength="20" required="true" name="pwd1" value="<c:out value="${admin.pwd1}" />">
                            </div>
                            <div class="form-group">
                                <label>Confirmar Password </label>
                                <input class="form-control" type="password" maxlength="20" required="true" name="pwd2" value="<c:out value="${admin.pwd2}" />">
                            </div>
                        </c:if>
                        <c:if test="${msgErrorPwd1 != null && msgErrorPwd2 == null}" >
                            <div class="form-group has-error">
                                <label  class="control-label" for="inputError">Password (min 6 caracteres) </label>
                                <input class="form-control" type="password" maxlength="20" required="true" name="pwd1" id="inputError" value="<c:out value="${admin.pwd1}" />">
                            </div>
                            <div class="form-group has-error">
                                <label  class="control-label" for="inputError">Repetir Password </label>
                                <input class="form-control" type="password" maxlength="20" required="true" name="pwd2" id="inputError" value="<c:out value="${admin.pwd2}" />">
                            </div>
                        </c:if>  
                        <c:if test="${msgErrorPwd1 == null && msgErrorPwd2 != null}" >
                            <div class="form-group has-error">
                                <label  class="control-label" for="inputError">Password (min 6 caracteres) </label>
                                <input class="form-control" type="password" maxlength="20" required="true" name="pwd1" id="inputError" value="<c:out value="${admin.pwd1}" />">
                            </div>
                            <div class="form-group has-error">
                                <label  class="control-label" for="inputError">Repetir Password </label>
                                <input class="form-control" type="password" maxlength="20" required="true" name="pwd2" id="inputError" value="<c:out value="${admin.pwd2}" />">
                            </div>
                        </c:if> 
                        <c:if test="${msgErrorPwd1 != null && msgErrorPwd2 != null}" >
                            <div class="form-group has-error">
                                <label  class="control-label" for="inputError">Password (min 6 caracteres) </label>
                                <input class="form-control" type="password" maxlength="20" required="true" name="pwd1" id="inputError" value="<c:out value="${admin.pwd1}" />">
                            </div>
                            <div class="form-group has-error">
                                <label  class="control-label" for="inputError">Repetir Password </label>
                                <input class="form-control" type="password" maxlength="20" required="true" name="pwd2" id="inputError" value="<c:out value="${admin.pwd2}" />">
                            </div>
                        </c:if>
                        <button type="submit" name="add" class="btn btn-default"><strong><font size="1">AGREGAR</font></strong></button>
                        <button type="reset" class="btn btn-default"><strong><font size="1">RESET</font></strong></button> 
                    </form>
                </div>
            </div><!-- /.row -->
            <div class="row">                  
                <div class="col-lg-12">                        

                </div>
            </div><!-- /.row -->

        </div><!-- /#page-wrapper -->

    </div><!-- /#wrapper -->

    <script type="text/javascript" src="js/jquery.min.js"></script>
    <script type="text/javascript" src="js/imperiobootstrap.min.js"></script>
    <script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
    <script type="text/javascript" src="js/imperio.general.js"></script>
    <script type="text/javascript" src="js/imperio.tables.js"></script>

</body>
</html>