<%@ include file="head.jsp" %>
<%@ include file="header.jsp" %>
<div id="content" ng-controller="blog">
    <div id="response" ng-show="response.view" ng-class="response.class"><p>{{response.message}}</p></div>
    <div class="content">
        <div class="wrapper">
            <div class="watch" ng-init="initializeData('${entry._id}')">
                <div class="video">
                    <div class="file">
                        <c:choose>
                            <c:when test="${entry.file.source == 'Openload'}">
                                <iframe src="https://oload.stream/embed/${entry.file.name}/" scrolling="no"
                                        frameborder="0" allowfullscreen="true" webkitallowfullscreen="true"
                                        mozallowfullscreen="true"></iframe>
                            </c:when>
                            <c:when test="${entry.file.source == 'Rapid Video'}">
                                <iframe src="https://www.rapidvideo.com/e/${entry.file.name}/" scrolling="no"
                                        frameborder="0" allowfullscreen="true" webkitallowfullscreen="true"
                                        mozallowfullscreen="true"></iframe>
                            </c:when>
                            <c:when test="${entry.file.source == 'Very Stream'}">
                                <iframe src="https://verystream.com/e/${entry.file.name}/" scrolling="no"
                                        frameborder="0" allowfullscreen="true" webkitallowfullscreen="true"
                                        mozallowfullscreen="true"></iframe>
                            </c:when>
                            <c:otherwise>
                                <iframe src="https://www.youtube.com/embed/${entry.file.name}/" scrolling="no"
                                        frameborder="0" allowfullscreen="true" webkitallowfullscreen="true"
                                        mozallowfullscreen="true"
                                        allow="accelerometer; encrypted-media; gyroscope; picture-in-picture"></iframe>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <h2>${entry.title}</h2>
                    <div class="rating">
                        <c:set var="rateAmount"><fmt:parseNumber type="number" value="${entry.rate.amount}"/></c:set>
                        <c:set var="rateContributor"><fmt:parseNumber type="number" value="${entry.rate.contributor}"/></c:set>
                        <c:set var="rateResult"><fmt:formatNumber type="number" maxFractionDigits="0"
                                                                  value="${rateAmount / rateContributor}"/></c:set>
                        <p class="view">${entry.view.raw} views</p>
                        <p class="member">
                            <i class="star-yellow square-20 margin-right-5"></i>
                            <i class="star-yellow square-20 margin-right-5"></i>
                            <i class="star-yellow square-20 margin-right-5"></i>
                            <i class="star-yellow square-20 margin-right-5"></i>
                            <i class="star-yellow square-20 margin-right-5"></i>
                        </p>
                        <p class="total"><i class="star-yellow square-20 margin-right-5"></i>${rateResult}/10</p>
                        <div class="clearfix mobile"></div>
                        <p class="dislike"><i class="thumb-down-black square-20 margin-right-5"
                                              ng-click="dislikeBlog($event)"></i>${entry.dislike}</p>
                        <p class="like"><i class="thumb-up-black square-20 margin-right-5"></i>${entry.like}</p>
                        <div class="clearfix"></div>
                    </div>
                    <div class="description">
                        <h3>Story</h3>
                        <div class="story">
                            ${entry.description}
                        </div>
                    </div>
                </div>
                <div class="banner">
                    <!-- JuicyAds v3.0 -->
                    <script type="text/javascript" data-cfasync="false" async
                            src="https://adserver.juicyads.com/js/jads.js"></script>
                    <ins id="762590" data-width="728" data-height="102"></ins>
                    <script type="text/javascript" data-cfasync="false"
                            async>(adsbyjuicy = window.adsbyjuicy || []).push({'adzone': 762590});</script>
                    <!--JuicyAds END-->
                </div>
                <div class="related">
                    <!--<a href="#">
                        <div class="item">
                            <div class="picture">
                                <img class="responsive" src="${url.image}/temp/slider-1.jpg" alt="${setting.name} Member Profile Picture" />
                            </div>
                            <div class="description">
                                <h3>Stef Vlogs about Java for Web Apps in 2019 ... and MORE!</h3>
                                <p>Action, Drama</p>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                    </a>-->
                </div>
                <div class="comment">
                    <h3>${fn:length(comment)} Comments</h3>
                    <div class="item">
                        <div class="picture">
                            <img class="responsive" src="${url.panel}${url.image}/member/member-picture.png"
                                 alt="${setting.name} Member Profile Picture"/>
                        </div>
                        <div class="value">
                            <form method="POST" action="">
                                <textarea id="tinymce-1" name="comment-value"
                                          placeholder="Write your comment"></textarea>
                                <button class="send" name="comment" type="submit" ng-click="commentBlog($event)">
                                    Comment
                                </button>
                            </form>
                        </div>
                        <div class="clearfix"></div>
                    </div>
                    <c:forEach items="${comment}" var="comment" varStatus="status">
                        <div class="item">
                            <div class="picture">
                                <img class="responsive" src="${url.panel}${url.image}/member/member-picture.png"
                                     alt="${setting.name} Member Profile Picture"/>
                            </div>
                            <div class="value">
                                <p class="member">panpan<span>3 months ago</span></p>
                                <p>Comment Comment Comment Comment Comment Comment Comment Comment Comment Comment
                                    Comment Comment Comment Comment Comment Comment Comment Comment Comment Comment</br>
                                    comment</p>
                                <p>Comment</p>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="footer.jsp" %>
