function getCookie(name) {
    var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
    if (arr = document.cookie.match(reg)) {
        return unescape(arr[2]);
    }

    return null;
}

axios.defaults.baseURL = 'http://127.0.0.1:9100';

let telephone = getCookie("login_member_telephone");

if(telephone){
    axios.defaults.headers.common['login_member_telephone'] = telephone;
}
