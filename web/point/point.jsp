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
                        <h1>Mantenedor <small> Puntos</small></h1>
                        <ol class="breadcrumb">
                            <li class="active"><a href="PointMainServlet"><i class="fa fa-table"></i> DataTable</a></li>
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
                        <c:if test="${msgDel != null}" >
                            <div class="alert alert-dismissable alert-warning">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgDel}" /></strong></br>
                            </div>
                        </c:if>
                        <c:if test="${msgErrorFound != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorFound}" /></strong></br>
                            </div>
                        </c:if>
                        <c:if test="${msgErrorReference != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorReference}" /></strong></br>
                            </div>
                        </c:if>
                    </div>

                </div><!-- /.row -->
                <div class="row">                  
                    <div class="col-lg-12"> 

                        <div class="table-responsive">

                            <form action="PointMainServlet" method="POST" name="form">
                                <div class="row-fluid">
                                    <div class="span12">                            
                                        <div class="box">                                
                                            <div class="box-title">
                                                Datatable
                                                <object align="right"> <button class="btn btn-primary btn-mini" name="btnAddEvent" type="button" onclick="location.href = 'PointAddServlet';" ><font size="1"><strong>AGREGAR</strong></font></button></object>
                                                </br>DB
                                            </div>
                                            <div class="box-content nopadding">
                                                <table id="datatable" class="table table-striped table-bordered">
                                                    <thead>
                                                        <tr>
                                                            <th><input class="check_all" type="checkbox" /></th>
                                                            <th>Plaza <i class="fa fa-sort"></i></th>
                                                            <th>Ciudad <i class="fa fa-sort"></i></th>
                                                            <th>Rut <i class="fa fa-sort"></i></th>
                                                            <th>Nombres <i class="fa fa-sort"></i></th>
                                                            <th>Apellidos <i class="fa fa-sort"></i></th>
                                                            <th>Puntos <i class="fa fa-sort"></i></th>
                                                            <th>Total Pts <i class="fa fa-sort"></i></th>
                                                            <th></th>
                                                            <th></th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <tr>
                                                            <c:forEach var="list" items="${list}">  
                                                                <td class="center"><input type="checkbox" name="chk" value="<c:out value="${list.idPlace}" />-<c:out value="${list.rut}" />"/></td>
                                                                <td class="center"><c:out value="${list.namePlace}" /></td>
                                                                <td class="center"><c:out value="${list.nameCity}" /></td>
                                                                <td class="center"><c:out value="${list.rut}" />-<c:out value="${list.dv}" /></td>
                                                                <td class="center"><c:out value="${list.firstName}" /></td>
                                                                <td class="center"><c:out value="${list.lastName}" /></td>
                                                                <td class="center"><c:out value="${list.points}" /></td>
                                                                <td class="center"><c:out value="${list.totalPoint}" /></td>
                                                                <td class="center">
                                                                    <a href="PointGetServlet?idPlace=<c:out value="${list.idPlace}" />&rut=<c:out value="${list.rut}" />"><button class="btn btn-primary btn-mini" name="btnUpOne" type="button"><strong><font size="1">ACTUALIZAR</font></strong></button></a>
                                                                </td>
                                                                <td class="center">
                                                                    <form method="post" action="PointMainServlet">
                                                                        <button class="btn btn-danger btn-mini delete" name="btnDelRow" type="submit"><strong><font size="1"> ELIMINAR </font></strong></button>
                                                                        <input type="hidden" name="idPlace" value="<c:out value="${list.idPlace}" />"/>
                                                                        <input type="hidden" name="rut" value="<c:out value="${list.rut}" />"/>
                                                                    </form>
                                                                </td>
                                                            </tr>
                                                        </c:forEach>                                                                              		                                    		
                                                    </tbody>
                                                    <tfoot>
                                                        <tr>
                                                            <th><button class="btn btn-danger btn-mini delete" name="btnDelCol" type="submit"><font size="1">ELIMINAR</font></button></th>
                                                            <th>Plaza </th>
                                                            <th>Ciudad </th>
                                                            <th>Rut </th>
                                                            <th>Nombres </th>
                                                            <th>Apellidos </th>
                                                            <th>Puntos </th>
                                                            <th>Total Pts </th>
                                                            <th></th>
                                                            <th></th>
                                                        </tr>
                                                    </tfoot>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </form>

                        </div>
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