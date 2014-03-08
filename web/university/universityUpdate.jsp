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

        <title>OTL</title>

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
                    <h1>Mantenedor <small> Universidades</small></h1>
                    <ol class="breadcrumb">
                        <li><a href="UniversityMainServlet"><i class="fa fa-table"></i> DataTable</a></li>
                        <li class="active"><i class="fa fa-edit"></i> Actualizar</li>
                    </ol>
                    <c:if test="${msgOk != null}" >
                        <div class="alert alert-dismissable alert-success">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong><c:out value="${msgOk}" /></strong>
                        </div>
                    </c:if>
                    <c:if test="${msgErrorId != null}" >
                        <div class="alert alert-dismissable alert-danger">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong><c:out value="${msgErrorId}" /></strong></br>
                        </div>
                    </c:if> 
                    <c:if test="${msgErrorName != null}" >
                        <div class="alert alert-dismissable alert-danger">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong><c:out value="${msgErrorName}" /></strong></br>
                        </div>
                    </c:if>
                    <c:if test="${msgErrorDup != null}" >
                        <div class="alert alert-dismissable alert-danger">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong><c:out value="${msgErrorDup}" /></strong></br>
                        </div>
                    </c:if>
                    <c:if test="${msgErrorFound != null}" >
                        <div class="alert alert-dismissable alert-danger">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong><c:out value="${msgErrorFound}" /></strong></br>
                        </div>
                    </c:if>

                </div>
                <div class="col-lg-4">
                    <form role="form" action="UniversityUpdateServlet" method="POST" name="formUpdate">
                        <div class="form-group">
                            <label for="disabledSelect">ID Universidad</label>
                            <input class="form-control" id="disabledInput" type="text" placeholder="<c:out value="${uni.idUniversity}" />" disabled>
                            <input type="hidden" name="idUniversity" value="<c:out value="${uni.idUniversity}" />"/>
                        </div>
                        <c:if test="${msgErrorDup == null }" >
                            <div class="form-group">
                                <label>Nombre Universidad</label>
                                <input class="form-control" required="true" maxlength="50" name="nameUniversity" value="<c:out value="${uni.nameUniversity}" />">
                            </div>
                        </c:if>
                        <c:if test="${msgErrorDup != null }" >
                            <div class="form-group has-error">
                                <label class="control-label" for="inputError">Nombre Universidad</label>
                                <input class="form-control" required="true" maxlength="50" name="nameUniversity" id="inputError" value="<c:out value="${uni.nameUniversity}" />">
                            </div>
                        </c:if>
                        <button type="submit" class="btn btn-default"><strong><font size="1">ACTUALIZAR</font></strong></button>
                    </form>

                </div>
            </div><!-- /.row -->
            <div class="row">                  
                <div class="col-lg-12">                        

                </div>
            </div><!-- /.row -->

        </div><!-- /#page-wrapper -->

    </div><!-- /#wrapper -->
</body>
</html>