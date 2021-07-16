
function showBoxCateHomeByID(id,element_general){
	$(element_general).removeClass('active-box-category');
	$(element_general).css({display:"none",opacity:0});
	$(id).css({display:'block'}).animate({opacity:1},500);
}
function showBoxSearchMobile(){
	var html = $('.header-content').find('.search-box')[0];
	$('.header-content .box-search-mobile').html(html);
	$('.header-content .mask-search,.header-content .box-search-mobile').css({opacity:0,visibility:'visible'}).animate({opacity:1},500);
}
function hiddenBoxSearchMobile(){
	$('.header-content .mask-search,.header-content .box-search-mobile').css({opacity:0,visibility:'hidden'});
}
 function showMenuMobie(){
 	maskFullContent('hiddenMenuMobile');
 	$('.menu-mobile-left-content').animate({
 		left:'0'
 	},500);
 	$('.menu-mobile-left-content').show();
 }
 function hiddenMenuMobile(){
 	$('.menu-mobile-left-content').animate({
 		left:'-301px'
 	},500);
 }
function showCartBoxDetail(){ 
	if($('.cart-detail-header').is(':hidden')){
		$('.cart-detail-header').css({display:'block',opacity:0}).animate({opacity:1},500);
	}else{
		$('.cart-detail-header').animate({opacity:0},500,'swing',function(){
			$('.cart-detail-header').css({display:"none"});
		});
	}
}
function maskFullContent(func){
	var html = '<aside id="mask-full-content" onclick="removeMaskFullContent()" data-function="'+func+'"></aside>';
	$('body').append(html);
	$('#mask-full-content').css({opacity:0,visibility:'visible'}).animate({opacity:1},100);
}
function showPopupQuickView(id){
	if($('#quick-view-web').length){
		maskFullContent();
		
	}else{
		$('body').append('<div id="quick-view-web"></div>');
		showPopupQuickView();
	}
}
function showSideBar(){
	var pos = $('#slide-bar-category').position();
	if(pos.left <= -285 ){
		maskFullContent('showSideBar');
		$('#slide-bar-category').css({left:-285}).animate({left:0},500);
		$('#slide-bar-category').show();
	}else{
		$('#slide-bar-category').animate({left:-285},500,'swing',function(){
			$('#slide-bar-category').removeAttr('style');
		});
	}
}
function removeMaskFullContent(){
	var func = $('#mask-full-content').attr('data-function');
	var multi_func = func.split(',');
	$(multi_func).each(function(k,v){
		if(typeof window[v] === 'function'){
			window[v]();
		}
	});
	$('#mask-full-content').remove();
}
function showMenuHomeV3(){
		var check = $('.menu_more_header').height();
		var check_1 = $('.menu-slide').find('.menu-web').length;
		var box_offset = $('.menu-header ul li:first-child').offset();
		var box_h = $('.menu-header ul li:first-child').height();
		if($(window).width() <= 980 || check_1 == 0){
			if(!check){
				$('.menu_more_header').css({top:box_offset.top + box_h,left:box_offset.left,display:"block",height:0}).animate({height:588},500);
			}else{
				$('.menu_more_header').animate({height:0},500);
			}
		}
}
function printWebsite(){
	window.print();
}
function changeIconButtonSearch(){
	if($(window).width() <= 480){
		$('.search-box button').html('<i class="data-icon data-icon-basic icon-basic-magnifier"></i>');
	}else{
		$('.search-box button').html('Search');
	}
}
function changeTabsProductDetail(num){
	$('.title-tabs-product-detail').removeClass('active-title-tabs');
	$('.title-tabs-'+num).addClass('active-title-tabs');
	var pos = $('.content-tab-'+num).position();
	if(pos.top == 0){
		$('.content-tabs-product-detail').removeClass('active-tabs-product-detail').css({display:"none",opacity:0});
		$('.content-tab-'+num).css({display:"block",opacity:0}).animate({opacity:1},500);
	}
}
function minusBtnInput(input){
	var num = $(input).val();
	num = parseInt(num, 10) - 1;
	if(num > 0){
		$(input).val(num);
	}else{
		alert('Maximum number must be 1');
	}
}
function plusBtnInput(input){
	var num = $(input).val();
	num = parseInt(num, 10) + 1;
	if(num < 100){
		$(input).val(num);
	}else{
		alert('Maximum max product are 100');
	}
}
function getValsRanger(){
	// Get slider values
	var parent = document.getElementsByClassName("range-slider")[0];
	var slides = parent.getElementsByTagName('input');
	var slide1 = parseFloat( slides[0].value );
	var slide2 = parseFloat( slides[1].value );
	// Neither slider will clip the other, so make sure we determine which is larger
	if( slide1 > slide2 ){ var tmp = slide2; slide2 = slide1; slide1 = tmp; }
	var displayElement = document.getElementsByClassName("number-range")[0];
	displayElement.innerHTML = "$" + slide1 + " - $" + slide2;
}
function selectTabComment(num){
	$('.title-comment-box-tabs').removeClass('active-title-comment');
	$('.title-tabs-comment-'+num).addClass('active-title-comment');
	$('.comment-g-tab').removeClass('active-comment-g-tab').css({display:'none',opacity:0});
	$('.comment-g-tab-'+num).css({display:'block',opacity:0}).animate({opacity:1},500);
}
function selectOptionShoping(item){
	$('.select-ship-option p').html($(item).text());
}
function optionShiping(item){
	var check = $(item).parent().find('.show-option-now').length;
	if(check){
		$(item).find('ul').animate({opacity:0},500,'swing',function(){
			$(item).removeClass('show-option-now');
			$(item).find('ul').css({display:"none"});
		});
		$(item).find('i').css({transform:'rotate(0deg)'});
	}else{
		$(item).addClass('show-option-now');
		$(item).find('ul').css({display:'block',opacity:0}).animate({opacity:1},500);
		$(item).find('i').css({transform:"rotate(180deg)"});
	}
}
(function($){
	"use strict";
	$(document).ready(function($){
		//Fixed Header
    $(window).scroll(function() {
        if($('.header-ontop').length>0){
            if($(window).width()>812){
                var ht = $('header').height();
                var st = $(window).scrollTop();
                if(st>ht){
                    $('.header-ontop').addClass('fixed-ontop');
                }else{
                    $('.header-ontop').removeClass('fixed-ontop');
                }
            }
        }
      

    });

		// Push menu home 5
	    var menuLeft = $('.pushmenu-left');
	    var menuHome6 = $('.menu-home5');
	    var nav_list = $('.open-cart');
	    var nav_click = $('.icon-pushmenu');
	    nav_list.on("click", function(event) {
	        event.stopPropagation();
	        $(this).toggleClass('active');
	        $('body').toggleClass('pushmenu-push-toright-cart');
	        menuLeft.toggleClass('pushmenu-open');
	        $(".container").toggleClass("canvas-container");
	    });
	    nav_click.on("click", function(event) {
	        event.stopPropagation();
	        $(this).toggleClass('active');
	        $('body').toggleClass('pushmenu-push-toleft');
	        menuHome6.toggleClass('pushmenu-open');
	        $('.menu-mobile-left-content').hide();
	        $('#slide-bar-category').hide();
	    });
	    $(".wrappage").on("click", function() {
	        $(this).removeClass('active');
	        $('body').removeClass('pushmenu-push-toright-cart').removeClass('pushmenu-push-toleft');
	        menuLeft.removeClass('pushmenu-open');
	        menuHome6.removeClass('pushmenu-open');
	    });
	    $(".close-left").on("click", function() {
	        $(this).removeClass('active');
	        $('body').removeClass('pushmenu-push-toright-cart');
	        menuLeft.removeClass('pushmenu-open');
	    });
	    $(".close-left").on("click", function() {
	        $('body').removeClass('pushmenu-push-toleft');
	        menuHome6.removeClass('pushmenu-open');
	    });
	    // Open menu dropdown home 5
	    $(".js-menubar li .icon-sub-menu").on("click", function() {

	        $(this).toggleClass('up-icon');
	        $(this).parent().find(".js-open-menu").slideToggle('fast', function() {
	            $(this).next().stop(true).toggleClass('open', $(this).is(":visible"));
	        });
	    });
		$('#quickview').modal('show');
		/* Count animate numeric */
		$('.Count').each(function () {
		  var $this = $(this);
		  jQuery({ Counter: 0 }).animate({ Counter: $this.text() }, {
		    duration: 2000,
		    easing: 'swing',
		    step: function (now) {
			  $this.text(Math.ceil(now));
			}
		  });
		});
		if($('.grid-box-content').length){
			$('.grid-box-content').masonry({
			    itemSelector:'.card',
			    gutter: 30,
			    horizontalOrder:true,
			    originLeft: true,
			    transitionDuration: '0.5s',
			    resize:true,
			    stagger:30
			});
		}
		if($('.range-slider').length){
			var sliderSections = document.getElementsByClassName("range-slider")[0];
			var sliders = sliderSections.getElementsByTagName("input");
			for( var y = 0; y < sliders.length; y++ ){
				if( sliders[y].type ==="range" ){
					sliders[y].oninput = getValsRanger;
					// Manually trigger event first time to display values
					sliders[y].oninput();
				}
			}
		}
		changeIconButtonSearch();
		$('.category-box').on('click',function(){
			var check = $('.search-box').find('.show-box-category-seach').length;
			if(check){
				$('.box-category-search').removeClass('show-box-category-seach');
			}else{
				$('.box-category-search').addClass('show-box-category-seach');
			}
		});
		$('.form-placeholde-animate').find('input, textarea').on('keyup blur focus', function (e) {

			var $this = $(this),
			label = $this.prev('label');

			if (e.type === 'keyup') {
				if ($this.val() === '') {
					label.removeClass('active highlight');
				} else {
					label.addClass('active highlight');
				}
			} else if (e.type === 'blur') {
				if( $this.val() === '' ) {
					label.removeClass('active highlight'); 
				} else {
					label.removeClass('highlight');   
				}   
			} else if (e.type === 'focus') {

				if( $this.val() === '' ) {
					label.removeClass('highlight'); 
				} 
				else if( $this.val() !== '' ) {
					label.addClass('highlight');
				}
			}

		});
		if($('#owl-big-slide').length){
			$('#owl-big-slide').slick({
				slidesToShow: 1,
				slidesToScroll: 1,
				arrows: false,
				fade: true,
				asNavFor: '#owl-thumbnail-slide'
			});
			$('#owl-thumbnail-slide').slick({
				slidesToShow: 3,
				slidesToScroll: 1,
				asNavFor: '#owl-big-slide',
				dots: false,
				autoplay: true,
		  		autoplaySpeed: 5000,
				focusOnSelect: true,
				responsive: [
					{
				      breakpoint: 1170,
				      settings: {
				        slidesToShow: 3
				      }
				    },
				    {
				      breakpoint: 768,
				      settings: {
				        slidesToShow: 3
				      }
				    },
				    {
				      breakpoint: 480,
				      settings: {
				        slidesToShow: 2
				      }
				    },
				    {
				      breakpoint: 320,
				      settings: {
				        slidesToShow: 1
				      }
				    }
				]
			});
		}
	$('.tab a').on('click', function (e) {
	  
	  e.preventDefault();
	  
	  $(this).parent().addClass('active');
	  $(this).parent().siblings().removeClass('active');
	  
	  target = $(this).attr('href');

	  $('.tab-content > div').not(target).hide();
	  
	  $(target).fadeIn(600);
	  
	});
		$('.category-image > div').owlCarousel({
			loop:true,
			nav:false,
			dots:false,
		    autoplay:true,
		    autoplayTimeout:5500,
		    autoplayHoverPause:true,
		    responsive:{
		    	320:{
		    		items:2
		    	},
		    	480:{
		    		items:3
		    	},
		    	760:{
		    		items:5
		    	}
		    }
		});
		$('.slide-related-product .owl-theme').each(function(k){
			var id_btn,data_items,items_owl;
			id_btn = k+1
			data_items = $(this).attr('data-items');
			items_owl = data_items.split(',');
			$(this).owlCarousel({
				loop:true,
				nav:true,
				dots:false,
			    autoplay:true,
			    autoplayTimeout:5500,
			    autoplayHoverPause:true,
			    navContainer:'#btn-slide-'+id_btn,
			    margin:30,
			    navText:['<i class="fa fa-angle-left" aria-hidden="true"></i>','<i class="fa fa-angle-right" aria-hidden="true"></i>'],
			    responsive : {
			    	320:{
			    		items:items_owl[0]
			    	},
			    	480:{
			    		items:items_owl[1]
			    	},
			    	768:{
			    		items:items_owl[2]
			    	},
			    	1170:{
			    		items:items_owl[3]
			    	}
			    }
			});
		});
		$('.good-deal-product .owl-theme').each(function(){
			$(this).owlCarousel({
				loop:true,
				nav:false,
				dots:false,
			    autoplay:true,
			    autoplayTimeout:5500,
			    autoplayHoverPause:true,
			    margin:30,
			    responsive:{
			    	320:{
			    		items:1
			    	},
			    	480:{
			    		items:2
			    	},
			    	768:{
			    		items:4
			    	}
			    }
			});
		});
		$('.slide-product-category .owl-theme').each(function(){
			$(this).owlCarousel({
				loop:true,
				nav:false,
				dots:false,
			    autoplay:true,
			    autoplayTimeout:5500,
			    autoplayHoverPause:true,
			    responsive:{
			    	320:{
			    		items:1
			    	},
			    	480:{
			    		items:2
			    	},
			    	768:{
			    		items:4
			    	}
			    },
			});
		});
		$('.slide-on-sale-sidebar .owl-theme').owlCarousel({
			loop:true,
			nav:true,
			dots:false,
		    autoplay:true,
		    autoplayTimeout:5500,
		    autoplayHoverPause:true,
		    items:1,
		    navContainerClass:"btn-slide-on-sale"
		});
		$(window).resize(function(){
			changeIconButtonSearch();
		});
		$('.slide-home').owlCarousel({
		    loop:true,
		    nav:false,
		    autoplay:true,
		    autoplayTimeout:5000,
		    autoplayHoverPause:true,
		    items:1,
		    dotsClass:'dots-slide',
		    dotClass:'dot-slide-home'
		});
		$('.slide-logo-brand').owlCarousel({
			loop:true,
		    nav:true,
		    dots:false,
		    autoplay:true,
		    autoplayTimeout:5000,
		    autoplayHoverPause:true,
		    navText:['<i class="fa fa-angle-left" aria-hidden="true"></i>','<i class="fa fa-angle-right" aria-hidden="true"></i>'],
		    navContainer:'.nav-next,.nav-prev',
		    responsive:{
		    	0:{
		    		items:2
		    	},
		    	480:{
		    		items:2
		    	},
		    	760:{
		    		items:4
		    	},
		    	980:{
		    		items:4
		    	},
		    	1170:{
		    		items:6
		    	}
		    }
		});
	});

})(jQuery)