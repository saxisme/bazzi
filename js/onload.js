jQuery(document).ready(function($) {
	
    //position the slant div before and after - where is needed depending on .slant class
    $('.slant').prepend('<div class="tm-slant-block-bottom widget-slant"></div>');
    $('.slant').append('<div class="tm-slant-block-top widget-slant"></div>');

});