# SIGDB — AP2 — Matias Temprano
Prototipo operacional de consola Java 17 + MySQL 8 + JDBC. No incluye autenticación ni interfaz gráfica; esas funciones están especificadas para incrementos futuros.

## Ejecución
1. Crear la base ejecutando `sql/schema.sql` con MySQL 8.
2. Crear usuario MySQL con permisos mínimos sobre `sigdb` (o configurar usuario local de desarrollo).
3. Configurar variables `SIGDB_URL`, `SIGDB_USER`, `SIGDB_PASSWORD`.
4. Importar como proyecto Maven en IntelliJ IDEA Community, NetBeans o Eclipse.
5. Ejecutar `ar.edu.sigdb.Main`. Para datos de muestra ejecutar `sql/operaciones.sql` sobre una base vacía (contiene también un DELETE de demostración).

## Alcance y limitaciones
Se implementan alta/listado de jugadores, asignación de categoría, registro o corrección de asistencia e historial. Requiere categorías, entrenadores y entrenamientos precargados mediante SQL. El código NO aplica control de roles ni autenticación; no se debe publicar en producción. La seguridad requiere autenticación con hashes fuertes, permisos por operación, auditoría y TLS según despliegue. No se han ejecutado pruebas de integración contra un servidor MySQL en este entorno.

## GitHub
Publicar esta carpeta en un repositorio propio y reemplazar en el informe el campo de URL pendiente por el enlace real.

## Incremento TP3 - Programación Orientada a Objetos
Se incorporaron `Persona` (clase abstracta, atributos privados y validaciones), `Jugador` y `Entrenador` (herencia y sobrescritura polimórfica), y `DemoPOO` (recorrido de una lista de objetos mediante referencia de tipo base). El menú principal incorpora la opción 6 para demostrar POO. El alta de jugadores instancia y valida un objeto `Jugador` antes de persistirlo mediante JDBC.

**Prueba sin MySQL:** compilar las clases de `src/main/java/ar/edu/sigdb/model/` y ejecutar `ar.edu.sigdb.model.DemoPOO`.

**Aplicación completa:** configurar `SIGDB_URL`, `SIGDB_USER` y `SIGDB_PASSWORD`, ejecutar `sql/schema.sql` en MySQL, compilar con Maven (`mvn package`) y ejecutar `ar.edu.sigdb.Main` con el conector JDBC en el classpath. No se incluyen credenciales.
