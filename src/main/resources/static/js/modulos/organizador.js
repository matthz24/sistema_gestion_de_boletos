document.querySelectorAll('[data-modal]').forEach(function (boton) {
    boton.addEventListener('click', function () {
        document.getElementById(boton.dataset.modal).showModal();
    });
});

document.querySelectorAll('[data-cerrar]').forEach(function (boton) {
    boton.addEventListener('click', function () {
        document.getElementById(boton.dataset.cerrar).close();
    });
});

var buscador = document.getElementById('buscarOrganizacion');
if (buscador) {
    buscador.addEventListener('input', function () {
        document.querySelectorAll('#tablaSolicitudes tbody tr').forEach(function (fila) {
            fila.hidden = !fila.textContent.toLowerCase().includes(buscador.value.toLowerCase());
        });
    });
}
