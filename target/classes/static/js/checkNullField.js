$(function () {
    $("#postTitle").focusout(function () {
        var fname = $("#postTitle").val();
        if (fname !== '') {
            $("#postTitle").css("border", "2px solid green");

        } else {
            $("#postTitle").css("border", "2px solid red")
        }
    });
    $("#postDiscription").focusout(function () {
        var fname = $("#postDiscription").val();
        if (fname !== '') {
            $("#postDiscription").css("border", "2px solid green");

        } else {
            $("#postDiscription").css("border", "2px solid red")
        }
    });
    $("#postPrice").focusout(function () {
        var fname = $("#postPrice").val();
        if (fname !== '') {
            $("#postPrice").css("border", "2px solid green");

        } else {
            $("#postPrice").css("border", "2px solid red")
        }
    });
    $("#postContactNumber").focusout(function () {
        var fname = $("#postContactNumber").val();
        if (fname !== '') {
            $("#postContactNumber").css("border", "2px solid green");

        } else {
            $("#postContactNumber").css("border", "2px solid red")
        }
    });

    $("#postEmail").focusout(function () {
        var fname = $("#postEmail").val();
        if (fname !== '') {
            $("#postEmail").css("border", "2px solid green");

        } else {
            $("#postEmail").css("border", "2px solid red")
        }
    });
    $("#mapsearch").focusout(function () {
        var fname = $("#mapsearch").val();
        if (fname !== '') {
            $("#mapsearch").css("border", "2px solid green");

        } else {
            $("#mapsearch").css("border", "2px solid red")
        }
    });
    $("#maplong").focusout(function () {
        var fname = $("#maplong").val();
        if (fname !== '') {
            $("#maplong").css("border", "2px solid green");

        } else {
            $("#maplong").css("border", "2px solid red")
        }
    });
    $("#maplat").focusout(function () {
        var fname = $("#maplat").val();
        if (fname !== '') {
            $("#maplat").css("border", "2px solid green");

        } else {
            $("#maplat").css("border", "2px solid red")
        }
    });
    /*$("#fileselect").focusout(function () {
        var fname = $("#fileselect").val();
        if (fname !== '') {
            $("#fileselect").css("border", "2px solid green");

        } else {
            $("#fileselect").css("border", "2px solid red")
        }
    });*/
    $("#videoSelect").focusout(function () {
        var fname = $("#videoSelect").val();
        if (fname !== '') {
            $("#videoSelect").css("border", "2px solid green");

        } else {
            $("#videoSelect").css("border", "2px solid red")
        }
    });
    $("#modelManu").focusout(function () {
        var fname = $("#modelManu").val();
        if (fname !== '') {
            $("#modelManu").css("border", "2px solid green");

        } else {
            $("#modelManu").css("border", "2px solid red")
        }
    });
    $("#modelCondition").focusout(function () {
        var fname = $("#modelCondition").val();
        if (fname !== '') {
            $("#modelCondition").css("border", "2px solid green");

        } else {
            $("#modelCondition").css("border", "2px solid red")
        }
    });
    $("#modelModel").focusout(function () {
        var fname = $("#modelModel").val();
        if (fname !== '') {
            $("#modelModel").css("border", "2px solid green");

        } else {
            $("#modelModel").css("border", "2px solid red")
        }
    });
    $("#modelRam").focusout(function () {
        var fname = $("#modelRam").val();
        if (fname !== '') {
            $("#modelRam").css("border", "2px solid green");

        } else {
            $("#modelRam").css("border", "2px solid red")
        }
    });
    $("#modelPros").focusout(function () {
        var fname = $("#modelPros").val();
        if (fname !== '') {
            $("#modelPros").css("border", "2px solid green");

        } else {
            $("#modelPros").css("border", "2px solid red")
        }
    });
    $("#modelColor").focusout(function () {
        var fname = $("#modelColor").val();
        if (fname !== '') {
            $("#modelColor").css("border", "2px solid green");

        } else {
            $("#modelColor").css("border", "2px solid red")
        }
    });
    $("#modelStorage").focusout(function () {
        var fname = $("#modelStorage").val();
        if (fname !== '') {
            $("#modelStorage").css("border", "2px solid green");

        } else {
            $("#modelStorage").css("border", "2px solid red")
        }
    });
    $("#modelSimType").focusout(function () {
        var fname = $("#modelSimType").val();
        if (fname !== '') {
            $("#modelSimType").css("border", "2px solid green");

        } else {
            $("#modelSimType").css("border", "2px solid red")
        }
    });
    $("#modelNetType").focusout(function () {
        var fname = $("#modelNetType").val();
        if (fname !== '') {
            $("#modelNetType").css("border", "2px solid green");

        } else {
            $("#modelNetType").css("border", "2px solid red")
        }
    });
    $("#modelFrontCamera").focusout(function () {
        var fname = $("#modelFrontCamera").val();
        if (fname !== '') {
            $("#modelFrontCamera").css("border", "2px solid green");

        } else {
            $("#modelFrontCamera").css("border", "2px solid red")
        }
    });
    $("#modelBackCamera").focusout(function () {
        var fname = $("#modelBackCamera").val();
        if (fname !== '') {
            $("#modelBackCamera").css("border", "2px solid green");

        } else {
            $("#modelBackCamera").css("border", "2px solid red")
        }
    });
});


