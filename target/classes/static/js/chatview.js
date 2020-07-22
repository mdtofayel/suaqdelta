
$(document).ready(function () {
    $('#action_menu_btn').click(function () {
        $('.action_menu').toggle();
    });
});

$(document).ready(function () {
    $('#sendMesseages').on('click', function () {
        var messageBody = $("#messageBody").val();
        var receiverId = $("#receiverId").val();
        /* if($('#messageBody'.val() != ''))*/
        $.ajax({
            type: "POST",
            url: "/user/chat/save",
            data: {message: messageBody, sendTo: receiverId},
            dataType: 'json',
            success: function (result) {
                if (result.length > 0) {
                    $.each(result, function (key, entry) {
                        if (entry.sendFrom.id == receiverId) {
                            $('#userListPanel').append(

                                $('<div>').addClass('d-flex justify-content-start mb-4').append(
                                    $('<div>').addClass('img_cont_msg').append(
                                        $('<img>').addClass('rounded-circle user_img_msg').attr({src: '/images/profileImage/' + entry.sendFrom.id})
                                    )
                                ).append(
                                    $('<div>').addClass('msg_cotainer').text(entry.message).append(
                                        $('<span>').addClass('msg_time').text(new Date(entry.time).toLocaleTimeString())
                                    )
                                )

                            )
                            console.log('messages Save 2');
                        } else {
                            $('#userListPanel').append(
                                $('<div>').addClass('d-flex justify-content-end mb-4').append(
                                    $('<div>').addClass('msg_cotainer_send').text(entry.message).append(

                                        $('<span>').addClass('msg_time_send').text(new Date(entry.time).toLocaleTimeString())

                                    ).append(
                                        $('<p>').addClass('seen').append(
                                            $('<i>').addClass('fas fa-check-double')
                                        )
                                    )
                                ).append(
                                    $('<div>').addClass('img_cont_msg').append(
                                        $('<img>').addClass('rounded-circle user_img_msg').attr({src: '/images/profileImage/' + entry.sendFrom.id})
                                    )
                                )
                            )

                        }
                        console.log('messages Save 3');

                    });
                }
                scroller();
                $('#messageBody').val('');
            },
            error: function (e) {
            }
        })
            .done(function (msg) {

            });
    })
})





$(document).ready(function () {
    $('#messageBody').on('click', function () {

        var receiverId = $("#receiverId").val();
        $.ajax({
            type: "POST",
            /* /!* contentType: "application/json",*!/*/
            url: "/user/chat/seen",
            /*/!*data: JSON.stringify(formData),*!/*/

            dataType: 'json',

            data: {sendTo: receiverId},
            success: function (result) {
            },
            error: function (e) {
            }


        })
    })
})

var globalId = false;
var prevId  = -1 ;
function chatviewIndividualMessages(id) {

    if (prevId!=id) {
        $('#chatImageUpperPart').empty();
        $('#userListPanel').empty();
        findImage(id);
        prevId = id
        globalId = true;
        $.ajax({

            url: '/user/chatview/listMessage',
            type: 'POST',
            data: {id: id},
            success: function (data) {

                if (data.length > 0) {

                    $.each(data, function (key, entry) {
                        if (entry.sendFrom.id == id) {
                            $('#userListPanel').append(

                                $('<div>').addClass('d-flex justify-content-start mb-4').append(
                                    $('<div>').addClass('img_cont_msg').append(
                                        $('<img>').addClass('rounded-circle user_img_msg').attr({src: '/images/profileImage/' + entry.sendFrom.id})
                                    )
                                ).append(
                                    $('<div>').addClass('msg_cotainer').text(entry.message).append(
                                        $('<span>').addClass('msg_time').text(new Date(entry.time).toLocaleTimeString())
                                    )
                                )

                            )
                        } else {
                            $('#userListPanel').append(
                                $('<div>').addClass('d-flex justify-content-end mb-4').append(
                                    $('<div>').addClass('msg_cotainer_send').text(entry.message).append(

                                        $('<span>').addClass('msg_time_send').text(new Date(entry.time).toLocaleTimeString())

                                    ).append(
                                        $('<p>').addClass('seen').append(
                                            $('<i>').addClass('fas fa-check-double')
                                        )
                                    )
                                ).append(
                                    $('<div>').addClass('img_cont_msg').append(
                                        $('<img>').addClass('rounded-circle user_img_msg').attr({src: '/images/profileImage/' + entry.sendFrom.id})
                                    )
                                )
                            )

                        }

                    });
                }
                scroller();
            }
        });
    }

};
function scroller()
{
    console.log('working');
    var objDiv = document.getElementById("userListPanel");
    objDiv.scrollTop = objDiv.scrollHeight;
}
findImage = function (id) {
        var id = id;
        console.log('mta i am in chat view image.');
        $.ajax({
            url: '/user/chatviewUserName/userName',
            type: 'POST',
            data: {id: id},
            success: function (data) {
                $('#receiverId').val(id);
                $('#chatImageUpperPart').append(
                    $('<div>').addClass('img_cont').append(
                        $('<img>').addClass('rounded-circle user_img').attr({src: '/images/profileImage/' + id})
                    )
                ).append(
                    $('<div>').addClass('user_info').append(
                        $('<span>').text(data)
                    )
                )
            }
        })
    }

function search() {
    /*   console.log('work');
       alert('kaall');*/
    $('.user_name').hide();
    var txt = $('#search-criteria').val();
    $('.user_name:contains("' + txt + '")').show();
};