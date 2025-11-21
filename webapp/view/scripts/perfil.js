document.addEventListener("DOMContentLoaded", () => {

    let cropper;
    const modalEl = document.getElementById("modalCrop");
    const modalCrop = new bootstrap.Modal(modalEl);
    const input = document.getElementById("inputImagem");

    function carregarPerfil() {
        $.ajax({
            url: "/ProjetoTCC/api/UsuarioControl",
            method: "GET",
            dataType: "json",
            success: function(usuario) {
                document.getElementById('nome').textContent = usuario.nome;
                document.getElementById('username').textContent = usuario.username;

                $("#imagem").attr("src", "/ProjetoTCC/" + usuario.foto).show();
            },
            error: function() {
                console.error("Erro ao carregar usuário");
            }
        });
    }

    document.getElementById("cardImagemUsuario").addEventListener("click", () => {
        input.click();
    });

    input.addEventListener("change", () => {

        const file = input.files[0];
        if (!file) return;

        if (file.size > 5 * 1024 * 1024) {
            alert("A imagem deve ter no máximo 5MB.");
            input.value = "";
            return;
        }

        const reader = new FileReader();

        reader.onload = function(e) {
            document.getElementById("cropperImg").src = e.target.result;

            modalCrop.show();

            $(modalEl).one("shown.bs.modal", function () {

                if (cropper) {
                    try { cropper.destroy(); } catch (err) { }
                    cropper = null;
                }

                cropper = new Cropper(document.getElementById("cropperImg"), {
                    aspectRatio: 1,
                    viewMode: 2,
                    autoCropArea: 1,
                    responsive: true,
                    background: false
                });
            });
        };

        reader.readAsDataURL(file);
    });

    document.getElementById("cropConfirm").addEventListener("click", function() {

        if (!cropper) {
            modalCrop.hide();
            return;
        }

        const canvas = cropper.getCroppedCanvas({
            width: 500,
            height: 500
        });

        canvas.toBlob((blob) => {
            const arquivoCortado = new File([blob], "foto.jpg", { type: "image/jpeg" });
            atualizarFotoCroppada(arquivoCortado);
        }, "image/jpeg", 0.9);

        modalCrop.hide();
    });

    $(modalEl).on("hidden.bs.modal", function () {

        if (cropper) {
            try { cropper.destroy(); } catch (err) { }
            cropper = null;
        }

        document.getElementById("cropperImg").src = "";
        input.value = "";
    });

    function atualizarFotoCroppada(imagem) {

        const formData = new FormData();
        formData.append('acao', 'atualizarFotoPerfil');
        formData.append('imagem', imagem);

        $.ajax({
            url: '/ProjetoTCC/api/UsuarioControl',
            type: 'POST',
            data: formData,
            processData: false,
            contentType: false,
            success: function() {
                carregarPerfilInstantaneo(imagem);
            },
            error: function(xhr, status, error) {
                console.error("Erro:", status, error);
                console.error("Resposta:", xhr.responseText);
            }
        });
    }

    function carregarPerfilInstantaneo(file) {
        const reader = new FileReader();

        reader.onload = function(e) {
            $("#imagem").attr("src", e.target.result).show();
        };

        reader.readAsDataURL(file);
    }

    carregarPerfil();

});