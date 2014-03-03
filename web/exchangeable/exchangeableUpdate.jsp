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

        <title>OTL - Actualizar Producto</title>

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
                    <h1>Mantenedor <small> Productos canjeables</small></h1>
                    <ol class="breadcrumb">
                        <li><a href="ExchangeableMainServlet"><i class="fa fa-table"></i> DataTable</a></li>
                        <li class="active"><i class="fa fa-edit"></i> Actualizar</li>
                    </ol>
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
                    <c:if test="${msgErrorIdPlace != null}" >
                        <div class="alert alert-dismissable alert-danger">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong><c:out value="${msgErrorIdPlace}" /></strong></br>
                        </div>
                    </c:if>
                    <c:if test="${msgErrorUrlImage != null }" >
                        <div class="alert alert-dismissable alert-danger">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong><c:out value="${msgErrorUrlImage}" /></strong></br>
                        </div>
                    </c:if> 
                    <c:if test="${msgErrorIdExchangeable != null}" >
                        <div class="alert alert-dismissable alert-danger">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong><c:out value="${msgErrorIdExchangeable}" /></strong></br>
                        </div>
                    </c:if>
                    <c:if test="${msgErrorNamePlace != null}" >
                        <div class="alert alert-dismissable alert-danger">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong><c:out value="${msgErrorNamePlace}" /></strong></br>
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
                    <c:if test="${msgErrorRequest != null }" >
                        <div class="alert alert-dismissable alert-danger">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong><c:out value="${msgErrorRequest}" /></strong></br>
                        </div>
                    </c:if>
                </div>
                <div class="col-lg-4">
                    <form role="form" action="ExchangeableUpdateServlet" method="POST" name="formUpdate">
                        <div class="form-group">
                            <label for="disabledSelect">Plaza</label>
                            <input class="form-control" id="disabledInput" type="text" placeholder="<c:out value="${exchange.namePlace}" />" disabled>
                            <input type="hidden" name="namePlace" value="<c:out value="${exchange.namePlace}" />"/>
                            <input type="hidden" name="idPlace" value="<c:out value="${exchange.idPlace}" />"/>
                        </div>
                        <div class="form-group">
                            <label for="disabledSelect">Id Exchangeable</label>
                            <input class="form-control" id="disabledInput" type="text" placeholder="<c:out value="${exchange.idExchangeable}" />" disabled>
                            <input type="hidden" name="idExchangeable" value="<c:out value="${exchange.idExchangeable}" />"/>
                        </div>
                        <c:if test="${msgErrorDup == null && msgErrorTittle == null}" >
                            <div class="form-group">
                                <label>Título de Producto</label>
                                <input class="form-control" required="true" maxlength="100" name="tittle" value="<c:out value="${exchange.tittle}" />">
                            </div>
                        </c:if>
                        <c:if test="${msgErrorDup != null || msgErrorTittle != null}" >
                            <div class="form-group has-error">
                                <label class="control-label" for="inputError">Título de Producto</label>
                                <input class="form-control" required="true" maxlength="100" id="inputError" name="tittle" value="<c:out value="${exchange.tittle}" />">
                            </div>
                        </c:if>                        
                        <c:if test="${msgErrorPoints != null }" >     
                            <div class="form-group has-error">
                                <label class="control-label" for="inputError"> Puntos</label>
                                <input class="form-control" type="number" required="true" min="0" max="99999" name="points" value="<c:out value="${exchange.points}" />">
                            </div>
                        </c:if>
                        <c:if test="${msgErrorPoints == null }" > 
                            <div class="form-group">
                                <label>Puntos</label>
                                <input class="form-control" type="number" required="true" min="0" max="99999" name="points" value="<c:out value="${exchange.points}" />">
                            </div>
                        </c:if>
                        <c:if test="${msgErrorUrlImage == null }" >
                            <div class="form-group">
                                <label>URL imagen </label>
                                <input class="form-control" required="true" maxlength="200" name="urlImage" value="<c:out value="${exchange.urlImage}" />">
                            </div>
                        </c:if>
                        <c:if test="${msgErrorUrlImage != null }" >
                            <div class="form-group has-error">
                                <label class="control-label" for="inputError">URL imagen </label>
                                <input class="form-control" required="true" maxlength="200" id="inputError" name="urlImage" value="<c:out value="${exchange.urlImage}" />">
                            </div>
                        </c:if>
                        <div class="form-group">
                            <label>Solicitud: </label>
                            <select class="form-control" name="exchangeRequest">                                
                                <option value="0" <c:if test="${exchange.request == 0}">selected</c:if>>Pendiente</option>
                                <option value="1" <c:if test="${exchange.request == 1}">selected</c:if>>Aceptada</option>
                                <option value="2" <c:if test="${exchange.request == 2}">selected</c:if>>Rechazada</option>
                            </select>          
                        </div>
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