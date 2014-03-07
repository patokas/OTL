<%-- 
    Document   : userCardAdd
    Created on : 03-01-2014, 01:36:06 PM
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
        <title>OTL - Cliente</title>

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
                        <h1>Mantenedor <small> Clientes</small></h1>
                        <ol class="breadcrumb">
                            <li><a href="ClientMainServlet"><i class="fa fa-table"></i> DataTable</a></li>
                            <li class="active"><i class="fa fa-edit"></i> Agregar</li>
                        </ol>
                        <c:if test="${msg != null }" >
                            <div class="alert alert-dismissable alert-info">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msg}" /></strong></br>
                            </div>
                        </c:if>
                        <c:if test="${msgErrorRutWarning != null }" >
                            <div class="alert alert-dismissable alert-warning">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorRutWarning}" /></strong></br>
                            </div>
                        </c:if>
                        <c:if test="${msgErrorRut != null }" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorRut}" /></strong></br>
                            </div>
                        </c:if>
                        <c:if test="${msgErrorLastName != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorLastName}" /></strong></br>
                            </div>
                        </c:if>
                        <c:if test="${msgErrorFirstName != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorFirstName}" /></strong></br>
                            </div>
                        </c:if>
                        <c:if test="${msgAdd != null}" >
                            <div class="alert alert-dismissable alert-success">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgAdd}" /></strong>
                            </div>
                        </c:if>                  
                        <c:if test="${msgErrorEmail != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorEmail}" /></strong></br>
                            </div>
                        </c:if>
                        <c:if test="${msgErrorTelephone != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorTelephone}" /></strong></br>
                            </div>
                        </c:if>
                        <c:if test="${msgErrorDup != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorDup}" /></strong></br>
                            </div>
                        </c:if>

                    </div>
                    <div class="col-lg-4">
                        <form role="form" action="UserCardAddServlet" method="POST" name="formAdd">                        
                            <c:choose>
                                <c:when test="${msgErrorRut == null}">
                                    <div class="form-group">
                                        <label>Rut </label>
                                        <input class="form-control" required="true" maxlength="12" name="rut" value="<c:out value="${rut}" />">
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="form-group has-error">
                                        <label class="control-label" for="inputError">rut </label>
                                        <input class="form-control" required="true" maxlength="12" name="rut" id="inputError" value="<c:out value="${rut}" />">
                                    </div>
                                </c:otherwise>
                            </c:choose>

                            <c:choose>
                                <c:when test="${msgErrorFirstName == null}">
                                    <div class="form-group">
                                        <label>Nombre </label>
                                        <input class="form-control" required="true" maxlength="30" name="firstName" value="<c:out value="${reg.firstName}" />">
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="form-group has-error">
                                        <label class="control-label" for="inputError">Nombre </label>
                                        <input class="form-control" required="true" maxlength="30" name="firstName" id="inputError" value="<c:out value="${reg.firstName}" />">
                                    </div>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${msgErrorLastName == null}">
                                    <div class="form-group">
                                        <label>Apellido </label>
                                        <input class="form-control" required="true" maxlength="30" name="lastName" value="<c:out value="${reg.lastName}" />">
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="form-group has-error">
                                        <label class="control-label" for="inputError">Apellido </label>
                                        <input class="form-control" required="true" maxlength="30" name="lastName" id="inputError" value="<c:out value="${reg.lastName}" />">
                                    </div>
                                </c:otherwise>
                            </c:choose>
                            <div class="form-group">
                                <label>Genero </label>
                                <select class="form-control" required="true" name="gender">
                                    <option value="0"  <c:if test="${reg.gender == 0}" > selected </c:if>> Masculino</option>
                                    <option value="1" <c:if test="${reg.gender == 1}" > selected </c:if>> Femenino</option>
                                    </select>
                                </div>
                            <c:choose>
                                <c:when test="${msgErrorEmail == null}">
                                    <div class="form-group input-group">
                                        <span class="input-group-addon">@</span>
                                        <input type="email" required="true" name="email" maxlength="254" class="form-control" placeholder="Email" value="<c:out value="${reg.email}" />" >
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="form-group input-group has-error">
                                        <span class="input-group-addon" for="inputError">@</span>
                                        <input type="email" required="true" name="email" maxlength="254" id="inputError" maxlength="50" class="form-control" placeholder="Email" value="<c:out value="${reg.email}" />" >
                                    </div>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${msgErrorTelephone == null}">
                                    <div class="form-group">
                                        <label>Telefono </label>
                                        <input class="form-control" required="true" maxlength="10" name="telephone" value="<c:out value="${reg.telephone}" />">
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="form-group has-error">
                                        <label class="control-label" for="inputError">Telephone </label>
                                        <input class="form-control" required="true" maxlength="10" name="telephone" id="inputError" value="<c:out value="${reg.telephone}" />">
                                    </div>
                                </c:otherwise>
                            </c:choose>
                            <div class="form-group">
                                <label>Cuidad *</label>
                                <select class="form-control" required="true" name="idCity">
                                    <c:forEach var="listCity" items="${listCity}">
                                        <option value="<c:out value="${listCity.idCity}" />" > <c:out value="${listCity.nameCity}" /> </option>
                                    </c:forEach>
                                </select>
                            </div>                                                       
                            <button type="submit" name="add" class="btn btn-default">Agregar</button>
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
