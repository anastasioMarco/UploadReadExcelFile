# UploadReadExcelFile

**Progetto di esempio su come caricare un file Excel e leggerlo, restituendone i dati letti.**

* Spring Boot
* Maven
* Apache POI
* IntelliJ IDEA

Creare una base per il progetto andando su [Spring Inizializr](https://start.spring.io/)

Impostare il progetto come più si preferisce e far generare il progetto. Io ho utilizzato le seguenti impostazioni:

![SpringBoot](/src/main/resources/images/spring_1.png)

Una volta finito il download del progetto base basterà estrarlo e aprirlo con un IDE come IntelliJ IDEA. La struttura del progetto sarà in questo modo:

Creiamo una pagina HTML contenente il pulsante per l'upload del file e la lettura dei dati:
```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Upload</title>
</head>
<body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

<div class="custom-file">
    <form id="upload-form" enctype="multipart/form-data">
        <input class="custom-file-input" id="file" name="file" type="file"
               accept="application/vnd.ms-excel,
                       application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
               onchange="onUpload();">
    </form>
</div>

<span id="showData"></span>

</body>
</html>
```
Andiamo adesso a gestire l'upload del file tramite uno script Javascript:
```jshelllanguage
function onUpload() {

        event.preventDefault();

        "use strict";

        var fileInput = document.getElementById('file');
        var file = fileInput.files[0];
        var formData = new FormData();
        formData.append('file',file);

		$.ajax({
            type: "post",
            url: '/onUpload',
            enctype: 'multipart/form-data',
    		contentType: false,
            data: formData,
            processData: false,
            cache: false,
            timeout: 600000,
            success: function(data) {
                document.getElementById("showData").innerHTML = data;
            },
            error: function() {
                console.log("error");
            }
        });
    }
```

```html
class UploadController.java
end

```
