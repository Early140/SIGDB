USE sigdb;
-- Datos de ejemplo (ejecutar una vez sobre esquema vacío)
INSERT INTO usuario(nombre,email,password_hash,rol) VALUES ('Entrenador Demo','entrenador@club.test','HASH_PENDIENTE','ENTRENADOR');
INSERT INTO entrenador(id_usuario,nombre,apellido) VALUES (1,'Lucas','Pérez');
INSERT INTO categoria(nombre,rama,temporada) VALUES ('U13','MASCULINA',2026);
INSERT INTO jugador(nombre,apellido,fecha_nacimiento) VALUES ('Juan','Gómez','2013-04-10'),('Pedro','López','2013-07-21');
INSERT INTO jugador_categoria VALUES (1,1,'2026-09-01'),(2,1,'2026-09-01');
INSERT INTO entrenador_categoria VALUES (1,1);
INSERT INTO entrenamiento(fecha,id_categoria,id_entrenador,turno) VALUES ('2026-09-28',1,1,'TARDE');
INSERT INTO asistencia(id_entrenamiento,id_jugador,estado) VALUES (1,1,'PRESENTE'),(1,2,'AUSENTE');
-- Actualización sin duplicados
INSERT INTO asistencia(id_entrenamiento,id_jugador,estado) VALUES (1,2,'PRESENTE') ON DUPLICATE KEY UPDATE estado=VALUES(estado);
-- Historial por jugador
SELECT j.nombre,j.apellido,e.fecha,c.nombre AS categoria,a.estado FROM asistencia a JOIN jugador j ON j.id_jugador=a.id_jugador JOIN entrenamiento e ON e.id_entrenamiento=a.id_entrenamiento JOIN categoria c ON c.id_categoria=e.id_categoria WHERE j.id_jugador=1 ORDER BY e.fecha;
-- Porcentaje sobre asistencias efectivamente registradas
SELECT j.id_jugador,j.nombre,j.apellido,COUNT(*) AS clases_registradas,SUM(a.estado='PRESENTE') AS presentes,ROUND(100*SUM(a.estado='PRESENTE')/COUNT(*),2) AS porcentaje FROM jugador j JOIN asistencia a ON a.id_jugador=j.id_jugador GROUP BY j.id_jugador,j.nombre,j.apellido;
-- Consulta de plantel por entrenador
SELECT DISTINCT j.id_jugador,j.nombre,j.apellido FROM jugador j JOIN jugador_categoria jc ON jc.id_jugador=j.id_jugador JOIN entrenador_categoria ec ON ec.id_categoria=jc.id_categoria WHERE ec.id_entrenador=1 AND j.activo=TRUE;
-- Borrado de registro de asistencia de prueba (no borra al jugador)
DELETE FROM asistencia WHERE id_entrenamiento=1 AND id_jugador=2;
-- Baja lógica para preservar historial
UPDATE jugador SET activo=FALSE WHERE id_jugador=2;
