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
    <h2>lista uzytkownikow</h2>
    <!-- SLIDE 1 -->
    <div class="help--slides active" data-id="1">
        <p>tutaj mozesz dodawac, usuwac lub edytowac dane uzytkownikow</p>
        <ul class="help--slides-items">
            <c:forEach items="${users}" var="user" >
                <li>
                <div class="col">
                    <div class="title">${user.username}</div>
                    <div class="title">${user.email}</div>
                </div>
                <sec:authorize access="hasRole('ADMIN')">
                    <div class="col">
                        <div class="subtitle">
                            <a href="/admin/edit/user/${user.id}" class="btn btn-success rounded-0 text-light m-1">edytuj</a>
                            <a href="/admin/deleteConfirm/user/${user.id}" class="btn btn-success rounded-0 text-light m-1">usun</a>
                            <c:if test="${user.enabled == 1}">
                                <a href="/admin/blockUser/${user.id}" class="btn btn-success rounded-0 text-light m-1">zablokuj</a>
                            </c:if>
                            <c:if test="${user.enabled == 0}">
                                <a href="/admin/unblockUser/${user.id}" class="btn btn-success rounded-0 text-light m-1">odblokuj</a>
                            </c:if>
                        </div>
                    </div>
                    </li>
                </sec:authorize>
                <sec:authorize access="hasRole('SUPER-ADMIN')">
                    <div class="col">
                        <div class="subtitle">
                            <a href="/admin/edit/user/${user.id}" class="btn btn-success rounded-0 text-light m-1">edytuj</a>
                            <a href="/admin/deleteConfirm/user/${user.id}" class="btn btn-success rounded-0 text-light m-1">usun</a>
                            <c:if test="${user.enabled == 1}">
                                <a href="/admin/blockUser/${user.id}" class="btn btn-success rounded-0 text-light m-1">zablokuj</a>
                            </c:if>
                            <c:if test="${user.enabled == 0}">
                                <a href="/admin/unblockUser/${user.id}" class="btn btn-success rounded-0 text-light m-1">odblokuj</a>
                            </c:if>
                        </div>
                    </div>
                    </li>
                </sec:authorize>
            </c:forEach>
        </ul>
    </div>
</section>
<jsp:include page="parts/footer.jsp"/>
