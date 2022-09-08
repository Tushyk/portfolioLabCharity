<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
<section class="help">
    <h2>lista instytucji z ktorymi wspolpracujemy</h2>
    <sec:authorize access="hasRole('ADMIN')">
        <a href="/admin/add/institution" class="btn btn-success rounded-0 text-light m-1">Dodaj organizacje</a>
    </sec:authorize>
    <sec:authorize access="hasRole('SUPER-ADMIN')">
        <a href="/admin/add/institution" class="btn btn-success rounded-0 text-light m-1">Dodaj organizacje</a>
    </sec:authorize>
    <!-- SLIDE 1 -->
    <div class="help--slides active" data-id="1">
        <p>W naszej bazie znajdziesz listę zweryfikowanych Fundacji, z którymi współpracujemy. Możesz sprawdzić czym się zajmują.</p>
        <ul class="help--slides-items">
            <c:forEach items="${institutions}" var="institution" >
                <li>
                    <sec:authorize access="!isAuthenticated()">
                    <ul>
                    </sec:authorize>
                        <sec:authorize access="hasRole('USER')">
                        <ul>
                        </sec:authorize>
                    <div class="col">
                        <div class="title">${institution.name}</div>
                        <div class="title">${institution.description}</div>
                    </div>
                <sec:authorize access="!isAuthenticated()">
                     </ul>
                   </li>
                </sec:authorize>
                <sec:authorize access="hasRole('USER')">
                    </ul>
                    </li>
                </sec:authorize>
                <sec:authorize access="hasRole('ADMIN')">
                    <div class="col">
                        <div class="subtitle">
                            <a href="/admin/edit/institution/${institution.id}" class="btn btn-success rounded-0 text-light m-1">edytuj</a>
                            <a href="/admin/deleteConfirm/institution/${institution.id}" class="btn btn-success rounded-0 text-light m-1">usun</a>
                        </div>
                    </div>
                    </li>
                </sec:authorize>
                <sec:authorize access="hasRole('SUPER-ADMIN')">
                    <div class="col">
                        <div class="subtitle">
                            <a href="/admin/edit/institution/${institution.id}" class="btn btn-success rounded-0 text-light m-1">edytuj</a>
                            <a href="/admin/deleteConfirm/institution/${institution.id}" class="btn btn-success rounded-0 text-light m-1">usun</a>
                        </div>
                    </div>
                    </li>
                </sec:authorize>
            </c:forEach>
        </ul>
    </div>
</section>
<jsp:include page="parts/footer.jsp"/>
