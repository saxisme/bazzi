(function(b, f) {
    var e = b.UIkit || {};
    if (!e.fn) {
        e.fn = function(a, d) {
            var g = arguments,
                c = a.match(/^([a-z\-]+)(?:\.([a-z]+))?/i),
                f = c[1],
                m = c[2];
            return !e[f] ? (b.error("UIkit component [" + f + "] does not exist."), this) : this.each(function() {
                var a = b(this),
                    c = a.data(f);
                c || a.data(f, c = new e[f](this, m ? void 0 : d));
                m && c[m].apply(c, Array.prototype.slice.call(g, 1))
            })
        };
        e.support = {};
        var c = e.support,
            a;
        a: {
            a = f.body || f.documentElement;
            var g = {
                    WebkitTransition: "webkitTransitionEnd",
                    MozTransition: "transitionend",
                    OTransition: "oTransitionEnd otransitionend",
                    transition: "transitionend"
                },
                d;
            for (d in g)
                if (void 0 !== a.style[d]) {
                    a = g[d];
                    break a
                }
            a = void 0
        }
        c.transition = a && {
            end: a
        };
        e.support.touch = "ontouchstart" in window || window.DocumentTouch && document instanceof window.DocumentTouch;
        e.Utils = {};
        e.Utils.debounce = function(a, b, d) {
            var g;
            return function() {
                var c = this,
                    e = arguments,
                    f = d && !g;
                clearTimeout(g);
                g = setTimeout(function() {
                    g = null;
                    d || a.apply(c, e)
                }, b);
                f && a.apply(c, e)
            }
        };
        e.Utils.options = function(a) {
            if (b.isPlainObject(a)) return a;
            var d = a.indexOf("{"),
                g = {};
            if (-1 != d) try {
                g = (new Function("", "var json = " + a.substr(d) + "; return JSON.parse(JSON.stringify(json));"))()
            } catch (c) {}
            return g
        };
        b.UIkit = e;
        b.fn.uk = e.fn;
        b.UIkit.langdirection = "rtl" == b("html").attr("dir") ? "right" : "left"
    }
})(jQuery, document);
(function(b) {
    function f(a, b, d, g) {
        var c = Math.abs(a - b),
            j = Math.abs(d - g);
        return c >= j ? 0 < a - b ? "Left" : "Right" : 0 < d - g ? "Up" : "Down"
    }

    function e() {
        h = null;
        a.last && (a.el.trigger("longTap"), a = {})
    }

    function c() {
        g && clearTimeout(g);
        d && clearTimeout(d);
        j && clearTimeout(j);
        h && clearTimeout(h);
        g = d = j = h = null;
        a = {}
    }
    var a = {},
        g, d, j, h;
    b(document).ready(function() {
        var k, l;
        b(document.body).bind("touchstart", function(d) {
            k = Date.now();
            l = k - (a.last || k);
            a.el = b("tagName" in d.originalEvent.touches[0].target ? d.originalEvent.touches[0].target : d.originalEvent.touches[0].target.parentNode);
            g && clearTimeout(g);
            a.x1 = d.originalEvent.touches[0].pageX;
            a.y1 = d.originalEvent.touches[0].pageY;
            0 < l && 250 >= l && (a.isDoubleTap = !0);
            a.last = k;
            h = setTimeout(e, 750)
        }).bind("touchmove", function(b) {
            h && clearTimeout(h);
            h = null;
            a.x2 = b.originalEvent.touches[0].pageX;
            a.y2 = b.originalEvent.touches[0].pageY
        }).bind("touchend", function() {
            h && clearTimeout(h);
            h = null;
            a.x2 && 30 < Math.abs(a.x1 - a.x2) || a.y2 && 30 < Math.abs(a.y1 - a.y2) ? j = setTimeout(function() {
                a.el.trigger("swipe");
                a.el.trigger("swipe" +
                    f(a.x1, a.x2, a.y1, a.y2));
                a = {}
            }, 0) : "last" in a && (d = setTimeout(function() {
                var d = b.Event("tap");
                d.cancelTouch = c;
                a.el.trigger(d);
                a.isDoubleTap ? (a.el.trigger("doubleTap"), a = {}) : g = setTimeout(function() {
                    g = null;
                    a.el.trigger("singleTap");
                    a = {}
                }, 250)
            }, 0))
        }).bind("touchcancel", c);
        b(window).bind("scroll", c)
    });
    "swipe swipeLeft swipeRight swipeUp swipeDown doubleTap tap singleTap longTap".split(" ").forEach(function(a) {
        b.fn[a] = function(b) {
            return this.bind(a, b)
        }
    })
})(jQuery);
(function(b, f) {
    var e = function(c, a) {
        var g = this;
        this.options = b.extend({}, this.options, a);
        this.element = b(c).on("click", this.options.trigger, function(a) {
            a.preventDefault();
            g.close()
        })
    };
    b.extend(e.prototype, {
        options: {
            fade: !0,
            duration: 200,
            trigger: ".uk-alert-close"
        },
        close: function() {
            function b() {
                a.trigger("closed").remove()
            }
            var a = this.element.trigger("close");
            this.options.fade ? a.css("overflow", "hidden").css("max-height", a.height()).animate({
                height: 0,
                opacity: 0,
                "padding-top": 0,
                "padding-bottom": 0,
                "margin-top": 0,
                "margin-bottom": 0
            }, this.options.duration, b) : b()
        }
    });
    f.alert = e;
    b(document).on("click.alert.uikit", "[data-uk-alert]", function(c) {
        var a = b(this);
        a.data("alert") || (a.data("alert", new e(a, f.Utils.options(a.data("uk-alert")))), b(c.target).is(a.data("alert").options.trigger) && (c.preventDefault(), a.data("alert").close()))
    })
})(jQuery, jQuery.UIkit);
(function(b, f) {
    var e = function(a, d) {
        var c = this,
            e = b(a);
        this.options = b.extend({}, this.options, d);
        this.element = e.on("click", this.options.target, function(a) {
            a.preventDefault();
            e.find(c.options.target).not(this).removeClass("uk-active").blur();
            e.trigger("change", [b(this).addClass("uk-active")])
        })
    };
    b.extend(e.prototype, {
        options: {
            target: ".uk-button"
        },
        getSelected: function() {
            this.element.find(".uk-active")
        }
    });
    var c = function(a, d) {
        var c = b(a);
        this.options = b.extend({}, this.options, d);
        this.element = c.on("click", this.options.target, function(a) {
            a.preventDefault();
            c.trigger("change", [b(this).toggleClass("uk-active").blur()])
        })
    };
    b.extend(c.prototype, {
        options: {
            target: ".uk-button"
        },
        getSelected: function() {
            this.element.find(".uk-active")
        }
    });
    var a = function(a) {
        var d = this;
        this.element = b(a).on("click", function(a) {
            a.preventDefault();
            d.toggle();
            d.element.blur()
        })
    };
    b.extend(a.prototype, {
        toggle: function() {
            this.element.toggleClass("uk-active")
        }
    });
    f.button = a;
    f["button-checkbox"] = c;
    f["button-radio"] = e;
    b(document).on("click.button-radio.uikit", "[data-uk-button-radio]", function(a) {
        var d = b(this);
        d.data("button-radio") || (d.data("button-radio", new e(d, f.Utils.options(d.data("uk-button-radio")))), b(a.target).is(d.data("button-radio").options.target) && b(a.target).trigger("click"))
    });
    b(document).on("click.button-checkbox.uikit", "[data-uk-button-checkbox]", function(a) {
        var d = b(this);
        d.data("button-checkbox") || (d.data("button-checkbox", new c(d, f.Utils.options(d.data("uk-button-checkbox")))), b(a.target).is(d.data("button-checkbox").options.target) && b(a.target).trigger("click"))
    });
    b(document).on("click.button.uikit", "[data-uk-button]", function() {
        var c = b(this);
        c.data("button") || c.data("button", new a(c, c.data("uk-button"))).trigger("click")
    })
})(jQuery, jQuery.UIkit);
(function(b, f) {
    var e = !1,
        c = function(a, c) {
            var d = this;
            this.options = b.extend({}, this.options, c);
            this.element = b(a);
            this.dropdown = this.element.find(".uk-dropdown");
            this.centered = this.dropdown.hasClass("uk-dropdown-center");
            this.justified = this.options.justify ? b(this.options.justify) : !1;
            this.boundary = b(this.options.boundary);
            this.boundary.length || (this.boundary = b(window));
            if ("click" == this.options.mode) this.element.on("click", function(a) {
                b(a.target).parents(".uk-dropdown").length || a.preventDefault();
                e && e[0] != d.element[0] && e.removeClass("uk-open");
                if (d.element.hasClass("uk-open")) {
                    if (b(a.target).is("a") || !d.element.find(".uk-dropdown").find(a.target).length) d.element.removeClass("uk-open"), e = !1
                } else d.checkDimensions(), d.element.addClass("uk-open"), e = d.element, b(document).off("click.outer.dropdown"), setTimeout(function() {
                    b(document).on("click.outer.dropdown", function(a) {
                        if (e && e[0] == d.element[0] && (b(a.target).is("a") || !d.element.find(".uk-dropdown").find(a.target).length)) e.removeClass("uk-open"), b(document).off("click.outer.dropdown")
                    })
                }, 10)
            });
            else this.element.on("mouseenter", function() {
                d.remainIdle && clearTimeout(d.remainIdle);
                e && e[0] != d.element[0] && e.removeClass("uk-open");
                d.checkDimensions();
                d.element.addClass("uk-open");
                e = d.element
            }).on("mouseleave", function() {
                d.remainIdle = setTimeout(function() {
                    d.element.removeClass("uk-open");
                    d.remainIdle = !1;
                    e && e[0] == d.element[0] && (e = !1)
                }, d.options.remaintime)
            })
        };
    b.extend(c.prototype, {
        remainIdle: !1,
        options: {
            mode: "hover",
            remaintime: 800,
            justify: !1,
            boundary: b(window)
        },
        checkDimensions: function() {
            if (this.dropdown.length) {
                var a = this.dropdown.css("margin-" + b.UIkit.langdirection, "").css("min-width", ""),
                    c = a.show().offset(),
                    d = a.outerWidth(),
                    e = this.boundary.width(),
                    h = this.boundary.offset() ? this.boundary.offset().left : 0;
                if (this.centered && (a.css("margin-" + b.UIkit.langdirection, -1 * (parseFloat(d) / 2 - a.parent().width() / 2)), c = a.offset(), d + c.left > e || 0 > c.left)) a.css("margin-" + b.UIkit.langdirection, ""), c = a.offset();
                if (this.justified && this.justified.length) {
                    var f = this.justified.outerWidth();
                    a.css("min-width", f);
                    "right" == b.UIkit.langdirection ? (c = e - (this.justified.offset().left + f), f = e - (a.offset().left + a.outerWidth()), a.css("margin-right", c - f)) : a.css("margin-left", this.justified.offset().left - c.left);
                    c = a.offset()
                }
                d + (c.left - h) > e && (a.addClass("uk-dropdown-flip"), c = a.offset());
                0 > c.left && a.addClass("uk-dropdown-stack");
                a.css("display", "")
            }
        }
    });
    f.dropdown = c;
    b(document).on("mouseenter.dropdown.uikit", "[data-uk-dropdown]", function() {
        var a = b(this);
        a.data("dropdown") || (a.data("dropdown", new c(a, f.Utils.options(a.data("uk-dropdown")))), "hover" == a.data("dropdown").options.mode && a.trigger("mouseenter"))
    })
})(jQuery, jQuery.UIkit);
(function(b, f) {
    var e = b(window),
        c = function(a, d) {
            var c = this;
            this.options = b.extend({}, this.options, d);
            this.element = b(a);
            this.columns = this.element.children();
            this.elements = this.options.target ? this.element.find(this.options.target) : this.columns;
            if (this.columns.length) e.on("resize orientationchange", function() {
                var a = function() {
                    c.match()
                };
                b(function() {
                    a();
                    e.on("load", a)
                });
                return f.Utils.debounce(a, 150)
            }())
        };
    b.extend(c.prototype, {
        options: {
            target: !1
        },
        match: function() {
            this.revert();
            var a = this.columns.filter(":visible:first");
            if (a.length) {
                var d = 0,
                    c = this;
                if (!(100 <= Math.ceil(100 * parseFloat(a.css("width")) / parseFloat(a.parent().css("width"))))) return this.elements.each(function() {
                    d = Math.max(d, b(this).outerHeight())
                }).each(function(a) {
                    var g = b(this),
                        e = "border-box" == g.css("box-sizing") ? "outerHeight" : "height";
                    a = c.columns.eq(a);
                    e = g.height() + (d - a[e]());
                    g.css("min-height", e + "px")
                }), this
            }
        },
        revert: function() {
            this.elements.css("min-height", "");
            return this
        }
    });
    var a = function(a) {
        var d = this;
        this.element = b(a);
        this.columns = this.element.children();
        if (this.columns.length) e.on("resize orientationchange", function() {
            var a = function() {
                d.process()
            };
            b(function() {
                a();
                e.on("load", a)
            });
            return f.Utils.debounce(a, 150)
        }())
    };
    b.extend(a.prototype, {
        process: function() {
            this.revert();
            var a = !1,
                d = this.columns.filter(":visible:first"),
                c = d.length ? d.offset().top : !1;
            if (!1 !== c) return this.columns.each(function() {
                var d = b(this);
                d.is(":visible") && (a ? d.addClass("uk-grid-margin") : d.offset().top != c && (d.addClass("uk-grid-margin"), a = !0))
            }), this
        },
        revert: function() {
            this.columns.removeClass("uk-grid-margin");
            return this
        }
    });
    f["grid-match"] = c;
    f["grid-margin"] = a;
    b(function() {
        b("[data-uk-grid-match],[data-uk-grid-margin]").each(function() {
            var g = b(this);
            g.is("[data-uk-grid-match]") && !g.data("grid-match") && g.data("grid-match", new c(g, f.Utils.options(g.data("uk-grid-match"))));
            g.is("[data-uk-grid-margin]") && !g.data("grid-margin") && g.data("grid-margin", new a(g, f.Utils.options(g.data("uk-grid-margin"))))
        })
    })
})(jQuery, jQuery.UIkit);
(function(b, f, e) {
    var c = !1,
        a = b("html"),
        g = function(a, d) {
            var g = this;
            this.element = b(a);
            this.options = b.extend({
                keyboard: !0,
                show: !1,
                bgclose: !0
            }, d);
            this.transition = f.support.transition;
            this.dialog = this.element.find(".uk-modal-dialog");
            this.element.on("click", ".uk-modal-close", function(a) {
                a.preventDefault();
                g.hide()
            }).on("click", function(a) {
                b(a.target)[0] == g.element[0] && g.options.bgclose && g.hide()
            });
            if (this.options.keyboard) b(document).on("keyup.ui.modal.escape", function(a) {
                c && (27 == a.which && g.isActive()) && g.hide()
            })
        };
    b.extend(g.prototype, {
        transition: !1,
        toggle: function() {
            this[this.isActive() ? "hide" : "show"]()
        },
        show: function() {
            this.isActive() || (c && c.hide(!0), this.resize(), this.element.removeClass("uk-open").show(), c = this, a.addClass("uk-modal-page").height(), this.element.addClass("uk-open").trigger("uk.modal.show"))
        },
        hide: function(a) {
            if (this.isActive())
                if (!a && f.support.transition) {
                    var b = this;
                    this.element.one(f.support.transition.end, function() {
                        b._hide()
                    }).removeClass("uk-open")
                } else this._hide()
        },
        resize: function() {
            this.dialog.css("margin-left", "");
            var a = parseInt(this.dialog.css("width"), 10),
                b = a + parseInt(this.dialog.css("margin-left"), 10) + parseInt(this.dialog.css("margin-right"), 10) < e.width();
            this.dialog.css("margin-left", a && b ? -1 * Math.ceil(a / 2) : "")
        },
        _hide: function() {
            this.element.hide().removeClass("uk-open");
            a.removeClass("uk-modal-page");
            c === this && (c = !1);
            this.element.trigger("uk.modal.hide")
        },
        isActive: function() {
            return c == this
        }
    });
    var d = function(a, d) {
        var c = this,
            e = b(a);
        this.options = b.extend({
            target: e.is("a") ? e.attr("href") : !1
        }, d);
        this.element = e;
        this.modal = new g(this.options.target, d);
        e.on("click", function(a) {
            a.preventDefault();
            c.show()
        });
        b.each(["show", "hide", "isActive"], function(a, b) {
            c[b] = function() {
                return c.modal[b]()
            }
        })
    };
    d.Modal = g;
    f.modal = d;
    b(document).on("click.modal.uikit", "[data-uk-modal]", function() {
        var a = b(this);
        a.data("modal") || (a.data("modal", new d(a, f.Utils.options(a.data("uk-modal")))), a.data("modal").show())
    });
    e.on("resize orientationchange", f.Utils.debounce(function() {
        c && c.resize()
    }, 150))
})(jQuery, jQuery.UIkit, jQuery(window));
(function(b, f) {
    var e, c;
    f.support.touch && b("html").addClass("uk-touch");
    var a = b(window),
        g = b(document),
        d = {
            show: function(f) {
                f = b(f);
                if (f.length) {
                    var j = b("html"),
                        l = f.find(".uk-offcanvas-bar:first"),
                        n = l.hasClass("uk-offcanvas-bar-flip") ? -1 : 1,
                        m = -1 == n && a.width() < window.innerWidth ? window.innerWidth - a.width() : 0;
                    e = window.scrollX;
                    c = window.scrollY;
                    f.addClass("uk-active");
                    j.css({
                        width: window.innerWidth,
                        height: window.innerHeight
                    }).addClass("uk-offcanvas-page");
                    j.css("margin-left", (l.outerWidth() - m) * n).width();
                    l.addClass("uk-offcanvas-bar-show").width();
                    f.off(".ukoffcanvas").on("click.ukoffcanvas swipeRight.ukoffcanvas swipeLeft.ukoffcanvas", function(a) {
                        var c = b(a.target);
                        if (a.type.match(/swipe/) || !c.hasClass("uk-offcanvas-bar") && !c.parents(".uk-offcanvas-bar:first").length) a.stopImmediatePropagation(), d.hide()
                    });
                    g.on("keydown.offcanvas", function(a) {
                        27 === a.keyCode && d.hide()
                    })
                }
            },
            hide: function(a) {
                var d = b("html"),
                    f = b(".uk-offcanvas.uk-active"),
                    j = f.find(".uk-offcanvas-bar:first");
                f.length && (b.UIkit.support.transition && !a ? (d.one(b.UIkit.support.transition.end, function() {
                    d.removeClass("uk-offcanvas-page").attr("style", "");
                    f.removeClass("uk-active");
                    window.scrollTo(e, c)
                }).css("margin-left", ""), setTimeout(function() {
                    j.removeClass("uk-offcanvas-bar-show")
                }, 50)) : (d.removeClass("uk-offcanvas-page").attr("style", ""), f.removeClass("uk-active"), j.removeClass("uk-offcanvas-bar-show"), window.scrollTo(e, c)), f.off(".ukoffcanvas"), g.off(".ukoffcanvas"))
            }
        },
        j = function(a, c) {
            var g = this,
                e = b(a);
            this.options = b.extend({
                target: e.is("a") ? e.attr("href") : !1
            }, c);
            this.element = e;
            e.on("click", function(a) {
                a.preventDefault();
                d.show(g.options.target)
            })
        };
    j.offcanvas = d;
    f.offcanvas = j;
    g.on("click.offcanvas.uikit", "[data-uk-offcanvas]", function(a) {
        a.preventDefault();
        a = b(this);
        a.data("offcanvas") || (a.data("offcanvas", new j(a, f.Utils.options(a.data("uk-offcanvas")))), a.trigger("click"))
    })
})(jQuery, jQuery.UIkit);
(function(b, f) {
    function e(a) {
        a = b(a);
        var c = "auto";
        if (a.is(":visible")) c = a.outerHeight();
        else {
            var d = {
                    position: a.css("position"),
                    visibility: a.css("visibility"),
                    display: a.css("display")
                },
                c = a.css({
                    position: "absolute",
                    visibility: "hidden",
                    display: "block"
                }).outerHeight();
            a.css(d)
        }
        return c
    }
    var c = function(a, c) {
        var d = this;
        this.options = b.extend({}, this.options, c);
        this.element = b(a).on("click", this.options.toggler, function(a) {
            a.preventDefault();
            a = b(this);
            d.open(a.parent()[0] == d.element[0] ? a : a.parent("li"))
        });
        this.element.find(this.options.lists).each(function() {
            var a = b(this),
                c = a.parent(),
                g = c.hasClass("uk-active");
            a.wrap('<div style="overflow:hidden;height:0;position:relative;"></div>');
            c.data("list-container", a.parent());
            g && d.open(c, !0)
        })
    };
    b.extend(c.prototype, {
        options: {
            toggler: ">li.uk-parent > a[href='#']",
            lists: ">li.uk-parent > ul",
            multiple: !1
        },
        open: function(a, c) {
            var d = this.element,
                f = b(a);
            this.options.multiple || d.children(".uk-open").not(a).each(function() {
                b(this).data("list-container") && b(this).data("list-container").stop().animate({
                    height: 0
                }, function() {
                    b(this).parent().removeClass("uk-open")
                })
            });
            f.toggleClass("uk-open");
            f.data("list-container") && (c ? f.data("list-container").stop().height(f.hasClass("uk-open") ? "auto" : 0) : f.data("list-container").stop().animate({
                height: f.hasClass("uk-open") ? e(f.data("list-container").find("ul:first")) : 0
            }))
        }
    });
    f.nav = c;
    b(function() {
        b("[data-uk-nav]").each(function() {
            var a = b(this);
            a.data("nav") || a.data("nav", new c(a, f.Utils.options(a.data("uk-nav"))))
        })
    })
})(jQuery, jQuery.UIkit);
(function(b, f) {
    var e, c = function(a, c) {
        var d = this;
        this.options = b.extend({}, this.options, c);
        this.element = b(a).on({
            focus: function() {
                d.show()
            },
            blur: function() {
                d.hide()
            },
            mouseenter: function() {
                d.show()
            },
            mouseleave: function() {
                d.hide()
            }
        });
        this.tip = "function" === typeof this.options.src ? this.options.src.call(this.element) : this.options.src;
        this.element.attr("data-cached-title", this.element.attr("title")).attr("title", "")
    };
    b.extend(c.prototype, {
        tip: "",
        options: {
            offset: 5,
            pos: "top",
            src: function() {
                return this.attr("title")
            }
        },
        show: function() {
            if (this.tip.length) {
                e.css({
                    top: -2E3,
                    visibility: "hidden"
                }).show();
                e.html('<div class="uk-tooltip-inner">' + this.tip + "</div>");
                var a = b.extend({}, this.element.offset(), {
                        width: this.element[0].offsetWidth,
                        height: this.element[0].offsetHeight
                    }),
                    c = e[0].offsetWidth,
                    d = e[0].offsetHeight,
                    f = "function" === typeof this.options.offset ? this.options.offset.call(this.element) : this.options.offset,
                    h = "function" === typeof this.options.pos ? this.options.pos.call(this.element) : this.options.pos,
                    k = {
                        display: "none",
                        visibility: "visible",
                        top: a.top + a.height + d,
                        left: a.left
                    },
                    l = h.split("-");
                if (("left" == l[0] || "right" == l[0]) && "right" == b.UIkit.langdirection) l[0] = "left" == l[0] ? "right" : "left";
                switch (l[0]) {
                    case "bottom":
                        b.extend(k, {
                            top: a.top + a.height + f,
                            left: a.left + a.width / 2 - c / 2
                        });
                        break;
                    case "top":
                        b.extend(k, {
                            top: a.top - d - f,
                            left: a.left + a.width / 2 - c / 2
                        });
                        break;
                    case "left":
                        b.extend(k, {
                            top: a.top + a.height / 2 - d / 2,
                            left: a.left - c - f
                        });
                        break;
                    case "right":
                        b.extend(k, {
                            top: a.top + a.height / 2 - d / 2,
                            left: a.left + a.width + f
                        })
                }
                2 == l.length && (k.left = "left" == l[1] ? a.left : a.left + a.width - c);
                e.css(k).attr("class", "uk-tooltip uk-tooltip-" + h).show()
            }
        },
        hide: function() {
            this.element.is("input") && this.element[0] === document.activeElement || e.hide()
        },
        content: function() {
            return this.tip
        }
    });
    f.tooltip = c;
    b(function() {
        e = b('<div class="uk-tooltip"></div>').appendTo("body")
    });
    b(document).on("mouseenter.tooltip.uikit focus.tooltip.uikit", "[data-uk-tooltip]", function() {
        var a = b(this);
        a.data("tooltip") || a.data("tooltip", new c(a, f.Utils.options(a.data("uk-tooltip")))).trigger("mouseenter")
    })
})(jQuery, jQuery.UIkit);
(function(b, f) {
    var e = function(c, a) {
        var e = this;
        this.options = b.extend({}, this.options, a);
        this.element = b(c).on("click", this.options.toggler, function(a) {
            a.preventDefault();
            e.show(this)
        });
        if (this.options.connect) {
            this.connect = b(this.options.connect).find(".uk-active").removeClass(".uk-active").end();
            var d = this.element.find(this.options.toggler).filter(".uk-active");
            d.length && this.show(d)
        }
    };
    b.extend(e.prototype, {
        options: {
            connect: !1,
            toggler: ">*"
        },
        show: function(c) {
            c = isNaN(c) ? b(c) : this.element.find(this.options.toggler).eq(c);
            if (!c.hasClass("uk-disabled")) {
                this.element.find(this.options.toggler).filter(".uk-active").removeClass("uk-active");
                c.addClass("uk-active");
                if (this.options.connect && this.connect.length) {
                    var a = this.element.find(this.options.toggler).index(c);
                    this.connect.children().removeClass("uk-active").eq(a).addClass("uk-active")
                }
                this.element.trigger("uk.switcher.show", [c])
            }
        }
    });
    f.switcher = e;
    b(function() {
        b("[data-uk-switcher]").each(function() {
            var c = b(this);
            c.data("switcher") || c.data("switcher", new e(c, f.Utils.options(c.data("uk-switcher"))))
        })
    })
})(jQuery, jQuery.UIkit);
(function(b, f) {
    var e = function(c, a) {
        var e = this;
        this.element = b(c);
        this.options = b.extend({
            connect: !1
        }, this.options, a);
        this.options.connect && (this.connect = b(this.options.connect));
        if (window.location.hash) {
            var d = this.element.children().filter(window.location.hash);
            d.length && this.element.children().removeClass("uk-active").filter(d).addClass("uk-active")
        }
        var f = b('<li class="uk-tab-responsive uk-active"><a href="javascript:void(0);"></a></li>'),
            h = f.find("a:first"),
            d = b('<div class="uk-dropdown uk-dropdown-small"><ul class="uk-nav uk-nav-dropdown"></ul><div>'),
            k = d.find("ul");
        h.html(this.element.find("li.uk-active:first").find("a").text());
        this.element.hasClass("uk-tab-bottom") && d.addClass("uk-dropdown-up");
        this.element.hasClass("uk-tab-flip") && d.addClass("uk-dropdown-flip");
        this.element.find("a").each(function(a) {
            var c = b(this).parent(),
                c = b('<li><a href="javascript:void(0);">' + c.text() + "</a></li>").on("click", function() {
                    e.element.data("switcher").show(a)
                });
            b(this).parents(".uk-disabled:first").length || k.append(c)
        });
        this.element.uk("switcher", {
            toggler: ">li:not(.uk-tab-responsive)",
            connect: this.options.connect
        });
        f.append(d).uk("dropdown", {
            mode: "click"
        });
        this.element.append(f).data({
            dropdown: f.data("dropdown"),
            mobilecaption: h
        }).on("uk.switcher.show", function(a, b) {
            f.addClass("uk-active");
            h.html(b.find("a").text())
        })
    };
    f.tab = e;
    b(function() {
        b("[data-uk-tab]").each(function() {
            var c = b(this);
            c.data("tab") || c.data("tab", new e(c, f.Utils.options(c.data("uk-tab"))))
        })
    })
})(jQuery, jQuery.UIkit);
(function(b, f) {
    var e = function(c, a) {
        var e = this;
        this.options = b.extend({}, this.options, a);
        this.element = b(c);
        this.value = this.timer = null;
        this.input = this.element.find(".uk-search-field");
        this.form = this.input.length ? b(this.input.get(0).form) : b();
        this.input.attr("autocomplete", "off");
        this.input.on({
            keydown: function(a) {
                e.form[e.input.val() ? "addClass" : "removeClass"](e.options.filledClass);
                if (a && a.which && !a.shiftKey) switch (a.which) {
                    case 13:
                        e.done(e.selected);
                        a.preventDefault();
                        break;
                    case 38:
                        e.pick("prev");
                        a.preventDefault();
                        break;
                    case 40:
                        e.pick("next");
                        a.preventDefault();
                        break;
                    case 27:
                    case 9:
                        e.hide()
                }
            },
            keyup: function() {
                e.trigger()
            },
            blur: function(a) {
                setTimeout(function() {
                    e.hide(a)
                }, 200)
            }
        });
        this.form.find("button[type=reset]").bind("click", function() {
            e.form.removeClass("uk-open").removeClass("uk-loading").removeClass("uk-active");
            e.value = null;
            e.input.focus()
        });
        this.dropdown = b('<div class="uk-dropdown uk-dropdown-search"><ul class="uk-nav uk-nav-search"></ul></div>').appendTo(this.form).find(".uk-nav-search");
        this.options.flipDropdown && this.dropdown.parent().addClass("uk-dropdown-flip")
    };
    b.extend(e.prototype, {
        options: {
            source: !1,
            param: "search",
            method: "post",
            minLength: 3,
            delay: 300,
            flipDropdown: !1,
            match: ":not(.uk-skip)",
            skipClass: "uk-skip",
            loadingClass: "uk-loading",
            filledClass: "uk-active",
            resultsHeaderClass: "uk-nav-header",
            moreResultsClass: "",
            noResultsClass: "",
            listClass: "results",
            hoverClass: "uk-active",
            msgResultsHeader: "Search Results",
            msgMoreResults: "More Results",
            msgNoResults: "No results found",
            onSelect: function(b) {
                window.location = b.data("choice").url
            },
            onLoadedResults: function(b) {
                return b
            }
        },
        request: function(c) {
            var a = this;
            this.form.addClass(this.options.loadingClass);
            this.options.source ? b.ajax(b.extend({
                url: this.options.source,
                type: this.options.method,
                dataType: "json",
                success: function(b) {
                    b = a.options.onLoadedResults.apply(this, [b]);
                    a.form.removeClass(a.options.loadingClass);
                    a.suggest(b)
                }
            }, c)) : this.form.removeClass(a.options.loadingClass)
        },
        pick: function(b) {
            var a = !1;
            "string" !== typeof b && !b.hasClass(this.options.skipClass) && (a = b);
            if ("next" == b || "prev" == b)
                if (a = this.dropdown.children().filter(this.options.match), this.selected) var e = a.index(this.selected),
                    a = "next" == b ? a.eq(e + 1 < a.length ? e + 1 : 0) : a.eq(0 > e - 1 ? a.length - 1 : e - 1);
                else a = a["next" == b ? "first" : "last"]();
            a && a.length && (this.selected = a, this.dropdown.children().removeClass(this.options.hoverClass), this.selected.addClass(this.options.hoverClass))
        },
        done: function(b) {
            b ? (b.hasClass(this.options.moreResultsClass) ? this.form.submit() : b.data("choice") && this.options.onSelect.apply(this, [b]), this.hide()) : this.form.submit()
        },
        trigger: function() {
            var b = this,
                a = this.value,
                e = {};
            this.value = this.input.val();
            if (this.value.length < this.options.minLength) return this.hide();
            this.value != a && (this.timer && window.clearTimeout(this.timer), this.timer = window.setTimeout(function() {
                e[b.options.param] = b.value;
                b.request({
                    data: e
                })
            }, this.options.delay, this));
            return this
        },
        suggest: function(c) {
            if (c) {
                var a = this,
                    e = {
                        mouseover: function() {
                            a.pick(b(this).parent())
                        },
                        click: function(c) {
                            c.preventDefault();
                            a.done(b(this).parent())
                        }
                    };
                !1 === c ? this.hide() : (this.selected = null, this.dropdown.empty(), this.options.msgResultsHeader && b("<li>").addClass(this.options.resultsHeaderClass + " " + this.options.skipClass).html(this.options.msgResultsHeader).appendTo(this.dropdown), c.results && 0 < c.results.length ? (b(c.results).each(function() {
                    var c = b('<li><a href="#">' + this.title + "</a></li>").data("choice", this);
                    this.text && c.find("a").append("<div>" + this.text + "</div>");
                    a.dropdown.append(c)
                }), this.options.msgMoreResults && (b("<li>").addClass("uk-nav-divider " +
                    a.options.skipClass).appendTo(a.dropdown), b("<li>").addClass(a.options.moreResultsClass).html('<a href="#">' + a.options.msgMoreResults + "</a>").appendTo(a.dropdown).on(e)), a.dropdown.find("li>a").on(e)) : this.options.msgNoResults && b("<li>").addClass(this.options.noResultsClass + " " + this.options.skipClass).html("<a>" + this.options.msgNoResults + "</a>").appendTo(this.dropdown), this.show())
            }
        },
        show: function() {
            this.visible || (this.visible = !0, this.form.addClass("uk-open"))
        },
        hide: function() {
            this.visible && (this.visible = !1, this.form.removeClass(this.options.loadingClass).removeClass("uk-open"))
        }
    });
    f.search = e;
    b(document).on("focus.search.uikit", "[data-uk-search]", function() {
        var c = b(this);
        c.data("search") || c.data("search", new e(c, f.Utils.options(c.data("uk-search"))))
    })
})(jQuery, jQuery.UIkit);
(function(b, f) {
    var e = b(window),
        c = function(a, c) {
            this.options = b.extend({}, this.options, c);
            var d = this,
                j, h, k = function() {
                    var a;
                    a = d.element;
                    var b = d.options;
                    if (a.is(":visible")) {
                        var c = e.scrollLeft(),
                            f = e.scrollTop(),
                            g = d.offset || a.offset(),
                            k = g.left,
                            g = g.top;
                        a = g + a.height() >= f && g - b.topoffset <= f + e.height() && k + a.width() >= c && k - b.leftoffset <= c + e.width() ? !0 : !1
                    } else a = !1;
                    a && !j && (h || (d.element.addClass(d.options.initcls), d.offset = d.element.offset(), h = !0, d.element.trigger("uk-scrollspy-init")), d.element.addClass("uk-scrollspy-inview").addClass(d.options.cls).width(), j = !0, d.element.trigger("uk.scrollspy.inview"));
                    !a && (j && d.options.repeat) && (d.element.removeClass("uk-scrollspy-inview").removeClass(d.options.cls), j = !1, d.element.trigger("uk.scrollspy.outview"))
                };
            this.element = b(a);
            e.on("scroll", k).on("resize orientationchange", f.Utils.debounce(k, 50));
            k()
        };
    b.extend(c.prototype, {
        options: {
            cls: "uk-scrollspy-inview",
            initcls: "uk-scrollspy-init-inview",
            topoffset: 0,
            leftoffset: 0,
            repeat: !1
        }
    });
    f.scrollspy = c;
    b(function() {
        b("[data-uk-scrollspy]").each(function() {
            var a = b(this);
            a.data("scrollspy") || a.data("scrollspy", new c(a, f.Utils.options(a.data("uk-scrollspy"))))
        })
    })
})(jQuery, jQuery.UIkit);
(function(b, f) {
    var e = function(c, a) {
        var e = this;
        this.options = b.extend({
            duration: 1E3,
            transition: "easeOutExpo"
        }, a);
        this.element = b(c).on("click", function() {
            var a = (b(this.hash).length ? b(this.hash) : b("body")).offset().top,
                c = b(document).height(),
                f = b(window).height();
            a + f > c && (a = a - f + 50);
            b("html,body").stop().animate({
                scrollTop: a
            }, e.options.duration, e.options.transition);
            return !1
        })
    };
    f["smooth-scroll"] = e;
    b.easing.easeOutExpo || (b.easing.easeOutExpo = function(b, a, e, d, f) {
        return a == f ? e + d : d * (-Math.pow(2, -10 * a / f) + 1) +
            e
    });
    b(document).on("click.smooth-scroll.uikit", "[data-uk-smooth-scroll]", function() {
        var c = b(this);
        c.data("smooth-scroll") || c.data("smooth-scroll", new e(c, f.Utils.options(c.data("uk-smooth-scroll")))).trigger("click")
    })
})(jQuery, jQuery.UIkit);
(function(d) {
    var b = {};
    d.fn.socialButtons = function(a) {
        a = d.extend({
            wrapper: '<div class="tm-socialbuttons uk-clearfix">'
        }, a);
        if (!a.twitter && !a.plusone && !a.facebook) return this;
        a.twitter && !b.twitter && (b.twitter = d.getScript("//platform.twitter.com/widgets.js"));
        a.plusone && !b.plusone && (b.plusone = d.getScript("//apis.google.com/js/plusone.js"));
        if (!window.FB && a.facebook && !b.facebook) {
            d("body").append('<div id="fb-root"></div>');
            var c, e = document.getElementsByTagName("script")[0];
            document.getElementById("facebook-jssdk") || (c = document.createElement("script"), c.id = "facebook-jssdk", c.src = "//connect.facebook.net/en_US/all.js#xfbml=1", e.parentNode.insertBefore(c, e));
            b.facebook = !0
        }
        return this.each(function() {
            var b = d(this).data("permalink"),
                c = d(a.wrapper).appendTo(this);
            a.twitter && c.append('<div><a href="http://twitter.com/share" class="twitter-share-button" data-url="' + b + '" data-count="none">Tweet</a></div>');
            a.plusone && c.append('<div><div class="g-plusone" data-size="medium" data-annotation="none" data-href="' + b + '"></div></div>');
            a.facebook && c.append('<div><div class="fb-like" data-href="' + b + '" data-send="false" data-layout="button_count" data-width="100" data-show-faces="false"></div></div>')
        })
    }
})(jQuery);

