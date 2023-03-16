var tds = document.getElementsByTagName("td"),
    index,
    td;

for (index = 0; index < tds.length; ++index) {
    td = tds[index];
    td.parentNode.getElementsByTagName("td")
    if (td.contentEditable) {
        td.onblur = function () {
            var text = this.innerHTML;
            text.replace(/&/g, "&amp").replace(/</g, "&lt;");
            this.parentNode.classList.add("edited");
            // var user_id = td.parent().attr("user_id");
            // var x=new XMLHttpRequest();
            // x.open("patch", "users/edit/" + user_id);
            // x.send("user_id");
        };
    }
}
var buttons = document.getElementsByTagName("button")
for (i = 0; i < buttons.length; ++i) {
    // buttons[i].onclick = function () {
    //     var x = new XMLHttpRequest();
    //     var edited_trs = this.parent().getElementsByClassName("edited");
    //     for (var i = 0; i < edited_trs.length; ++i) {
    //         var editet_tds = edited_trs[i].getElementsByTagName("td")
    //         var params = "";
    //         for (var j = 0; j < editet_tds.length; ++j) {
    //             td = editet_tds[j];
    //             params = params + td.className + "=" + td.textContent;
    //             if (j < editet_tds.length - 1) {
    //                 params = params + "&"
    //             }
    //         }
    //         x.open("patch", "users/edit/");
    //         x.send(params);
    //     }
    // }
    var button = buttons[i];
    if (button.className === "save_btn") {
    button.addEventListener("click", function () {
        var x = new XMLHttpRequest();
        var tr = this.parentNode.parentNode;
        if (tr.className === "edited") {
            var editet_tds = tr.getElementsByTagName("td")
            var params = "?";
            for (var j = 0; j < 4; ++j) {
                td = editet_tds[j];
                params = params + td.className + "=" + td.textContent;
                if (j < 3) {
                    params = params + "&"
                }
            }
            x.open("PATCH", "edit/" + params);
            x.send();
        }
    });
    } else if (button.className === "remove_btn") {
        button.addEventListener("click", function () {
            var x = new XMLHttpRequest();
            x.open("DELETE", "edit/?user_id=" + button.getAttribute("user_id"));
            x.send()
            window.location.href = "/users/";
        })
    }

}