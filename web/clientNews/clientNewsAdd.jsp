<%-- 
    Document   : clientNewsAdd
    Created on : 05-03-2014, 06:29:15 PM
    Author     : alexander
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>OTL -Nueva Noticias Cliente</title>

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
    <body>
        <div id="wrapper">

            <!-- Collect the nav links, forms, and other content for toggling -->
            <c:import var="menu" url="/mainMenu.jsp" />
            <c:out value="${menu}" escapeXml="false" />
            <!-- /.navbar-collapse -->

            <div id="page-wrapper">

                <div class="row">
                    <div class="col-lg-12">
                        <h1>Mantenedor <small> Noticias Cliente</small></h1>
                        <ol class="breadcrumb">
                            <li><a href="ClientNewsMainServlet"><i class="fa fa-table"></i> DataTable</a></li>
                            <li class="active"><i class="fa fa-edit"></i> Agregar</li>
                        </ol>
                        <c:if test="${msg != null }" >
                            <div class="alert alert-dismissable alert-info">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msg}" /></strong></br>
                            </div>
                        </c:if>
                        <c:if test="${msgErrorTittle != null }" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorTittle}" /></strong></br>
                            </div>
                        </c:if>
                        <c:if test="${msgErrorDate != null }" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorDate}" /></strong></br>
                            </div>
                        </c:if>
                        <c:if test="${msgAdd != null}" >
                            <div class="alert alert-dismissable alert-success">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgAdd}" /></strong>
                            </div>
                        </c:if>     
                        <c:if test="${msgOk != null}" >
                            <div class="alert alert-dismissable alert-success">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgOk}" /></strong>
                            </div>
                        </c:if>   
                        <c:if test="${msgErrorTypeNews != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorTypeNews}" /></strong></br>
                            </div>
                        </c:if>
                        <c:if test="${msgErrorRut != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorRut}" /></strong></br>
                            </div>
                        </c:if>
                        <c:if test="${msgErrorDup != null }" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorDup}" /></strong></br>
                            </div>
                        </c:if>
                    </div>
                    <div class="col-lg-4">
                        <form role="form" action="ClientNewsAddServlet" method="POST" name="formAdd">                        
                            <c:if test="${msgErrorDup == null && msgErrorTittle == null }" >
                                <div class="form-group">
                                    <label>Título de Noticia </label>
                                    <input class="form-control" required="true" maxlength="100" name="tittle" value="<c:out value="${cnews.tittle}" />">
                                </div>
                            </c:if>
                            <c:if test="${msgErrorDup != null || msgErrorTittle != null}" >
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">Título de Noticia</label>
                                    <input type="text" class="form-control" required="true" name="tittle" id="inputError" value="<c:out value="${cnews.tittle}" />">
                                </div>
                            </c:if>
                            <div class="form-group">
                                <label>Tipo de Noticias</label>
                                <select class="form-control" required="true" name="typeNews">
                                    <option value="1"  <c:if test="${news.typeNews == 1}" > selected </c:if>> Advertencia</option>
                                    <option value="2" <c:if test="${news.typeNews == 2}" > selected </c:if>> Notificación</option>
                                    <option value="3" <c:if test="${news.typeNews == 3}" > selected </c:if>> Información</option>
                                    <option value="4" <c:if test="${news.typeNews == 4}" > selected </c:if>> Atención</option>
                                    </select>
                                </div> 
                            <c:choose>
                                <c:when test="${msgErrorRut == null}">
                                    <div class="form-group">
                                        <label>Rut (ex: 12345678-9)</label>
                                        <input class="form-control" required="true" maxlength="12" name="rut" value="<c:out value="${cnews.rut}" />-<c:out value="${cnews.dv}" />">
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="form-group has-error">
                                        <label class="control-label" for="inputError">Rut (ex: 12345678-9)</label>
                                        <input class="form-control" required="true" maxlength="12" name="rut" id="inputError" value="<c:out value="${cnews.rut}" />-<c:out value="${cnews.dv}" />">
                                    </div>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${msgErrorDup == null && msgErrorDate == null }">
                                    <div class="form-group">
                                        <label>Fecha de Inicio</label>
                                        <input class="form-control" type="datetime-local" required="true" name="dateBegin" value="<c:out value="${cnews.dateBegin}" />">
                                    </div>
                                    <div class="form-group">
                                        <label>Fecha de Término</label>
                                        <input class="form-control" type="datetime-local" required="true" name="dateEnd" value="<c:out value="${cnews.dateEnd}" />">
                                    </div>
                                </c:when>
                                <c:when test="${msgErrorDup != null && msgErrorDate == null }">
                                    <div class="form-group has-error">
                                        <label class="control-label" for="inputError">Fecha de Inicio</label>
                                        <input class="form-control" type="datetime-local" required="true" name="dateBegin" id="inputError" value="<c:out value="${cnews.dateBegin}" />">
                                    </div>
                                    <div class="form-group has-error">
                                        <label class="control-label" for="inputError">Fecha de Término</label>
                                        <input class="form-control" type="datetime-local" required="true" name="dateEnd" id="inputError" value="<c:out value="${cnews.dateEnd}" />">
                                    </div>
                                </c:when>
                                <c:when test="${msgErrorDup == null && msgErrorDate != null }">
                                    <div class="form-group has-error">
                                        <label class="control-label" for="inputError">Fecha de Inicio</label>
                                        <input class="form-control" type="datetime-local" required="true" name="dateBegin" id="inputError" value="<c:out value="${cnews.dateBegin}" />">
                                    </div>
                                    <div class="form-group has-error">
                                        <label class="control-label" for="inputError">Fecha de Término</label>
                                        <input class="form-control" type="datetime-local" required="true" name="dateEnd" id="inputError" value="<c:out value="${cnews.dateEnd}" />">
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="form-group">
                                        <label>Fecha de Inicio</label>
                                        <input class="form-control" type="datetime-local" required="true" name="dateBegin" value="<c:out value="${cnews.dateBegin}" />">
                                    </div>
                                    <div class="form-group">
                                        <label>Fecha de Término</label>
                                        <input class="form-control" type="datetime-local" required="true" name="dateEnd" value="<c:out value="${cnews.dateEnd}" />">
                                    </div>
                                </c:otherwise>
                            </c:choose>

                            <button type="submit" name="add" class="btn btn-default">Add</button>
                            <button type="reset" class="btn btn-default">Reset</button> 
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
