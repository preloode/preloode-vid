$(document).ready(function () {


    tinymce.init({
        "branding": false,
        "height": 50,
        "menubar": false,
        "selector": "#tinymce-1",
        "statusbar": false,
        "toolbar": false
    });


});


app.controller("blog", ["$scope", "$log", "blog", function ($scope, $log, blog) {


    $scope.comment = {
        "value": ""
    };

    $scope.dislike = {
        "value": ""
    }

    $scope.id = {
        "value": ""
    };

    $scope.like = {
        "value": ""
    }

    $scope.valid = {
        "comment": false
    };


    $scope.checkComment = function () {

        if ($scope.comment.value != "") {

            if ($scope.comment.value.length < 5 || $scope.comment.value.length > 255) {

                $scope.response.message = "Please enter between 5 - 255 characters";
                $scope.firstName.response.class = "error";
                $scope.firstName.response.view = true;
                $scope.valid.comment = false;

            } else {

                $scope.firstName.response.view = false;
                $scope.valid.comment = true;

            }

        } else {

            $scope.response.message = "Please enter comment";
            $scope.response.class = "error";
            $scope.response.view = true;
            $scope.valid.comment = false;

        }

    }


    $scope.commentBlog = function (event) {

        $scope.checkComment();

        var valid = true;

        angular.forEach($scope.valid, function (value, key) {

            if (!value) {

                valid = false;

                return false;

            }

        });

        if (valid) {

            $scope.loading.view = true;

            var rest = {
                "data": {
                    "id": $scope.id.value,
                    "comment": $scope.comment.value
                },
                "url": document.getElementById("config").getAttribute("data-base-url") + "/video/comment"
            };
            blog.rest(rest, function (response) {

                if (response.result) {

                    $scope.response.class = "success";

                } else {

                    $scope.response.class = "error";

                }

                $scope.loading.view = false;

                $scope.response.message = response.response;
                $scope.response.view = true;

                $scope.hideResponse();

            });

        } else {

            $scope.response.class = "error";
            $scope.response.message = "Please enter a valid data";
            $scope.response.view = true;

            $scope.hideResponse();

        }

        event.preventDefault();

    }


    $scope.dislikeBlog = function (event) {

        $scope.loading.view = true;

        var rest = {
            "data": {
                "id": $scope.id.value
            },
            "url": document.getElementById("config").getAttribute("data-base-url") + "/video/dislike"
        };
        blog.rest(rest, function (response) {

            if (response.result) {

                $scope.response.class = "success";

            } else {

                $scope.response.class = "error";

            }

            $scope.loading.view = false;

            $scope.response.message = response.response;
            $scope.response.view = true;

            $scope.hideResponse();

        });

        event.preventDefault();

    }


    $scope.initializeData = function (id) {

        $scope.loading.view = true;

        var rest = {
            "data": {
                "id": id
            },
            "url": document.getElementById("config").getAttribute("data-base-url") + "/video/initialize-data"
        }
        blog.rest(rest, function (response) {

            if (response.result) {

                if (response.hasOwnProperty("data")) {

                    $scope.id.value = response.data._id;

                }

            } else {

                $scope.response.class = "error";
                $scope.response.message = response.response;
                $scope.response.view = true;

            }

            $scope.loading.view = false;

            $scope.hideResponse();

        });

    }


}]);


app.provider("blog", function () {


    this.$get = ["$http", function ($http) {


        var blog = {};


        blog.rest = function (rest, callback) {

            $http({
                "data": rest.data,
                "headers": {"Content-Type": "application/json"},
                "method": "POST",
                "url": rest.url
            }).then(function (response) {

                callback(response.data);

            });

        }


        blog.restMultipart = function (rest, callback) {

            $http({
                "data": rest.data,
                "headers": {"Content-Type": undefined, "Process-Data": false},
                "method": "POST",
                "transformRequest": angular.identity,
                "url": rest.url
            }).then(function (response) {

                callback(response.data);

            });

        }


        return blog;


    }];


});
