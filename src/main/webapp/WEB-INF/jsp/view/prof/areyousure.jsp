<%@ page import="com.gsnotes.web.models.UserAndAccountInfos" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/security/tags"%>


<jsp:include page="../fragments/userheader.jsp" />
<div class="container">

    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">



            <jsp:include page="../fragments/usermenu.jsp" />

        </div>
    </nav>
    <h4>You already have imported the grades. are you sure you want to replace them with the new data?</h4>
    <a href="/prof/import?sure=1" class="btn link-danger">Yes</a>
    <a href="/prof/import" class="btn link-success"￥>No</a>

<jsp:include page="../fragments/userfooter.jsp" />