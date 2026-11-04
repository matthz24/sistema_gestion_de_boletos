document.querySelectorAll('.cat-filtros select').forEach(function (select) {
    select.addEventListener('change', function () {
        select.form.submit();
    });
});

(function () {
    var panel = document.getElementById('panelBoletos');
    if (!panel) {
        return;
    }

    var MAXIMO = 10;
    var subtotal = document.getElementById('subtotal');
    var boton = document.getElementById('btnCarrito');
    var mensaje = document.getElementById('mensajeCarrito');

    function formato(valor) {
        return '₡' + String(valor).replace(/\B(?=(\d{3})+(?!\d))/g, ' ');
    }

    function cantidadDe(fila) {
        return parseInt(fila.querySelector('[data-cantidad]').textContent, 10) || 0;
    }

    function actualizar() {
        var total = 0;
        panel.querySelectorAll('[data-ticket]').forEach(function (fila) {
            total += cantidadDe(fila) * parseInt(fila.dataset.precio, 10);
        });
        subtotal.textContent = formato(total);
        boton.disabled = total === 0;
    }

    panel.addEventListener('click', function (evento) {
        var control = evento.target.closest('[data-accion]');
        if (!control) {
            return;
        }
        var fila = control.closest('[data-ticket]');
        var cantidad = cantidadDe(fila);
        cantidad = control.dataset.accion === 'mas'
                ? Math.min(MAXIMO, cantidad + 1)
                : Math.max(0, cantidad - 1);
        fila.querySelector('[data-cantidad]').textContent = cantidad;
        mensaje.hidden = true;
        actualizar();
    });

    // El carrito es de otro módulo: aquí solo se avisa con un evento del documento.
    boton.addEventListener('click', function () {
        var items = [];
        panel.querySelectorAll('[data-ticket]').forEach(function (fila) {
            var cantidad = cantidadDe(fila);
            if (cantidad > 0) {
                items.push({ tipoEntradaId: Number(fila.dataset.id), cantidad: cantidad });
            }
        });
        document.dispatchEvent(new CustomEvent('eventicket:agregar-carrito', {
            detail: { eventoId: Number(panel.dataset.evento), items: items }
        }));
        mensaje.textContent = 'Boletos seleccionados. El carrito se conectará en el módulo de compra.';
        mensaje.hidden = false;
    });

    actualizar();
})();
