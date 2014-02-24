<%-- 
    Document   : admin
    Created on : 26-dic-2013, 16:08:09
    Author     : patricio alberto
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>OTL - Admin</title>

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
                chk = document.getElementById("chk");
                pwd = document.getElementById("pwd");


                if (document.formUpdate.chk.checked) {
                    pwd.style.display = 'block';
                } else {
                    pwd.style.display = 'none';
                }
            }
        </script>
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
                        <h1>Mantenedor <small> Admin</small></h1>
                        <ol class="breadcrumb">
                            <li><a href="AdminMainServlet"><i class="fa fa-table"></i> DataTable</a></li>
                            <li class="active"><i class="fa fa-edit"></i> Actualizar</li>
                        </ol>
                        <c:if test="${msgOk != null}" >
                            <div class="alert alert-dismissable alert-success">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgOk}" /></strong>
                            </div>
                        </c:if>
                        <c:if test="${msgErrorId != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorId}" /></strong></br>
                            </div>
                        </c:if> 
                        <c:if test="${msgErrorUsername != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorUsername}" /></strong></br>
                            </div>
                        </c:if>
                        <c:if test="${msgErrorEmail != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorEmail}" /></strong></br>
                            </div>
                        </c:if> 
                        <c:if test="${msgErrorPwd1 != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorPwd1}" /></strong></br>
                            </div>
                        </c:if> 
                        <c:if test="${msgErrorPwd2 != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorPwd2}" /></strong></br>
                            </div>
                        </c:if>
                        <c:if test="${msgErrorFound != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorFound}" /></strong></br>
                            </div>
                        </c:if>

                    </div>
                    <div class="col-lg-4">
                        <form role="form" action="AdminUpdateServlet" method="POST" name="formUpdate">
                            <div class="form-group">
                                <label for="disabledSelect">ID Admin</label>
                                <input class="form-control" id="disabledInput" type="text" placeholder="<c:out value="${admin.idAdmin}" />" disabled>
                                <input type="hidden" name="id" value="<c:out value="${admin.idAdmin}" />"/>
                            </div>
                            <c:choose>
                                <c:when test="${msgErrorUsername == null}">
                                    <div class="form-group">
                                        <label>Username </label>
                                        <input class="form-control" required="true" maxlength="30" name="username" value="<c:out value="${admin.username}" />">
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="form-group has-error">
                                        <label class="control-label" for="inputError">Username </label>
                                        <input class="form-control" required="true" maxlength="30" name="username" id="inputError" value="<c:out value="${admin.username}" />">
                                    </div>
                                </c:otherwise>
                            </c:choose>

                            <c:choose>
                                <c:when test="${msgErrorEmail == null}">
                                    <div class="form-group input-group">
                                        <span class="input-group-addon">@</span>
                                        <input type="email" required="true" name="email" maxlength="50" class="form-control" placeholder="Email" value="<c:out value="${admin.email}" />" >
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="form-group input-group has-error">
                                        <span class="input-group-addon" for="inputError">@</span>
                                        <input type="email" required="true" name="email"  id="inputError" maxlength="50" class="form-control" placeholder="Email" value="<c:out value="${admin.email}" />" >
                                    </div>
                                </c:otherwise>
                            </c:choose>
                            <div class="form-group">
                                <label class="checkbox-inline">
                                    <input type="checkbox" name="chk" id="chk" onClick="changeDisplay();"/> Restablecer password
                                </label>
                            </div>     
                            <div id="pwd" style="display:none">
                                <c:if test="${msgErrorPwd1 == null && msgErrorPwd2 == null }" >
                                    <div class="form-group">
                                        <label>Password (min 6 caracteres) </label>
                                        <input class="form-control" type="password" maxlength="20" name="pwd1" value="<c:out value="${admin.pwd1}" />">
                                    </div>
                                    <div class="form-group">
                                        <label>Repetir Password </label>
                                        <input class="form-control" type="password" maxlength="20" name="pwd2" value="<c:out value="${admin.pwd2}" />">
                                    </div>
                                </c:if>
                                <c:if test="${msgErrorPwd1 != null && msgErrorPwd2 == null}" >
                                    <div class="form-group has-error">
                                        <label  class="control-label" for="inputError">Password (min 6 caracteres) </label>
                                        <input class="form-control" type="password" maxlength="20" name="pwd1" id="inputError" value="<c:out value="${admin.pwd1}" />">
                                    </div>
                                    <div class="form-group has-error">
                                        <label  class="control-label" for="inputError">Repetir Password </label>
                                        <input class="form-control" type="password" maxlength="20" name="pwd2" id="inputError" value="<c:out value="${admin.pwd2}" />">
                                    </div>
                                </c:if>  
                                <c:if test="${msgErrorPwd1 == null && msgErrorPwd2 != null}" >
                                    <div class="form-group has-error">
                                        <label  class="control-label" for="inputError">Password (min 6 caracteres) </label>
                                        <input class="form-control" type="password" maxlength="20" name="pwd1" id="inputError" value="<c:out value="${admin.pwd1}" />">
                                    </div>
                                    <div class="form-group has-error">
                                        <label  class="control-label" for="inputError">Repetir Password </label>
                                        <input class="form-control" type="password" maxlength="20" name="pwd2" id="inputError" value="<c:out value="${admin.pwd2}" />">
                                    </div>
                                </c:if> 
                                <c:if test="${msgErrorPwd1 != null && msgErrorPwd2 != null}" >
                                    <div class="form-group has-error">
                                        <label  class="control-label" for="inputError">Password (min 6 caracteres) </label>
                                        <input class="form-control" type="password" maxlength="20" name="pwd1" id="inputError" value="<c:out value="${admin.pwd1}" />">
                                    </div>
                                    <div class="form-group has-error">
                                        <label  class="control-label" for="inputError">Repetir Password </label>
                                        <input class="form-control" type="password" maxlength="20" name="pwd2" id="inputError" value="<c:out value="${admin.pwd2}" />">
                                    </div>
                                </c:if>
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