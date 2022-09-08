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
    <h2>Twoje zbiorki</h2>

    <!-- SLIDE 1 -->
    <div class="help--slides active" data-id="1">
        <p>dziekujemy ze pomagasz</p>

        <ul class="help--slides-items">

            <c:forEach items="${donations}" var="donation" >
                    <li>
                        <ul>
                            <div class="col">
                                <div class="title">Dla fundacji: ${donation.institution.name}</div>
                                <c:if test="${donation.status == 'nie odebrano'}">
                                <div class="title"> odebranie paczki w dniu: ${donation.pickUpDate} godzina: ${donation.pickUpTime}</div>
                                </c:if>
                                <c:if test="${donation.status == 'odebrano'}">
                                    <div class="title"> paczka odebrana w dniu: ${donation.pickUpSuccessDate}</div>
                                </c:if>
                                <c:if test="${donation.status == 'nie odebrano'}">
                                <div class="title"><a href="/user/donation-status-success/${donation.id}" class="btn btn-danger rounded-0 text-light m-1">status: ${donation.status}</a></div>
                                </c:if>
                                <c:if test="${donation.status == 'odebrano'}">
                                    <div class="title"><a href="/user/donation-status-in-progress/${donation.id}" class="btn btn-success rounded-0 text-light m-1">status: ${donation.status}</a></div>
                                </c:if>
                            </div>
                        </ul>
                    </li>
            </c:forEach>
        </ul>
    </div>
</section>


<jsp:include page="parts/footer.jsp"/>
