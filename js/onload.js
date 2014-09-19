jQuery(document).ready(function() {
	
    //position the slant div before and after - where is needed depending on .slant class
    jQuery('.slant').prepend('<div class="tm-slant-block-bottom widget-slant"></div>');
    jQuery('.slant').append('<div class="tm-slant-block-top  widget-slant"></div>');

})();