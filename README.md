# Inventory Audit API 🏬📦

**Versión 2025 — Java 24 + Spring Boot + MySQL**

Una API RESTful desarrollada en Java con Spring Boot para gestionar el control de inventarios en múltiples bodegas, registrando entradas, salidas, y ajustes con auditoría de cada operación. Diseñada para entornos empresariales que requieren trazabilidad completa de movimientos de stock.

---

## 🧩 Problemática que resuelve

Empresas con múltiples bodegas necesitan:
- Registrar movimientos de productos (entradas, salidas, ajustes).
- Validar operaciones según disponibilidad y estado.
- Mantener un historial completo de auditoría con detalles antes/después.
- Evitar inconsistencias en stock ante errores o anulaciones.

---

## ✨ Funcionalidades principales

- Gestión de productos, bodegas, marcas y categorías.
- Registro de movimientos con ajuste automático de stock y estado.
- Anulación lógica de movimientos con reversión del stock.
- Auditoría automática de todas las operaciones CRUD en formato JSON.
- Validaciones personalizadas y mensajes claros para el usuario.
- DTOs diferenciados para Request y Response.
- Consultas dinámicas usando Specifications.
- Arquitectura por capas profesional.

---

## 🏗️ Estructura del proyecto

```
src/main/java
├── controller          # Controladores REST
├── dto
│   ├── request         # DTOs para entrada de datos
│   └── response        # DTOs para salida de datos
├── enums               # ENUMs como tipo de movimiento, estado, etc.
├── exception           # Manejo global de errores y reglas de negocio
├── mapper              # MapStruct Mappers para entidades y DTOs
├── model               # Entidades JPA
├── repository          # Repositorios JPA con consultas personalizadas
├── specification       # Filtros dinámicos con Specification API
├── service
│   ├── impl            # Lógica de negocio
│   └── interfaces      # Interfaces de servicios
```

---

## 🧠 Detalles técnicos destacados

- **Auditoría avanzada**: Cada cambio se registra en una entidad `Audit` con:
  - `EntityName`, `Operation`, `DataBefore`, `DataAfter`, `User`, `Date`.
  - Formato JSON completo antes y después del cambio.
  - Funciones específicas para `logInsert`, `logUpdate`, `logDelete`.
  - Uso de `@Transactional` para garantizar integridad.

- **Validaciones y reglas de negocio**:
  - Validaciones con anotaciones y excepciones personalizadas.
  - Campos únicos verificados manualmente (correo, documento, nombres de entidades).
  - Manejo de estados automáticos (ej: producto agotado si stock = 0).
  - Prevención de eliminaciones que causen inconsistencias de stock.

- **Specification API**:
  - Consultas dinámicas con filtros compuestos para:
    - Productos
    - Movimientos
    - Auditorías

- **Librerías clave**:
  - [Spring Boot](https://spring.io/projects/spring-boot)
  - [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
  - [Hibernate](https://hibernate.org/)
  - [MapStruct](https://mapstruct.org/)
  - [Lombok](https://projectlombok.org/)

---

## ⚙️ Tecnologías

| Herramienta       | Versión / Uso                          |
|-------------------|----------------------------------------|
| Java              | 24                                     |
| Spring Boot       | Última versión 2025                    |
| MySQL             | Última versión (vía Workbench)         |
| IntelliJ IDEA     | IDE principal                          |
| Postman           | Pruebas manuales de QA                 |
| Git & GitHub      | Control de versiones                   |
| Maven             | Gestión de dependencias y compilación  |

---

## 🚀 Cómo ejecutar el proyecto

1. **Clona el repositorio**
   ```bash
   git clone https://github.com/contactodaviddm/inventory-audit-api.git 
   cd inventory-audit-api
   ```

2. **Configura la base de datos**
   - Asegúrate de tener MySQL corriendo localmente.
   - Ejecuta el archivo `warehouse_audit.sql` en tu Workbench o con terminal para crear la base de datos y poblarla.
   - Luego ajusta las credenciales en `src/main/resources/application.properties`, por ejemplo:

     ```
     spring.datasource.url=jdbc:mysql://localhost:3306/warehouse_audit
     spring.datasource.username=root
     spring.datasource.password=tu_clave
     spring.jpa.hibernate.ddl-auto=none
     ```

   - Nota: El proyecto no crea las tablas automáticamente, debes importar el `.sql`.

3. **Ejecuta el proyecto**
   ```bash
   ./mvnw clean install
   ./mvnw spring-boot:run
   ```

4. **Importa en Postman** (opcional)
   - Usa las colecciones de prueba manual si están disponibles en el repo.

---

## 🧪 Estado actual

- Seguridad con JWT y Spring Security **no implementada todavía**.
- La API utiliza un usuario estándar.
- Pensado para integrarse en siguientes versiones.

---

## 🧠 Autor y análisis

Este proyecto fue completamente diseñado, modelado y desarrollado por [David Domínguez](mailto:contacto.david.dm@gmail.com) como parte de su portafolio profesional, incluyendo:

- Análisis de requerimientos y modelado UML.
- Diseño de base de datos relacional (MER).
- Validaciones, pruebas y control de calidad manual.
- Estructura profesional, moderna y escalable.

---

## ✅ Pendientes y mejoras futuras

- Integración de Spring Security + JWT.
- Pruebas automatizadas (unitarias/integración).
- Documentación Swagger/OpenAPI.
- Despliegue en la nube (Heroku, Render, etc.).

---

## 📫 Contacto

¿Dudas o sugerencias? Puedes escribirme a:

- 📧 contacto.david.dm@gmail.com
- 🌐 [LinkedIn](https://www.linkedin.com/in/kevin-david-dominguez-mora-541464375)

---

## 📌 Licencia

Este proyecto está disponible para fines educativos y de demostración. No usar en producción sin autorización.

---