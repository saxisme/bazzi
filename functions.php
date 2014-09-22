<?php
//* Child Theme Language override
define('GENESIS_LANGUAGES_DIR', STYLESHEETPATH.'/languages');
define('GENESIS_LANGUAGES_URL', STYLESHEETPATH.'/languages');

//* Start the engine
include_once( get_template_directory() . '/lib/init.php' );

//* Set Localization (do not remove)
load_child_theme_textdomain( 'bazzi', apply_filters( 'child_theme_textdomain', get_stylesheet_directory() . '/languages', 'bazzi' ) );

//* Child theme (do not remove)
define( 'CHILD_THEME_NAME', 'Bazzi&Partners' );
define( 'CHILD_THEME_URL', 'http://www.theblink.it/' );
define( 'CHILD_THEME_VERSION', '1.0' );

//* Enqueue Google fonts
add_action( 'wp_enqueue_scripts', 'genesis_sample_google_fonts' );
function genesis_sample_google_fonts() {
	wp_enqueue_style( 'google-font-lato', '//fonts.googleapis.com/css?family=Lato:300,700', array(), CHILD_THEME_VERSION );
	wp_enqueue_style( 'google-font-raleway','http://fonts.googleapis.com/css?family=Raleway:400,700', array(), CHILD_THEME_VERSION );
	wp_enqueue_script( 'on-load-js', get_bloginfo( 'stylesheet_directory' ) . '/js/onload.js', array( 'jquery' ), CHILD_THEME_VERSION );
}

//* Add excerpt support to pages
add_action('init', 'sax_page_excerpt');
function sax_page_excerpt() {
	add_post_type_support( 'page', 'excerpt' );
}

//* Add image size
add_image_size( 'features-thumb', 500, 500, true ); //Features Shortcode (cropped)

//* Add shortcode functionality to sidebar
add_filter( 'widget_text', 'do_shortcode' );
add_filter( 'widget_text', array( $wp_embed, 'run_shortcode' ), 8 );
add_filter( 'widget_text', array( $wp_embed, 'autoembed'), 8 ); //adds embed funciontality - ex. youtube videos
add_filter( 'example', 'do_shortcode' );

//* Define content width - used for Jetpack's Tiled Galleries
if ( ! isset( $content_width ) )
    $content_width = 845;

//* Add HTML5 markup structure
add_theme_support( 'html5' );

//* Add viewport meta tag for mobile browsers
add_theme_support( 'genesis-responsive-viewport' );

//* Add support for custom background
add_theme_support( 'custom-background' );

//* Add support for 3-column footer widgets
add_theme_support( 'genesis-footer-widgets', 3 );

add_theme_support( 'genesis-structural-wraps', array( 'header', 'nav', 'subnav', 'inner', 'footer-widgets', 'footer' ) );


//* Remove 'You are here' from the front of breadcrumb trail
add_filter( 'genesis_breadcrumb_args', 'sax_prefix_breadcrumb' );
function sax_prefix_breadcrumb( $args ) {
  $args['labels']['prefix'] = '';
  return $args;
}

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
add_action( 'genesis_before_footer', 'sax_secondary_sidebar' );
function sax_secondary_sidebar() {
	$sidebar = get_post_meta( get_the_ID(), 'wpcf-widget-after-content', true);
	if ( is_active_sidebar ( $sidebar ) ) { 
		echo '<div class="clearfix"></div><aside class="after-content widget-area full-width"><div class="wrap clearfix">';
		dynamic_sidebar($sidebar);
		echo '</div></aside>';
	}
	echo '<div class="clearfix"></div><aside class="footer-banner full-width">';
	echo '<img src="'. get_stylesheet_directory_uri() . '/images/banner-footer.png" alt="footer banner"/>';
	echo '<div class="wrap clearfix">';
	echo '</div></aside>';
}
//add_action( 'genesis_before_footer', 'sax_footer_banner' );
/**
 * Relocate Footer Widgets
 *
 * @author Jen Baumann - Sacha Benda
 * @link http://dreamwhisperdesigns.com/?p=726
 * IMPORTANT: Code to be placed after the secondary widget area otherwise it will be placed on top of it
 */
remove_action( 'genesis_before_footer', 'genesis_footer_widget_areas' );
add_action( 'genesis_before_footer', 'genesis_footer_widget_areas' );


