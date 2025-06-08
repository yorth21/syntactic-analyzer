# Evaluador de Expresiones Lógico-Aritméticas

Este proyecto implementa un **analizador sintáctico recursivo descendente** en Java, capaz de interpretar y evaluar expresiones que combinan operaciones aritméticas, relacionales y lógicas.

## 🚀 ¿Qué hace este programa?

Permite ingresar expresiones como:

```
(10 + 5) > 12 & 3 < 8 | 4 == 4
```

Y responde con:

- ✅ Si la expresión es válida
- 🧮 El resultado (puede ser un número o un valor lógico como `true` o `false`)

---

## 🧠 ¿Qué tipos de operaciones soporta?

### ✔️ Aritméticas
- Suma: `+`
- Resta: `-`
- Multiplicación: `*`
- División: `/`
- Potencias: `^`

### ✔️ Relacionales
- Menor que: `<`
- Mayor que: `>`
- Menor o igual: `<=`
- Mayor o igual: `>=`
- Igual a: `==`
- Diferente de: `!=`

### ✔️ Lógicas
- Y lógico: `&` o `AND`
- O lógico: `|` o `OR`
- Negación lógica: `¬` o `NOT`

---

## 📥 ¿Cómo usarlo?

### 1. Clona el repositorio

```bash
git clone https://github.com/yorth21/syntactic-analyzer.git
cd tu-repo
```

### 2. Compílalo

Usa cualquier IDE (como IntelliJ o VS Code) o desde consola:

```bash
javac *.java
```

### 3. Ejecútalo

```bash
java Main
```

### 4. Interactúa

Escribe expresiones como estas:

```
5 + 10 * 2
(5 > 3) & (2 < 4)
10 == 10 | 20 != 5
```

Para salir, escribe:

```
salir
```

---

## 👨‍💻 ¿Qué tecnologías se usaron?

- Java puro (sin librerías externas)
- Programación estructurada con orientación a objetos
- Técnicas de análisis sintáctico descendente (gramáticas LL(1))

---

## 📂 Estructura del proyecto

- `Lexer.java`: separa la entrada en tokens
- `Parser.java`: analiza y evalúa la expresión
- `Token` y `TokenType`: representan los tipos y contenidos de tokens
- `NoTerminal`: clase usada para transportar valores parciales (números o lógicos)
- `Main.java`: interfaz simple por consola

---

## 📝 Créditos

Desarrollado por **Yorth** como parte de ejercicios académicos para la materia de **Lenguajes Formales y Autómatas**.

---

## 📃 Licencia

Este proyecto es de uso libre para fines académicos y educativos. Se prohíbe su venta o comercialización sin permiso del autor.
