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

        <title>OTL - Eventos</title>

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
                    <h1>Mantenedor <small> Eventos</small></h1>
                    <ol class="breadcrumb">
                        <li><a href="EventMainServlet"><i class="fa fa-table"></i> DataTable</a></li>
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
                    <c:if test="${msgErrormsgErrorIdPlace != null }" >
                        <div class="alert alert-dismissable alert-danger">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong><c:out value="${msgErrormsgErrorIdPlace}" /></strong></br>
                        </div>
                    </c:if>
                    <c:if test="${msgErrorDate != null}" >
                        <div class="alert alert-dismissable alert-danger">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong><c:out value="${msgErrorDate}" /></strong></br>
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
                    <c:if test="${msgErrorDateBegin != null }" >
                        <div class="alert alert-dismissable alert-danger">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong><c:out value="${msgErrorDateBegin}" /></strong></br>
                        </div>
                    </c:if>
                    <c:if test="${msgErrorDateEnd != null }" >
                        <div class="alert alert-dismissable alert-danger">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong><c:out value="${msgErrorDateEnd}" /></strong></br>
                        </div>
                    </c:if>
                    <c:if test="${msgErrorEvent != null }" >
                        <div class="alert alert-dismissable alert-danger">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong><c:out value="${msgErrorEvent}" /></strong></br>
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
                    <form role="form" action="EventAddServlet" method="POST" name="formAdd">                        
                        <div class="form-group">
                            <label>Plaza</label>
                            <select class="form-control" name="idPlace">
                                <c:forEach var="listPlace" items="${listPlace}">  
                                    <option value="<c:out value="${listPlace.idPlace}" />" <c:if test="${event.idPlace == listPlace.idPlace}">selected</c:if>> <c:out value="${listPlace.namePlace}" /> </option>
                                </c:forEach>
                            </select>                                
                        </div>
                        <c:choose>
                            <c:when test="${msgErrorEvent == null && msgErrorTittle == null}">
                                <div class="form-group">
                                    <label>Título de Evento</label>
                                    <input class="form-control" required="true" maxlength="100" name="tittle" value="<c:out value="${event.tittle}" />">
                                </div>
                            </c:when>
                            <c:when test="${msgErrorEvent != null && msgErrorTittle == null}">
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">Título de Evento</label>
                                    <input type="text" class="form-control" required="true" name="tittle" id="inputError" value="<c:out value="${event.tittle}" />">
                                </div>
                            </c:when>
                            <c:when test="${msgErrorEvent == null && msgErrorTittle != null}">
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">Título de Evento</label>
                                    <input type="text" class="form-control" required="true" name="tittle" id="inputError" value="<c:out value="${event.tittle}" />">
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="form-group">
                                    <label>Título de Evento</label>
                                    <input class="form-control" required="true" maxlength="100" name="tittle" value="<c:out value="${event.tittle}" />">
                                </div>
                            </c:otherwise>
                        </c:choose>
                        <div class="form-group">
                            <label>Detalles</label>
                            <input class="form-control" maxlength="100" name="details" value="<c:out value="${event.details}" />">
                        </div>
                        <c:if test="${msgErrorPoints != null }" >
                            <div class="form-group has-error">
                                <label class="control-label" for="inputError">Puntos</label>
                                <input type="number" class="form-control" min="0" max="99999" name="points" id="inputError" value="<c:out value="${event.points}" />">
                            </div>
                        </c:if>
                        <c:if test="${msgErrorPoints == null }" >
                            <div class="form-group">
                                <label>Puntos</label>
                                <input type="number" class="form-control" min="0" max="99999" name="points" value="<c:out value="${event.points}" />">
                            </div>
                        </c:if>
                        <div class="form-group">
                            <label>Imagen (ingrese la URL)</label>
                            <input class="form-control" maxlength="200" name="url" value="<c:out value="${event.urlImage}" />">
                        </div>
                        <c:choose>
                            <c:when test="${msgErrorEvent == null && msgErrorDate == null }">
                                <div class="form-group">
                                    <label>Fecha de Inicio</label>
                                    <input class="form-control" type="datetime-local" required="true" name="dateBegin" value="<c:out value="${event.dateBegin}" />">
                                </div>
                                <div class="form-group">
                                    <label>Fecha de Término</label>
                                    <input class="form-control" type="datetime-local" required="true" name="dateEnd" value="<c:out value="${event.dateEnd}" />">
                                </div>
                            </c:when>
                            <c:when test="${msgErrorEvent != null && msgErrorDate == null }">
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">Fecha de Inicio</label>
                                    <input class="form-control" type="datetime-local" required="true" name="dateBegin" id="inputError" value="<c:out value="${event.dateBegin}" />">
                                </div>
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">Fecha de Término</label>
                                    <input class="form-control" type="datetime-local" required="true" name="dateEnd" id="inputError" value="<c:out value="${event.dateEnd}" />">
                                </div>
                            </c:when>
                            <c:when test="${msgErrorEvent == null && msgErrorDate != null }">
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">Fecha de Inicio</label>
                                    <input class="form-control" type="datetime-local" required="true" name="dateBegin" id="inputError" value="<c:out value="${event.dateBegin}" />">
                                </div>
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">Fecha de Término</label>
                                    <input class="form-control" type="datetime-local" required="true" name="dateEnd" id="inputError" value="<c:out value="${event.dateEnd}" />">
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="form-group">
                                    <label>Fecha de Inicio</label>
                                    <input class="form-control" type="datetime-local" required="true" name="dateBegin" value="<c:out value="${event.dateBegin}" />">
                                </div>
                                <div class="form-group">
                                    <label>Fecha de Término</label>
                                    <input class="form-control" type="datetime-local" required="true" name="dateEnd" value="<c:out value="${event.dateEnd}" />">
                                </div>
                            </c:otherwise>
                        </c:choose>
                        <div class="form-group">
                            <label>Solicitud: </label>
                            <select class="form-control" name="eventRequest">                                
                                <option value="0" <c:if test="${event.request == 0}">selected</c:if>>Pendiente</option>
                                <option value="1" <c:if test="${event.request == 1}">selected</c:if>>Aceptada</option>
                                <option value="2" <c:if test="${event.request == 2}">selected</c:if>>Rechazada</option>
                            </select>                                
                        </div>
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