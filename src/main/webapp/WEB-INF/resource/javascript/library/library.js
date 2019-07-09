class class_accordion {


    constructor() {

        this.setting = {
            "defaultAccordion": 0,
            "openOnlyOneAtATime": true
        };

    }


    initialize(element, setting) {

        this.setSetting(setting);
        var setting = this.getSetting();

        var accordion = Array.prototype.slice.call(document.getElementsByClassName(element));

        accordion.forEach(function (value, key) {

            var navigation = Array.prototype.slice.call(value.getElementsByClassName("accordion-navigation"));
            var content = Array.prototype.slice.call(value.getElementsByClassName("accordion-content"));

            content.forEach(function (valueLevel1, keyLevel1) {

                if (keyLevel1 == setting.defaultAccordion) {

                    valueLevel1.classList.add("active");

                }

            });

            navigation.forEach(function (valueLevel1, keyLevel1) {

                if (keyLevel1 == setting.defaultAccordion) {

                    valueLevel1.classList.add("active");

                    var icon = valueLevel1.getElementsByTagName("i");

                    if (icon.length > 0) {

                        icon[0].classList.remove("plus-white");
                        icon[0].classList.add("minus-white");

                    }

                }

                valueLevel1.addEventListener("click", function (event) {

                    var index = valueLevel1.getAttribute("data-index");

                    navigation.forEach(function (valueLevel2, keyLevel2) {

                        icon = valueLevel2.getElementsByTagName("i");

                        if (valueLevel2.getAttribute("data-index") == index) {

                            if (valueLevel2.classList.value.includes("active")) {

                                valueLevel2.classList.remove("active");

                                if (icon.length > 0) {

                                    icon[0].classList.remove("minus-white");
                                    icon[0].classList.add("plus-white");

                                }

                            } else {

                                valueLevel2.classList.add("active");

                                if (icon.length > 0) {

                                    icon[0].classList.remove("plus-white");
                                    icon[0].classList.add("minus-white");

                                }

                            }

                        } else {

                            if (setting.openOnlyOneAtATime) {

                                valueLevel2.classList.remove("active");

                                if (icon.length > 0) {

                                    icon[0].classList.remove("minus-white");
                                    icon[0].classList.add("plus-white");

                                }

                            }

                        }

                    });

                    content.forEach(function (valueLevel2, keyLevel2) {

                        if (valueLevel2.getAttribute("data-index") == index) {

                            if (valueLevel2.classList.value.includes("active")) {

                                valueLevel2.classList.remove("active");

                            } else {

                                valueLevel2.classList.add("active");

                            }

                        } else {

                            if (setting.openOnlyOneAtATime) {

                                valueLevel2.classList.remove("active");

                            }

                        }

                    });

                    var layout = new class_layout();
                    layout.initializePopup();

                });

            });

        });

        var content = document.getElementById("content");

        if (content) {

            angular.element(document.getElementById("content")).scope().rebuild();

        }

    }


    getSetting() {

        return this.setting;

    }


    setSetting(setting) {

        if (setting.hasOwnProperty("defaultAccordion")) {

            this.setting.defaultAccordion = setting.defaultAccordion;

        }

        if (setting.hasOwnProperty("openOnlyOneAtATime")) {

            this.setting.openOnlyOneAtATime = setting.openOnlyOneAtATime;

        }

    }


}


class class_layout {


    initialize() {

        this.initializeMainMenu();

        this.initializeLoading();

        this.initializePopup();

        var pageNotFound = document.getElementsByClassName("page-not-found")[0];

        if (pageNotFound != undefined) {

            pageNotFound.onload = initializePageNotFoundLayout;

        }

        var menu = document.getElementById("menu");

        if (menu) {

            var menuIcon = document.getElementsByClassName("menu-icon");

            if (menuIcon.length > 0) {

                var height = window.innerHeight/* - document.getElementById("menu-icon").offsetHeight*/;
                menu.style.height = height + "px";

            }

        }

        menu = document.getElementsByClassName("menu");

        if (menu.length > 0) {

            var index = 0;

            for (var i = 0; i < menu.length; i++) {

                var anchor = menu[i].getElementsByTagName("a");

                if (anchor.length > 0) {

                    if (anchor[0].getAttribute("href") == window.location.pathname || anchor[0].getAttribute("href") + "/" == window.location.pathname) {

                        var span = menu[i].getElementsByTagName("span");

                        if (span.length > 0) {

                            span[0].style.width = "100%";

                        }

                        if (/child\-menu/.test(menu[i].getAttribute("class"))) {

                            index = menu[i].getAttribute("data-index");

                        }

                    }

                }

            }

            if (index > 0) {

                var childMenu = document.getElementsByClassName("child-menu");

                if (childMenu.length > 0) {

                    for (var i = 0; i < childMenu.length; i++) {

                        if (childMenu[i].getAttribute("data-index") == index) {

                            childMenu[i].style.display = "block";
                            childMenu[i].style.opacity = 1;

                            if (!/separator/.test(childMenu[i].getAttribute("class"))) {

                                childMenu[i].style.height = "30px";

                            } else {

                                childMenu[i].style.height = "3px";

                            }

                        }

                    }

                }

                var toggle = document.getElementsByClassName("menu-toggle");

                if (toggle.length > 0) {

                    for (var i = 0; i < toggle.length; i++) {

                        if (toggle[i].getAttribute("data-index") == index) {

                            toggle[i].getElementsByTagName("i")[0].style.transform = "rotate(90deg)";

                        }

                    }

                }

            }

        }

    }


