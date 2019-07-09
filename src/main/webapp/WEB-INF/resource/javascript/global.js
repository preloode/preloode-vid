/* Initialize preloode library */
var library = new class_library();


/* Disable debugger */
//window.alert = function() {};
//console.log = function() {};


function preventRestoreOriginalView() {

    var anchor = Array.prototype.slice.call(document.getElementsByTagName("a"));

    anchor.forEach(function (value, key) {

        value.addEventListener("click", function (event) {

            event.stopPropagation();

        });

    });

    var input = Array.prototype.slice.call(document.getElementsByTagName("input"));

    input.forEach(function (value, key) {

        value.addEventListener("click", function (event) {

            event.stopPropagation();

        });

    });

    var button = Array.prototype.slice.call(document.getElementsByTagName("button"));

    button.forEach(function (value, key) {

        value.addEventListener("click", function (event) {

            event.stopPropagation();

        });

    });

}


function restoreOriginalView(event) {

    if (event.button == 0) {

        var menu = document.getElementById("menu");

        if (menu) {

            var position = document.getElementById("menu").getBoundingClientRect();
            var width = document.getElementById("menu").clientWidth;
            /*var iconWidth = document.getElementById("menu-icon").getElementsByTagName("i")[0].clientWidth;
             var iconPadding = parseInt(window.getComputedStyle(document.getElementById("menu-icon"), null).getPropertyValue("padding-right")) * 2;*/

            if (position.left == 0) {

                document.getElementById("menu").style.transform = "translateX(-100%)";
                element.style.transform = "translateX(-100%)";
                /*"translateX(-" + (width - (iconWidth + iconPadding)) + "px)";*/

                var now = new Date();
                var expires = now.setTime(now.getTime() + (365 * 24 * 60 * 60 * 1000));
                expires = new Date(expires);
                document.cookie = "preloode_main_menu=" + JSON.stringify("Closed") + "; expires=" + expires + "; path=/";

            }

        }

    }

}


function toggleChildMenu(element) {

    var index = element.parentNode.getAttribute("data-index");

    var childMenu = document.getElementsByClassName("child-menu");

    for (var i = 0; i < childMenu.length; i++) {

        if (childMenu[i].getAttribute("data-index") == index) {

            if (childMenu[i].style.height == "30px" || childMenu[i].style.height == "3px") {

                childMenu[i].style.height = 0;
                childMenu[i].style.opacity = 0;
                element.getElementsByTagName("i")[0].style.transform = "none";

            } else {

                if (!/separator/.test(childMenu[i].getAttribute("class"))) {

                    childMenu[i].style.height = "30px";
                    childMenu[i].style.opacity = 1;

                } else {

                    childMenu[i].style.height = "3px";

                }

                element.getElementsByTagName("i")[0].style.transform = "rotate(90deg)";

            }

        }

    }

}


function toggleMenu(element) {

    var cookie = "";

    var position = document.getElementById("menu").getBoundingClientRect();
    /*var width = document.getElementById("menu").clientWidth;
     var iconWidth = document.getElementById("menu-icon").getElementsByTagName("i")[0].clientWidth;
     var iconPadding = parseInt(window.getComputedStyle(document.getElementById("menu-icon"), null).getPropertyValue("padding-right")) * 2;*/

    if (position.left < 0) {

        document.getElementById("menu").style.transform = "translateX(0)";
        //element.style.transform = "translateX(0)";

        cookie = "Wide Open";

    } else {

        document.getElementById("menu").style.transform = "translateX(-100%)";
        //element.style.transform = "translateX(-" + (width - (iconWidth + iconPadding)) + "px)";

        cookie = "Closed";

    }

    var now = new Date();
    var expires = now.setTime(now.getTime() + (365 * 24 * 60 * 60 * 1000));
    expires = new Date(expires);
    document.cookie = "preloode_main_menu=" + JSON.stringify(cookie) + "; expires=" + expires + "; path=/";

}


