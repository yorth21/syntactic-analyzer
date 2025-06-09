# 🧠 Analizador de Expresiones Lógico-Aritméticas

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Java Version](https://img.shields.io/badge/Java-11+-orange.svg)](https://www.oracle.com/java/)
[![Build Status](https://img.shields.io/badge/Build-Passing-brightgreen.svg)]()

Este proyecto implementa dos tipos de analizadores sintácticos en Java que permiten interpretar y evaluar expresiones que combinan operaciones aritméticas, relacionales y lógicas.

---

## ✨ ¿Qué incluye el proyecto?

🔹 **Analizador Recursivo Descendente:**  
Interpreta expresiones lógico-aritméticas completas como:

```
(10 + 5) > 12 & 3 < 8 | 4 == 4
```

🔹 **Analizador con Pila (No Recursivo):**  
Evalúa expresiones aritméticas y genera:

- Resultado
- Notación infija
- Notación postfija
- Notación prefija

---

## 🛠️ ¿Cómo usarlo?

### 1. Clona el repositorio

```bash
git clone https://github.com/yorth21/syntactic-analyzer.git
cd syntactic-analyzer
```

### 2. Compílalo

```bash
javac com/yorth21/**/*.java
```

### 3. Ejecútalo

```bash
java com.guiller.Main
```

---

## 📋 Menú de opciones

Cuando ejecutes el programa, verás el siguiente menú interactivo:

```
=== Analizador de Expresiones ===
1. Analizador Recursivo
2. Analizador con Pila
3. Salir
```

🔹 Escribe una expresión y presiona Enter.  
🔹 El sistema mostrará el resultado o los errores correspondientes.  
🔹 Puedes escribir expresiones múltiples hasta elegir "Salir".

---

## 📂 Documentación por Analizador

- 🔎 [Ver detalles del Analizador Recursivo](README-recursive.md)
- ⚙️ [Ver detalles del Analizador con Pila](README-pila.md)

---

## 🧪 Requisitos y Tecnologías

- Java 11+
- No usa librerías externas
- Estructurado por paquetes:
    - `recursive` para el parser recursivo
    - `pila` para el parser con pila
    - `shared` para componentes comunes (Lexer, Token, etc.)

---

## 📝 Créditos

Desarrollado por **Yorth** como parte de prácticas académicas en la asignatura de **Lenguajes Formales y Autómatas**.

---

## 🪪 Licencia

Este proyecto es de uso educativo. Puedes modificarlo, mejorarlo y compartirlo libremente, siempre con fines no comerciales.