/***************Submitting post***********/

$('#submitButtonSmartPhones').on('click', function (e) {

    /***********Append data for post form************/
    var formDataPost = new FormData();

    var editPostId = $('#editPostId').val();
    if (editPostId > -1) {
        formDataPost.append("id", editPostId);
    }

    var postStatus = $('#editPostStatus').val();

    if (postStatus == true || postStatus == false) {
        formDataPost.append("postStatus.sold", postStatus);
    }

    // var formDataForModel = new FormData();
    formDataPost.append("title", $("#postTitle").val());
    formDataPost.append("description", $("#postDiscription").val());
    formDataPost.append("price", $("#postPrice").val());
    formDataPost.append("contactNumber", $("#postContactNumber").val());
    formDataPost.append("email ", $("#postEmail").val());
    formDataPost.append("address", $("#mapsearch").val());
    formDataPost.append("longitude", $("#maplong").val());
    formDataPost.append("latitude", $("#maplat").val());

    $.each($("#fileselect")[0].files, function (index, value) {
        formDataPost.append("photos", value);
    });
    $.each($("#videoSelect")[0].files, function (index, value) {
        formDataPost.append("videos", value);
    });

    formDataPost.append("subCategory.id", $("#subcategory").val());

    /******Append data for Model************/

    formDataPost.append("smartPhones.manufacturer", $("#modelManu").val());
    formDataPost.append("smartPhones.condition", $("#modelCondition").val());
    formDataPost.append("smartPhones.model", $("#modelModel").val());
    formDataPost.append("smartPhones.ram", $("#modelRam").val());
    formDataPost.append("smartPhones.processor", $("#modelPros").val());
    formDataPost.append("smartPhones.color", $("#modelColor").val());
    formDataPost.append("smartPhones.storage", $("#modelStorage").val());
    formDataPost.append("smartPhones.simType", $("#modelSimType").val());
    formDataPost.append("smartPhones.networkType", $("#modelNetType").val());
    formDataPost.append("smartPhones.frontCamera", $("#modelFrontCamera").val());
    formDataPost.append("smartPhones.backCamera", $("#modelBackCamera").val());
    formDataPost.append("smartPhones.display", $("#smartPhoneDisplay").val());


    var title = $("#postTitle").val();
    var discr = $("#postDiscription").val();
    var price = $("#postPrice").val();
    var contactNo = $("#postContactNumber").val();
    var email = $("#postEmail").val();
    var search = $("#mapsearch").val();
    var maplong = $("#maplong").val();
    var maplat = $("#maplat").val();
    var file = $("#fileselect").val();
    var video = $("#videoSelect").val();
    var modelManu = $("#modelManu").val();
    var modelCondition = $("#modelCondition").val();
    var modelModel = $("#modelModel").val();
    var modelRam = $("#modelRam").val();
    var modelPros = $("#modelPros").val();
    var modelColor = $("#modelColor").val();
    var modelStorage = $("#modelStorage").val();
    var modelSimType = $("#modelSimType").val();
    var modelNetType = $("#modelNetType").val();
    var modelFrontCamera = $("#modelFrontCamera").val();
    var modelBackCamera = $("#modelBackCamera").val();
    /*console.log(title);*/
    if (title == '') {
        $("#postTitle").css("border", "2px solid red");
        $("#postTitle").focus();
        return false;
    } else if (discr == '') {

        $("#postDiscription").css("border", "2px solid red");
        $("#postDiscription").focus();
        return false;

    } else if (price == '') {
        $("#postPrice").css("border", "2px solid red");
        $("#postPrice").focus();
        return false;
    } else if (contactNo == '') {
        $("#postContactNumber").css("border", "2px solid red");
        $("#postContactNumber").focus();
        return false;
    } else if (email == '') {
        $("#postEmail").css("border", "2px solid red");
        $("#postEmail").focus();
        return false;
    } else if (search == '') {
        $("#mapsearch").css("border", "2px solid red");
        $("#mapsearch").focus();
        return false;
    } else if (maplong == '') {
        $("#maplong").css("border", "2px solid red");
        $("#maplong").focus();
        return false;
    } else if (maplat == '') {
        $("#maplat").css("border", "2px solid red");
        $("#maplat").focus();
        return false;

    } else if (video == '') {
        $("#videoSelect").css("border", "2px solid red");
        $("#videoSelect").focus();
        return false;
    } else if (modelManu == '') {
        $("#modelManu").css("border", "2px solid red");
        $("#modelManu").focus();
        return false;
    } else if (modelCondition == '') {
        $("#modelCondition").css("border", "2px solid red");
        $("#modelCondition").focus();
        return false;
    } else if (modelModel == '') {
        $("#modelModel").css("border", "2px solid red");
        $("#modelModel").focus();
        return false;
    } else if (modelRam == '') {
        $("#modelRam").css("border", "2px solid red");
        $("#modelRam").focus();
        return false;
    } else if (modelPros == '') {
        $("#modelPros").css("border", "2px solid red");
        $("#modelPros").focus();
        return false;
    } else if (modelColor == '') {
        $("#modelColor").css("border", "2px solid red");
        $("#modelColor").focus();
        return false;
    } else if (modelStorage == '') {
        $("#modelStorage").css("border", "2px solid red");
        $("#modelStorage").focus();
        return false;
    } else if (modelSimType == '') {
        $("#modelSimType").css("border", "2px solid red");
        $("#modelSimType").focus();
        return false;
    } else if (modelNetType == '') {
        $("#modelNetType").css("border", "2px solid red");
        $("#modelNetType").focus();
        return false;
    } else if (modelFrontCamera == '') {
        $("#modelFrontCamera").css("border", "2px solid red");
        $("#modelFrontCamera").focus();
        return false;
    } else if (modelBackCamera == '') {
        $("#modelBackCamera").css("border", "2px solid red");
        $("#modelBackCamera").focus();
        return false;
    }


    $.ajax({
        url: '/userPost/savePostSmartPhone',
        type: 'POST',
        data: formDataPost,
        cache: false,
        contentType: false,
        enctype: 'multipart/form-data',
        processData: false,
        success: function (data) {
            $("#successModal #modalMessage").html(data.response);
            $('#successModal').modal('show');
        },
        error: function (response, textStatus, errorThrown) {
            $("#successModal #modalMessage").html(response.responseText);
            $('#successModal').modal('show');
        }
    })

});


