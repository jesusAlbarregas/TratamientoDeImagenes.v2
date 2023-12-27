/* global waitingDialog */

if (typeof imagenInput === 'undefined') {
    var imagenInput;
}

$(document).ready(function () {
    let original = $('#previa').attr('src');
    let selloTiempo = new Date();
    $('#previa').attr('src', original + '?' + selloTiempo);
    $('#ficheroAvatar').on('change', function () {
        readURL(this);
    });


//cambio de avatar
    $("#cambiarAvatar").on('click', function () {

        event.preventDefault();
        var form = $("#modAvatar")[0];
        var data = new FormData(form);

        $("#cambiarAvatar").prop("disabled", true);

        console.log("Antes del ajax");
        $.ajax({
            type: "POST",
            enctype: 'multipart/form-data',
            url: "AvatarAjax",
            data: data,
            processData: false,
            contentType: false,
            cache: false,
            success: function (data) {

                $.notify(data[1], {className: data[0]});
                if (data[0] !== 'error') {
                    $('#imagenAvatar').attr('src', imagenInput);
                }
                $("#cambiarAvatar").prop("disabled", false);
            },
            error: function () {
                console.log("entra en error");
            }
        });
    });
});

function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function (e) {
            imagenInput = e.target.result;
            
            $('#previa').attr('src', e.target.result);
        };
        reader.readAsDataURL(input.files[0]);
    }
}