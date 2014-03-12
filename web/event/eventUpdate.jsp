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

        <script type="text/javascript">
            function changeDisplay() {
                if (document.formUpdate.request.value == 2) {
                    document.formUpdate.reason.style.display = 'block';
                } else {
                    document.formUpdate.reason.style.display = 'none';
                }
            }
        </script>
    </head>

    <body onload="changeDisplay()">

        <div id="wrapper">

            <!-- Collect the nav links, forms, and other content for toggling -->
            <c:import var="menu" url="/mainMenu.jsp" />
            <c:out value="${menu}" escapeXml="false" />
            <!-- /.navbar-collapse -->

            <div id="page-wrapper">

                <div class="row">
                    <div class="col-lg-12">
                        <h1>Mantenedor <small> Eventos</small></h1>
                        <ol class="breadcrumb">
                            <li><a href="EventMainServlet"><i class="fa fa-table"></i> DataTable</a></li>
                            <li class="active"><i class="fa fa-edit"></i> Actualizar</li>
                        </ol>
                        <c:if test="${msgOk != null}" >
                            <div class="alert alert-dismissable alert-success">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgOk}" /></strong>
                            </div>
                        </c:if>
                        <c:if test="${msgErrorDate != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorDate}" /></strong></br>
                            </div>
                        </c:if>
                        <c:if test="${msgErrorIdPlace != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorIdPlace}" /></strong></br>
                            </div>
                        </c:if> 
                        <c:if test="${msgErrorIdEvent != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorIdEvent}" /></strong></br>
                            </div>
                        </c:if>    
                        <c:if test="${msgErrorUrlImage != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorUrlImage}" /></strong></br>
                            </div>
                        </c:if>
                        <c:if test="${msgErrorNamePlace != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorNamePlace}" /></strong></br>
                            </div>
                        </c:if> 
                        <c:if test="${msgErrorTittle != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorTittle}" /></strong></br>
                            </div>
                        </c:if> 
                        <c:if test="${msgErrorDetails != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorDetails}" /></strong></br>
                            </div>
                        </c:if> 
                        <c:if test="${msgErrorDateBegin != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorDateBegin}" /></strong></br>
                            </div>
                        </c:if> 
                        <c:if test="${msgErrorDateEnd != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorDateEnd}" /></strong></br>
                            </div>
                        </c:if> 
                        <c:if test="${msgErrorDupEvent != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorDupEvent}" /></strong></br>
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
                        <form role="form" action="EventUpdateServlet" method="POST" name="formUpdate">
                            <div class="form-group">
                                <label for="disabledSelect">Lugar</label>
                                <input class="form-control" id="disabledInput" type="text" placeholder="<c:out value="${event.namePlace}" />" disabled>
                                <input type="hidden" name="namePlace" value="<c:out value="${event.namePlace}" />"/>
                                <input type="hidden" name="idPlace" value="<c:out value="${event.idPlace}" />"/>
                            </div>
                            <div class="form-group">
                                <label for="disabledSelect">ID Evento</label>
                                <input class="form-control" id="disabledInput" type="text" placeholder="<c:out value="${event.idEvent}" />" disabled>
                                <input type="hidden" name="idEvent" value="<c:out value="${event.idEvent}" />"/>
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
                            <c:if test="${msgErrorDetails == null}" >
                                <div class="form-group">                            
                                    <label>Detalles</label>
                                    <input class="form-control" required="true" maxlength="100" name="details" value="<c:out value="${event.details}" />">                            
                                </div>
                            </c:if>
                            <c:if test="${msgErrorDetails != null}" >
                                <div class="form-group has-error">                            
                                    <label class="control-label" for="inputError">Detalles</label>
                                    <input class="form-control" required="true" maxlength="100" id="inputError" name="details" value="<c:out value="${event.details}" />">                            
                                </div>
                            </c:if>
                            <div class="form-group">
                                <label>Código de Vestir</label>
                                <select class="form-control" name="idDressCode">
                                    <c:forEach var="listDressCode" items="${listDressCode}">  
                                        <option value="<c:out value="${listDressCode.idDressCode}" />" <c:if test="${event.idDressCode == listDressCode.idDressCode}">selected</c:if>> <c:out value="${listDressCode.nameDressCode}" /> </option>
                                    </c:forEach>
                                </select>                                
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
                            <c:if test="${msgErrorUrlImage == null}" >
                                <div class="form-group">
                                    <label>URL de Imagen</label>
                                    <input class="form-control" required="true" maxlength="100" name="urlImage" value="<c:out value="${event.urlImage}" />">
                                </div> 
                            </c:if>
                            <c:if test="${msgErrorUrlImage != null}" >
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">URL de Imagen</label>
                                    <input class="form-control" required="true" maxlength="100" id="inputError" name="urlImage" value="<c:out value="${event.urlImage}" />">
                                </div> 
                            </c:if>
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
                                <select class="form-control" id="request" name="request" onchange="changeDisplay()">                                
                                    <option value="0" <c:if test="${event.request == 0}">selected</c:if>>Pendiente</option>
                                    <option value="1" <c:if test="${event.request == 1}">selected</c:if>>Aceptada</option>
                                    <option value="2" <c:if test="${event.request == 2}">selected</c:if>>Rechazada</option>
                                    </select>                                
                                </div>
                                <div class="form-group" id="reason">
                                    <label>Razón de rechazo</label>
                                    <textarea class="form-control" name="reason" maxlength="255" rows="4"><c:out value="${event.reason}"/></textarea>
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