$('#submitButtonPetAnimal').on('click', function (e) {

    /***********Append data for post form************/

    var formDataPost = new FormData();
    // var formDataForModel = new FormData();

    var editPostId = $('editPostId').val();
    if (editPostId > -1) {
        formDataPost.append("id", editPostId);
    }

    var postStatus = $('#editPostStatus').val();

    if (postStatus == true || postStatus == false) {
        formDataPost.append("postStatus.sold", postStatus);
    }

    formDataPost.append("title", $("#postTitle").val());
    formDataPost.append("description", $("#postDiscription").val());
    formDataPost.append("price", $("#postPrice").val());
    formDataPost.append("contactNumber", $("#postContactNumber").val());
    formDataPost.append("email ", $("#postEmail").val());
    formDataPost.append("address", $("#mapsearch").val());
    formDataPost.append("longitude", $("#maplong").val());
    formDataPost.append("latitude", $("#maplat").val());
    $.each($("#fileselect")[0].files, function (index, value) {
        formDataPost.append("photos", value);
    });
    $.each($("#videoSelect")[0].files, function (index, value) {
        formDataPost.append("videos", value);
    });

    formDataPost.append("subCategory.id", $("#subcategory").val());

    /******Append data for Model************/

    formDataPost.append("petAnimal.age", $("#petAge").val());
    formDataPost.append("petAnimal.quantity", $("#petQuantity").val());
    formDataPost.append("petAnimal.gender", $("#petGender").val());
    formDataPost.append("petAnimal.color", $("#petColor").val());


    $.ajax({
        url: '/userPost/savePostPetAnimal',
        type: 'POST',
        data: formDataPost,

        cache: false,
        contentType: false,
        enctype: 'multipart/form-data',
        processData: false,
        success: function (data) {
            $("#successModal #modalMessage").html(data.response);
            $('#successModal').modal('show');
        },
        error: function (response, textStatus, errorThrown) {
            $("#successModal #modalMessage").html(response.responseText);
            $('#successModal').modal('show');
        }
    })
});

