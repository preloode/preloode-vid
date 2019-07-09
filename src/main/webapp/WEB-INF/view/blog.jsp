<%@ include file="head.jsp" %>
<%@ include file="header.jsp" %>
<div id="content">
    <div id="response" ng-if="response.view" ng-class="response.class"><p>{{response.message}}</p></div>
    <div class="content">
        <div class="wrapper">
            <div class="grid-view">
                <c:forEach items="${pagination}" var="pagination" varStatus="status">
                    <c:set var="rateAmount"><fmt:parseNumber type="number" value="${pagination.rate.amount}"/></c:set>
                    <c:set var="rateContributor"><fmt:parseNumber type="number" value="${pagination.rate.contributor}"/></c:set>
                    <c:set var="rateResult"><fmt:formatNumber type="number" maxFractionDigits="0"
                                                              value="${rateAmount / rateContributor}"/></c:set>
                    <div class="grid">
                        <a href="${url.base}/video/watch/${pagination.path[fn:length(pagination.path) - 1]}/">
                            <div class="preview">
                                <img src="${url.panel}${path.image}/blog/thumbnail/${pagination.thumbnail}"
                                     alt="${setting.name} Video ${pagination.title} Thumbnail"/>
                            </div>
                            <div class="play">
                                <div class="image"></div>
                            </div>
                        </a>
                        <a href="${url.base}/video/watch/${pagination.path[fn:length(pagination.path) - 1]}/">
                            <div class="description">
                                <h2>${pagination.title}</h2>
                                <p><i class="star-yellow square-15 margin-right-5"></i>${rateResult}/10<c:if
                                        test="${!empty pagination.star.name}"> / ${pagination.star.name[0]}</c:if></p>
                            </div>
                        </a>
                    </div>
                </c:forEach>
            </div>
            <div class="separator"></div>
            <div class="banner">
                <!-- JuicyAds v3.0 -->
                <script type="text/javascript" data-cfasync="false" async
                        src="https://adserver.juicyads.com/js/jads.js"></script>
                <ins id="762590" data-width="728" data-height="102"></ins>
                <script type="text/javascript" data-cfasync="false"
                        async>(adsbyjuicy = window.adsbyjuicy || []).push({'adzone': 762590});</script>
                <!--JuicyAds END-->
            </div>
        </div>
    </div>
</div>
<%@ include file="footer.jsp" %>
