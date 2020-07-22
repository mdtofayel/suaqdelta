// Call the dataTables jQuery plugin
$(document).ready(function () {
    window.categoryTable = $('#category').DataTable({
        "sAjaxDataProp": "",
        "order": [[0, "asc"]],
        "sAjaxSource": "/manager/categoryList",
        "aoColumns": [
            {"mData": "id"},
            {"mData": "postType.name"},
            {"mData": "name"},
            {"mData": "creatAt"},
            {"mData": "updateAt"},
            {"mData": "id"}
        ],
        "columnDefs": [{
            "targets": 5,
            "data": null,
            "className": "center",
            "orderable": false,
            "render": function (data, type, row, meta) {
                return '<a href="#" onclick="editCategoryFunction(' + data + ')"  data-toggle="modal" data-target="#editModal"  class="btn btn-primary btn-icon-split btn-sm"> <span class="icon text-white-50"> <i class="fas fa-edit"></i> </span> <span class="text">Edit</span></a>'
            }
        }]
    });
});
$(document).ready(function () {
    window.subCategoryTable = $('#subCategory').DataTable({
        "sAjaxDataProp": "",
        "order": [[0, "asc"]],
        "sAjaxSource": "/manager/subcategoryList",
        "aoColumns": [
            {"mData": "id"},
            {"mData": "category.postType.name"},
            {"mData": "category.name"},
            {"mData": "postModel.modelName"},
            {"mData": "name"},
            {"mData": "creatAt"},
            {"mData": "updateAt"},
            {"mData": "id"}
        ],
        "columnDefs": [{
            "targets": 7,
            "data": null,
            "className": "center",
            "orderable": false,
            "render": function (data, type, row, meta) {
                return '<a href="#" onclick="editSubcategoryFunction(' + data + ')"  data-toggle="modal" data-target="#editModal"  class="btn btn-primary btn-icon-split btn-sm"> <span class="icon text-white-50"> <i class="fas fa-edit"></i> </span> <span class="text">Edit</span></a> <a href="#" onclick="addManufacturer(' + data + ')"   data-toggle="modal" data-target="#addmanufacture"  class="btn btn-success btn-icon-split btn-sm"> <span class="icon text-white-50"> <i class="fas fa-plus"></i> </span> <span class="text">Add Manufacture</span></a>'
            }
        }]
    });
});

$(document).ready(function () {
    window.postTypeTable = $('#postType').DataTable({
        "sAjaxDataProp": "",
        "order": [[0, "asc"]],
        "sAjaxSource": "/manager/getPostTypeList",
        "aoColumns": [
            {"mData": "id"},
            {"mData": "name"},
            {"mData": "creatAt"},
            {"mData": "updateAt"},
            {"mData": "id"}
        ],
        "columnDefs": [{
            "targets": 4,
            "data": null,
            "className": "center",
            "orderable": false,
            "render": function (data, type, row, meta) {
                return '<a href="#" onclick="editPostTypeFunction(' + data + ')"  data-toggle="modal" data-target="#editModal"  class="btn btn-primary btn-icon-split btn-sm"> <span class="icon text-white-50"> <i class="fas fa-edit"></i> </span> <span class="text">Edit</span></a>'
            }
        }]
    });
});


$(document).ready(function () {
    window.manufacturerTable = $('#manufacturerList').DataTable({
        "sAjaxDataProp": "",
        "order": [[0, "asc"]],
        "sAjaxSource": "/manager/manufacturerList",
        "aoColumns": [
            {"mData": "id"},
            {"mData": "name"},
            {"mData": "creatAt"},
            {"mData": "updateAt"},
            {"mData": "id"}
        ],
        "columnDefs": [{
            "targets": 4,
            "data": null,
            "className": "center",
            "orderable": false,
            "render": function (data, type, row, meta) {
                return '<a href="#" onclick="editManufacturerFunction(' + data + ')"  data-toggle="modal" data-target="#editModal"  class="btn btn-primary btn-icon-split btn-sm"> <span class="icon text-white-50"> <i class="fas fa-edit"></i> </span> <span class="text">Edit</span></a>'
            }
        }]
    })
});

