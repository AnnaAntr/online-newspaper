# Интернет-газета (Backend)

Backend-часть приложения "Интернет-газета".

## Настройка и локальный запуск проекта
### 1. Клонирование репозитория
#### В Intellij Idea
1.1 File -> New -> Project from Version Control -> GitLab  
1.2 URL проекта на GitLab: http://git.nic.etu/aantrushina/online-newspaper  
1.3 После открытия проекта переключение на ветку develop: Remote -> develop -> Checkout  
#### В командной строке
```shell
git clone http://git.nic.etu/aantrushina/online-newspaper
cd online-newspaper
git checkout develop
```
### 2. Настройка БД
2.1 Установить PostgreSQL  
2.2 Создать базу данных (и пользователя при необходимости, по умолчанию пользователь - postgres):  
```
sudo -u postgres psql

CREATE DATABASE online_newspaper;
CREATE USER your_user WITH ENCRYPTED PASSWORD 'your_password';
GRANT ALL PRIVILEGES ON DATABASE online_newspaper TO your_user;
```
2.3 Восстановление данных из дампа  
```
cd /путь/к/PostgreSQL/версия/bin
psql -h localhost -p 5432 -U newspaper_admin -d online_newspaper -f /путь/к/db_dump.sql
```
### 3. Создание файла с чувствительными данными
3.1 В корне проекта создать файл .env  
3.2 Заполнить в формате:  
```
DB_URL=your_db_name
DB_USERNAME=your_username
DB_PASSWORD=your_password
JWT_SECRET=your_jwt_secret
```
### 4. Запуск в Intellij Idea
4.1 Открыть файл BackendApplication.java  
4.2 Перейти в параметры конфигурации (Edit Configuration при клике на название файла рядом с кнопкой Run)  
4.3 В разделе Environment Variables выбрать ранее созданный файл .env  
4.4 Запустить приложение нажатием на кнопку Run или сочетанием Shift + F10  
### 5. Запуск тестов
5.1 ПКМ на папку /src/test/java/online.mewspaper.backend/services  
5.2 Run 'Tests in 'online.mewspaper.backend.services''
