// переключение между вкладками
$('#myTab a').on('click', function (e) {
    e.preventDefault()
    $(this).tab('show')
});

(async () => {

    const roles = ["admin", "user"];
    const authority = ["ROLE_ADMIN", "ROLE_USER"];

    const apiAdminUrl = "/api/admin/users";

    const newUserTab = $("#new_user");

    const editModal = $("#editUser");
    const rolesPlaceEditModal = $(".roles-edit-label");
    const submitEditButton = $(".save-edit-user")[0];

    const removeModal = $("#deleteUser");
    const rolesPlaceRemoveModal = $(".roles-remove-label");
    const submitRemoveButton = $(".remove-user")[0];


    const users = await getAllUsers(apiAdminUrl);

    initPage();

    // Обработчики отправляющие запрос на сервер
    function submitEditUser(id) {
        return async function editUser() {
            let newRoles = [];
            for (let i = 0; i < authority.length; i++) {
                if ($("#role-edit-" + (i + 1)).is(":checked")) {
                    newRoles.push({
                        authority: authority[i],
                    });
                }
            }

            let newUser = {
                id: id,
                name: editModal.find("input[name=name]").val(),
                lastName: editModal.find("input[name=lastName]").val(),
                age: editModal.find("input[name=age]").val(),
                username: editModal.find("input[name=username]").val(),
                password: editModal.find("input[name=password]").val(),
                roles: newRoles
            }
            let response = await fetch(apiAdminUrl, {
                method: 'PATCH',
                headers: {
                    'Content-Type': 'application/json;charset=utf-8'
                },
                body: JSON.stringify(newUser)
            });
            if (response.status === 200) {
                editUserRow(newUser)
                editModal.modal('hide');
                this.removeEventListener("click", editUser);
            } else {
                editModal.find("input[name=username]").addClass("is-invalid")
            }
        };
    }

    function submitRemoveUser(id) {
        return async function removeUser() {
            let response = await fetch(apiAdminUrl + "/" + id, {
                method: 'DELETE',
            });
            if (response.status === 200) {
                removeUserRow(id);
                removeModal.modal('hide');
            }
            this.removeEventListener("click", removeUser);
        };
    }


    // Обработчики нажатия на кнопки в строках таблицы
    function clickEditUser(user) {
        return function () {
            editModal.find("input[name='name']").val(user.name);
            editModal.find("input[name='lastName']").val(user.lastName);
            editModal.find("input[name='age']").val(user.age);
            editModal.find("input[name='username']").val(user.username);
            if (!rolesPlaceEditModal[0].hasChildNodes()) {
                rolesPlaceEditModal.text("Roles:");
                for (let i = 0; i < authority.length; i++) {
                    rolesPlaceEditModal.append("<label class='font-weight-normal form-check-label px-2' " +
                        "for='role-edit-" + (i + 1) + "'>" + roles[i] + "</label>")
                        .append("<input class='form-check-input px-1 roles-check' type='checkbox' id='role-edit-" + (i + 1) + "'>");
                }
            }
            let rolesChecks = editModal.find(".roles-check");
            for (let i = 0; i < authority.length; i++) {
                rolesChecks[i].checked = !!user.roles.some(e => e.authority === authority[i]);
            }
            editModal.modal("show");
            submitEditButton.addEventListener("click", submitEditUser(user.id))
        };
    }

    function clickRemoveUser(user) {
        return function () {
            removeModal.find("input[name='name']").val(user.name);
            removeModal.find("input[name='lastName']").val(user.lastName);
            removeModal.find("input[name='age']").val(user.age);
            removeModal.find("input[name='username']").val(user.username);
            if (!rolesPlaceRemoveModal[0].hasChildNodes()) {
                rolesPlaceRemoveModal.text("Roles:");
                for (let i = 0; i < authority.length; i++) {
                    rolesPlaceRemoveModal.append("<label class='font-weight-normal form-check-label px-2' " +
                        "for='role-remove-" + (i + 1) + "'>" + roles[i] + "</label>")
                        .append("<input class='form-check-input px-1 roles-check' type='checkbox' id='role-remove-" + (i + 1) + "' disabled readonly>");
                }
            }
            let rolesChecks = removeModal.find(".roles-check");
            for (let i = 0; i < authority.length; i++) {
                rolesChecks[i].checked = !!user.roles.some(e => e.authority === authority[i]);
            }
            removeModal.modal("show");
            submitRemoveButton.addEventListener("click", submitRemoveUser(user.id))
        };
    }


    // Изменение отображения строк в таблице
    function editUserRow(user) {
        let myRow = $("#user-row-" + user.id);
        myRow.children(".name").text(user.name);
        myRow.children(".last_name").text(user.lastName);
        myRow.children(".age").text(user.age);
        myRow.children(".username").text(user.username);
        myRow.children(".roles_cell").empty()

        for (let i = 0; i < user.roles.length; i++) {
            let shortRole = user.roles[i].authority.slice(5).toLowerCase();
            myRow.children(".roles_cell").append('<span>' + shortRole + (i === user.roles.length - 1 ? '' : ', ') + '</span>');
        }
    }

    function removeUserRow(id) {
        $("#user-row-" + id).empty();
    }

    function addUserRow(user) {
        $(".all-users-table > tbody")
            .append($("<tr class=user_row id=user-row-" + user.id + ">"));
        $("tr .user_row:last")
            .append($("<td class=user_id>").text(user.id))
            .append($("<td class='name'>").text(user.name))
            .append($("<td class='last_name'>").text(user.lastName))
            .append($("<td class='age'>").text(user.age))
            .append($("<td class='username'>").text(user.username))
            .append($("<td class='roles_cell'>"))
            .append($("<td>")
                .append($("<button class=\"btn btn-primary edit_btn\">").text("Edit")))
            .append($("<td>")
                .append($("<button class=\"btn btn-danger remove_btn\">").text("Delete"))
            );
        for (let i = 0; i < user.roles.length; i++) {
            let shortRole = user.roles[i].authority.slice(5).toLowerCase();
            $(".roles_cell:last").append('<span>' + shortRole + (i === user.roles.length - 1 ? '' : ', ') + '</span>');
        }
        $(".edit_btn:last")[0].addEventListener("click", clickEditUser(user));
        $(".remove_btn:last")[0].addEventListener("click", clickRemoveUser(user));
    }


    // Инициализация страницы
    function initPage() {
        // initHeadAndBar();
        initBody();
        initNewUserTab();
    }

    function initHeadAndBar() {
        $(".user_link").text(this_user.username);
        $('.navbar-brand').append("<b>" + this_user.username + "</b> with roles ");
        for (let i = 0; i < this_user.roles.length; i++) {
            let shortRole = this_user.roles[i].authority.slice(5).toLowerCase();
            $(".navbar-brand").append('<span>' + shortRole + (i === this_user.roles.length - 1 ? '' : ', ') + '</span>');
        }
    }

    function initBody() {
        for (let user of users) {
            addUserRow(user);
        }
    }

    function initNewUserTab() {


        for (let i = 0; i < roles.length; i++) {
            newUserTab.find(".roles-label").append("<label class='font-weight-normal form-check-label pe-2' " +
                "for='role-" + (i + 1) + "'>" + roles[i] + "</label>")
                .append("<input class='form-check-input px-1' type='checkbox' id='role-" + (i + 1) + "'>");
        }

        newUserTab.find(".btn-add-user")[0].addEventListener("click", async function (e) {
            e.preventDefault();
            //
            let new_roles = [];
            for (let i = 0; i < roles.length; i++) {
                if ($("#role-" + (i + 1)).is(":checked")) {
                    new_roles.push({
                        authority: authority[i],
                    });
                }
            }

            let user = {
                name: newUserTab.find("input[name=name]").val(),
                lastName: newUserTab.find("input[name=lastName]").val(),
                age: newUserTab.find("input[name=age]").val(),
                username: newUserTab.find("input[name=username]").val(),
                password: newUserTab.find("input[name=password]").val(),
                roles: new_roles
            }
            let response = await fetch(apiAdminUrl, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json;charset=utf-8'
                },
                body: JSON.stringify(user)
            });
            if (response.ok) {
                let new_user = await response.json();
                if(new_user.id != null) {
                    user = new_user;
                    users.push(user);
                }
                addUserRow(user);
                $('#myTab a[href="#users_table"]').tab('show')
            } else {
                newUserTab.find("input[name=username]").addClass("is-invalid");
            }

        });
    }

    // util-функции
    async function getAllUsers(url) {
        let response = await fetch(url);
        return await response.json();
    }
})();
