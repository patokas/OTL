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

        <title>OTL - Invitados</title>

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
                    <h1>Mantenedor <small> Invitados</small></h1>
                    <ol class="breadcrumb">
                        <li><a href="GuestMainServlet"><i class="fa fa-table"></i> DataTable</a></li>
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
                    <c:if test="${msgErrorDup != null}" >
                        <div class="alert alert-dismissable alert-danger">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong><c:out value="${msgErrorDup}" /></strong></br>
                        </div>
                    </c:if>
                    <c:if test="${msgErrorIdEvent != null}" >
                        <div class="alert alert-dismissable alert-danger">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong><c:out value="${msgErrorIdEvent}" /></strong></br>
                        </div>
                    </c:if>
                    <c:if test="${msgErrorRut != null}" >
                        <div class="alert alert-dismissable alert-danger">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong><c:out value="${msgErrorRut}" /></strong></br>
                        </div>
                    </c:if>                    
                </div>
                <div class="col-lg-4">
                    <form role="form" action="GuestAddServlet" method="POST" name="formAdd">                         
                        <div class="form-group">
                            <label>Plaza</label>
                            <select class="form-control" name="idPlace">
                                <c:forEach var="listPlace" items="${listPlace}">  
                                    <option value="<c:out value="${listPlace.idPlace}" />" <c:if test="${guest.idPlace == listPlace.idPlace}">selected</c:if>> <c:out value="${listPlace.namePlace}" /> </option>
                                </c:forEach>
                            </select>                                
                        </div>
                        <c:if test="${msgErrorIdEvent == null}" >
                            <div class="form-group ">
                                <label  class="control-label">id Evento </label>
                                <input class="form-control" maxlength="6" required="true" name="idEvent" value="<c:out value="${guest.idEvent}" />">
                            </div>
                        </c:if>
                        <c:if test="${msgErrorIdEvent != null}" >
                            <div class="form-group has-error ">
                                <label  class="control-label" for="inputError">id Evento </label>
                                <input class="form-control" maxlength="6" required="true" id="inputError" name="idEvent" value="<c:out value="${guest.idEvent}" />">
                            </div>
                        </c:if>
                        <c:if test="${msgErrorRut == null}" >
                            <div class="form-group">
                                <label  class="control-label">RUT </label>
                                <input class="form-control" maxlength="12" required="true" name="rut" value="<c:out value="${guest.rut}" />-<c:out value="${guest.dv}" />">
                            </div>
                        </c:if>
                        <c:if test="${msgErrorRut != null}" >
                            <div class="form-group has-error">
                                <label  class="control-label" for="inputError">RUT </label>
                                <input class="form-control" maxlength="12" id="inputError" required="true" name="rut" value="<c:out value="${guest.rut}" />-<c:out value="${guest.dv}" />">
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