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
    <h2>lista administratorow</h2>
        <a href="/super-admin/add/admin" class="btn btn-success rounded-0 text-light m-1">Dodaj admina</a>
    <!-- SLIDE 1 -->
    <div class="help--slides active" data-id="1">
        <p>tutaj mozesz dodawac, usuwac lub edytowac dane administratorow</p>
        <ul class="help--slides-items">
            <c:forEach items="${admins}" var="admin" >
                <li>
                    <div class="col">
                        <div class="title">${admin.username}</div>
                        <div class="title">${admin.email}</div>
                    </div>
                    <div class="col">
                        <div class="subtitle">
                            <a href="/super-admin/edit/admin/${admin.id}" class="btn btn-success rounded-0 text-light m-1">edytuj</a>
                            <a href="/super-admin/deleteConfirm/admin/${admin.id}" class="btn btn-success rounded-0 text-light m-1">usun</a>
                            <c:if test="${admin.enabled == 1}">
                                <a href="/super-admin/blockAdmin/${admin.id}" class="btn btn-success rounded-0 text-light m-1">zablokuj</a>
                            </c:if>
                            <c:if test="${admin.enabled == 0}">
                                <a href="/super-admin/unblockAdmin/${admin.id}" class="btn btn-success rounded-0 text-light m-1">odblokuj</a>
                            </c:if>
                        </div>
                    </div>
                </li>
            </c:forEach>
        </ul>
    </div>
</section>
<jsp:include page="parts/footer.jsp"/>
