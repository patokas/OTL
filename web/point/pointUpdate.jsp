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

        <title>OTL - Puntos</title>

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

    <body>

        <div id="wrapper">

            <!-- Collect the nav links, forms, and other content for toggling -->
            <c:import var="menu" url="/mainMenu.jsp" />
            <c:out value="${menu}" escapeXml="false" />
            <!-- /.navbar-collapse -->

            <div id="page-wrapper">

                <div class="row">
                    <div class="col-lg-12">
                        <h1>Mantenedor <small> Puntos</small></h1>
                        <ol class="breadcrumb">
                            <li><a href="PointMainServlet"><i class="fa fa-table"></i> DataTable</a></li>
                            <li class="active"><i class="fa fa-edit"></i> Actualizar</li>
                        </ol>
                        <c:if test="${msgOk != null}" >
                            <div class="alert alert-dismissable alert-success">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgOk}" /></strong>
                            </div>
                        </c:if>                                       
                        <c:if test="${msgErrorFound != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorFound}" /></strong></br>
                            </div>
                        </c:if>
                        <c:if test="${msgErrorPoints != null }" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorPoints}" /></strong></br>
                            </div>
                        </c:if>
                        <c:if test="${msgErrorRut != null }" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorRut}" /></strong></br>
                            </div>
                        </c:if>
                        <c:if test="${msgErrorFound != null }" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorFound}" /></strong></br>
                            </div>
                        </c:if>
                    </div>
                    <div class="col-lg-4">
                        <form role="form" action="PointUpdateServlet" method="POST" name="formUpdate">
                            <div class="form-group">
                                <label for="disabledSelect">Plaza</label>
                                <input class="form-control" id="disabledInput" type="text" placeholder="<c:out value="${point.namePlace}" />" disabled>
                                <input type="hidden" name="namePlace" value="<c:out value="${point.namePlace}" />"/>
                                <input type="hidden" name="idPlace" value="<c:out value="${point.idPlace}" />"/>
                            </div>
                            <div class="form-group">
                                <label for="disabledSelect">Rut</label>
                                <input class="form-control" id="disabledInput" type="text" placeholder="<c:out value="${point.rut}" />-<c:out value="${point.dv}" />" disabled>
                                <input type="hidden" name="rut" value="<c:out value="${point.rut}" />"/>
                                <input type="hidden" name="dv" value="<c:out value="${point.dv}" />"/>
                            </div>                                             
                            <c:if test="${msgErrorPoints != null }" >
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">Puntos</label>
                                    <input type="number" class="form-control" min="0" max="99999" name="points" id="inputError" value="<c:out value="${point.points}" />">
                                </div>
                            </c:if>
                            <c:if test="${msgErrorPoints == null }" >
                                <div class="form-group">
                                    <label>Puntos</label>
                                    <input type="number" class="form-control" min="0" max="99999" name="points" value="<c:out value="${point.points}" />">
                                </div>
                            </c:if>                   
                            <button type="submit" class="btn btn-default"><strong><font size="1">ACTUALIZAR</font></strong></button>
                        </form>
                    </div>
                </div><!-- /.row -->
            </div><!-- /#page-wrapper -->
        </div><!-- /#wrapper -->
    </body>
</html>