$('#submitButtonHire').on('click', function (e) {

    /***********Append data for post form************/

    var formDataPost = new FormData();
    // var formDataForModel = new FormData();

    var editPostId = $('editPostId').val();
    if (editPostId > -1) {
        formDataPost.append("id", editPostId);
    }

    var postStatus = $('#editPostStatus').val();

    if (postStatus == true || postStatus == false) {
        formDataPost.append("postStatus.sold", postStatus);
    }

    formDataPost.append("title", $("#postTitle").val());
    formDataPost.append("description", $("#postDiscription").val());
    formDataPost.append("price", $("#postPrice").val());
    formDataPost.append("contactNumber", $("#postContactNumber").val());
    formDataPost.append("email ", $("#postEmail").val());
    formDataPost.append("address", $("#mapsearch").val());
    formDataPost.append("longitude", $("#maplong").val());
    formDataPost.append("latitude", $("#maplat").val());
    $.each($("#fileselect")[0].files, function (index, value) {
        formDataPost.append("photos", value);
    });
    $.each($("#videoSelect")[0].files, function (index, value) {
        formDataPost.append("videos", value);
    });

    formDataPost.append("subCategory.id", $("#subcategory").val());

    /******Append data for Model************/

    formDataPost.append("hire.employmentType", $("#hireEmploymentType").val());
    formDataPost.append("hire.position", $("#hirePosition").val());
    formDataPost.append("hire.type", $("#hireType").val());
    formDataPost.append("hire.jobLevel", $("#hireJobLevel").val());
    formDataPost.append("hire.language", $("#hireLnagauge").val());
    formDataPost.append("hire.nationality", $("#hireNationality").val());
    formDataPost.append("hire.gender", $("#hireGender").val());
    formDataPost.append("hire.experiences", $("#hireExperiences").val());
    formDataPost.append("hire.salary", $("#hireSalary").val());
    formDataPost.append("hire.companyName", $("#hireCompanyName").val());
    formDataPost.append("hire.Url", $("#hireUrl").val());
    formDataPost.append("hire.email", $("#hireEmail").val());


    $.ajax({
        url: '/userPost/saveHire',
        type: 'POST',
        data: formDataPost,

        cache: false,
        contentType: false,
        enctype: 'multipart/form-data',
        processData: false,
        success: function (data) {
            $("#successModal #modalMessage").html(data.response);
            $('#successModal').modal('show');
        },
        error: function (response, textStatus, errorThrown) {
            $("#successModal #modalMessage").html(response.responseText);
            $('#successModal').modal('show');
        }
    })
});

