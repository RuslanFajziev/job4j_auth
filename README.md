## Описание ##
Учебный проект.
Сборник REST-сервисов job4j
#### Сервис auth ####
REST-сервис авторизации пользователей.
Для авторизации использует Json Web Token.
#### Сервис employees ####
REST-сервис сотрудников.
Он связан через RestTemplate с сервисом авторизации пользователей.
#### Сервис chat ####
REST-сервис чат пользователей.
Также связан через RestTemplate с сервисом авторизации пользователей.
#### Технологии проекта ####
![badge](https://img.shields.io/badge/PostgreSQL-14-blue)
![badge](https://img.shields.io/badge/Java-17-green)
![badge](https://img.shields.io/badge/Maven-3.6-green)
![badge](https://img.shields.io/badge/SpringBot-3.10-yellow)