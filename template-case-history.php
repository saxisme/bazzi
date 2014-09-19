<?php

/**
* Template Name: Case History
* Description: Used as a page template to show page contents, followed by the Case History 
* through the "Case Histories" CPT
*/

// Enque scripts
wp_enqueue_script('jquery-ui-accordion');
wp_enqueue_script( 'toggle-accordion', get_bloginfo( 'stylesheet_directory' ) . '/js/toggle-accordion.init.js', array( 'jquery-ui-accordion' ), CHILD_THEME_VERSION );


// Add our custom loop
//remove_action('genesis_loop','genesis_do_loop'); //remove if the default content is not neede
add_action('genesis_loop','sax_case_history');

function sax_case_history() {
	global $post;
 
	// arguments, adjust as needed
	$args = array(
		'post_type' => 'case-history',
		'posts_per_page' => -1,
		'order' => 'ASC',
	);
 
	/* 
	Overwrite $wp_query with our new query.
	The only reason we're doing this is so the pagination functions work,
	since they use $wp_query. If pagination wasn't an issue, 
	use: https://gist.github.com/3218106
	*/
	global $wp_query;
	$wp_query = new WP_Query( $args );

	if ( have_posts() ) :

		echo '<div class="entry entry-case-history">';
		echo '<div id="accordion">';
		while ( have_posts() ) : the_post();

		$data_accadimento = genesis_get_custom_field('wpcf-data-accadimento');
		$importo_anticipo_indennizzo = genesis_get_custom_field( 'wpcf-importo-anticipo-indennizzo' );
		$data_anticipo_indennizzo = genesis_get_custom_field( 'wpcf-data-anticipo-indennizzo' );
		$importo_liquidato = genesis_get_custom_field( 'wpcf-importo-liquidato' );
		$data_liquidazione = genesis_get_custom_field( 'wpcf-data-liquidazione' );	

		?>
 
		<h3 class="trigger"><a href="#"><?php the_title(); ?></a></h3>
		<div class="toggle_container">
			<ul>
				<li><?php echo do_shortcode('[gallery type="rectangular" link="none" orderby="rand"]');//the_post_thumbnail ('medium'); ?></li>
				<li>Data accadimento: <span><?php echo $data_accadimento; ?></li>
				<li>Importo anticipo indennizzo: <span><?php echo $importo_anticipo_indennizzo; ?></li>
				<li>Data anticipo indennizzo: <span><?php echo $data_anticipo_indennizzo; ?></li>
				<li>Importo liquidato: <span><?php echo $importo_liquidato; ?></li>
				<li>Data liquidazione: <span><?php echo $data_liquidazione; ?></li>
		</div>
 
		<?php endwhile;
		echo '</div></div>';
		do_action( 'genesis_after_endwhile' );
	endif;
 
	wp_reset_query();
}

genesis();