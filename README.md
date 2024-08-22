# Приложение Vitte-Core на Spring Boot

Это приложение Vitte-Core, созданное на основе Spring Boot. Оно предназначено для управления заявками пользователей и предоставляет административные функции. Приложение доступно как локально, так и на удаленном сервере.

## Запуск приложения локально

### Необходимые условия

Убедитесь, что на вашем компьютере установлены следующие компоненты:

- **Java 21** или выше
- **Maven 3.6.3** или выше
- **MySQL** (или другая реляционная база данных, поддерживаемая Spring Boot)
- **Git**

### Клонирование репозитория

```bash
git clone https://github.com/Juergen-J/Vitte-PD-Core.git
cd Vitte-PD-Core
```

### Конфигурация

1. **Настройка базы данных**:
    - Создайте базу данных в вашем локальном MySQL сервере:
      ```sql
      CREATE DATABASE vitte_core_db;
      ```
    - Обновите файл `application.properties` или `application.yml`, находящийся в `src/main/resources/`, с вашими локальными данными для подключения к базе данных:
      ```properties
      spring.datasource.url=jdbc:mysql://localhost:3306/vitte_core_db
      spring.datasource.username=your_db_username
      spring.datasource.password=your_db_password
      spring.jpa.hibernate.ddl-auto=update
      ```

2. **Сборка проекта**:
    - Выполните следующую команду Maven для сборки проекта:
      ```bash
      mvn clean install
      ```

### Запуск приложения

После настройки окружения, вы можете запустить приложение с помощью команды:

```bash
mvn spring-boot:run
```

Приложение будет доступно по адресу `http://localhost:8080`.

## Доступ к приложению

После запуска приложения вы можете получить доступ к различным разделам через следующие URL:

- **Панель управления пользователя**: [http://localhost:8080/dashboard](http://localhost:8080/dashboard)
    - Здесь пользователи могут просматривать и управлять своими заявками.
- **Административная панель**: [http://localhost:8080/admin/dashboard](http://localhost:8080/admin/dashboard)
    - Этот раздел предназначен для управления пользователями и административных задач.

### Удаленный доступ

Приложение также развернуто и доступно удаленно по следующим адресам:

- [https://vitte-core.up.railway.app/](https://vitte-core.up.railway.app/)
- **Панель управления пользователя**: [https://vitte-core.up.railway.app/dashboard](https://vitte-core.up.railway.app/dashboard)
- **Административная панель**: [https://vitte-core.up.railway.app/admin/dashboard](https://vitte-core.up.railway.app/admin/dashboard)

## Роли пользователей и права

Ниже представлена таблица, описывающая различные роли пользователей и их права в приложении:

| Имя пользователя | Пароль  | Роль      | Права                                                  |
|------------------|---------|-----------|--------------------------------------------------------|
| user1            | password| USER      | Может добавлять и изменять свои заявки                 |
| employee         | password| EMPLOYEE  | Может просматривать все заявки и отвечать на них       |
| admin            | password| ADMIN     | Управляет пользователями и выполняет административные задачи |

### Описание ролей

- **USER**: Эта роль предназначена для обычных пользователей, которые могут подавать и управлять своими заявками.
- **EMPLOYEE**: Пользователи с этой ролью имеют доступ ко всем заявкам и отвечают за их обработку.
- **ADMIN**: Администраторы имеют полный контроль над приложением, включая управление учетными записями пользователей и их ролями.