$(document).ready(function () {
    window.modelTable = $('#modelList').DataTable({
        "sAjaxDataProp": "",
        "order": [[0, "asc"]],
        "sAjaxSource": "/manager/productModelList",
        "aoColumns": [
            {"mData": "id"},
            {"mData": "manufacturer.name"},
            {"mData": "name"},
            {"mData": "creatAt"},
            {"mData": "updateAt"},
            {"mData": "id"}
        ],
        "columnDefs": [{
            "targets": 5,
            "data": null,
            "className": "center",
            "orderable": false,
            "render": function (data, type, row, meta) {
                return '<a href="#" onclick="editModelFunction(' + data + ')"  data-toggle="modal" data-target="#editModal"  class="btn btn-primary btn-icon-split btn-sm"> <span class="icon text-white-50"> <i class="fas fa-edit"></i> </span> <span class="text">Edit</span></a>'
            }
        }]
    })
});

/*$(document).ready(function () {
    window.modelTable = $('#modelList').DataTable({
        "sAjaxDataProp": "",
        "order": [[0, "asc"]],
        "sAjaxSource": "/manager/productModelList",
        "aoColumns": [
            {"mData": "id"},
            {"mData": "manufacturer.name"},
            {"mData": "name"},
            {"mData": "creatAt"},
            {"mData": "updateAt"},
            {"mData": "id"}
        ],
        "columnDefs": [{
            "targets": 5,
            "data": null,
            "className": "center",
            "orderable": false,
            "render": function (data, type, row, meta) {
                return '<a href="#" onclick="editModelFunction(' + data + ')"  data-toggle="modal" data-target="#editModal"  class="btn btn-primary btn-icon-split btn-sm"> <span class="icon text-white-50"> <i class="fas fa-edit"></i> </span> <span class="text">Edit</span></a>'
            }
        }]
    })
});*/

$(document).ready(function () {
    window.usersTable = $('#users').DataTable({
        "sAjaxDataProp": "",
        "order": [[0, "asc"]],
        "sAjaxSource": "/admin/viewRestUsers",
        "aoColumns": [
            {"mData": "id"},
            {"mData": "email"},
            {
                "mData": function (data) {
                    var getRoles = "";
                    for (var i = 0; i < data.roles.length; i++) {
                        if (i > 0) {
                            getRoles += ', ';
                        }
                        getRoles += data.roles[i].role;
                    }

                    return getRoles;
                }
            },
            {"mData": "active"},
            {"mData": "createdDate"},
            {"mData": "updatedDate"},
            {"mData": "id"}
        ],
        "columnDefs": [{
            "targets": 6,
            "data": null,
            "className": "center",
            "orderable": false,
            "render": function (data, type, row, meta) {
                return '<a href="#" onclick="editUserFunction(' + data + ')"  data-toggle="modal" data-target="#editModal"  class="btn btn-primary btn-icon-split btn-sm"> <span class="icon text-white-50"> <i class="fas fa-edit"></i> </span> <span class="text">Edit</span></a>'
            }
        }]
    });
});


/*$(document).ready(function () {
    $('#allAdsDataTables').DataTable(
        {
            "processing": true,
            "serverSide": true,
            "pageLength": 5,
            "ajax": {
                "url": "/users/paginated",
                "data": function ( data ) {
                    //process data before sent to server.
                }},
            "columns": [
                { "data": "id", "name" : "ID", "title" : "ID"  },
                { "data": "name", "name" : "Name" , "title" : "Name"},
                { "data": "salary", "name" : "Salary" , "title" : "Salary"}
            ]
        });

    $('#paginatedTable').dataTable().fnSetFilteringEnterPress();
    )});*/

$(document).ready(function () {
    $('#dataTable2').DataTable();
});
$(document).ready(function () {
    $('#dataTable3').DataTable();
});
$(document).ready(function () {
    $('#dataTable4').DataTable();
});
$(document).ready(function () {
    $('#dataTable5').DataTable();
});
