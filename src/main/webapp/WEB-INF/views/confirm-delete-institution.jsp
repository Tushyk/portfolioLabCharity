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
    <h2>podaj haslo by usunac instytucje</h2>
    <form method="get" action="/admin/delete/institution/${institutionId}">
        <div class="form-group">
            <label>
                <input type="password" name="password" placeholder="password">
            </label>
        </div>
        <div class="form-group form-group--buttons">
            <button class="btn" type="submit">usun</button>
        </div>
    </form>
</section>
<jsp:include page="parts/footer.jsp"/>