//* CUSTOM FUNCTIONS - BAZZI&PARTNERS

/**
 * Add Responsive Menu
 *
 * @author Sacha Benda
 */
//* Enqueue scripts
add_action( 'wp_enqueue_scripts', 'sax_enqueue_custom_scripts' );
function sax_enqueue_custom_scripts() {
	wp_enqueue_script( 'sax-responsive-menu', get_bloginfo( 'stylesheet_directory' ) . '/js/responsive-menu.js', array( 'jquery' ), CHILD_THEME_VERSION );
	wp_enqueue_script( 'sax-slant', get_bloginfo( 'stylesheet_directory' ) . '/js/slant-init.js', array( 'jquery' ), CHILD_THEME_VERSION );
	//wp_enqueue_script( 'sax-custom-js', get_bloginfo( 'stylesheet_directory' ) . '/js/custom.js', array( 'jquery' ), '1.0.0' );
}

/**
 * Add search icon from Dashicons to search box
 *
 * @author Sacha Benda
 * @ref http://sridharkatakam.com/add-magnifying-glass-icon-font-using-dashicons-search-button-genesis/
 */
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
	return esc_attr( __('Search','bazzi') );
}

/**
 * Add extra wrapper div for triangular shapes
 *
 * @author Sacha Benda
 * @ref http://www.wetherbyhigh.co.uk/en/
 */
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


/**
 * Add parallax
 * Include Stellar.js only for non-mobile devices
 *
 * @author Sacha Benda
 */
add_action( 'wp_enqueue_scripts', 'sax_enqueue_stellar' );
function sax_enqueue_stellar() {
 
	if ( wp_is_mobile() )
		return;
 
	wp_enqueue_script( 'stellar',  get_stylesheet_directory_uri() . '/js/jquery.stellar.min.js', array( 'jquery' ), CHILD_THEME_VERSION, true );
	wp_enqueue_script( 'stellar-init',  get_stylesheet_directory_uri() . '/js/jquery.stellar.init.js', array( 'stellar' ), '1.0.0', true );
 
}

/**
 * Display image below header
 *
 * @author Sacha Benda
 * @ref http://sridharkatakam.com/add-full-width-responsive-image-site-header-genesis/
 */
add_action ( 'genesis_after_header', 'sax_add_header_image', 9 );
function sax_add_header_image() {
	global $post;
	$header_image = get_post_meta( get_the_ID(), 'wpcf-banner-image', true);
	if ( $header_image != "" ) {
		echo '<style>#parallax-banner{background-image:url("'. $header_image .'");}</style>';
		echo '<div id="parallax-banner" class="parallax-section" data-stellar-background-ratio="0.6">';
		echo '<div class="header-banner">';
		//echo '<img src="'. $header_image . '" alt="banner"/>';
		echo '<div class="wrap">';
		//get the custom fields for the Top Banner section
		$header_vp = get_post_meta( get_the_ID(), 'wpcf-banner-value-proposition', true);
		$header_button = get_post_meta( get_the_ID(), 'wpcf-banner-button-text', true);
		$header_url = get_post_meta( get_the_ID(), 'wpcf-banner-button-url', true);
		
		echo '<div class="banner-container">';
			if ( $header_vp != '' ) {
				echo '<div class="banner-vp"><h2>' . $header_vp . '</h2></div>';
			}
			if ( $header_button != '' ) {	?>
				<div class="banner-button">
					<div class="button"><a href="<?php echo $header_url ?>"><?php echo $header_button ?></a></div>
				</div>
			<?php } else if ( $header_button = '' ) { ?>
				<div class="banner-button">
					<div class="button"><a href="#site-inner"><?php echo __('Find out more','bazzi') ?></a></div>
				</div>
			<?php }
		echo '</div>';
		echo '</div><div class="tm-slant-block-top slant-banner"></div></div></div>';		
	}
}

/**
 * SHORTCODES
 *
 * @author Sacha Benda
 */

