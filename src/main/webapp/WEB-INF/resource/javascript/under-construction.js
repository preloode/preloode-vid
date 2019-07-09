function initializePageNotFoundLayout() {

    var height = window.innerHeight - (document.getElementById("footer").offsetHeight + parseInt(window.getComputedStyle(document.getElementsByClassName("description")[0]).height) + parseInt(window.getComputedStyle(document.getElementsByClassName("countdown")[0]).height));
    var pictureHeight = parseInt(window.getComputedStyle(document.getElementsByClassName("picture")[0]).height);

    if (pictureHeight < height) {

        var padding = (height - pictureHeight) / 2;
        document.getElementsByClassName("picture")[0].style.paddingTop = padding + "px";
        document.getElementsByClassName("countdown")[0].style.paddingBottom = padding + "px";

    } else {

        document.getElementsByClassName("picture")[0].removeAttribute("style");
        document.getElementsByClassName("countdown")[0].removeAttribute("style");

    }

}


document.addEventListener("DOMContentLoaded", function (event) {


    var picture = document.getElementsByClassName("picture")[0];
    picture.onload = initializePageNotFoundLayout;


});


window.addEventListener("resize", function (event) {


    var picture = document.getElementsByClassName("picture")[0];
    picture.onload = initializePageNotFoundLayout;


});


app.controller("underConstruction", ["$scope", "$interval", "$log", "underConstruction", function ($scope, $interval, $log, underConstruction) {


    $scope.timer = {
        current: new Date(),
        limit: new Date()
    }


    $scope.initializeCountdown = function () {

        $scope.loading.view = true;

        var rest = {
            "data": {},
            "url": document.getElementById("config").getAttribute("data-base-url") + "/under-construction/initialize-countdown"
        }
        underConstruction.rest(rest, function (response) {

            if (response.result) {

                $scope.timer.limit = response.countdown;

                var interval = $interval(function () {

                    $scope.timer.current = new Date();

                    if ($scope.timer.current >= $scope.timer.limit) {

                        $interval.cancel(interval);

                    }

                }, 1000);

            } else {

                $scope.response.class = "error";
                $scope.response.message = response.response;
                $scope.response.view = true;

                $scope.hideResponse();

            }

            $scope.loading.view = false;

        });

    }


}]);


app.provider("underConstruction", function () {


    this.$get = ["$http", function ($http) {


        var underConstruction = {};


        underConstruction.rest = function (rest, callback) {

            $http({
                "data": rest.data,
                "headers": {"Content-Type": "application/json"},
                "method": "POST",
                "url": rest.url
            }).then(function (response) {

                callback(response.data);

            });

        }


        return underConstruction;


    }];


});
