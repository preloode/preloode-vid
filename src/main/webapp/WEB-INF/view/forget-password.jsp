<%@ include file="head.jsp" %>
<%@ include file="header.jsp" %>
<div id="content">
    <div id="response" ng-if="response.view" ng-class="response.class"><p>{{response.message}}</p></div>
    <div class="content">
        <div class="wrapper">
            <div class="forget-password">
                <h2>Forget Password</h2>
                <form method="POST" action="">
                    <p class="title"><span class="required">* </span>Email</p>
                    <input class="data" name="forget-password-email" type="text" placeholder="Email" value=""/>
                    <p class="response" ng-class="email.response.class">{{email.response.value}}</p>
                    <p class="title"><span class="required">* </span>I'm not robot</p>
                    <div class="captcha-input">
                        <input class="data" name="forget-password-captcha" type="text" placeholder="Captcha"
                               ng-keyup="checkCaptcha()" ng-model="captcha.value"/>
                    </div>
                    <div class="captcha-image">
                        <img class="dynamic-captcha" src="captcha" alt="Captcha Image"/>
                    </div>
                    <p class="refresh-captcha"><i class="refresh-black square-20" ng-click="refreshCaptcha()"></i></p>
                    <div class="clearfix"></div>
                    <p class="response" ng-class="captcha.response.class">{{captcha.response.value}}</p>
                    <button class="forget-password" name="forget-password" type="submit">Send</button>
                </form>
            </div>
        </div>
    </div>
</div>
<%@ include file="footer.jsp" %>
