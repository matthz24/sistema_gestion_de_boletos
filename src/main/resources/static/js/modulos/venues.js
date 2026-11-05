document.addEventListener("DOMContentLoaded", function () {
    const formModal = document.getElementById("venueFormModal");

    if (formModal && formModal.dataset.autoOpen === "true") {
        bootstrap.Modal.getOrCreateInstance(formModal).show();
    }

    const deleteModal = document.getElementById("eliminarVenueModal");

    if (deleteModal) {
        deleteModal.addEventListener("show.bs.modal", function (event) {
            const button = event.relatedTarget;

            document.getElementById("venueIdEliminar").value =
                    button.dataset.bsId;

            document.getElementById("venueNombreEliminar").textContent =
                    button.dataset.bsNombre;
        });
    }

    const search = document.getElementById("buscarVenue");

    if (search) {
        search.addEventListener("input", function () {
            const query = search.value.toLowerCase();

            document.querySelectorAll("#tablaVenues tr[data-venue-row]")
                    .forEach(function (row) {
                        row.hidden = !row.textContent.toLowerCase().includes(query);
                    });
        });
    }
});