    initializeLoading() {

        var loading = document.getElementById("loading");

        if (loading) {

            var position = (window.innerHeight - loading.getElementsByClassName("wrapper")[0].offsetHeight) / 2;
            loading.getElementsByClassName("wrapper")[0].style.marginTop = position + "px";

        }

    }


    initializeMainMenu() {

        var menu = document.getElementById("menu");

        if (menu) {

            var height = window.innerHeight - menu.getElementsByClassName("close")[0].offsetHeight - 20;
            menu.getElementsByClassName("main-menu")[0].style.height = height + "px";

        }

    }


    initializePageNotFoundLayout() {

        var height = window.innerHeight - (document.getElementById("footer").offsetHeight + parseInt(window.getComputedStyle(document.getElementsByClassName("back")[0]).height));
        var pictureHeight = parseInt(window.getComputedStyle(document.getElementsByClassName("page-not-found")[0]).height);

        if (pictureHeight < height) {

            var padding = (height - pictureHeight) / 2;
            document.getElementsByClassName("page-not-found")[0].style.paddingTop = padding + "px";
            document.getElementsByClassName("back")[0].style.paddingBottom = padding + "px";

        } else {

            document.getElementsByClassName("page-not-found")[0].removeAttribute("style");
            document.getElementsByClassName("back")[0].removeAttribute("style");

        }

    }


    initializePopup() {

        var popup = document.getElementById("popup");

        if (popup) {

            var padding = parseInt(window.getComputedStyle(popup.getElementsByClassName("wrapper")[0], null).getPropertyValue("padding-top")) * 2;
            popup.getElementsByClassName("wrapper")[0].getElementsByClassName("popup")[0].style.maxHeight = (window.innerHeight - (40 + padding)) + "px";

            var margin = (window.innerHeight - popup.getElementsByClassName("wrapper")[0].offsetHeight) / 2;
            popup.getElementsByClassName("wrapper")[0].style.marginTop = margin + "px";

        }

    }


}


class class_library {


    constructor() {

        this.accordion = new class_accordion();

        this.layout = new class_layout();

        this.numeral = new class_numeral();

        this.rsaEncryption = new class_rsaEncryption();

    }


}


class class_numeral {


    initializeSeparator(number) {

        var result = "";

        number = number.replace(/[^0-9.]/g, "");

        number = number.split(".");
        number[0] = number[0].split("");

        var index = {
            "key": 0,
            "loop": number[0].length - 1
        };
        var separator = 3;

        for (var i = index.loop; i >= 0; i--) {

            if (index.key == separator) {

                result = "," + result;
                separator = separator + 3;

            }

            result = number[0][i] + result;

            index.key++;

        }

        if (number[1] != null) {

            result = result + '.' + number[1];

        }

        return result;

    }


}


class class_rsaEncryption {


    constructor() {

        this.privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAI2Ex6JriAbY1NFM7YyjboPQrXiBvGa7uAyZxUOCl1CjGNhXU73Q0k1Gjw66ox3vV2z+n9ZYclnfgpS4xDGv3qmXvnmLSQ3oJ3T/ovZUNtlINV7Tg2wLQhiyfxqEfHnhEM8DqfGgqSRMGuv+TIJpRpYAWcGPh5tlLoloGAzqK1H5AgMBAAECgYBuGhvndZnDGfeZTyyR+qUE0Nnlo6ock3fB9fDPcU6gYgP2bRqt10WG8ZpjbzlxFMED/6YDcZSl74r8gjPCJH4qc7tPthjxMkEwXldv2ce8ITnCw0k6EKXnf0cgNOvMMxFAvYFJS3hlhHUsu1CgbBqpV3/j96Z8dpYiJfISyY40wQJBAMFTZyozK4C/WvYjcnmb+SjsYqxMxQGf98EvWQc3LV5PTfvKTRAZGEpogs7QdHaStj+B/1hepYzIK0rCZDI1ggUCQQC7ZcXLiwNN3nVq9m++nHPUOY+ooSBpK0ac2USNQcI0KcbYlnIcrKPucZi4SMNvlZTVD3OxUEMzQ1vnsP5a485lAkEAkl0jgOaGrA2zvx/tHjbA94On3MyuZmGHAGJpY7YZigo+fz+VAngNVOA1EIDve4ntor035d6aNbCiXhI4K28pRQJAJwrfGLcPqyuL3wsU4OuLnk2XxpQ7Qm4HuinyBFQM9/00Nm+xupOlW8pC/TH7tcW+Sl17xxdnyMhh33WaqKkJmQJAQ7ed8jDpJoRznj7tErcSApQFeoqcm8suIaWuA/nd1xGDJbjmqHoIboU4CK0ehYOWswzSGpbFERIqXhHU1BV7gA==";

        this.publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCNhMeia4gG2NTRTO2Mo26D0K14gbxmu7gMmcVDgpdQoxjYV1O90NJNRo8OuqMd71ds/p/WWHJZ34KUuMQxr96pl755i0kN6Cd0/6L2VDbZSDVe04NsC0IYsn8ahHx54RDPA6nxoKkkTBrr/kyCaUaWAFnBj4ebZS6JaBgM6itR+QIDAQAB";

    }


    decrypt(string) {

        var jsEncrypt = new JSEncrypt();
        jsEncrypt.setPrivateKey(this.privateKey);

        return jsEncrypt.decrypt(string);

    }


    encrypt(string) {

        var jsEncrypt = new JSEncrypt();
        jsEncrypt.setPublicKey(this.publicKey);

        return jsEncrypt.encrypt(string);

    }


}
