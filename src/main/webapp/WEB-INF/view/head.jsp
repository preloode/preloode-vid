<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:useBean id="javaUtilDate" class="java.util.Date"/>
<!DOCTYPE html>
<html ng-app="app" ng-controller="global">
<head>
    <meta charset="UTF-8">
    <meta name="language" content="english"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <title><c:if
            test="${(!empty setting.metaTitle) and (setting.metaTitle != 'Index')}">${setting.metaTitle} | </c:if><c:if
            test="${!empty setting.name}">${setting.name}</c:if></title>
    <c:if test="${!empty setting.metaDescription}">
        <meta name="description" content="${setting.metaDescription}"/>
    </c:if>
    <c:if test="${!empty setting.metaKeyword}">
        <meta name="keyword" content="${setting.metaKeyword}"/>
    </c:if>
    <c:if test="${!empty setting.author}">
        <link rel="author" href="${setting.author}"/>
    </c:if>
    <c:if test="${!empty setting.publisher}">
        <link rel="publisher" href="${setting.publisher}"/>
    </c:if>
    <c:choose>
        <c:when test="${!empty setting.favicon}">
            <link rel="icon" type="image/x-icon" href="${url.panel}${url.image}/setting/${setting.favicon}"/>
        </c:when>
        <c:otherwise>
            <link rel="icon" type="image/x-icon" href="${url.image}/favicon.ico"/>
        </c:otherwise>
    </c:choose>
    <link rel="stylesheet" type="text/css" href="${url.css}/library/angular-js/angular-material.min.css"/>
    <link rel="stylesheet" type="text/css" href="${url.css}/library/angular-js/angular-scrollbar.min.css"/>
    <link rel="stylesheet" type="text/css" href="${url.css}/library/flatpicker.min.css"/>
    <link rel="stylesheet" type="text/css" href="${url.css}/library/owl-carousel.min.css"/>
    <link rel="stylesheet" type="text/css" href="${url.css}/library/owl-theme-default.min.css"/>
    <link rel="stylesheet" type="text/css" href="${url.css}/library/library.css"/>
    <link rel="stylesheet" type="text/css" href="${url.css}/global.css"/>
    <c:if test="${!empty setting.css}">
        <c:forEach items="${setting.css}" var="value">
            <link rel="stylesheet" type="text/css" href="${url.css}/${value}"/>
        </c:forEach>
    </c:if>
    <script type="text/javascript" src="${url.javascript}/library/angular-js/angular.min.js"></script>
    <script type="text/javascript" src="${url.javascript}/library/angular-js/angular-animate.min.js"></script>
    <script type="text/javascript" src="${url.javascript}/library/angular-js/angular-aria.min.js"></script>
    <script type="text/javascript" src="${url.javascript}/library/angular-js/angular-material.min.js"></script>
    <script type="text/javascript" src="${url.javascript}/library/angular-js/angular-sanitize.min.js"></script>
    <script type="text/javascript" src="${url.javascript}/library/angular-js/angular-scrollbar.min.js"></script>
    <script type="text/javascript" src="${url.javascript}/library/jquery/jquery.min.js"></script>
    <script type='text/javascript' src="${url.javascript}/library/jsencrypt.js"></script>
    <script type="text/javascript" src="${url.javascript}/library/flatpicker.min.js"></script>
    <script type="text/javascript" src="${url.javascript}/library/owl-carousel.min.js"></script>
    <script type="text/javascript" src="${url.javascript}/library/library.js"></script>
    <script type="text/javascript" src="${url.plugin}/tinymce/tinymce.min.js"></script>
    <script type="text/javascript" src="${url.javascript}/global.js"></script>
    <c:if test="${!empty setting.javascript}">
        <c:forEach items="${setting.javascript}" var="value">
            <script type="text/javascript" src="${url.javascript}/${value}"></script>
        </c:forEach>
    </c:if>
</head>

<body>
<base id="config" data-audio-url="${url.audio}" data-base-url="${url.base}" data-image-url="${url.image}"
      data-video-url="${url.video}" data-website-url="${url.website}" data-panel-url="${url.panel}">
<audio id="notification">
    <source src="${url.audio}/notification.mp3" type="audio/mpeg">
</audio>
<div id="popup" ng-show="popup.view">
    <div class="wrapper" ng-show="popup.view">
        <div class="close">
            <i class="remove-black square-15" ng-click="closePopup()"></i>
        </div>
        <div class="popup" ng-scrollbar rebuild-on-resize rebuild-on="rebuild:scrollbar">
            <login-form ng-if="popup.login"></login-form>
            <register-form ng-if="popup.register"></register-form>
        </div>
    </div>
</div>
<div id="loading" ng-show="loading.view">
    <div class="wrapper">
        <div class="circle"></div>
        <div class="circle-1"></div>
    </div>
</div>
