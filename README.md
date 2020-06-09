# Usage

```
$ mvn spring-boot:run
```

# Authorization

The default spring-security authentication routes have been left unmodified (i.e. use "/login" to login and "/logout" to logout instead of "/user/authorise")

# Accounts

Admin accounts have access to all routes, however, no admin "checking" has been implemented (i.e. trying to insert an appointment while being logged in as an admin will not work, since the patient information is based on the authenticated user)

Here are some pre-created user accounts

|----------|----------|---------|-------------------|
| Login    | Password | Role    | Routes            |
|----------|----------|---------|-------------------|
| admin    | password | ADMIN   | *                 |
|----------|----------|---------|-------------------|
| patient1 | password | PATIENT | /patient          |
|----------|----------|---------|-------------------|
| doctor1  | password | DOCTOR  | /doctor, /doctors |
|----------|----------|---------|-------------------|