document.addEventListener("DOMContentLoaded", function (event) {


    library.layout.initialize();


    var menu = document.getElementById("menu");
    var menuIcon = document.getElementsByClassName("menu-icon");
    var closeIcon = document.getElementsByClassName("close-menu");

    if (menuIcon.length > 0) {

        menuIcon[0].addEventListener("click", function (event) {

            event.stopPropagation();

            toggleMenu(this);

        });

    }

    if (closeIcon.length > 0) {

        closeIcon[0].addEventListener("click", function (event) {

            event.stopPropagation();

            toggleMenu(this);

        });

    }

    if (menu) {

        menu.addEventListener("click", function (event) {

            event.stopPropagation();

        });

    }


    var toggle = document.getElementsByClassName("menu-toggle");

    for (var i = 0; i < toggle.length; i++) {

        toggle[i].addEventListener("click", function () {

            toggleChildMenu(this);

        });

    }


    flatpickr(".date-picker", {
        "dateFormat": "Y-m-d",
    });

    flatpickr(".date-range-picker", {
        "dateFormat": "Y-m-d",
        "mode": "range"
    });

    flatpickr(".date-time-picker", {
        "dateFormat": "Y-m-d H:i:ss",
        "enableTime": true,
    });


    library.accordion.initialize("accordion", {
        "openOnlyOneAtATime": false
    });


    preventRestoreOriginalView();


    document.addEventListener("click", function (event) {

        restoreOriginalView(event);

    });


});


window.addEventListener("resize", function (event) {


    library.layout.initialize();


});


var app = angular.module("app", ["ngMaterial", "ngSanitize", "ngAnimate", "ngScrollbar"]);


app.config(["$httpProvider", function ($httpProvider) {


    $httpProvider.defaults.useXDomain = true;
    delete $httpProvider.defaults.headers.common["X-Requested-With"];


}]);


