let getUserUrl = "/rest/user";
async function getUser(url) {
    let response = await fetch("/rest/user");
    let user = await response.json();

    $(".name").text(user.name);
    $(".last_name").text(user.lastName);
    $(".age").text(user.age);
    $(".username").text(user.username);
    $(".user_link").text(user.username);
    $(".navbar-brand").append("<b>" + user.username + "</b> with roles ");
    for (let i = 0; i < user.roles.length; i++) {
        let shortRole = user.roles[i].authority.slice(5).toLowerCase();
        $(".roles_cell").append('<span>' + shortRole + (i === user.roles.length -1 ? '' : ', ') + '</span>');
        $(".navbar-brand").append('<span>' + shortRole + (i === user.roles.length -1 ? '' : ', ') + '</span>');
    }
}
getUser(getUserUrl);