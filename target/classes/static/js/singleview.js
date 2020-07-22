$('#like_button2').on('click', function () {
    var postId = $('#postId').val();
    $.ajax({
        url: '/user/post/likeUnlike',
        type: 'POST',
        data: {postId: postId},
        success: function (data) {
            if (data == "false" || data == "true") {
                if (data == "true") {
                    $('#like_button2').text('Unlike');
                }
                if (data == "false") {
                    $('#like_button2').text('Like');
                }
            } else {
                var pathname = window.location.pathname;
                window.location.replace('/user' + pathname);
            }
        }
    })
})

    $('#fav_button2').on('click', function () {
        var postId = $('#postId').val();
        $.ajax({
            url: '/user/post/favorite',
            type: 'POST',
            data: {postId: postId},
            success: function (data) {
                if (data == "false" || data == "true") {
                    if (data == "true") {
                        $('#fav_buttoni').addClass("red");
                    }
                    if (data == "false") {
                        $('#fav_buttoni').removeClass("red");
                    }
                } else {
                    var pathname = window.location.pathname;
                    window.location.replace('/user' + pathname);
                }
            }
        })
    })

$('#submitComment').on('click', function () {


    var postId = $('#postId').val();
    var commentBody = $('#newComment').val();
    if (commentBody != '') {
        $.ajax({
            url: '/userPost/post/comment',
            type: 'POST',
            data: {postId: postId, commentBody: commentBody},
            success: function (data) {
                $('#newComment').val('');
                if (data.respose == 'Success') {
                    $.ajax({
                        url: '/userPost/post/showComment',
                        type: 'POST',
                        data: {postId: postId},
                        success: function (result) {
                            $('#showComments').empty();
                            if (result.length > 0) {
                                $.each(result, function (key, entry) {
                                    $('#showComments').append(
                                        $('<ul>').addClass('comments-list').append(
                                            $('<li>').addClass('comment').append(
                                                $('<a>').addClass('pull-left').attr({href: '/profile/' + entry.commentedBy.id}).append(
                                                    $('<img>').addClass('avatar').attr({src: '/images/profileImage/' + entry.commentedBy.id}).error(function (){

                                                        $(this).attr({src:'/images/user_default.png'})
                                                    })
                                                )
                                            ).append(
                                                $('<div>').addClass('comment-body').append(
                                                    $('<div>').addClass('comment-heading').append(
                                                        $('<h4>').attr({fnc: entry.commentedBy.id}).addClass('user').text(entry.commentedBy.profile.firstName)
                                                    )
                                                ).append(
                                                    $('<h5>').addClass('time').text(new Date(entry.localDateTime).toLocaleDateString())
                                                )
                                            ).append(
                                                $('<p>').text(entry.comment)
                                            )
                                        )
                                    )
                                })
                            }
                        }


                    })
                } else {
                    var pathname = window.location.pathname;
                    window.location.replace('/user' + pathname);
                }
            }

        })

    }
})

    function scroller_comment() {
        var objDiv = document.getElementById("showComments");
        objDiv.scrollTop = objDiv.scrollHeight;
    }
$(document).ready(function () {
    var postId = $('#postId').val();
    $.ajax({
        url: '/userPost/post/showComment',
        type: 'POST',
        data: {postId: postId},
        success: function (data) {
            if (data.length > 0) {
                $.each(data, function (key, entry) {
                    $('#showComments').append(
                        $('<ul>').addClass('comments-list').append(
                            $('<li>').addClass('comment').append(
                                $('<a>').addClass('pull-left').attr({href: '/profile/' + entry.commentedBy.id}).append(
                                    $('<img>').addClass('avatar').attr({src: '/images/profileImage/' + entry.commentedBy.id}).error(function (){

                                        $(this).attr({src:'/images/user_default.png'})
                                    })
                                )
                            ).append(
                                $('<div>').addClass('comment-body').append(
                                    $('<div>').addClass('comment-heading').append(
                                        $('<h4>').addClass('user').text(entry.commentedBy.profile.firstName)
                                    )
                                ).append(
                                    $('<h5>').addClass('time').text(new Date(entry.localDateTime).toLocaleDateString())
                                )
                            ).append(
                                $('<p>').text(entry.comment)
                            )
                        )
                    )

                })
            }
        }


    })
})
// Open the Modal
function openModal() {
    document.getElementById('myModal').style.display = "block";
}

// Close the Modal
function closeModal() {
    document.getElementById('myModal').style.display = "none";
}

var slideIndex = 1;
showSlides(slideIndex);

// Next/previous controls
function plusSlides(n) {
    showSlides(slideIndex += n);
}

// Thumbnail image controls
function currentSlide(n) {
    showSlides(slideIndex = n);
}

function showSlides(n) {
    var i;
    var slides = document.getElementsByClassName("mySlides");
    var dots = document.getElementsByClassName("demo");
    var captionText = document.getElementById("caption");
    if (n > slides.length) {
        slideIndex = 1
    }
    if (n < 1) {
        slideIndex = slides.length
    }
    for (i = 0; i < slides.length; i++) {
        slides[i].style.display = "none";
    }
    for (i = 0; i < dots.length; i++) {
        dots[i].className = dots[i].className.replace(" active", "");
    }
    slides[slideIndex - 1].style.display = "block";
    dots[slideIndex - 1].className += " active";
    captionText.innerHTML = dots[slideIndex - 1].alt;
}





