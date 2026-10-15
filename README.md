# Auth API

REST API sederhana untuk **register** dan **login** menggunakan Spring Boot. Data disimpan **in-memory** (`ConcurrentHashMap`), jadi semua data hilang saat aplikasi di-restart.

## Fitur

- Register user baru
- Login dengan email dan password
- Password di-hash dengan BCrypt
- Validasi input (Bean Validation)
- Dokumentasi API dengan Swagger UI (springdoc-openapi)

## Tech Stack

- Java 17+
- Spring Boot 3.x
- Spring Web, Spring Validation
- Spring Security Crypto (BCrypt)
- springdoc-openapi 2.8.9
- Maven

## Struktur Project

```
com.svc.api.auth
├── AuthApplication.java
├── config
│   ├── AppConfig.java              # bean PasswordEncoder (BCrypt)
│   ├── OpenApiConfig.java          # info Swagger
│   └── exception
│       └── GlobalExceptionHandler.java
├── controller
│   └── AuthController.java
├── service
│   ├── AuthService.java            # interface
│   └── impl
│       └── AuthServiceImpl.java    # implementasi logika register & login
├── repository
│   └── AuthRepository.java         # penyimpanan in-memory
└── model
    ├── entity
    │   └── Users.java
    └── vo
        └── AuthVo.java             # request body (register & login)

```

## Cara Menjalankan

```bash
# clone project
git clone <url-repo>
cd <nama-folder>

# jalankan
./mvnw spring-boot:run
```

Aplikasi berjalan di `http://localhost:8080`.

## Dokumentasi API (Swagger)

| Nama         | URL                                           |
|--------------|-----------------------------------------------|
| Swagger UI   | http://localhost:8080/swagger-ui/index.html   |
| OpenAPI JSON | http://localhost:8080/v3/api-docs             |

## Endpoint

Base path: `/api/auth`

| Method | Endpoint    | Deskripsi         |
|--------|-------------|-------------------|
| POST   | `/register` | Daftar user baru  |
| POST   | `/login`    | Login user        |

> Response sukses berupa **teks biasa** (`text/plain`), bukan JSON.

### Register

**Request**

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"email":"budi@mail.com","password":"secret123"}'
```

**Response `201 Created`**

```
Registration successful
```

### Login

**Request**

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"budi","password":"secret123"}'
```

**Response `200 OK`**

```
Login successful
```

## Validasi Input

| Field    | Aturan                         |
|----------|--------------------------------|
| email    | wajib diisi, format email valid|
| password | wajib diisi, 6 - 100 karakter  |

## Kode Response

| Status | Arti                                      |
|--------|-------------------------------------------|
| 200    | Login berhasil                            |
| 201    | Registrasi berhasil                       |
| 400    | Input tidak valid                         |
| 401    | email atau password salah                 |
| 409    | email sudah terdaftar                     |

## Catatan

- Data hilang setiap aplikasi di-restart karena disimpan di memori.
- Pesan error login sama untuk "user tidak ditemukan" dan "password salah" agar tidak membocorkan email yang terdaftar.
- Belum ada session atau token. Login hanya memverifikasi kredensial.

## Rencana Pengembangan

- [ ] JWT authentication
- [ ] Ganti penyimpanan in-memory dengan Spring Data JPA (H2 / PostgreSQL)
- [ ] Unit test dan integration test