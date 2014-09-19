<?php

/**
* Template Name: Case History
* Description: Used as a page template to show page contents, followed by the Case History 
* through the "Case Histories" CPT
*/

// Add our custom loop
remove_action('genesis_loop','genesis_do_loop');
add_action( 'genesis_loop', 'sax_case_history_loop' );

function sax_case_history_loop() {
	$global $post;

	//arguments
	$args = array(
		'post_type' => 'case-history', // replace with your category slug
		// 'orderby'       => 'post_date',
		// 'order'         => 'DESC',
		// 'posts_per_page'=> '12', // overrides posts per page in theme settings
	);

	global $loop;
	$loop = new WP_Query( $args );
	if( $loop->have_posts() ) {

		// loop through posts
		while( $loop->have_posts() ): $loop->the_post();

		// $data_accadimento = esc_attr( genesis_get_custom_field( 'wpcf-data-accadimento' ) );
		// $importo_anticipo_indennizzo = esc_attr( genesis_get_custom_field( 'wpcf-importo-anticipo-indennizzo' ) );
		// $data_anticipo_indennizzo = esc_attr( genesis_get_custom_field( 'wpcf-data-anticipo-indennizzo' ) );
		// $importo_liquidato = esc_attr( genesis_get_custom_field( 'wpcf-importo-liquidato' ) );
		// $data_liquidazione = esc_attr( genesis_get_custom_field( 'wpcf-data-liquidazione' ) );

		//$video_thumbnail = '<img src="http://img.youtube.com/vi/' . $video_id . '/0.jpg" alt="" />';
		//$video_link = 'http://www.youtube.com/watch?v=' . $video_id;

		// echo '<div class="one-third first">';
		// 	echo '<a href="' . $video_link . '" target="_blank">' . $video_thumbnail . '</a>';
		// echo '</div>';
		// echo '<div class="two-thirds">';
		// 	echo '<h4>' . get_the_title() . '</h4>';
		// 	echo '<p>' . get_the_date() . '</p>';
		// 	echo '<p><a href="' . $video_link . '" target="_blank">Watch It</a> | <a href="' . get_permalink() . '" target="_blank">Show Notes</a></p>';
		// echo '</div>';

		?>
		<h3><a href="#"><?php the_title(); ?></a></h3>
		<div class="toggle_container">
			<ul>
				<li><?php the_post_thumbnail ('medium'); ?></li>
				<li>Data accadimento: <span><?php echo $data_accadimento; ?></li>
				<li>Importo anticipo indennizzo: <span><?php echo $importo_anticipo_indennizzo; ?></li>
				<li>Data anticipo indennizzo: <span><?php echo $data_anticipo_indennizzo; ?></li>
				<li>Importo liquidato: <span><?php echo $importo_liquidato; ?></li>
				<li>Data liquidazione: <span><?php echo $data_liquidazione; ?></li>
		</div>

		endwhile;
	}

	wp_reset_query();

}

genesis();