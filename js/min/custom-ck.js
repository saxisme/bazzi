jQuery(function($) {

    $(window).on('resize', (function() {
        var fn = function() {
            $('.tm-slant-block-bottom').each(function() {
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

// jQuery(document).ready(function() {

    // jQuery(function ($) {

        // $(window).on('resize', function(){    
        //     var fn = function () {
        //         $('.tm-slant-block-bottom').each(function () {
        //             var elem = $(this),
        //                 //slantWidth = elem.parent().outerWidth(),
        //                 slantWidth = $(document).outerWidth(),
        //                 slantHeight = slantWidth / 100 * 2.5,
        //                 css = {
        //                     'border-right-width': slantWidth,
        //                     'border-top-width': slantHeight,
        //                     'top': -slantHeight + 1
        //                 };
        //             if (elem.hasClass("tm-slant-block-bottom")) {
        //                 css.bottom = css.top;
        //                 css.top = "";
        //             }
        //             elem.css(css);
        //         });
        //     };
        //     fn();
        //     return fn;
        // });

        // //toggle shortcode
        // $(document).ready(function(){
        //     $(".toggle_container").hide();
        //     $("h3.trigger").click(function(){
        //         $(this).toggleClass("active").next().slideToggle("normal");
        //         return false; //Prevent the browser jump to the link anchor
        //     });
        // });

    // });
// })();

//accordion shortcode
// jQuery(document).ready(function() {
//     jQuery("#accordion").accordion({
//         heightStyle: "content"
//     });
// })();