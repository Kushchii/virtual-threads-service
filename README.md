# 📈 Performance & Load Testing Report

Цей репозиторій містить результати тестування навантаження та використання CPU під час обробки HTTP-запитів із використанням загальних потоків та віртуальних потоків.

---

## ⚙️ Reactor Flow

### 🚀 1000 запитів за 3 хвилини

**CPU використання:**

![CPU reactor 1000 requests](images/reactor%201000r.png)

**Навантаження (K6):**

![Load test reactor 1000 requests](images/reactor%201000r%20k6.png)

---

### 🚀 2000 запитів за 3 хвилини

**CPU використання:**

![CPU reactor 2000 requests](images/reactor%202000r.png)

**Навантаження (K6):**

![Load test reactor 2000 requests](images/reactor%202000r%20k6.png)

---

### 🚀 4000 запитів за 3 хвилини

**CPU використання:**

![CPU reactor 4000 requests](images/reactor%204000r.png)

**Навантаження (K6):**

![Load test reactor 4000 requests](images/reactor%204000r%20k6.png)


## 📂 Структура

- `images/` — всі графіки CPU та K6 для різних конфігурацій потоків.

---