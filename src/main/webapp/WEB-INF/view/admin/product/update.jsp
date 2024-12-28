<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <meta name="description" content="Dự án laptopshop" />
    <meta name="author" content="FE clone" />
    <title>Update product</title>
    <link href="/css/styles.css" rel="stylesheet" />
    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
     <!-- Jquery preview image -->
    <script>
        $(document).ready(() => {
            const imageFile = $("#imageFile");
            const imagePreview = $("#imagePreview");

            const imageFileName = "${newProduct.image}"; 
            const imageFolderPath = "/images/product/"; 

            if (imageFileName) {
                const fullImageURL = imageFolderPath + imageFileName;
                imagePreview.attr("src", fullImageURL);
                imagePreview.css({ "display": "block" });
            }

            imageFile.change(function (e) {
                const imgURL = URL.createObjectURL(e.target.files[0]);
                imagePreview.attr("src", imgURL);
                imagePreview.css({ "display": "block" });
            });
        });
    </script>
</head>

<body class="sb-nav-fixed">
    <jsp:include page = "../layout/header.jsp"/>
    <div id="layoutSidenav">
        <jsp:include page = "../layout/sidebar.jsp"/>
        <div id="layoutSidenav_content">
            <main>
                <div class="container-fluid px-4">
                    <h1 class="mt-4">Manage product</h1>
                    <ol class="breadcrumb mb-4">
                        <li class="breadcrumb-item"> <a href="/admin">Dashboard</a></li>
                        <li class="breadcrumb-item"> <a href="../product">Product</a></li>
                        <li class="breadcrumb-item active">Update</li>
                    </ol>
                    <div class="mt-5">
                        <div class="row">
                            <div class="col-md-6 col-12 mx-auto">
                                <h3>Update a product</h3>
                                <hr />
                                <div class="col-12 mb-3">
                                    <label class="form-label">Image:</label>
                                    <img style="max-height: 250px; display: none;" alt="image preview"
                                        id="imagePreview" />
                                </div>
                                <form:form method = "POST" enctype="multipart/form-data" action="/admin/product/update" modelAttribute = "newProduct" class="row">
                                    <div class="mb-3 col-12 col-md-6">

                                        <div class="mb-3" style="display : none">
                                            <label class="form-label">ID:</label>
                                            <form:input type="text" class="form-control" path = "id"/>
                                        </div>

                                        <label class="form-label">Name:</label>
                                        <c:set var = "errorName">
                                            <form:errors path = "name" cssClass="invalid-feedback" />
                                        </c:set>

                                        <form:input type = "text" class="form-control ${not empty errorName? 'is-invalid':''}" path = "name"/>
                                        ${errorName}
                                    </div>
                                    <div class="mb-3 col-12 col-md-6">
                                        <label class="form-label">Price:</label>
                                        <c:set var = "errorPrice">
                                            <form:errors path = "price" cssClass="invalid-feedback"/>
                                        </c:set>

                                        <form:input type="number" class="form-control ${not empty errorPrice? 'is-invalid':''}" path="price" />
                                        ${errorPrice}
                                    </div>
                                    <div class="mb-3 col-12">
                                        <label class="form-label">Detail description:</label>
                                        <c:set var = "errorDetailDesc">
                                            <form:errors path = "detailDesc" cssClass="invalid-feedback"/>
                                        </c:set>

                                        <form:textarea type="text" class="form-control ${not empty errorDetailDesc ? 'is-invalid':''}" path="detailDesc" />
                                        ${errorDetailDesc}
                                    </div>
                                    <div class="mb-3 col-12 col-md-6">
                                        <label class="form-label">Short description:</label>
                                        <c:set var = "errorShortDesc">
                                            <form:errors path = "shortDesc" cssClass = "invalid-feedback" />
                                        </c:set>

                                        <form:input type="text" class="form-control ${not empty errorShortDesc ? 'is-invalid':''}" path="shortDesc" />
                                        ${errorShortDesc}
                                    </div>
                                    <div class="mb-3 col-12 col-md-6">
                                        <label class="form-label">Quantity:</label>
                                        <c:set var = "errorQuantity">
                                            <form:errors path = "quantity" cssClass="invalid-feedback"/>
                                        </c:set>

                                        <form:input type="number" class="form-control ${not empty errorQuantity ? 'is-invalid':''}" path="quantity" />
                                        ${errorQuantity}
                                    </div>

                                    <div class="mb-3 col-12 col-md-6">
                                        <label class="form-label">Factory:</label>
                                        <form:select class="form-select" path="factory">
                                            <form:option value="APPLE">Apple (MacBook)</form:option>
                                            <form:option value="ASUS">Asus</form:option>
                                            <form:option value="LENOVO">Lenovo</form:option>
                                            <form:option value="DELL">Dell</form:option>
                                            <form:option value="LG">LG</form:option>
                                            <form:option value="ACER">Acer</form:option>
                                        </form:select>
                                    </div>

                                    <div class="mb-3 col-12 col-md-6">
                                        <label class="form-label">Target:</label>
                                        <form:select class="form-select" path="target">
                                            <form:option value="GAMING">Gaming</form:option>
                                            <form:option value="SINHVIEN-VANPHONG">Sinh viên - Văn phòng
                                            </form:option>
                                            <form:option value="THIET-KE-DO-HOA">Thiết kế đồ họa
                                            </form:option>
                                            <form:option value="MONG-NHE">Mỏng nhẹ</form:option>
                                            <form:option value="DOANH-NHAN">Doanh nhân</form:option>
                                        </form:select>
                                    </div>
                                    <div class="mb-3 col-12 col-md-6">
                                        <label for="avatarFile" class="form-label">Image:</label>
                                        <input class="form-control" type="file" id="avatarFile"
                                            accept=".png, .jpg, .jpeg" name="thienFile" />
                                    </div>
                                    <div class="col-12 mb-3">
                                        <img style="max-height: 250px; display: none;" alt="avatar preview"
                                            id="avatarPreview" />
                                    </div>
                                    
                                    <div class="col-12 mb-5">
                                        <button type="submit" class="btn btn-warning">Update</button>
                                    </div>
                                </form:form>
                            </div>
                            
                        </div>
                        
                    </div>
                </div>
            </main>
            <jsp:include page = "../layout/footer.jsp"/>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
        crossorigin="anonymous"></script>
    <script src="js/scripts.js"></script>
</body>

</html>