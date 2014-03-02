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
                    <h1>Mantenedor <small> Código de Vestir</small></h1>
                    <ol class="breadcrumb">
                        <li><a href="DressCodeMainServlet"><i class="fa fa-table"></i> DataTable</a></li>
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
                    <c:if test="${msgErrorDup != null }" >
                        <div class="alert alert-dismissable alert-danger">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong><c:out value="${msgErrorDup}" /></strong></br>
                        </div>
                    </c:if>
                    <c:if test="${msgErrorTittle != null }" >
                        <div class="alert alert-dismissable alert-danger">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong><c:out value="${msgErrorTittle}" /></strong></br>
                        </div>
                    </c:if>
                    <c:if test="${msgErrorMenDetails != null }" >
                        <div class="alert alert-dismissable alert-danger">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong><c:out value="${msgErrorMenDetails}" /></strong></br>
                        </div>
                    </c:if>
                    <c:if test="${msgErrorWomenDetails != null }" >
                        <div class="alert alert-dismissable alert-danger">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong><c:out value="${msgErrorWomenDetails}" /></strong></br>
                        </div>
                    </c:if>
                    <c:if test="${msgErrorUrlImage != null }" >
                        <div class="alert alert-dismissable alert-danger">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong><c:out value="${msgErrorUrlImage}" /></strong></br>
                        </div>
                    </c:if>
                </div>
                <div class="col-lg-4">
                    <form role="form" action="DressCodeAddServlet" method="POST" name="formAdd">   
                        <c:if test="${msgErrorDup == null && msgErrorTittle == null}" >
                            <div class="form-group">
                                <label>Título</label>
                                <input class="form-control" required="true" maxlength="30" name="tittle" value="<c:out value="${tittle}" />">
                            </div>
                        </c:if>
                        <c:if test="${msgErrorDup != null || msgErrorTittle != null }" >
                            <div class="form-group has-error">
                                <label class="control-label" for="inputError">Título</label>
                                <input class="form-control" required="true" maxlength="30" name="tittle" id="inputError" value="<c:out value="${tittle}" />">
                            </div>
                        </c:if>
                        <c:if test="${msgErrorMenDetails == null}" >
                            <div class="form-group">
                                <label>Descripción para Hombres</label>
                                <input class="form-control" required="true" maxlength="255" name="menDetails" value="<c:out value="${menDetails}" />">
                            </div>
                        </c:if>
                        <c:if test="${msgErrorMenDetails != null }" >
                            <div class="form-group has-error">
                                <label class="control-label" for="inputError">Descripción para Hombres</label>
                                <input class="form-control" required="true" maxlength="255" name="menDetails" id="inputError" value="<c:out value="${menDetails}" />">
                            </div>
                        </c:if>
                        <c:if test="${msgErrorWomenDetails == null}" >
                            <div class="form-group">
                                <label>Descripción para Mujeres</label>
                                <input class="form-control" required="true" maxlength="255" name="womenDetails" value="<c:out value="${womenDetails}" />">
                            </div>
                        </c:if>
                        <c:if test="${msgErrorWomenDetails != null }" >
                            <div class="form-group has-error">
                                <label class="control-label" for="inputError">Descripción para Mujeres</label>
                                <input class="form-control" required="true" maxlength="255" name="womenDetails" id="inputError" value="<c:out value="${womenDetails}" />">
                            </div>
                        </c:if>
                        <c:if test="${msgErrorUrlImage == null}" >
                            <div class="form-group">
                                <label>URL imagen</label>
                                <input class="form-control" required="true" maxlength="255" name="urlImage" value="<c:out value="${urlImage}" />">
                            </div>
                        </c:if>
                        <c:if test="${msgErrorUrlImage != null }" >
                            <div class="form-group has-error">
                                <label class="control-label" for="inputError">URL imagen</label>
                                <input class="form-control" required="true" maxlength="255" name="urlImage" id="inputError" value="<c:out value="${urlImage}" />">
                            </div>
                        </c:if>
                        <button type="submit" name="add" class="btn btn-default"><strong><font size="1">AGREGAR</font></strong></button>
                        <button type="reset" class="btn btn-default"><strong><font size="1">RESET</font></strong></button> 
                    </form>
                </div>
            </div><!-- /.row -->          

        </div><!-- /#page-wrapper -->

    </div><!-- /#wrapper -->

</body>
</html>