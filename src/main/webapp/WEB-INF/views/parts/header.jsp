<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="X-UA-Compatible" content="ie=edge"/>
    <title>Document</title>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="<c:url value="${pageContext.request.contextPath}/resources/css/style.css"/>"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
</head>
<body>
<header class="header--main-page">
    <nav class="container container--70">
        <sec:authorize access="isAuthenticated()">
        <ul class="nav--actions">
            <li class="logged-user">
                <sec:authentication property="principal.username"/>
                <ul class="dropdown">
                    <li><a href="/user/edit">edytuj profil</a></li>
                    <li><a href="/user/donations">Moje zbiórki</a></li>
                    <li><form action="<c:url value="/logout"/>" method="post">
                        <input type="submit" value="wyloguj">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form></li>
                </ul>
            </li>
        </ul>
        </sec:authorize>
        <sec:authorize access="!isAuthenticated()">
        <ul class="nav--actions">
            <li><a href="${pageContext.request.contextPath}/login" class="btn btn--small btn--without-border">Zaloguj</a></li>
            <li><a href="${pageContext.request.contextPath}/registration" class="btn btn--small btn--highlighted">Załóż konto</a></li>
        </ul>
        </sec:authorize>
        <ul>
            <li><a href="${pageContext.request.contextPath}/" class="btn btn--without-border active">Start</a></li>
            <li><a href="#" class="btn btn--without-border">O co chodzi?</a></li>
            <li><a href="#" class="btn btn--without-border">O nas</a></li>
            <li><a href="${pageContext.request.contextPath}/institutions" class="btn btn--without-border">Fundacje i organizacje</a></li>
            <li><a href="/donation/form" class="btn btn--without-border">Przekaż dary</a></li>
            <li><a href="#" class="btn btn--without-border">Kontakt</a></li>
            <sec:authorize access="hasRole('SUPER-ADMIN')">
                <li><a href="${pageContext.request.contextPath}/super-admin/admin/list" class="btn btn--without-border">lista adminow</a></li>
            </sec:authorize>
            <sec:authorize access="hasRole('ADMIN')">
                <li><a href="/admin/user/list" class="btn btn--without-border">lista uzytkownikow</a></li>
            </sec:authorize>
            <sec:authorize access="hasRole('SUPER-ADMIN')">
                <li><a href="/admin/user/list" class="btn btn--without-border">lista uzytkownikow</a></li>
            </sec:authorize>
        </ul>
    </nav>