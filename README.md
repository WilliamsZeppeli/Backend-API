# Flask REST API + Android Client (Retrofit)

Proyecto académico que demuestra el ciclo completo de comunicación **Android ↔ API REST**.
El backend fue desarrollado con **Flask**, autenticación con **bcrypt**, y se ejecuta en un contenedor **Docker**.
La aplicación Android consume los endpoints mediante **Retrofit**.

---

# Descripción del proyecto

Este proyecto implementa un servicio REST simple que permite:

* Verificar que la API está activa
* Registrar usuarios
* Autenticar usuarios

La aplicación Android consume estos servicios y muestra los resultados al usuario.

El objetivo es comprender el flujo completo de comunicación HTTP entre una aplicación móvil y un backend.

---

# Tecnologías utilizadas

## Backend

* Python
* Flask
* bcrypt
* Docker
* Docker Compose

## Cliente móvil

* Android Studio
* Kotlin
* Retrofit
* Gson Converter

---

# Arquitectura

Android App
↓ HTTP (Retrofit)
Flask REST API
↓
Docker Container

---

# Endpoints de la API

| Método | Endpoint  | Descripción                       | Auth |
| ------ | --------- | --------------------------------- | ---- |
| GET    | /         | Verifica que la API está activa   | No   |
| POST   | /register | Registra un usuario (hash bcrypt) | No   |
| POST   | /login    | Autentica un usuario              | No   |

---

# Estructura del proyecto

```
project-root
│
├ backend-flask
│   ├ app.py
│   ├ requirements.txt
│   ├ Dockerfile
│   └ docker-compose.yml
│
└ android-app
    └ FlaskApiClient
```

---

# Ejecución del backend

## 1 Clonar el repositorio

```
git clone https://github.com/tuusuario/flask-android-api.git
cd flask-android-api/backend-flask
```

## 2 Construir el contenedor

```
docker compose build
```

## 3 Iniciar la API

```
docker compose up
```

La API quedará disponible en:

```
http://localhost:5000
```

---

# Ejecución de la aplicación Android

1. Abrir la carpeta del proyecto en **Android Studio**
2. Ejecutar el emulador Android
3. Ejecutar la aplicación

La app se conecta al backend usando:

```
http://10.0.2.2:5000
```

Esto permite al emulador acceder al **localhost del host**.

---

# Ejercicio 1 — Verificación de la API

La aplicación realiza una petición **GET /** al iniciar.

Resultado esperado:

```
API activa
```

Captura requerida:

```
screenshots/api_check.png
```

---

# Ejercicio 2 — Registro de usuario

La pantalla de registro permite ingresar:

* Usuario
* Contraseña

Se envía una petición:

```
POST /register
```

Ejemplo JSON:

```
{
 "username": "ana",
 "password": "1234"
}
```

Resultados posibles:

```
Usuario registrado correctamente
```

```
El usuario ya existe
```

Capturas requeridas:

```
screenshots/register_success.png
screenshots/register_duplicate.png
```

---

# Ejercicio 3 — Login

Pantalla de inicio de sesión.

Se envía una petición:

```
POST /login
```

Ejemplo:

```
{
 "username": "ana",
 "password": "1234"
}
```

Resultados posibles:

Login correcto:

```
Bienvenido ana
```

Login incorrecto:

```
Credenciales incorrectas
```

Capturas requeridas:

```
screenshots/login_success.png
screenshots/login_failed.png
```

---

# Ejercicio 4 — Manejo de errores de red

Para probar el manejo de errores:

Detener el backend:

```
docker compose down
```

Intentar iniciar sesión desde la app.

Resultado esperado:

```
Error de conexión
```

Captura requerida:

```
screenshots/network_error.png
```

---

# Seguridad implementada

Las contraseñas se almacenan usando **hash bcrypt**, evitando guardar contraseñas en texto plano.

Ejemplo en el backend:

```
bcrypt.hashpw(password.encode('utf-8'), bcrypt.gensalt())
```

---

# Autor

Proyecto desarrollado como práctica de integración entre **backend REST y cliente Android**.