//Create shortcode with parameters so that the user can define what's queried - default is to list all blog posts
add_shortcode( 'list-posts', 'sax_post_listing_parameters_shortcode' );
function sax_post_listing_parameters_shortcode( $atts ) {
    ob_start();
 
    // define attributes and their defaults
    extract( shortcode_atts( array (
        'type' => 'feature',
        'order' => 'ASC',
        'orderby' => 'title',
        'posts' => -1,
        'category' => '',
        'class' => 'one-half clearfix',
        'term' => '',
    ), $atts ) );
 
    // define query parameters based on attributes
    $options = array(
        'post_type' => $type,
        'order' => $order,
        'orderby' => $orderby,
        'posts_per_page' => $posts,
        'category_name' => $category,
        'class' => $class . ' clearfix',
        'feature-category' => $term,
    );
    $query = new WP_Query( $options );
    // run the loop based on the query
    if ( $query->have_posts() ) { ?>
        <div class="posts-listing wrapper clearfix">
        	<? while ( $query->have_posts() ) {
			$query->the_post();
			$url = genesis_get_custom_field( '_url' );
			$file_url = genesis_get_custom_field ( 'wpcf-feature-file-url' ); 
			?>
			<?php
			// if ( $file_url !='' ) { $class .=' featured-single' }; 
			// if ( $file_url !='' && $url !='' ) { $class .= ' clearfix'};
			// if ( $file_url ='' && $url ='' ) { $class .= ' clearfix no-button'};
			?>
	            <article id="post-<?php the_ID(); ?>" <?php post_class($class); ?>>
	            	<?php if ( $url != '' ) { ?>
	            		<div class="image-wrapper"><a href="<?php echo genesis_get_custom_field( '_url' ) ?>"><?php the_post_thumbnail ( 'features-thumb' ); ?></a></div>
	            	<?php } else { ?>
	            		<div class="image-wrapper"><?php the_post_thumbnail ( 'features-thumb' ); ?></div>	            	
	            	<?php } ?>	
	                <div class="content-wrapper">
		                <h2 class="entry-title"><a href="<?php echo genesis_get_custom_field( '_url' ) ?>"><?php the_title(); ?></a></h2>
		                <div class="entry-content clearfix">
		                	<?php the_excerpt(); ?>
		                	<?php if ( $url != '' ) { ?>
		                		<div class="button featured-button"><a href="<?php echo $url; ?>"><?php echo __('DETTAGLI'); ?></a></div>
		                	<?php } ?>
		                </div>
	            	</div>
	            </article>
	        <?php   
			}
			echo '</div>';
		wp_reset_postdata();
        $myvariable = ob_get_clean();
        return $myvariable;
    }
}
/**
 * Create shortcode with parameters so that the user can define what's queried - default is to list all blog posts
 *
 * @author Sacha Benda
 * @author WPExplorer
 * @ref http://www.wpexplorer.com/wordpress-toggle-shortcode/
 */
//toggle shortcode
 function sax_toggle_shortcode( $atts, $content = null ) {
 extract( shortcode_atts(
	 array(
	   'title' => 'Click To Open',
	   'color' => ''
	 ),
 $atts ) );
 return '<h3><a href="#">'. $title .'</a></h3><div class="toggle_container">' . do_shortcode($content) . '</div>';
 }
 add_shortcode('toggle', 'sax_toggle_shortcode');	


//* CTA - Call To Action Shortcode
function button($atts, $content = null) {
 extract( shortcode_atts( array(
          'url' => '#',
          'text' => ''
), $atts ) );
//return '<a href="'.$url.'" class="download-button '.$color.'-button"><span>' . do_shortcode($content) . '</span></a>';
return '<div class="wrapper cta"><div class="cta-text">' . do_shortcode( $content ) . '</div><a href="'.$url.'" class="button button-cta">'.$text.'</a></div>';
}
add_shortcode('cta', 'button');

//simple button
function simple_button($atts, $content = null) {
 extract( shortcode_atts( array(
          'url' => '#'
), $atts ) );
return '<a href="'.$url.'" class="button button-cta">' . do_shortcode($content) . '</a>';
}
add_shortcode('button', 'simple_button');

//tweets
function tweet_text($atts, $content = null) {
 extract( shortcode_atts( array(
          'autore' => '#'
), $atts ) );
return '<div class="tweet"><a href="'.$url.'" class="button button-cta">' . do_shortcode($content) . '</a>';
}
add_shortcode('tweet', 'tweet_text');
