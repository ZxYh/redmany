
function alert(title, msg) {
    $.messager.alert(title, msg);
}
function alert(title, msg,type) {
    $.messager.alert(title, msg, type);
}
function openwindow(id) {
    $('#d2').dialog('open');
    $("#KeyId").val(id);
}
function closewindow() {
    $('#d2').dialog('close');
}
function confirm1(title, msg) {
    $.messager.confirm(title, msg, function (r) {
        if (r) {
//            alert('confirmed:' + r);
//            location.href = 'http://www.google.com';
        }
        else
        {
         return ;
        }
    });
}
