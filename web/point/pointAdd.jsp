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
</head>

<body>

    <div id="wrapper">

        <!-- Sidebar -->
        <!-- Brand and toggle get grouped for better mobile display -->
        <c:import var="menu" url="/mainMenu.jsp" />
        <c:out value="${menu}" escapeXml="false" />
        <!-- /.navbar-collapse -->

        <div id="page-wrapper">

            <div class="row">
                <div class="col-lg-12">
                    <h1>Mantenedor <small> Puntos</small></h1>
                    <ol class="breadcrumb">
                        <li><a href="PointMainServlet"><i class="fa fa-table"></i> DataTable</a></li>
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
                    <c:if test="${msgErrorPoints != null }" >
                        <div class="alert alert-dismissable alert-danger">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong><c:out value="${msgErrorPoints}" /></strong></br>
                        </div>
                    </c:if>
                    <c:if test="${msgErrorInsert != null }" >
                        <div class="alert alert-dismissable alert-danger">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong><c:out value="${msgErrorInsert}" /></strong></br>
                        </div>
                    </c:if>
                    <c:if test="${msgErrorRut != null }" >
                        <div class="alert alert-dismissable alert-danger">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong><c:out value="${msgErrorRut}" /></strong></br>
                        </div>
                    </c:if>
                </div>
                <div class="col-lg-4">
                    <form role="form" action="PointAddServlet" method="POST" name="formAdd">                        
                        <div class="form-group">
                            <label>Lugar</label>
                            <select class="form-control" name="idPlace">
                                <c:forEach var="listPlace" items="${listPlace}">  
                                    <option value="<c:out value="${listPlace.idPlace}" />" <c:if test="${point.idPlace == listPlace.idPlace}">selected</c:if>> <c:out value="${listPlace.namePlace}" /> </option>
                                </c:forEach>
                            </select>                                
                        </div>  
                        <c:if test="${msgErrorRut == null }" >
                            <div class="form-group">
                                <label>Rut</label>
                                <input type="text" class="form-control" maxlenth="12" name="rut" value="<c:out value="${rut}" />">
                            </div>
                        </c:if>
                        <c:if test="${msgErrorRut != null }" >
                            <div class="form-group has-error">
                                <label class="control-label" for="inputError">Rut</label>
                                <input type="text" class="form-control" maxlength="12" name="rut" id="inputError" value="<c:out value="${rut}" />">
                            </div>
                        </c:if>
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

</body>
</html>