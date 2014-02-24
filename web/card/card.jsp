<%-- 
    Document   : card
    Created on : 09-01-2014, 03:22:05 AM
    Author     : alexander
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>OTL - Tarjetas</title>

        <!-- imperio css -->
        <link rel="stylesheet" type="text/css" href="css/bootstrap.css" />
        <link rel="stylesheet" type="text/css" href="css/bootstrap-responsive.css" />
        <link rel="stylesheet" type="text/css" href="css/imperio.css" />

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
        <c:import var="menu" url="/mainMenu.jsp" />
        <c:out value="${menu}" escapeXml="false" />
        <!-- /.navbar-collapse -->
        <div id="page-wrapper">

            <div class="row">
                <div class="col-lg-12">
                    <h1>Mantenedor <small> Tarjetas</small></h1>
                    <ol class="breadcrumb">
                        <li class="active"><a href="CardMainServlet"><i class="fa fa-table"></i> DataTable</a></li>
                    </ol>
                    <c:if test="${msg != null}" >
                        <div class="alert alert-info alert-dismissable">
                            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                            <td><strong><c:out value="${msg}" /></strong></td>
                        </div>    
                    </c:if>
                    <c:if test="${msgDel != null}" >
                        <div class="alert alert-dismissable alert-warning">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <td><strong><c:out value="${msgDel}" /></strong></br></td>
                        </div>
                    </c:if>
                    <c:if test="${msgError1 != null}" >
                        <div class="alert alert-dismissable alert-danger">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong><c:out value="${msgError1}" /></strong></br>
                        </div>
                    </c:if>
                </div>                
            </div><!-- /.row -->
            <div class="row">                  
                <div class="col-lg-12">                        
                    <div class="table-responsive">

                        <form action="CardMainServlet" method="POST" name="form">
                            <div class="row-fluid">
                                <div class="span12">                            
                                    <div class="box">                                
                                        <div class="box-title">
                                            Datatable
                                            <object align="right"> <button class="btn btn-primary btn-mini" name="btnAdd" type="button" onclick="location.href = 'CardVerifyServlet';" ><font size="1"><strong>AGREGAR</strong></font></button></object>
                                            </br>DB
                                        </div>
                                        <div class="box-content nopadding">
                                            <table id="datatable" class="table table-striped table-bordered">
                                                <thead>
                                                    <tr>
                                                        <th><input class="check_all" type="checkbox" /></th>
                                                        <th>Codigo de Barra <i class="fa fa-sort"></i></th>
                                                        <th>Rut <i class="fa fa-sort"></i></th>
                                                        <th>Tipo de Tarjeta <i class="fa fa-sort"></i></th>
                                                        <th>Fecha Inicio <i class="fa fa-sort"></i></th>
                                                        <th>Fecha Termino <i class="fa fa-sort"></i></th>
                                                        <th></th>
                                                        <th></th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach var="list" items="${list}"> 
                                                        <tr>
                                                            <td class="center"><input type="checkbox" name="chk" value="<c:out value="${list.barCode}" />"/></td> 
                                                            <td class="center"><c:out value="${list.barCode}" /></td>
                                                            <td class="center"><c:out value="${list.rut}" />-<c:out value="${list.dv}"/></td>
                                                            <td class="center">
                                                                <c:if test="${list.cardType == 1}">Basic</c:if>
                                                                <c:if test="${list.cardType == 2}">Silver</c:if>
                                                                <c:if test="${list.cardType == 3}">Gold</c:if>
                                                                </td>
                                                                <td><c:out value="${list.dateBeginCard}" /></td>
                                                            <td class="center"><c:out value="${list.dateEndCard}" /></td>

                                                            <td class="center">      
                                                                <a href="CardGetServlet?rut=<c:out value="${list.rut}" />&barCode=<c:out value="${list.barCode}" />"><button class="btn btn-primary btn-mini" name="btnUpOne" type="button"><font size="1">ACTUALIZAR</font></button></a>
                                                            </td>
                                                            <td class="center">
                                                                <form action="CardMainServlet" method="post">
                                                                    <input type="hidden" name="rut" value="<c:out value="${list.rut}" />"/>
                                                                    <input type="hidden" name="barCode" value="<c:out value="${list.barCode}" />"/>
                                                                    <button class="btn btn-danger btn-mini delete" name="btnDelRow" type="submit"><font size="1">ELIMINAR</font></button>
                                                                </form>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>                                                                            		                                    		
                                                </tbody>
                                                <tfoot>
                                                    <tr>
                                                        <th> <button class="btn btn-danger btn-mini delete" name="btnDelCol" type="submit"><font size="1">ELIMINAR</font></button></th>
                                                        <th>Codigo de Barra</th>
                                                        <th>Rut</th>
                                                        <th>Tipo de Tarjeta</th>
                                                        <th>Fecha Inicio</th>
                                                        <th>Fecha Termino</th>
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
