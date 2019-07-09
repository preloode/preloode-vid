<%@ include file="head.jsp" %>
<%@ include file="header.jsp" %>
<div id="content">
    <div id="response" ng-if="response.view" ng-class="response.class"><p>{{response.message}}</p></div>
    <div class="content">
        <div class="wrapper">
            <div class="grid-view">
                <c:forEach items="${pagination}" var="pagination" varStatus="status">
                    <div class="grid">
                        <a href="${url.base}/star/${pagination.path}/">
                            <div class="preview">
                                <img src="${url.panel}${path.image}/blog/star/thumbnail/${pagination.thumbnail}"
                                     alt="${setting.name} Star ${pagination.name} Thumbnail"/>
                            </div>
                        </a>
                        <a href="${url.base}/star/${pagination.path}/">
                            <div class="description">
                                <h2>${pagination.name}</h2>
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
