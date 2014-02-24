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

        <title>OTL - Nueva Promoción</title>

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
                    <h1>Mantenedor <small> Promociones</small></h1>
                    <ol class="breadcrumb">
                        <li><a href="PromoGiftMainServlet"><i class="fa fa-table"></i> Principal</a></li>
                        <li class="active"><i class="fa fa-edit"></i> Agregar </li>
                    </ol>
                    <c:if test="${msg != null}" >
                        <div class="alert alert-info alert-dismissable">
                            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                            <td><strong><c:out value="${msg}" /></strong></td>
                        </div>
                    </c:if>
                    <c:if test="${msgErrorDate != null }" >
                        <div class="alert alert-dismissable alert-danger">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong><c:out value="${msgErrorDate}" /></strong></br>
                        </div>
                    </c:if>
                    <c:if test="${msgErrorUrlImage != null }" >
                        <div class="alert alert-dismissable alert-danger">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong><c:out value="${msgErrorUrlImage}" /></strong></br>
                        </div>
                    </c:if>
                    <c:if test="${msgErrorTittle != null }" >
                        <div class="alert alert-dismissable alert-danger">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong><c:out value="${msgErrorTittle}" /></strong></br>
                        </div>
                    </c:if>
                    <c:if test="${msgErrorDetails != null }" >
                        <div class="alert alert-dismissable alert-danger">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong><c:out value="${msgErrorDetails}" /></strong></br>
                        </div>
                    </c:if>
                    <c:if test="${msgErrorDup != null }" >
                        <div class="alert alert-dismissable alert-danger">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong><c:out value="${msgErrorDup}" /></strong></br>
                        </div>
                    </c:if>
                    <c:if test="${msgErrorPoints != null }" >
                        <div class="alert alert-dismissable alert-danger">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong><c:out value="${msgErrorPoints}" /></strong></br>
                        </div>
                    </c:if>
                    <c:if test="${msgOk != null}" >
                        <div class="alert alert-dismissable alert-success">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong><c:out value="${msgOk}" /></strong>
                        </div>
                    </c:if>

                </div>
                <div class="col-lg-4">
                    <form role="form" action="PromoAddServlet" method="POST" name="formAdd">  
                        <div class="form-group">
                            <label>Enviar a: </label>
                            <select class="form-control" name="sendTo">
                                <option value="1" <c:if test="${sendTo == 1}">selected</c:if>>Todos</option>
                                <option value="2" <c:if test="${sendTo == 2}">selected</c:if>>Sólo hombres</option>
                                <option value="3" <c:if test="${sendTo == 3}">selected</c:if>>Sólo mujeres</option>
                                <option value="4" <c:if test="${sendTo == 4}">selected</c:if>>Sólo cumpleañeros</option>
                                <option value="5" <c:if test="${sendTo == 5}">selected</c:if>>Ninguno. Agregar luego</option>
                                </select>                                
                            </div>
                            <div class="form-group">
                                <label>Plaza</label>
                                <select class="form-control" name="idPlace">
                                <c:forEach var="listPlace" items="${listPlace}">  
                                    <option value="<c:out value="${listPlace.idPlace}" />" <c:if test="${promo.idPlace == listPlace.idPlace}">selected</c:if>> <c:out value="${listPlace.namePlace}" /> </option>
                                </c:forEach>
                            </select>                                
                        </div>
                        <c:if test="${msgErrorDup == null && msgErrorTittle == null }" >
                            <div class="form-group">
                                <label>Título de Promoción </label>
                                <input class="form-control" required="true" maxlength="100" name="tittle" value="<c:out value="${promo.tittle}" />">
                            </div>
                        </c:if>
                        <c:if test="${msgErrorDup != null || msgErrorTittle != null}" >
                            <div class="form-group has-error">
                                <label class="control-label" for="inputError">Título de Promoción</label>
                                <input type="text" class="form-control" required="true" name="tittle" id="inputError" value="<c:out value="${promo.tittle}" />">
                            </div>
                        </c:if>
                        <c:if test="${msgErrorDetails == null }" >
                            <div class="form-group">
                                <label>Detalles</label>
                                <input class="form-control" required="true" maxlength="100" name="details" value="<c:out value="${promo.details}" />">
                            </div>
                        </c:if>
                        <c:if test="${msgErrorDetails != null }" >
                            <div class="form-group has-error">
                                <label class="control-label" for="inputError">Detalles</label>
                                <input class="form-control" required="true" maxlength="100" id="inputError" name="details" value="<c:out value="${promo.details}" />">
                            </div>
                        </c:if>
                        <c:if test="${msgErrorPoints != null }" >     
                            <div class="form-group has-error">
                                <label class="control-label" for="inputError"> Puntos Acumulables </label>
                                <input class="form-control" type="number" required="true" min="0" max="99999" name="points" value="<c:out value="${promo.points}" />">
                            </div>
                        </c:if>
                        <c:if test="${msgErrorPoints == null }" > 
                            <div class="form-group">
                                <label>Puntos Acumulables </label>
                                <input class="form-control" type="number" required="true" min="0" max="99999" name="points" value="<c:out value="${promo.points}" />">
                            </div>
                        </c:if>
                        <c:if test="${msgErrorUrlImage == null }" >
                            <div class="form-group">
                                <label>URL imagen </label>
                                <input class="form-control" required="true" maxlength="200" name="urlImage" value="<c:out value="${promo.urlImage}" />">
                            </div>
                        </c:if>
                        <c:if test="${msgErrorUrlImage != null }" >
                            <div class="form-group has-error">
                                <label class="control-label" for="inputError">URL imagen </label>
                                <input class="form-control" required="true" maxlength="200" id="inputError" name="urlImage" value="<c:out value="${promo.urlImage}" />">
                            </div>
                        </c:if>
                        <c:choose>
                            <c:when test="${msgErrorDup == null && msgErrorDate == null }">
                                <div class="form-group">
                                    <label>Fecha de Inicio</label>
                                    <input class="form-control" type="datetime-local" required="true" name="dateBegin" value="<c:out value="${promo.dateBegin}" />">
                                </div>
                                <div class="form-group">
                                    <label>Fecha de Término</label>
                                    <input class="form-control" type="datetime-local" required="true" name="dateEnd" value="<c:out value="${promo.dateEnd}" />">
                                </div>
                            </c:when>
                            <c:when test="${msgErrorDup != null && msgErrorDate == null }">
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">Fecha de Inicio</label>
                                    <input class="form-control" type="datetime-local" required="true" name="dateBegin" id="inputError" value="<c:out value="${promo.dateBegin}" />">
                                </div>
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">Fecha de Término</label>
                                    <input class="form-control" type="datetime-local" required="true" name="dateEnd" id="inputError" value="<c:out value="${promo.dateEnd}" />">
                                </div>
                            </c:when>
                            <c:when test="${msgErrorDup == null && msgErrorDate != null }">
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">Fecha de Inicio</label>
                                    <input class="form-control" type="datetime-local" required="true" name="dateBegin" id="inputError" value="<c:out value="${promo.dateBegin}" />">
                                </div>
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">Fecha de Término</label>
                                    <input class="form-control" type="datetime-local" required="true" name="dateEnd" id="inputError" value="<c:out value="${promo.dateEnd}" />">
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="form-group">
                                    <label>Fecha de Inicio</label>
                                    <input class="form-control" type="datetime-local" required="true" name="dateBegin" value="<c:out value="${promo.dateBegin}" />">
                                </div>
                                <div class="form-group">
                                    <label>Fecha de Término</label>
                                    <input class="form-control" type="datetime-local" required="true" name="dateEnd" value="<c:out value="${promo.dateEnd}" />">
                                </div>
                            </c:otherwise>
                        </c:choose>
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