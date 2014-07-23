<?php
//* Start the engine
include_once( get_template_directory() . '/lib/init.php' );

//* Child theme (do not remove)
define( 'CHILD_THEME_NAME', 'Genesis Sample Theme' );
define( 'CHILD_THEME_URL', 'http://www.studiopress.com/' );
define( 'CHILD_THEME_VERSION', '2.0.1' );

//* Enqueue Lato Google font
add_action( 'wp_enqueue_scripts', 'genesis_sample_google_fonts' );
function genesis_sample_google_fonts() {
	wp_enqueue_style( 'google-font-lato', '//fonts.googleapis.com/css?family=Lato:300,700', array(), CHILD_THEME_VERSION );
	wp_enqueue_style( 'font-google-raleway','http://fonts.googleapis.com/css?family=Raleway:400,700', '', 'false', 'all' );
}

//* Add HTML5 markup structure
add_theme_support( 'html5' );

//* Add viewport meta tag for mobile browsers
add_theme_support( 'genesis-responsive-viewport' );

//* Add support for custom background
add_theme_support( 'custom-background' );

//* Add support for 3-column footer widgets
add_theme_support( 'genesis-footer-widgets', 4 );

//* Reposition the breadcrumb
remove_action( 'genesis_before_loop', 'genesis_do_breadcrumbs' );
add_action( 'genesis_before_content', 'genesis_do_breadcrumbs' );

//* Remove Entry Title in Homepage 
add_action( 'get_header', 'sax_remove_page_titles' );
function sax_remove_page_titles() {
    if ( is_front_page() ) {
        remove_action( 'genesis_entry_header', 'genesis_do_post_title' );
    }
}

//* Change the footer text
add_filter('genesis_footer_creds_text', 'sax_footer_creds_filter');
function sax_footer_creds_filter( $creds ) {
	$creds = '[footer_copyright] &middot; <a href="http://www.bazziepartners.com">Bazzi&amp;Partners</a> &middot; Made by <a href="http://www.theblink.it" title="The Blink">The Blink</a>';
	return $creds;
}

/**
 * Insert custom sidebar created with Simple Sidebar plugin: use as widegtized area after the content
 *
 * @author Sacha Benda
 */
add_action( 'genesis_after_content', 'sax_secondary_sidebar' );
function sax_secondary_sidebar() {
	$sidebar = get_post_meta( get_the_ID(), 'wpcf-widget-after-content', true);
	if ( is_active_sidebar ( $sidebar ) ) { 
		echo '<div class="clearfix"></div><aside class="after-content widget-area"><div class="wrap">';
		dynamic_sidebar($sidebar);
		echo '</div></aside>';
	}
}

//* Add extra wrapper div for triangular shapes
//* Ref. http://www.wetherbyhigh.co.uk/en/
add_action( 'genesis_header','sax_slant_border' );
function sax_slant_border() {
	echo '<div class="tm-slant-block-bottom"></div>';
}

//* Add extra wrapper div
add_action( 'genesis_before_content_sidebar_wrap', 'sax_content_wrapper_before' );
function sax_content_wrapper_before() {
	echo '<div class="site-inner-wrap">';
}

add_action( 'genesis_after_content_sidebar_wrap', 'sax_content_wrapper_after' );
function sax_content_wrapper_after() {
	echo '</div>';
}

//* CUSTOM FUNCTIONS - BAZZI&PARTNERS
//* Add Responsive Menu
//* Enqueue scripts
add_action( 'wp_enqueue_scripts', 'sax_enqueue_custom_scripts' );
function sax_enqueue_custom_scripts() {
	wp_enqueue_script( 'sax-responsive-menu', get_bloginfo( 'stylesheet_directory' ) . '/js/responsive-menu.js', array( 'jquery' ), '1.0.0' );
	wp_enqueue_script( 'sax-custom-js', get_bloginfo( 'stylesheet_directory' ) . '/js/custom.js', array( 'jquery' ), '1.0.0' );
}

//Add search icon from Dashicons to search box
//http://sridharkatakam.com/add-magnifying-glass-icon-font-using-dashicons-search-button-genesis/
//* Enqueue Dashicons
add_action( 'wp_enqueue_scripts', 'enqueue_dashicons' );
function enqueue_dashicons() {
	wp_enqueue_style( 'dashicons' );
}
//* Customize search form input button text
add_filter( 'genesis_search_button_text', 'sax_search_button_text' );
function sax_search_button_text( $text ) {
	return esc_attr( '&#xf179;' );
}
//* Customize search form input box text
add_filter( 'genesis_search_text', 'sp_search_text' );
function sp_search_text( $text ) {
	return esc_attr( 'Search' );
}

//* Display image below header
// http://sridharkatakam.com/add-full-width-responsive-image-site-header-genesis/
add_action ( 'genesis_after_header', 'sax_add_header_image', 9 );
function sax_add_header_image() {
	global $post;
	$header_image = get_post_meta( get_the_ID(), 'wpcf-banner-image', true);
	if ( $header_image != "" ) {
		echo '<div class="header-banner">';
			echo '<img src="'. $header_image . '" alt="banner"/>';
		echo '</div><div class="tm-slant-block-bottom"></div>';
	}
 
}