/*---------------this for jobseeker---------------------------*/
$('#submitButtonJobSeeker').on('click', function (e) {

    /***********Append data for post form************/

    var formDataPost = new FormData();
    // var formDataForModel = new FormData();

    var editPostId = $('editPostId').val();
    if (editPostId > -1) {
        formDataPost.append("id", editPostId);
    }

    var postStatus = $('#editPostStatus').val();

    if (postStatus == true || postStatus == false) {
        formDataPost.append("postStatus.sold", postStatus);
    }

    formDataPost.append("title", $("#postTitle").val());
    formDataPost.append("description", $("#postDiscription").val());
    formDataPost.append("price", $("#postPrice").val());
    formDataPost.append("contactNumber", $("#postContactNumber").val());
    formDataPost.append("email ", $("#postEmail").val());
    formDataPost.append("address", $("#mapsearch").val());
    formDataPost.append("longitude", $("#maplong").val());
    formDataPost.append("latitude", $("#maplat").val());
    $.each($("#fileselect")[0].files, function (index, value) {
        formDataPost.append("photos", value);
    });
    $.each($("#videoSelect")[0].files, function (index, value) {
        formDataPost.append("videos", value);
    });

    formDataPost.append("subCategory.id", $("#subcategory").val());

    /******Append data for Model************/

    formDataPost.append("jobSeeker.employmentType", $("#seekerEmplType").val());
    formDataPost.append("jobSeeker.position", $("#seekerPosition").val());
    formDataPost.append("jobSeeker.type", $("#seekerType").val());
    formDataPost.append("jobSeeker.language", $("#seekerLanguage").val());
    formDataPost.append("jobSeeker.nationality", $("#seekerNatoinality").val());
    formDataPost.append("jobSeeker.gender", $("#seekerGender").val());
    formDataPost.append("jobSeeker.experiences", $("#seekerExperience").val());
    formDataPost.append("jobSeeker.expectedSalary", $("#seekerExptdSalary").val());


    $.ajax({
        url: '/userPost/saveJobSeeker',
        type: 'POST',
        data: formDataPost,

        cache: false,
        contentType: false,
        enctype: 'multipart/form-data',
        processData: false,
        success: function (data) {
            $("#successModal #modalMessage").html(data.response);
            $('#successModal').modal('show');
        },
        error: function (response, textStatus, errorThrown) {
            $("#successModal #modalMessage").html(response.responseText);
            $('#successModal').modal('show');
        }
    })
});
/***************this Script work for Land ************/

$('#submitButtonProperties').on('click', function (e) {

    /***********Append data for post form************/

    var formDataPost = new FormData();

    var editPostId = $('editPostId').val();
    if (editPostId > -1) {
        formDataPost.append("id", editPostId);
    }

    var postStatus = $('#editPostStatus').val();

    if (postStatus == true || postStatus == false) {
        formDataPost.append("postStatus.sold", postStatus);
    }

    // var formDataForModel = new FormData();
    formDataPost.append("title", $("#postTitle").val());
    formDataPost.append("description", $("#postDiscription").val());
    formDataPost.append("price", $("#postPrice").val());
    formDataPost.append("contactNumber", $("#postContactNumber").val());
    formDataPost.append("email", $("#postEmail").val());
    formDataPost.append("address", $("#mapsearch").val());
    formDataPost.append("longitude", $("#maplong").val());
    formDataPost.append("latitude", $("#maplat").val());
    $.each($("#fileselect")[0].files, function (index, value) {
        formDataPost.append("photos", value);
    });
    $.each($("#videoSelect")[0].files, function (index, value) {
        formDataPost.append("videos", value);
    });

    formDataPost.append("subCategory.id", $("#subcategory").val());

    /******Append data for Model************/

    formDataPost.append("properties.pricePerFeet", $("#landPricePerFeet").val());
    formDataPost.append("properties.area", $("#landArea").val());

    $.ajax({
        url: '/userPost/saveProperties',
        type: 'POST',
        data: formDataPost,

        cache: false,
        contentType: false,
        enctype: 'multipart/form-data',
        processData: false,
        success: function (data) {
            $("#successModal #modalMessage").html(data.response);
            $('#successModal').modal('show');
        },
        error: function (response, textStatus, errorThrown) {
            $("#successModal #modalMessage").html(response.responseText);
            $('#successModal').modal('show');
        }
    })
});

/***************These Script work for Vehicles*************/

