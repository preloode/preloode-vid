$(document).ready(function () {


    $(".slider").owlCarousel({
        "dots": false,
        "loop": true,
        "margin": 10,
        "nav": true,
        "responsiveClass": true,
        "responsive": {
            0: {
                "items": 1
            },
            480: {
                "items": 2
            },
            768: {
                "items": 3
            },
            1280: {
                "items": 5
            }
        }
    });


});
