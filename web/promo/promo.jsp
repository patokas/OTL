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

        <title>OTL - Promociones</title>

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
                        <h1>Mantenedor <small> Promociones</small></h1>
                        <ol class="breadcrumb">
                            <li class="active"><a href="PromoGiftMainServlet"><i class="fa fa-table"></i> DataTable</a></li>
                        </ol>
                        <c:if test="${msg!= null}" >
                            <div class="alert alert-info alert-dismissable">
                                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                                <td><strong><c:out value="${msg}" /></strong></td>
                            </div>
                        </c:if>
                        <c:if test="${msgDel != null}" >
                            <div class="alert alert-dismissable alert-warning">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgDel}" /></strong></br>
                            </div>
                        </c:if>                      
                        <c:if test="${msgErrorDel!= null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorDel}" /></strong></br>
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
                            <form action="PromoGiftMainServlet" method="POST" name="form">
                                <div class="row-fluid">
                                    <div class="span12">                            
                                        <div class="box">                                
                                            <div class="box-title">
                                                Datatable
                                                <object align="right"> <button class="btn btn-primary btn-mini" name="btnAdd" type="button" onclick="location.href = 'PromoAddServlet';" ><font size="1"><strong>AGREGAR</strong></font></button></object>
                                                </br>DB
                                            </div>
                                            <div class="box-content nopadding">
                                                <table id="datatable" class="table table-striped table-bordered">
                                                    <thead>
                                                        <tr>
                                                            <th><input class="check_all" type="checkbox" /></th>
                                                            <th>Lugar <i class="fa fa-sort"></i></th>
                                                            <th>ID <i class="fa fa-sort"></i></th>
                                                            <th>Título Promoción <i class="fa fa-sort"></i></th>
                                                            <th>Puntos <i class="fa fa-sort"></i></th>
                                                            <th>Solicitud <i class="fa fa-sort"></i></th>
                                                            <th>Fecha Inicio <i class="fa fa-sort"></i></th>
                                                            <th>Fecha Término <i class="fa fa-sort"></i></th>
                                                            <th></th>
                                                            <th></th>
                                                            <th></th>
                                                            <th></th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:forEach var="list" items="${list}">  
                                                            <tr>
                                                                <td class="center"><input type="checkbox" name="chk" value="<c:out value="${list.idPromo}" />"/></td>
                                                                <td class="center"><c:out value="${list.namePlace}" /></td>
                                                                <td class="center"><c:out value="${list.idPromo}" /></td>
                                                                <td class="center"><c:out value="${list.tittle}" /></td>
                                                                <td class="center"><c:out value="${list.points}" /></td>
                                                                <td class="center">
                                                                    <c:if test="${list.request == 0}">Pendiente</c:if>
                                                                    <c:if test="${list.request == 1}">Aceptada</c:if>
                                                                    <c:if test="${list.request == 2}">Rechazada</c:if>
                                                                    </td>
                                                                    <td class="center"><c:out value="${list.dateBegin}" /></td>
                                                                <td class="center"><c:out value="${list.dateEnd}" /></td>
                                                                <td class="center">
                                                                    <a href="PromoGetServlet?idPlace=<c:out value="${list.idPlace}" />&idPromo=<c:out value="${list.idPromo}" />"><button class="btn btn-primary btn-mini" name="btnUpOne" type="button"><strong><font size="1">ACEPTAR</font></strong></button></a>
                                                                </td>
                                                                <td class="center">
                                                                    <form action="PromoGiftMainServlet" method="post">
                                                                        <button class="btn btn-danger btn-mini delete" name="btnDelRow" type="submit"><strong><font size="1">RECHAZAR</font></strong></button></a>                                                                        
                                                                        <input type="hidden" name="idPromo" value="<c:out value="${list.idPromo}" />" />    
                                                                    </form>
                                                                </td>
                                                                <td class="center">
                                                                    <a href="PromoGetServlet?idPlace=<c:out value="${list.idPlace}" />&idPromo=<c:out value="${list.idPromo}" />"><button class="btn btn-primary btn-mini" name="btnUpOne" type="button"><strong><font size="1">ACTUALIZAR</font></strong></button></a>
                                                                </td>
                                                                <td class="center">
                                                                    <form action="PromoGiftMainServlet" method="post">
                                                                        <button class="btn btn-danger btn-mini delete" name="btnDelRow" type="submit"><strong><font size="1">ELIMINAR</font></strong></button></a>                                                                        
                                                                        <input type="hidden" name="idPromo" value="<c:out value="${list.idPromo}" />" />    
                                                                    </form>
                                                                </td>
                                                            </tr>
                                                        </c:forEach>
                                                    </tbody>
                                                    <tfoot>
                                                        <tr>
                                                            <th><button class="btn btn-danger btn-mini delete" name="btnDelCol" type="submit"><font size="1">ELIMINAR</font></button> </th>
                                                            <th>Plaza </th>
                                                            <th>ID</th>
                                                            <th>Título Promoción </th>
                                                            <th>Puntos </th>
                                                            <th>Solicitud </th>
                                                            <th>Fecha Inicio </th>
                                                            <th>Fecha Término </th>
                                                            <th></th>
                                                            <th></th>
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