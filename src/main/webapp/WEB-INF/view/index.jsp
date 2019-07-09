<%@ include file="head.jsp" %>
<%@ include file="header.jsp" %>
<div id="content">
    <div id="response" ng-if="response.view" ng-class="response.class"><p>{{response.message}}</p></div>
    <div class="wrapper">
        <div class="feature-big">
            <c:forEach items="${blogFeatureBig}" var="featureBig" varStatus="status">
                <c:set var="rateAmount"><fmt:parseNumber type="number" value="${featureBig.rate.amount}"/></c:set>
                <c:set var="rateContributor"><fmt:parseNumber type="number"
                                                              value="${featureBig.rate.contributor}"/></c:set>
                <c:set var="rateResult"><fmt:formatNumber type="number" maxFractionDigits="0"
                                                          value="${rateAmount / rateContributor}"/></c:set>
                <div class="item-big">
                    <a href="${url.base}/video/watch/${featureBig.path[fn:length(featureBig.path) - 1]}/">
                        <div class="preview">
                            <img src="${url.panel}${path.image}/blog/thumbnail/${featureBig.thumbnail}"
                                 alt="${setting.name} Video ${featureBig.title} Thumbnail"/>
                        </div>
                        <div class="play">
                            <div class="image"></div>
                        </div>
                        <div class="text">
                            <h3>${featureBig.title}</h3>
                            <p><i class="star-yellow square-15 margin-right-5"></i>${rateResult}/10<c:if
                                    test="${!empty featureBig.star.name}"> / ${featureBig.star.name[0]}</c:if></p>
                        </div>
                    </a>
                </div>
            </c:forEach>
            <c:forEach items="${blogFeatureSmall}" var="featureSmall" varStatus="status">
                <c:set var="rateAmount"><fmt:parseNumber type="number" value="${featureSmall.rate.amount}"/></c:set>
                <c:set var="rateContributor"><fmt:parseNumber type="number"
                                                              value="${featureSmall.rate.contributor}"/></c:set>
                <c:set var="rateResult"><fmt:formatNumber type="number" maxFractionDigits="0"
                                                          value="${rateAmount / rateContributor}"/></c:set>
                <div class="item-small">
                    <a href="${url.base}/video/watch/${featureSmall.path[fn:length(featureSmall.path) - 1]}/">
                        <div class="preview">
                            <img src="${url.panel}${path.image}/blog/thumbnail/${featureSmall.thumbnail}"
                                 alt="${setting.name} Video ${featureSmall.title} Thumbnail"/>
                        </div>
                        <div class="play">
                            <div class="image"></div>
                        </div>
                        <div class="text">
                            <h3>${featureSmall.title}</h3>
                            <p><i class="star-yellow square-15 margin-right-5"></i>${rateResult}/10<c:if
                                    test="${!empty featureSmall.star.name}"> / ${featureSmall.star.name[0]}</c:if></p>
                        </div>
                    </a>
                </div>
            </c:forEach>
        </div>
        <div class="separator"></div>
        <div class="feature-small">
            <h2>Latest Video</h2>
            <div class="slider owl-carousel owl-theme">
                <c:forEach items="${blogLatest}" var="blogLatest" varStatus="status">
                    <c:set var="rateAmount"><fmt:parseNumber type="number" value="${blogLatest.rate.amount}"/></c:set>
                    <c:set var="rateContributor"><fmt:parseNumber type="number" value="${blogLatest.rate.contributor}"/></c:set>
                    <c:set var="rateResult"><fmt:formatNumber type="number" maxFractionDigits="0"
                                                              value="${rateAmount / rateContributor}"/></c:set>
                    <div class="item">
                        <a href="${url.base}/video/watch/${blogLatest.path[fn:length(blogLatest.path) - 1]}/">
                            <div class="preview">
                                <img src="${url.panel}${path.image}/blog/thumbnail/${blogLatest.thumbnail}"
                                     alt="${setting.name} Video ${blogLatest.title} Thumbnail"/>
                            </div>
                            <div class="play">
                                <div class="image"></div>
                            </div>
                            <div class="text">
                                <h3>${blogLatest.title}</h3>
                                <p><i class="star-yellow square-15 margin-right-5"></i>${rateResult}/10<c:if
                                        test="${!empty blogLatest.star.name}"> / ${blogLatest.star.name[0]}</c:if></p>
                            </div>
                        </a>
                    </div>
                </c:forEach>
            </div>
        </div>
        <div class="separator"></div>
        <div class="feature-small">
            <h2>Top Viewed Video</h2>
            <div class="slider owl-carousel owl-theme">
                <c:forEach items="${blogTopViewed}" var="blogTopViewed" varStatus="status">
                    <c:set var="rateAmount"><fmt:parseNumber type="number"
                                                             value="${blogTopViewed.rate.amount}"/></c:set>
                    <c:set var="rateContributor"><fmt:parseNumber type="number"
                                                                  value="${blogTopViewed.rate.contributor}"/></c:set>
                    <c:set var="rateResult"><fmt:formatNumber type="number" maxFractionDigits="0"
                                                              value="${rateAmount / rateContributor}"/></c:set>
                    <div class="item">
                        <a href="${url.base}/video/watch/${blogTopViewed.path[fn:length(blogTopViewed.path) - 1]}/">
                            <div class="preview">
                                <img src="${url.panel}${path.image}/blog/thumbnail/${blogTopViewed.thumbnail}"
                                     alt="${setting.name} Video ${blogTopViewed.title} Thumbnail"/>
                            </div>
                            <div class="play">
                                <div class="image"></div>
                            </div>
                            <div class="text">
                                <h3>${blogTopViewed.title}</h3>
                                <p><i class="star-yellow square-15 margin-right-5"></i>${rateResult}/10<c:if
                                        test="${!empty blogTopViewed.star.name}"> / ${blogTopViewed.star.name[0]}</c:if>
                                </p>
                            </div>
                        </a>
                    </div>
                </c:forEach>
            </div>
        </div>
        <div class="separator"></div>
        <div class="feature-small">
            <h2>Top Rated Video</h2>
            <div class="slider owl-carousel owl-theme">
                <c:forEach items="${blogTopRated}" var="blogTopRated" varStatus="status">
                    <c:set var="rateAmount"><fmt:parseNumber type="number" value="${blogTopRated.rate.amount}"/></c:set>
                    <c:set var="rateContributor"><fmt:parseNumber type="number"
                                                                  value="${blogTopRated.rate.contributor}"/></c:set>
                    <c:set var="rateResult"><fmt:formatNumber type="number" maxFractionDigits="0"
                                                              value="${rateAmount / rateContributor}"/></c:set>
                    <div class="item">
                        <a href="${url.base}/video/watch/${blogTopRated.path[fn:length(blogTopRated.path) - 1]}/">
                            <div class="preview">
                                <img src="${url.panel}${path.image}/blog/thumbnail/${blogTopRated.thumbnail}"
                                     alt="${setting.name} Video ${blogTopRated.title} Thumbnail"/>
                            </div>
                            <div class="play">
                                <div class="image"></div>
                            </div>
                            <div class="text">
                                <h3>${blogTopRated.title}</h3>
                                <p><i class="star-yellow square-15 margin-right-5"></i>${rateResult}/10<c:if
                                        test="${!empty blogTopRated.star.name}"> / ${blogTopRated.star.name[0]}</c:if>
                                </p>
                            </div>
                        </a>
                    </div>
                </c:forEach>
            </div>
        </div>
        <div class="separator"></div>
        <div class="feature-small">
            <h2>Category</h2>
            <div class="slider owl-carousel owl-theme">
                <c:forEach items="${blogCategoryLatest}" var="blogCategoryLatest" varStatus="status">
                    <div class="item">
                        <a href="${url.base}/category/${blogCategoryLatest.path}/">
                            <div class="preview">
                                <img src="${url.panel}${path.image}/blog/category/thumbnail/${blogCategoryLatest.thumbnail}"
                                     alt="${setting.name} Video Category ${blogCategoryLatest.name} Thumbnail"/>
                            </div>
                            <div class="text-category">
                                <h3>${blogCategoryLatest.name}</h3>
                            </div>
                        </a>
                    </div>
                </c:forEach>
            </div>
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
<%@ include file="footer.jsp" %>