# 📈 Performance & Load Testing Report

Цей репозиторій містить результати тестування навантаження та використання CPU під час обробки HTTP-запитів із використанням загальних потоків та віртуальних потоків.

---

## 🔧 General Flow

### ✅ 1000 запитів за 3 хвилини

**CPU використання:**

![CPU general flow 1000 requests](images/general%20flow%201000%20request.png)

**Навантаження (K6):**

![Load test general flow 1000 requests](images/general%20flow%201000%20request%20k6.png)

---

### ✅ 2000 запитів за 3 хвилини

**Навантаження (K6):**

![Load test general flow 2000 requests](images/general%20flow%202000r%20k6.png)

---

## ⚙️ Virtual Threads Flow

### 🚀 1000 запитів за 3 хвилини

**CPU використання:**

![CPU virtual threads 1000 requests](images/virtual%20threads%201000r.png)

**Навантаження (K6):**

![Load test virtual threads 1000 requests](images/virtual%20threads%201000r%20k6.png)

---

### 🚀 4000 запитів за 3 хвилини

**CPU використання:**

![CPU virtual threads 4000 requests](images/virthual%20threads%204000r.png)

**Навантаження (K6):**

![Load test virtual threads 4000 requests](images/virhtual%20threads%204000r%20k6.png)


## 📂 Структура

- `images/` — всі графіки CPU та K6 для різних конфігурацій потоків.

---