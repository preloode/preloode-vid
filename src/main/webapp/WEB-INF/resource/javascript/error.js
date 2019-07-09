function initializePageNotFoundLayout() {

    var height = window.innerHeight - (document.getElementById("footer").offsetHeight + parseInt(window.getComputedStyle(document.getElementsByClassName("back")[0]).height));
    var pictureHeight = parseInt(window.getComputedStyle(document.getElementsByClassName("picture")[0]).height);

    if (pictureHeight < height) {

        var padding = (height - pictureHeight) / 2;
        document.getElementsByClassName("picture")[0].style.paddingTop = padding + "px";
        document.getElementsByClassName("back")[0].style.paddingBottom = padding + "px";

    } else {

        document.getElementsByClassName("picture")[0].removeAttribute("style");
        document.getElementsByClassName("back")[0].removeAttribute("style");

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
