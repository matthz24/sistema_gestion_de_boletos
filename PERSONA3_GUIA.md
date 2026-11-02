# Persona 3: solicitudes y administración de organizadores

Este módulo implementa las tres historias asignadas:

- **HU 13 (Organizador):** una organización envía sus datos comerciales y queda como `PENDIENTE`.
- **HU 18 (Administrador):** el administrador revisa la lista de solicitudes pendientes y los contadores por estado.
- **HU 19 (Administrador):** se aprueba o rechaza una solicitud pendiente; al rechazarla se guarda el motivo. Una solicitud ya revisada no se puede cambiar desde este flujo.

## Clases y función

| Archivo | Responsabilidad |
|---|---|
| `domain/EstadoSolicitud.java` | Estados `PENDIENTE`, `APROBADA` y `RECHAZADA`. |
| `domain/SolicitudOrganizador.java` | Entidad JPA, objeto del formulario y validaciones (`@Valid`), como las entidades del proyecto `tienda_vm`. |
| `repository/SolicitudOrganizadorRepository.java` | Consultas por estado, correo repetido y conteo de solicitudes por estado. |
| `service/SolicitudOrganizadorService.java` | Reglas para registrar, contar, listar, aprobar y rechazar; guarda el motivo del rechazo. |
| `controller/OrganizadorController.java` | Formulario público y registro de solicitudes. |
| `controller/AdministradorOrganizadorController.java` | Lista de pendientes y acciones para aprobar o rechazar. |
| `controller/InicioController.java` | Página inicial. |
| `templates/organizador/solicitud.html` | Página de formulario inspirada en la vista pública del prototipo. |
| `templates/organizador/pendientes.html` | Página administrativa con resumen, búsqueda, tabla y diálogo para rechazar. |
| `templates/organizador/fragmentos.html` | Componentes Thymeleaf reutilizables: panel informativo, formulario, estado, menú, resumen, tabla y diálogo de rechazo. |
| `templates/general/fragmentos.html` | Fragmentos comunes para el `head`, marca, encabezado público y mensajes. |
| `static/js/modulos/organizador.js` | Búsqueda de solicitudes y apertura/cierre del diálogo de rechazo. |

Las clases que tienen dependencias usan Lombok `@RequiredArgsConstructor`, como los controladores y servicios de `tienda_vm`. La entidad implementa `Serializable` y usa `@Getter` y `@Setter`. El controlador recibe la entidad con `@Valid`, igual que el proyecto guía.

## Métodos del material de clase

- El repositorio usa consultas derivadas de Spring Data JPA: `findByEstadoOrderByFechaSolicitudAsc(...)` busca por estado y ordena por fecha; `existsByCorreoIgnoreCase(...)` revisa si ya existe ese correo. No se escribe SQL ni JPQL para estas consultas.
- Los controladores siguen el flujo MVC visto en clase: `@GetMapping` muestra la página, `@PostMapping` recibe el formulario, el servicio procesa los datos y `Model` entrega a Thymeleaf la lista que se va a mostrar.
- Las entidades incluyen las validaciones `@NotBlank`, `@Email`, `@Size` y `@AssertTrue`; los controladores usan `@Valid` y `BindingResult`, como las operaciones de guardar de la tienda.
- Las páginas usan `th:fragment` en `fragmentos.html` y `th:replace` en las páginas principales para armar el formulario y el panel, siguiendo el patrón de los listados del proyecto guía.
- Los fragmentos con datos reciben parámetros, como `resumenAdmin(pendientesCount, aprobadasCount, rechazadasCount)` y `modalRechazo(solicitud)`, igual que el fragmento parametrizado `frmConsulta(...)` del material.
- El JavaScript de búsqueda y diálogo está en `static/js/modulos/organizador.js`, con la misma separación por módulos que usa la tienda.
- Lombok genera constructores, getters y setters para reducir código repetido, igual que en las clases de `tienda_vm`.

## Rutas

| Método | Ruta | Uso |
|---|---|---|
| GET | `/organizador/solicitud` | Muestra formulario público. |
| POST | `/organizador/solicitud` | Valida y guarda la solicitud como pendiente. |
| GET | `/admin/organizadores/pendientes` | Muestra todas las solicitudes pendientes. |
| POST | `/admin/organizadores/{id}/aprobar` | Cambia estado a `APROBADA`. |
| POST | `/admin/organizadores/{id}/rechazar` | Cambia estado a `RECHAZADA` y guarda el motivo enviado en `motivo`. |

## Ejecutarlo en NetBeans

1. Abre el proyecto y confirma que usa **JDK 21**.
2. Pulsa **Clean and Build** y luego **Run**.
3. Abre `http://localhost/`.
4. Pulsa **Administración: revisar solicitudes** (también aparece como **¿Eres administrador? Revisar solicitudes** en el formulario público).
5. En pendientes, usa los botones **Aprobar** o **Rechazar**. Al terminar, la solicitud sale de esa lista.

No hay inicio de sesión en este primer avance, de acuerdo con el nivel solicitado. La ruta administrativa está abierta mientras se desarrolla localmente; cuando el curso vea seguridad, se puede proteger con el rol de administrador.

## Base de datos

EvenTicket usa H2 en modo archivo para desarrollo local. La conexión se configura en `src/main/resources/application.properties` con usuario `sa` y contraseña vacía. Los datos se guardan en `./data/eventicket.mv.db`; la carpeta `data/` está excluida de Git, por lo que cada integrante tendrá su propia base local.

Hibernate crea o actualiza la tabla según la entidad al iniciar, mediante `spring.jpa.hibernate.ddl-auto=update`. El campo del formulario `tipoEventos` se guarda en la columna `descripcion` de la tabla.
