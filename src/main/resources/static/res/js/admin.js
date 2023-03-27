$('#myTab a').on('click', function (e) {
    e.preventDefault()
    $(this).tab('show')
})

$('#okModal').on('hidden.bs.modal', function () {
    location.reload();
})


// var buttons = document.getElementsByClassName("save-edit-user")
// for (i = 0; i < buttons.length; ++i) {
//     var button = buttons[i];
//     button.addEventListener("click", function () {
//         var x = new XMLHttpRequest();
//         var tr = this.parentNode.parentNode;
//         if (tr.className === "edited") {
//             var params = "?";
//             var j = 0;
//             var user = [];
//             while (editet_tds[j].className !== "roles_cell") {
//                 td = editet_tds[j++];
//                 if (td.className === "user_id" || td.className === "age") {
//                     user.push(Number(td.textContent));
//                 } else {
//                     user.push(td.textContent);
//                 }
//                 params = params + td.className + "=" + td.textContent + "&";
//             }
//             var role_cells = editet_tds[j].getElementsByTagName("input");
//             // var roles = [];
//             var flag = true;
//             for (let k = 0; k < role_cells.length; k++) {
//                 if (role_cells[k].checked) {
//                     // roles.push(role_cells[k].value)
//                     if (flag) {
//                         params = params + "roles=" + role_cells[k].value + ",";
//                         flag = false;
//                     } else {
//                         params = params + role_cells[k].value + ",";
//                     }
//                 }
//             }
//             params = params.substring(0, params.length - 1);
//
//
//             // $.ajax({
//             //     type : "PATCH",
//             //     url : "/admin/edit2/?roles=" + roles,
//             //     data : {
//             //         user: {
//             //             "id": user[0],
//             //             "name": user[1],
//             //             "lastName": user[2],
//             //             "age": user[3],
//             //             "username": user[4],
//             //             "password": user[5]
//             //         }
//             //     }
//             // });
//
//             x.open("PATCH", "/admin/edit/" + params);
//             x.send();
//
//     })
//
// };
//