$('#submitButtonVehicles').on('click', function (e) {

    /***********Append data for post form************/

    var formDataPost = new FormData();
    // var formDataForModel = new FormData();

    var editPostId = $('editPostId').val();
    if (editPostId > -1) {
        formDataPost.append("id", editPostId);
    }

    var postStatus = $('#editPostStatus').val();

    if (postStatus == true || postStatus == false) {
        formDataPost.append("postStatus.sold", postStatus);
    }

    formDataPost.append("title", $("#postTitle").val());
    formDataPost.append("description", $("#postDiscription").val());
    formDataPost.append("price", $("#postPrice").val());
    formDataPost.append("contactNumber", $("#postContactNumber").val());
    formDataPost.append("email", $("#postEmail").val());
    formDataPost.append("address", $("#mapsearch").val());
    formDataPost.append("longitude", $("#maplong").val());
    formDataPost.append("latitude", $("#maplat").val());
    $.each($("#fileselect")[0].files, function (index, value) {
        formDataPost.append("photos", value);
    });
    $.each($("#videoSelect")[0].files, function (index, value) {
        formDataPost.append("videos", value);
    });

    formDataPost.append("subCategory.id", $("#subcategory").val());

    /******Append data for Model************/

    formDataPost.append("vehicles.manufacturer", $("#vehicleManufacturer").val());
    formDataPost.append("vehicles.condition", $("#vehcilesCondition").val());
    formDataPost.append("vehicles.model", $("#vehiclesModel").val());
    formDataPost.append("vehicles.manufacturYear", $("#vehicleManufacturYear").val());
    formDataPost.append("vehicles.fuelType", $("#vehiclesFuelType").val());
    formDataPost.append("vehicles.bodyType", $("#vehicleBodyType").val());
    formDataPost.append("vehicles.numberOfDoor", $("#vehicleDoor").val());
    formDataPost.append("vehicles.color", $("#vehiclesColor").val());
    formDataPost.append("vehicles.transmission", $("#vehiclesTransmission").val());
    formDataPost.append("vehicles.serviceHistory", $("#vehicleServiceHistory").val());
    formDataPost.append("vehicles.mileage", $("#vehiclesMileage").val());
    formDataPost.append("vehicles.engineSize", $("#vehiclesEngineSize").val());


    $.ajax({
        url: '/userPost/saveVehicles',
        type: 'POST',
        data: formDataPost,

        cache: false,
        contentType: false,
        enctype: 'multipart/form-data',
        processData: false,
        success: function (data) {
            $("#successModal #modalMessage").html(data.response);
            $('#successModal').modal('show');
        },
        error: function (response, textStatus, errorThrown) {
            $("#successModal #modalMessage").html(response.responseText);
            $('#successModal').modal('show');
        }
    })
});


/***************These Script work for Laptop*************/

$('#submitButtonLaptop').on('click', function (e) {

    /***********Append data for post form************/

    var formDataPost = new FormData();
    // var formDataForModel = new FormData();

    var editPostId = $('editPostId').val();
    if (editPostId > -1) {
        formDataPost.append("id", editPostId);
    }

    var postStatus = $('#editPostStatus').val();

    if (postStatus == true || postStatus == false) {
        formDataPost.append("postStatus.sold", postStatus);
    }

    formDataPost.append("title", $("#postTitle").val());
    formDataPost.append("description", $("#postDiscription").val());
    formDataPost.append("price", $("#postPrice").val());
    formDataPost.append("contactNumber", $("#postContactNumber").val());
    formDataPost.append("email", $("#postEmail").val());
    formDataPost.append("address", $("#mapsearch").val());
    formDataPost.append("longitude", $("#maplong").val());
    formDataPost.append("latitude", $("#maplat").val());
    $.each($("#fileselect")[0].files, function (index, value) {
        formDataPost.append("photos", value);
    });
    $.each($("#videoSelect")[0].files, function (index, value) {
        formDataPost.append("videos", value);
    });

    formDataPost.append("subCategory.id", $("#subcategory").val());

    /******Append data for Model************/

    formDataPost.append("laptop.manufacturer", $("#laptopManfacturer").val());
    formDataPost.append("laptop.condition", $("#laptopCondition").val());
    formDataPost.append("laptop.ram", $("#laptopRam").val());
    formDataPost.append("laptop.processor", $("#laptopProcessor").val());
    formDataPost.append("laptop.storage", $("#laptopTstorage").val());
    formDataPost.append("laptop.Display", $("#laptopDisplay").val());


    $.ajax({
        url: '/userPost/saveLaptop',
        type: 'POST',
        data: formDataPost,

        cache: false,
        contentType: false,
        enctype: 'multipart/form-data',
        processData: false,
        success: function (data) {
            $("#successModal #modalMessage").html(data.response);
            $('#successModal').modal('show');
        },
        error: function (response, textStatus, errorThrown) {
            $("#successModal #modalMessage").html(response.responseText);
            $('#successModal').modal('show');
        }
    })
});


