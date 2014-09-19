jQuery(function($) {
    $(window).on('resize', (function() {
        var fn = function() {
            $('.tm-slant-block-top, .tm-slant-block-bottom').each(function() {
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
