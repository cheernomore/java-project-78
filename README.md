### Hexlet tests and linter status:
[![Actions Status](https://github.com/cheernomore/java-project-78/workflows/hexlet-check/badge.svg)](https://github.com/cheernomore/java-project-78/actions)
![Java CI](https://github.com/cheernomore/java-project-78/workflows/Java%20CI/badge.svg)
[![Maintainability](https://api.codeclimate.com/v1/badges/b54c3e9f1c13864fc7fc/maintainability)](https://codeclimate.com/github/cheernomore/java-project-78/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/b54c3e9f1c13864fc7fc/test_coverage)](https://codeclimate.com/github/cheernomore/java-project-78/test_coverage)


# Hexlet Schema Validator

Простой и гибкий инструмент для валидации данных в Java-приложениях с помощью схем. Позволяет легко и быстро задавать правила проверки и проверять соответствие данных этим правилам.

---

## О проекте

Проект предоставляет набор готовых схем (`StringSchema`, `NumberSchema`, `MapSchema`), которые позволяют описать требования к данным и быстро проверить их корректность.

---

## Используемые технологии

- **Java 17+**
- **Lombok**

---

## Схемы и возможности

Доступны следующие типы схем:

- **StringSchema**
    - обязательность значения (`required`)
    - минимальная длина (`minLength`)
    - проверка наличия подстроки (`contains`)

- **NumberSchema**
    - обязательность значения (`required`)
    - проверка на положительное число (`positive`)
    - проверка на попадание числа в диапазон (`range`)

- **MapSchema**
    - обязательность карты (`required`)
    - точный размер карты (`sizeof`)
    - вложенная проверка структуры карты (`shape`)

---

## Примеры использования

### StringSchema
```java
StringSchema schema = new StringSchema();

schema.required();
schema.minLength(4);
schema.contains("hex");

schema.isValid("hexlet");  // true
schema.isValid("hex");     // false (не подходит по длине)
schema.isValid("");        // false
```

### NumberSchema
```java
NumberSchema schema = new NumberSchema();

schema.required();
schema.positive();
schema.range(10, 20);

schema.isValid(15);   // true
schema.isValid(-5);   // false
schema.isValid(25);   // false
```

### MapSchema
```java
MapSchema schema = new MapSchema();

schema.required();
schema.sizeof(2);

Map<String, String> data = Map.of("key1", "val1", "key2", "val2");
schema.isValid(data);  // true

Map<String, BaseSchema<String>> shape = Map.of(
    "username", new StringSchema().required().minLength(3),
    "role", new StringSchema().required().contains("admin")
);

schema.shape(shape);

Map<String, String> user = Map.of(
    "username", "hexlet",
    "role", "admin"
);

schema.isValid(user);  // true
```

---

## Сборка и запуск

Для сборки и запуска проекта используйте вашу IDE или систему сборки (например, Gradle или Maven).  
Предполагается использование Java 17 или выше.
