var script = document.createElement('script');
script.src = 'https://code.jquery.com/jquery-3.6.3.min.js'; // Check https://jquery.com/ for the current version
document.getElementsByTagName('head')[0].appendChild(script);

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
            var j = 0;
            var user = [];
            while (editet_tds[j].className !== "roles_cell") {
                td = editet_tds[j++];
                if (td.className === "user_id" || td.className === "age") {
                    user.push(Number(td.textContent));
                } else {
                    user.push(td.textContent);
                }
                params = params + td.className + "=" + td.textContent + "&";
            }
            var role_cells = editet_tds[j].getElementsByTagName("input");
            // var roles = [];
            var flag = true;
            for (let k = 0; k < role_cells.length; k++) {
                if (role_cells[k].checked) {
                    // roles.push(role_cells[k].value)
                    if (flag) {
                        params = params + "roles=" + role_cells[k].value + ",";
                        flag = false;
                    } else {
                        params = params + role_cells[k].value + ",";
                    }
                }
            }
            params = params.substring(0, params.length - 1);


            // $.ajax({
            //     type : "PATCH",
            //     url : "/admin/edit2/?roles=" + roles,
            //     data : {
            //         user: {
            //             "id": user[0],
            //             "name": user[1],
            //             "lastName": user[2],
            //             "age": user[3],
            //             "username": user[4],
            //             "password": user[5]
            //         }
            //     }
            // });

            x.open("PATCH", "/admin/edit/" + params);
            x.send();
        }
    });
    } else if (button.className === "remove_btn") {
        button.addEventListener("click", function () {
            // var x = new XMLHttpRequest();
            // x.open("DELETE", "admin/edit/?user_id=" + this.getAttribute("user_id"));
            // x.send()

            $.ajax({
                type : "DELETE",
                url : "admin/edit/?user_id=" + this.getAttribute("user_id"),
                success: function (result) {
                    console.log(result);
                    window.location = '/admin'; // redirect
                }
            });
        })
    }

}j