app.controller("global", ["$scope", "$timeout", "$parse", "$window", "$log", "global", function ($scope, $timeout, $parse, $window, $log, global) {


    $scope.loading = {
        "view": false
    };

    $scope.loginPassword = {
        "response": {
            "class": "",
            "value": ""
        },
        "value": ""
    };

    $scope.loginRememberMe = {
        "value": false
    };

    $scope.loginUsername = {
        "response": {
            "class": "",
            "value": ""
        },
        "value": ""
    };

    $scope.popup = {
        "login": false,
        "register": false,
        "view": false
    };

    $scope.registerCaptcha = {
        "response": {
            "class": "",
            "value": ""
        },
        "value": ""
    };

    $scope.registerConfirmPassword = {
        "response": {
            "class": "",
            "value": ""
        },
        "value": ""
    };

    $scope.registerEmailAddress = {
        "response": {
            "class": "",
            "value": ""
        },
        "value": ""
    };

    $scope.registerGender = {
        "option": [
            {"name": "Gender"},
            {"name": "Female"},
            {"name": "Male"},
            {"name": "Other"}
        ],
        "response": {
            "class": "",
            "value": ""
        },
        "value": ""
    };

    $scope.registerGender.selected = $scope.registerGender.option[0];

    $scope.registerPassword = {
        "response": {
            "class": "",
            "value": ""
        },
        "value": ""
    };

    $scope.registerPhoneNumber = {
        "countryCode": {
            "option": [
                {"number": "+60"},
                {"number": "+62"},
                {"number": "+63"},
                {"number": "+66"},
                {"number": "+855"},
                {"number": "+86"},
                {"number": "+886"}
            ]
        },
        "response": {
            "class": "",
            "value": ""
        },
        "value": ""
    };

    $scope.registerPhoneNumber.countryCode.selected = $scope.registerPhoneNumber.countryCode.option[0];

    $scope.registerUsername = {
        "response": {
            "class": "",
            "value": ""
        },
        "value": ""
    };

    $scope.response = {
        "class": "",
        "message": "",
        "view": false
    };

    $scope.validLogin = {
        "password": false,
        "username": false
    };

    $scope.validRegister = {
        "confirmPassword": false,
        "emailAddress": false,
        "password": false,
        "username": false
    };


    $scope.checkDataLogin = function () {

        $scope.checkLoginPassword();

        $scope.checkLoginUsername();

    }


    $scope.checkDataRegister = function () {

        $scope.checkRegisterConfirmPassword();

        $scope.checkRegisterEmailAddress();

        $scope.checkRegisterGender();

        $scope.checkRegisterPassword();

        $scope.checkRegisterPhoneNumber();

        $scope.checkRegisterUsername();

    }


    $scope.checkLoginPassword = function () {

        if ($scope.loginPassword.value.length < 3) {

            $scope.loginPassword.response.value = "Please enter at least 3 characters";
            $scope.loginPassword.response.class = "error";
            $scope.validLogin.password = false;

        } else {

            $scope.loginPassword.response.value = "";
            $scope.validLogin.password = true;

        }

        $scope.loginPassword.value = $scope.loginPassword.value.trim();

    }


    $scope.checkLoginUsername = function () {

        if (/\s/.test($scope.loginUsername.value)) {

            $scope.loginUsername.response.value = "Please don't enter whitespace";
            $scope.loginUsername.response.class = "error";
            $scope.validLogin.username = false;

        } else if ($scope.loginUsername.value.length < 3 || $scope.loginUsername.value.length > 20) {

            $scope.loginUsername.response.value = "Please enter between 3 - 20 characters";
            $scope.loginUsername.response.class = "error";
            $scope.validLogin.username = false;

        } else {

            $scope.loginUsername.response.value = "";
            $scope.validLogin.username = true;

        }

        $scope.loginUsername.value = $scope.loginUsername.value.trim();

    }


    $scope.checkRegisterConfirmPassword = function () {

        if (!$scope.registerConfirmPassword.value.match($scope.registerPassword.value)) {

            $scope.registerConfirmPassword.response.value = "Password doesn't match";
            $scope.registerConfirmPassword.response.class = "error";
            $scope.validRegister.confirmPassword = false;

        } else {

            $scope.registerConfirmPassword.response.value = "";
            $scope.validRegister.confirmPassword = true;

        }

        $scope.registerConfirmPassword.value = $scope.registerConfirmPassword.value.trim();

    }


    $scope.checkRegisterEmailAddress = function () {

        if ($scope.registerEmailAddress.value != "") {

            if (!$scope.registerEmailAddress.value.match(/^([0-9A-Za-z_\-\.]){1,}\@([0-9A-Za-z_\-\.]){1,}\.([A-Za-z]){2,}$/) || $scope.registerEmailAddress.value.length > 50) {

                $scope.registerEmailAddress.response.value = "Please enter valid email address";
                $scope.registerEmailAddress.response.class = "error";
                $scope.validRegister.emailAddress = false;

            } else {

                $scope.registerEmailAddress.response.value = "";
                $scope.validRegister.emailAddress = true;

            }

        } else {

            $scope.registerEmailAddress.response.value = "";
            $scope.validRegister.emailAddress = true;

        }

        $scope.registerEmailAddress.value = $scope.registerEmailAddress.value.trim();

    }


    $scope.checkRegisterGender = function () {

        if ($scope.registerGender.selected.name == "Gender") {

            $scope.registerGender.response.value = "Please select gender";
            $scope.registerGender.response.class = "error";
            $scope.validRegister.gender = false;

        } else {

            $scope.registerGender.response.value = "";
            $scope.validRegister.gender = true;

        }

    }


    $scope.checkRegisterPassword = function () {

        if ($scope.registerPassword.value.length < 3) {

            $scope.registerPassword.response.value = "Please enter at least 3 characters";
            $scope.registerPassword.response.class = "error";
            $scope.validRegister.password = false;

        } else if (/[a-z]/.test($scope.registerPassword.value) && /[A-Z]/.test($scope.registerPassword.value) && /[0-9]/.test($scope.registerPassword.value) && /[-!$%^&*()_+|~=`{}\[\]:";'<>?,.\/]/.test($scope.registerPassword.value)) {

            $scope.registerPassword.response.value = "Strong security password";
            $scope.registerPassword.response.class = "success";
            $scope.validRegister.password = true;

        } else if (/[A-Z]/.test($scope.registerPassword.value) && /[0-9]/.test($scope.registerPassword.value) && /[-!$%^&*()_+|~=`{}\[\]:";'<>?,.\/]/.test($scope.registerPassword.value)) {

            $scope.registerPassword.response.value = "Secured password";
            $scope.registerPassword.response.class = "success";
            $scope.validRegister.password = true;

        } else if (/[a-z]/.test($scope.registerPassword.value) && /[0-9]/.test($scope.registerPassword.value) && /[-!$%^&*()_+|~=`{}\[\]:";'<>?,.\/]/.test($scope.registerPassword.value)) {

            $scope.registerPassword.response.value = "Secured password";
            $scope.registerPassword.response.class = "success";
            $scope.validRegister.password = true;

        } else if (/[a-z]/.test($scope.registerPassword.value) && /[A-Z]/.test($scope.registerPassword.value) && /[-!$%^&*()_+|~=`{}\[\]:";'<>?,.\/]/.test($scope.registerPassword.value)) {

            $scope.registerPassword.response.value = "Secured password";
            $scope.registerPassword.response.class = "success";
            $scope.validRegister.password = true;

        } else if (/[a-z]/.test($scope.registerPassword.value) && /[A-Z]/.test($scope.registerPassword.value) && /[0-9]/.test($scope.registerPassword.value)) {

            $scope.registerPassword.response.value = "Secured password";
            $scope.registerPassword.response.class = "success";
            $scope.validRegister.password = true;

        } else if (/[0-9]/.test($scope.registerPassword.value) && /[-!$%^&*()_+|~=`{}\[\]:";'<>?,.\/]/.test($scope.registerPassword.value)) {

            $scope.registerPassword.response.value = "Medium security password";
            $scope.registerPassword.response.class = "warning";
            $scope.validRegister.password = true;

        } else if (/[A-Z]/.test($scope.registerPassword.value) && /[-!$%^&*()_+|~=`{}\[\]:";'<>?,.\/]/.test($scope.registerPassword.value)) {

            $scope.registerPassword.response.value = "Medium security password";
            $scope.registerPassword.response.class = "warning";
            $scope.validRegister.password = true;

        } else if (/[A-Z]/.test($scope.registerPassword.value) && /[0-9]/.test($scope.registerPassword.value)) {

            $scope.registerPassword.response.value = "Medium security password";
            $scope.registerPassword.response.class = "warning";
            $scope.validRegister.password = true;

        } else if (/[a-z]/.test($scope.registerPassword.value) && /[-!$%^&*()_+|~=`{}\[\]:";'<>?,.\/]/.test($scope.registerPassword.value)) {

            $scope.registerPassword.response.value = "Medium security password";
            $scope.registerPassword.response.class = "warning";
            $scope.validRegister.password = true;

        } else if (/[a-z]/.test($scope.registerPassword.value) && /[0-9]/.test($scope.registerPassword.value)) {

            $scope.registerPassword.response.value = "Medium security password";
            $scope.registerPassword.response.class = "warning";
            $scope.validRegister.password = true;

        } else if (/[a-z]/.test($scope.registerPassword.value) && /[A-Z]/.test($scope.registerPassword.value)) {

            $scope.registerPassword.response.value = "Medium security password";
            $scope.registerPassword.response.class = "warning";
            $scope.validRegister.password = true;

        } else if (/[-!$%^&*()_+|~=`{}\[\]:";'<>?,.\/]/.test($scope.registerPassword.value)) {

            $scope.registerPassword.response.value = "Low security password";
            $scope.registerPassword.response.class = "warning";
            $scope.validRegister.password = true;

        } else if (/[0-9]/.test($scope.registerPassword.value)) {

            $scope.registerPassword.response.value = "Low security password";
            $scope.registerPassword.response.class = "warning";
            $scope.validRegister.password = true;

        } else if (/[A-Z]/.test($scope.registerPassword.value)) {

            $scope.registerPassword.response.value = "Low security password";
            $scope.registerPassword.response.class = "warning";
            $scope.validRegister.password = true;

        } else if (/[a-z]/.test($scope.registerPassword.value)) {

            $scope.registerPassword.response.value = "Low security password";
            $scope.registerPassword.response.class = "warning";
            $scope.validRegister.password = true;

        } else {

            $scope.registerPassword.response.value = "";
            $scope.validRegister.password = true;

        }

        $scope.registerPassword.value = $scope.registerPassword.value.trim();

    }


    $scope.checkRegisterPhoneNumber = function () {

        if ($scope.registerPhoneNumber.value != "") {

            if ($scope.registerPhoneNumber.value.startsWith("0")) {

                $scope.registerPhoneNumber.response.value = "Please enter without leading zero";
                $scope.registerPhoneNumber.response.class = "error";
                $scope.validRegister.phoneNumber = false;

            } else if (!$scope.registerPhoneNumber.value.match(/^[0-9]+$/) || $scope.registerPhoneNumber.value.length < 9 || $scope.registerPhoneNumber.value.length > 14) {

                $scope.registerPhoneNumber.response.value = "Please enter valid mobile phone number";
                $scope.registerPhoneNumber.response.class = "error";
                $scope.validRegister.phoneNumber = false;

            } else {

                $scope.registerPhoneNumber.response.value = "";
                $scope.validRegister.phoneNumber = true;

            }

        } else {

            $scope.registerPhoneNumber.response.value = "";
            $scope.validRegister.phoneNumber = true;

        }

        $scope.registerPhoneNumber.value = $scope.registerPhoneNumber.value.trim();

    }


    $scope.checkRegisterUsername = function () {

        if (/\s/.test($scope.registerUsername.value)) {

            $scope.registerUsername.response.value = "Please don't enter whitespace";
            $scope.registerUsername.response.class = "error";
            $scope.registerUsername.response.view = true;
            $scope.validRegister.username = false;

        } else if ($scope.registerUsername.value.length < 3 || $scope.registerUsername.value.length > 20) {

            $scope.registerUsername.response.value = "Please enter between 3 - 20 characters";
            $scope.registerUsername.response.class = "error";
            $scope.registerUsername.response.view = true;
            $scope.validRegister.username = false;

        } else {

            $scope.registerUsername.response.value = "";
            $scope.registerUsername.response.view = false;
            $scope.validRegister.username = true;

        }

        $scope.registerUsername.value = $scope.registerUsername.value.trim();

    }


    $scope.closePopup = function () {

        angular.forEach($scope.popup, function (value, key) {

            var model = $parse(key);
            model.assign($scope.popup, false);

        });

    }


    $scope.hideResponse = function () {

        $timeout(function () {

            $scope.response.view = false;
            $scope.$digest();

        }, 7000);

    }


    $scope.initializeLayout = function () {

        angular.element(document).ready(function () {

            library.layout.initialize();

        });

    }


    $scope.initializeRegisterInput = function () {

        var result = {
            "phoneNumber": $scope.registerPhoneNumber.value
        };

        if ($scope.registerPhoneNumber.value != "") {

            result.phoneNumber = $scope.registerPhoneNumber.countryCode.selected.number + $scope.registerPhoneNumber.value;

        }

        return result;

    }


    $scope.loadLogin = function () {

        $scope.loading.view = true;

        $scope.popup.register = false;
        $scope.popup.view = true;
        $scope.popup.login = true;

        $scope.rebuild();

        $scope.loading.view = false;

    }


    $scope.loadRegister = function () {

        $scope.loading.view = true;

        $scope.popup.login = false;
        $scope.popup.view = true;
        $scope.popup.register = true;

        $scope.rebuild();

        $scope.loading.view = false;

    }


    $scope.login = function (event) {

        $scope.loading.view = true;

        var rest = {
            "data": {
                "username": $scope.loginUsername.value,
                "password": library.rsaEncryption.encrypt($scope.loginPassword.value)
            },
            "url": document.getElementById("config").getAttribute("data-base-url") + "/member/login"
        };
        global.rest(rest, function (response) {

            if (response.result) {

                $window.location.reload();

            } else {

                $scope.response.class = "error";
                $scope.response.message = response.response;
                $scope.response.view = true;

            }

            $scope.loading.view = false;

            $scope.hideResponse();

        });

        event.preventDefault();

    }


    $scope.logout = function () {

        $scope.loading.view = true;

        var rest = {
            "data": {},
            "url": document.getElementById("config").getAttribute("data-base-url") + "/member/logout"
        };
        global.rest(rest, function (response) {

            if (response.result) {

                $window.location.reload();

            } else {

                $scope.response.class = "error";
                $scope.response.message = response.response;
                $scope.response.view = true;

            }

            $scope.loading.view = false;

        });

    }


    $scope.rebuild = function () {

        $scope.$broadcast("rebuild:scrollbar");

    }


    $scope.refreshRegisterCaptcha = function () {

        angular.element(document).ready(function () {

            var registerDynamicCaptcha = Array.prototype.slice.call(document.getElementsByClassName("register-dynamic-captcha"));

            registerDynamicCaptcha.forEach(function (value, key) {

                value.src = "captcha?t=" + new Date().getTime();

            });

        });

    }


    $scope.register = function (event) {

        $scope.loading.view = true;

        var input = $scope.initializeRegisterInput();

        var rest = {
            "data": {
                "captcha": $scope.registerCaptcha.value,
                "contact": {
                    "email_address": $scope.registerEmailAddress.value,
                    "phone_number": input.phoneNumber
                },
                "gender": $scope.registerGender.selected.name,
                "password": {
                    "main": library.rsaEncryption.encrypt($scope.registerPassword.value),
                    "recovery": library.rsaEncryption.encrypt($scope.registerPassword.value)
                },
                "username": $scope.registerUsername.value,
            },
            "url": document.getElementById("config").getAttribute("data-base-url") + "/member/register"
        };
        global.rest(rest, function (response) {

            if (response.result) {

                $scope.response.class = "success";

            } else {

                $scope.response.class = "error";

            }

            $scope.response.message = response.response;
            $scope.response.view = true;

            $scope.loading.view = false;

            $scope.hideResponse();

        });

        event.preventDefault();

    }


    $scope.rememberMeToggleCheckbox = function () {

        if (!$scope.loginRememberMe.value) {

            $scope.loginRememberMe.value = true;

        } else {

            $scope.loginRememberMe.value = false;

        }

    }


}]);


app.provider("global", function () {


    this.$get = ["$http", function ($http) {


        var global = {};


        global.rest = function (rest, callback) {

            $http({
                "data": rest.data,
                "headers": {"Content-Type": "application/json"},
                "method": "POST",
                "url": rest.url
            }).then(function (response) {

                callback(response.data);

            });

        }


        return global;


    }];


});


app.directive("fileInput", function ($parse) {


    var fileInput = {};


    fileInput.link = function ($scope, element, attrs) {

        element.on("change", function (event) {

            $parse(attrs.fileInput).assign($scope, element[0].files);
            $scope.$digest();

            $scope.uploadFile();

        })

    }


    return fileInput;


});


app.directive("thumbnailInput", function ($parse) {


    var thumbnailInput = {};


    thumbnailInput.link = function ($scope, element, attrs) {

        element.on("change", function (event) {

            $parse(attrs.thumbnailInput).assign($scope, element[0].files);
            $scope.$digest();

            $scope.uploadThumbnail();

        })

    }


    return thumbnailInput;


});


app.filter("lowerCase", function () {


    return function (data) {

        var result = "";

        if (data != "") {

            result = data.toLowerCase();

        }

        return result;

    }


});


app.filter("lowerCaseHyphen", function () {


    return function (data) {

        var result = "";

        if (data != "") {

            result = data.toLowerCase().replace(" ", "-");

        }

        return result;

    }


});


app.filter("lowerCaseUnderscore", function () {


    return function (data) {

        var result = "";

        if (data != "") {

            result = data.toLowerCase().replace(" ", "_");

        }

        return result;

    }


});


app.directive("loginForm", function () {


    var global = {};


    global.templateUrl = document.getElementById("config").getAttribute("data-base-url") + "/resource/html/login-form-popup.html";


    return global;


});


app.directive("registerForm", function () {


    var global = {};


    global.templateUrl = document.getElementById("config").getAttribute("data-base-url") + "/resource/html/register-form-popup.html";


    return global;


});
