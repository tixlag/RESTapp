let getUserUrl = "/api/user";
async function getUser(url) {
    let response = await fetch(url);
    let user = await response.json();

    $(".name").text(user.name);
    $(".last_name").text(user.lastName);
    $(".age").text(user.age);
    $(".username").text(user.username);
    $(".user_link").text(user.username);

}
getUser(getUserUrl);