<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:include page="parts/header.jsp"/>
<div class="slogan container container--90">
    <div class="slogan--item">
        <h1>
            Zacznij pomagać!<br/>
            Oddaj niechciane rzeczy w zaufane ręce
        </h1>
    </div>
</div>

</header>
<section class="login-page">
    <h2>Załóż konto</h2>
    <form:form modelAttribute="user" method="post">
        <form:hidden path="id"/>
        <div class="form-group">
<%--            <input type="email" name="email" placeholder="Email" />--%>
            <form:input path="username" placeholder="username"/>
            <form:errors path="username"/>
        </div>
        <div class="form-group">
            <form:input path="email" placeholder="email"/>
            <form:errors path="email"/>
        </div>
        <div class="form-group">
            <form:input type="password" path="password" placeholder="password"/>
            <form:errors path="password"/>
        </div>
        <div class="form-group">
            <form:input type="password" path="matchingPassword" placeholder="repeat Password"/>
            <form:errors path="matchingPassword"/>
        </div>
        <div class="form-group form-group--buttons">
            <a href="/login" class="btn btn--without-border">Zaloguj się</a>
            <button class="btn" type="submit">Załóż konto</button>
        </div>
        <form:errors path="*"/>
    </form:form>
</section>
<jsp:include page="parts/footer.jsp"/>