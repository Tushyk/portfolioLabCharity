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
    <h2>edytuj dane organizacji</h2>
    <c:url var="edit_url" value="/admin/update/institution"/>
    <form:form modelAttribute="institution" method="post" action="${edit_url}">
        <form:hidden path="id"/>
        <div class="form-group">
            <form:input path="name" placeholder="nazwa organizacji"/>
            <form:errors path="name"/>
        </div>
        <div class="form-group">
            <form:textarea  path="description" placeholder="opis organizacji"/>
            <form:errors path="description"/>
        </div>
        <div class="form-group form-group--buttons">
            <button class="btn" type="submit">edytuj</button>
        </div>
    </form:form>
</section>
<jsp:include page="parts/footer.jsp"/>