jQuery(function($) {
    var config = $('html').data('config') || {};
    $('article[data-permalink]').socialButtons(config);
    $(".parallax").parallax();
    $(window).on('resize', (function() {
        var fn = function() {
            $('.tm-slant-top + .tm-block > .tm-slant-block-top, .tm-slant-bottom > .tm-slant-block-bottom').each(function() {
                var elem = $(this),
                    slantWidth = elem.parent().outerWidth(),
                    slantHeight = slantWidth / 100 * 2.5,
                    css = {
                        'border-right-width': slantWidth,
                        'border-top-width': slantHeight,
                        'top': -slantHeight + 1
                    };
                if (elem.hasClass("tm-slant-block-bottom")) {
                    css.bottom = css.top;
                    css.top = "";
                }
                elem.css(css);
            });
        };
        fn();
        return fn;
    })());
});
(function(f) {
    f.fn.parallax = function(l) {
        l = f.extend({}, {
            ratio: 8,
            maxdiff: !1,
            start: 0,
            mode: "inview",
            childanimation: !0,
            childdir: 1,
            childratio: 3,
            childopacity: !0,
            sizeratio: 0.38,
            mintriggerwidth: !1
        }, l);
        return this.each(function() {
            var b = f(this).css("overflow", "hidden"),
                a = f.extend({}, l, b.data()),
                e = b.children().length ? b.children().eq(0) : b.children(),
                m = f(window).width(),
                n = "undefined" !== String(window.orientation),
                k;
            f(window).on("scroll", function() {
                var j = function() {
                    if (!n)
                        if (a.mintriggerwidth && m < parseInt(a.mintriggerwidth, 10)) b.css("background-position", ""), a.childanimation && e.length && e.css({
                            transform: "",
                            opacity: ""
                        });
                        else {
                            var d = f(window).scrollTop(),
                                c = b.offset().top,
                                j = f(window).height(),
                                g = !1,
                                h;
                            switch (a.mode) {
                                case "dock":
                                    g = c < d;
                                    h = a.maxdiff && d - c > a.maxdiff ? a.maxdiff : d - c;
                                    break;
                                default:
                                    g = j + d >= c, h = a.maxdiff && Math.abs(d - c) > a.maxdiff ? a.maxdiff : d - c
                            }
                            g ? (g = h / a.ratio, b.css({
                                "background-position": "50% " + (a.start + g) + "px"
                            }), a.childanimation && e.length && ("dock" == a.mode && c < d ? (h = a.maxdiff && d - c > a.maxdiff ? a.maxdiff : d - c, g = h / a.childratio, e.css({
                                transform: "translateY(" + Math.abs(g) * a.childdir + "px)",
                                opacity: a.childopacity ? 1 - Math.abs(h / k) : 1
                            })) : "inview" == a.mode ? (c = e.offset().top, j + d >= c && (h = a.maxdiff && d - c > a.maxdiff ? a.maxdiff : d - c, g = h / a.childratio, e.css({
                                transform: "translateY(" + Math.abs(g) * a.childdir + "px)",
                                opacity: a.childopacity ? 1 - Math.abs(h / k) : 1
                            }))) : e.css({
                                transform: "",
                                opacity: ""
                            }))) : (b.css("background-position", "100% " + a.start + "px"), a.childanimation && e.length && e.css("transform", "").css("opacity", ""))
                        }
                };
                j();
                f(window).on("load", j);
                return j
            }()).on("resize orientationchange", function() {
                var j = function() {
                    m = f(window).width();
                    a.sizeratio && ("false" !== a.sizeratio && "0" !== a.sizeratio) && (k = b.width() * a.sizeratio, b.css("height", k));
                    e.length && (k = b.height(), e = b.children().length ? b.children().eq(0) : b.children())
                };
                j();
                return j
            }())
        })
    }
})(jQuery);