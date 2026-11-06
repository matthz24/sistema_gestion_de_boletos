document.addEventListener("DOMContentLoaded", function () {
    const venueSelect = document.getElementById("venueId");
    const aforoInput = document.getElementById("aforo");

    if (venueSelect && aforoInput) {
        function actualizarAforo() {
            const option = venueSelect.options[venueSelect.selectedIndex];
            const capacidad = option ? option.dataset.capacidad : "";

            aforoInput.value = capacidad
                    ? Number(capacidad).toLocaleString("es-CR")
                    : "Selecciona un venue";
        }

        venueSelect.addEventListener("change", actualizarAforo);
        actualizarAforo();
    }

    const form = document.getElementById("eventoForm");
    const ticketRows = document.getElementById("ticketRows");
    const addTicketButton = document.getElementById("addTicket");
    const ticketTemplate = document.getElementById("ticketRowTemplate");
    const ticketCount = document.getElementById("ticketCount");

    if (!form || !ticketRows || !addTicketButton || !ticketTemplate) {
        return;
    }

    function reindexarBoletos() {
        const rows = ticketRows.querySelectorAll("[data-ticket-row]");

        rows.forEach(function (row, index) {
            row.querySelectorAll("[data-field]").forEach(function (input) {
                const field = input.dataset.field;
                input.name = `tiposEntrada[${index}].${field}`;
                input.id = `tiposEntrada${index}.${field}`;
            });
        });

        if (ticketCount) {
            ticketCount.textContent =
                    `${rows.length} ${rows.length === 1 ? "categoría" : "categorías"}`;
        }
    }

    addTicketButton.addEventListener("click", function () {
        const fragment = ticketTemplate.content.cloneNode(true);
        ticketRows.appendChild(fragment);
        reindexarBoletos();
    });

    ticketRows.addEventListener("click", function (event) {
        const removeButton = event.target.closest("[data-remove-ticket]");

        if (!removeButton) {
            return;
        }

        const rows = ticketRows.querySelectorAll("[data-ticket-row]");

        if (rows.length <= 1) {
            return;
        }

        removeButton.closest("[data-ticket-row]").remove();
        reindexarBoletos();
    });

    form.addEventListener("submit", reindexarBoletos);
    reindexarBoletos();
});

