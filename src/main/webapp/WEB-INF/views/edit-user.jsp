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
    <h2>edytuj dane</h2>
    <c:url var="edit_url" value="/user/update"/>
    <form action="${edit_url}">
        <div class="form-group">
            <input type="text" name="username" placeholder="username" value="${login}"/>
        </div>
        <div class="form-group">
            <input type="email" name="email" placeholder="email" value="${email}"/>
        </div>
        <div class="form-group">
            <input type="password" name="oldPassword" placeholder="old password"/>
        </div>
        <div class="form-group">
            <input type="password" name="newPassword" placeholder="new password"/>
        </div>
        <div class="form-group">
            <input type="password" name="repeatPassword" placeholder="repeat password"/>
        </div>
        <div class="form-group form-group--buttons">
            <button class="btn" type="submit">edytuj dane</button>
        </div>
    </form>
</section>
<jsp:include page="parts/footer.jsp"/>
