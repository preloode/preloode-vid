<div id="wrapper">
    <div id="menu" style="transform : translateX(${layout.mainMenuIconTranslateX})">
        <div class="close close-menu">
            <i class="remove-black square-15 margin-right-10"></i>Close
        </div>
        <div class="main-menu" ng-mousemove="rebuild()" ng-scrollbar rebuild-on-resize rebuild-on="rebuild:scrollbar">
            <ul class="main-menu">
                <li class="menu">
                    <a href="${url.base}/">
                        <div class="icon">
                            <span></span>
                        </div>
                        <p>Home</p>
                    </a>
                    <div class="clearfix"></div>
                </li>
                <li class="separator"></li>
                <li class="menu" data-index="1">
                    <a href="${url.base}/category/">
                        <div class="icon">
                            <span></span>
                        </div>
                        <p>Category</p>
                    </a>
                    <p class="menu-toggle" data-index="1" ng-click="rebuild()"><i class="toggle-black square-15"></i>
                    </p>
                    <div class="clearfix"></div>
                </li>
                <li class="separator"></li>
                <c:forEach items="${blogCategoryMenu}" var="category" varStatus="status">
                    <li class="menu child-menu" data-index="1">
                        <a href="${url.base}/category/${category.path}/">
                            <p><i class="toggle-black square-15 margin-right-5"></i>${category.name}</p>
                        </a>
                        <div class="clearfix"></div>
                    </li>
                    <li class="separator child-menu" data-index="1"></li>
                </c:forEach>
                <li class="menu">
                    <a href="${url.base}/video/">
                        <div class="icon">
                            <span></span>
                        </div>
                        <p>Video</p>
                    </a>
                    <div class="clearfix"></div>
                </li>
                <li class="separator"></li>
                <li class="menu">
                    <a href="${url.base}/star/">
                        <div class="icon">
                            <span></span>
                        </div>
                        <p>Star</p>
                    </a>
                    <div class="clearfix"></div>
                </li>
                <li class="separator"></li>
            </ul>
        </div>
    </div>
    <div id="header">
        <div class="wrapper">
            <div class="menu-icon">
                <i class="menu-black square-20"></i>
            </div>
            <div class="logo">
                <a href="${url.base}/">
                    <h1>
                        <c:choose>
                            <c:when test="${!empty setting.favicon}">
                                <img class="responsive" src="${url.panel}${url.image}/setting/${setting.logo}"
                                     alt="${setting.name} Logo"/>
                            </c:when>
                            <c:otherwise>
                                <img class="responsive" src="${url.image}/logo.png" alt="${setting.name} Logo"/>
                            </c:otherwise>
                        </c:choose>
                    </h1>
                </a>
            </div>
            <div class="search">
                <form method="POST" action="">
                    <input name="keyword" type="text" placeholder="Search" value=""/>
                    <button name="search" type="submit"><i class="search-black square-20"></i></button>
                </form>
            </div>
            <div class="account">
                <c:choose>
                    <c:when test="${!empty preloodeAccount._id}">
                        <p class="log" ng-click="logout()">Logout</p>
                        <p class="name"><i class="user-pink square-20 margin-right-10"></i>${preloodeAccount.username}
                        </p>
                    </c:when>
                    <c:otherwise>
                        <p class="log" ng-click="loadRegister()">Sign Up</span></p>
                        <p class="log" ng-click="loadLogin()">Login</p>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
