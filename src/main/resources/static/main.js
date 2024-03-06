const URL = "http://localhost:8080/api/v1/users/";

$(document).ready(function() {
    showHeader();
    showUser();
    showUserTable();

    $("#newUserForm").submit(function(event) {
        event.preventDefault();

        let serRoles = $("#roles").serializeArray();
        let roles = [];
        serRoles.forEach(el => el.value === "USER" ?
            roles.push({id: 2, role: "USER"}) :
            roles.push({id: 1, role: "ADMIN"}));

        createUser($("#firstname").val(),
            $("#lastname").val(),
            $("#age").val(),
            $("#username").val(),
            $("#pwd").val(),
            roles);

        $("#firstname").val("");
        $("#lastname").val("");
        $("#age").val("");
        $("#username").val("");
        $("#pwd").val("");
        $("#roles").children("option:selected").prop("selected", false);

    });

    $("#editUserModal").on("show.bs.modal", function(event) {
        let button = $(event.relatedTarget);
        let userId = button.data("userid");
        let modal = $("#editUserModal");

        showEditOrDeleteUserDialog(userId, modal);
    });

    $("#deleteUserModal").on("show.bs.modal", function (event) {
        let button = $(event.relatedTarget);
        let userId = button.data("userid");
        let modal = $("#deleteUserModal");

        showEditOrDeleteUserDialog(userId, modal);
    });

    $.ajax({
        url: URL + "principal",
        type: "GET",
        dataType: "json",
        async: false,
        success: function (principal) {
            if (principal.roles.every(el => el.role !== "ROLE_ADMIN")) {
                $("#admin").hide();
                $("#admin-nav").hide();
                $("#user-nav").addClass("active");
                $("#principal").addClass("active");
            }
        },
        error: (err) => console.log("Get principal error: " + err)
    });
});

function showHeader() {
    $.ajax({
        url: URL + "principal",
        type: "GET",
        dataType: "json",
        success: function (principal) {
            let navbar = $("#header");
            navbar.append(`
                    <span>${principal.username}</span> with roles:
                    <span>${principal.roles.map(r => r.role.substring(5)).join(" ")}</span>
                `);
        },
        error: (err) => console.log("Show header error: " + err)
    });
}

function showUser() {
    $.ajax({
        url: URL + "principal",
        type: "GET",
        dataType: "json",
        success: function (principal) {
            let user = $("#principal-body");
            user.append(`
                    <tr>
                        <td><a>${principal.id}</a></td>
                        <td><a>${principal.firstname}</a></td>
                        <td><a>${principal.lastname}</a></td>
                        <td><a>${principal.age}</a></td>
                        <td><a>${principal.username}</a></td>
                        <td><a>${principal.roles.map(r => r.role.substring(5)).join(" ")}</a></td>
                    </tr>
                `);
        },
        error: (err) => console.log("Show user error: " + err)
    });
}

function showUserTable() {
    $.ajax({
        url: URL,
        type: "GET",
        dataType: "json",
        success: function (users) {
            let userList = $("#table-body");
            userList.empty();
            users.forEach(function(user) {
                let row = `
                        <tr>
                          <td><a>${user.id}</a></td>
                          <td><a>${user.firstname}</a></td>
                          <td><a>${user.lastname}</a></td>
                          <td><a>${user.age}</a></td>
                          <td><a>${user.username}</a></td>
                          <td><a>${user.roles.map(r => r.role.substring(5)).join(" ")}</a></td>
                          <td><a role="button" class="btn btn-info text-light" data-toggle="modal" data-target="#editUserModal" data-userid="${user.id}">Edit</a></td>
                          <td><a role="button" class="btn btn-danger text-light" data-toggle="modal" data-target="#deleteUserModal" data-userid="${user.id}">Delete</a></td>
                      </tr>
                    `;
                userList.append(row);
            });
        },
        error: (err) => console.log("Show user table error: " + err)
    });
}

function createUser(firstname, lastname, age, username, password, roles) {

    let user = {
        firstname: firstname,
        lastname: lastname,
        age: age,
        username: username,
        password: password,
        roles: roles
    };

    console.log(user);

    $.ajax({
        url: URL,
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(user),
        success: function() {
            showUserTable();
            $("#new_user_nav").removeClass("active");
            $("#users_nav").addClass("active");
            $("#new_user_tab").removeClass("active");
            $("#users_tab").addClass("active");
        },
        error: (err) => console.log("Create user error: " + err)
    });
}

$("#editUserButton").click(function () {
    let modal = $("#editUserModal");
    let serRoles = modal.find("#roles").serializeArray();
    let roles = [];

    serRoles.forEach(el => el.value === "USER" ?
        roles.push({id: 2, role: "USER"}) :
        roles.push({id: 1, role: "ADMIN"}));

    let user = {
        id: modal.find("#userId").val(),
        firstname: modal.find("#firstName").val(),
        lastname: modal.find("#lastName").val(),
        age: modal.find("#userAge").val(),
        username: modal.find("#userName").val(),
        password: modal.find("#userPassword").val(),
        roles: roles
    }

    $.ajax({
        url: URL,
        type: "PUT",
        contentType: "application/json",
        data: JSON.stringify(user),
        success: function() {
            showUserTable();
            $("#editUserModal .modal-footer .btn-secondary").click();
        },
        error: (err) => console.log("Edit user error: " + err)
    });
});

$("#deleteUserButton").click(function () {
    $.ajax({
        url: URL + $("#deleteUserModal").find("#userId").val(),
        type: "DELETE",
        success: function() {
            showUserTable();
            $("#deleteUserModal .modal-footer .btn-secondary").click();
        },
        error: (err) => console.log("Delete user error: " + err)
    });
});

function showEditOrDeleteUserDialog(id, modal) {
    $.ajax({
        type: "GET",
        dataType: "json",
        url: URL + id,
        success: function (data) {
            modal.find("#userId").val(data.id);
            modal.find("#firstName").val(data.firstname);
            modal.find("#lastName").val(data.lastname);
            modal.find("#userAge").val(data.age);
            modal.find("#userName").val(data.username);
            modal.find("#userPassword").val(data.password);

        },
        error: (err) => console.log(err)
    });
}