/***************These Script work for Villa*************/

$('#submitButtonVilla').on('click', function (e) {

    /***********Append data for post form************/

    var formDataPost = new FormData();
    // var formDataForModel = new FormData();


    var editPostId = $('editPostId').val();
    if (editPostId > -1) {
        formDataPost.append("id", editPostId);
    }

    var postStatus = $('#editPostStatus').val();

    if (postStatus == true || postStatus == false) {
        formDataPost.append("postStatus.sold", postStatus);
    }

    formDataPost.append("title", $("#postTitle").val());
    formDataPost.append("description", $("#postDiscription").val());
    formDataPost.append("price", $("#postPrice").val());
    formDataPost.append("contactNumber", $("#postContactNumber").val());
    formDataPost.append("email", $("#postEmail").val());
    formDataPost.append("address", $("#mapsearch").val());
    formDataPost.append("longitude", $("#maplong").val());
    formDataPost.append("latitude", $("#maplat").val());
    $.each($("#fileselect")[0].files, function (index, value) {
        formDataPost.append("photos", value);
    });
    $.each($("#videoSelect")[0].files, function (index, value) {
        formDataPost.append("videos", value);
    });

    formDataPost.append("subCategory.id", $("#subcategory").val());

    /******Append data for Model************/

    formDataPost.append("villa.furnished", $("#villaFurnished").val());
    formDataPost.append("villa.numberOfRoom", $("#villaNumberOfRoom").val());
    formDataPost.append("villa.numberOfBathroom", $("#villaNumberOfBathRoom").val());
    formDataPost.append("villa.kitchenType", $("#villaKitchen").val());
    formDataPost.append("villa.parking", $("#villaParking").val());
    formDataPost.append("villa.deposit", $("#villaDeposit").val());


    $.ajax({
        url: '/userPost/saveVilla',
        type: 'POST',
        data: formDataPost,

        cache: false,
        contentType: false,
        enctype: 'multipart/form-data',
        processData: false,
        success: function (data) {
            $("#successModal #modalMessage").html(data.response);
            $('#successModal').modal('show');
        },
        error: function (response, textStatus, errorThrown) {
            $("#successModal #modalMessage").html(response.responseText);
            $('#successModal').modal('show');
        }
    })
});

/***************These Script work for Generic*************/

$('#submitButtonGeneric').on('click', function (e) {

    /***********Append data for post form************/

    var formDataPost = new FormData();
    // var formDataForModel = new FormData();

    var editPostId = $('editPostId').val();
    if (editPostId > -1) {
        formDataPost.append("id", editPostId);
    }

    var postStatus = $('#editPostStatus').val();

    if (postStatus == true || postStatus == false) {
        formDataPost.append("postStatus.sold", postStatus);
    }

    formDataPost.append("title", $("#postTitle").val());
    formDataPost.append("description", $("#postDiscription").val());
    formDataPost.append("price", $("#postPrice").val());
    formDataPost.append("contactNumber", $("#postContactNumber").val());
    formDataPost.append("email", $("#postEmail").val());
    formDataPost.append("address", $("#mapsearch").val());
    formDataPost.append("longitude", $("#maplong").val());
    formDataPost.append("latitude", $("#maplat").val());
    $.each($("#fileselect")[0].files, function (index, value) {
        formDataPost.append("photos", value);
    });
    $.each($("#videoSelect")[0].files, function (index, value) {
        formDataPost.append("videos", value);
    });

    formDataPost.append("subCategory.id", $("#subcategory").val());

    /******Append data for Model************/

    formDataPost.append("generic.manufacturerName", $("#genericManufacturerName").val());
    formDataPost.append("generic.condition", $("#genericCondition").val());

    $.ajax({
        url: '/userPost/savePostGeneric',
        type: 'POST',
        data: formDataPost,

        cache: false,
        contentType: false,
        enctype: 'multipart/form-data',
        processData: false,
        success: function (data) {
            $("#successModal #modalMessage").html(data.response);
            $('#successModal').modal('show');
        },
        error: function (response, textStatus, errorThrown) {
            $("#successModal #modalMessage").html(response.responseText);
            $('#successModal').modal('show');
        }
    })
});



