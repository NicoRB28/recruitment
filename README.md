
# Guía de Puesta en Marcha

## Requerimientos

- Docker

## Puesta en marcha

1. Dentro de la carpeta donde se encuentra el archivo `docker-compose.yml`, ejecutar el siguiente comando:

   ```bash
   docker-compose build
   ```
> ⚠️ En caso de obtener un error como el siguiente:
>  => ERROR [builder 6/8] RUN ./mvnw dependency:go-offline -B                                                        0.3s
> es posible que al clonar el repositorio el archivo dockerfile tenga incompatibilidad con el
> final de línea (CRLF/LF), basta con utilizar una herramienta como visual studio code para realizar el cambio de CRLF a LF
> y volver a ejecutar.

2. Una vez finalizado el proceso de build, ejecutar:

   ```bash
   docker-compose up
   ```
   Este comando levantara una instancia de postgres donde se realizará la persistencia de datos y luego una instancia de la aplicacion.

---

## Endpoints disponibles

### 1. Login

- **URL**: `http://localhost:8080/api/auth/login`
- **Method**: `POST`
- **Body**:

   ```json
   {
     "username": "user1",
     "password": "pass1"
   }
   ```

**Nota**: Al inicializar la aplicación se registran dos usuarios:

- `user1` con contraseña `pass1`
- `user2` con contraseña `pass2`

**Response**:

```json
{
  "jwt": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMSIsImlhdCI6MTc1MjgwODU0NiwiZXhwIjoxNzUyODEyMTQ2fQ.p072py2BcAgjonI0O1Ho3RqGuoHZBdidMv_pE5aqPUA"
}
```

> ⚠️ Este token de tipo **Bearer** debe ser enviado en el header `Authorization` con el valor:  
> `Bearer {jwt}` para poder ejecutar el resto de endpoints.

---

### 2. Crear cliente

- **URL**: `http://localhost:8080/cliente/`
- **Method**: `POST`
- **Body**:

   ```json
   {
     "dni": 30000123,
     "nombre": "Pablo",
     "apellido": "Garcia",
     "calle": "Las Heras",
     "numero": 2258,
     "codigoPostal": 1234,
     "telefono": null,
     "celular": "11 45645645",
     "productoBancario": [
       "PZOF",
       "CHEQ"
     ]
   }
   ```

---

### 3. Listar todos los clientes

- **URL**: `http://localhost:8080/cliente/`
- **Method**: `GET`

---

### 4. Listar un cliente en particular

- **URL**: `http://localhost:8080/cliente/{id}/detalle`
- **Method**: `GET`

---

### 5. Editar un cliente

- **URL**: `http://localhost:8080/cliente/{id}`
- **Method**: `PUT`
- **Body**:

   ```json
   {
     "dni": 30000123,
     "nombre": "Pablo Editado",
     "apellido": "Garcia",
     "calle": "Las Heras",
     "numero": 2258,
     "codigoPostal": 1234,
     "telefono": null,
     "celular": "11 45645645",
     "productoBancario": [
       "CHEQ"
     ]
   }
   ```

---

### 6. Listar clientes por producto bancario

- **URL**: `http://localhost:8080/cliente/{producto}`
- **Method**: `GET`
- **Ejemplo**:  
  `http://localhost:8080/cliente/CHEQ`

---

### 7. Eliminar un cliente

- **URL**: `http://localhost:8080/cliente/{id}`
- **Method**: `DELETE`
