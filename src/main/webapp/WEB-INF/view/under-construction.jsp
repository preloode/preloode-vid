<%@ include file="head.jsp" %>
<div id="wrapper" ng-controller="underConstruction">
    <div class="wrapper">
        <img class="picture responsive" src="${url.image}/under-construction.png"
             alt="${setting.name} Under Construction Icon"/>
        <p class="description">This site will be back online within</p>
        <p class="countdown xxxl-font" ng-init="initializeCountdown()">{{timer.current}}</p>
    </div>
</div>
<%@ include file="footer.jsp" %>
