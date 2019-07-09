function initializeForgetPasswordLayout() {

    var padding = parseInt(window.getComputedStyle(document.getElementsByClassName("forget-password")[0], null).getPropertyValue("padding-top")) * 2;
    var margin = (window.innerHeight - (document.getElementById("header").offsetHeight + document.getElementsByClassName("forget-password")[0].offsetHeight + padding + document.getElementById("footer").offsetHeight)) / 2;
    document.getElementsByClassName("forget-password")[0].style.marginTop = margin + "px";
    document.getElementsByClassName("forget-password")[0].style.marginBottom = margin + "px";

}


document.addEventListener("DOMContentLoaded", function (event) {


    initializeForgetPasswordLayout()


});


window.addEventListener("resize", function (event) {


    initializeForgetPasswordLayout();


});
