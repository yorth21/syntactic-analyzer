# 🔍 Analizador Recursivo Descendente

Este analizador implementa una gramática lógica-aritmética utilizando un **parser descendente recursivo** construido manualmente en Java.

Permite interpretar y evaluar expresiones que combinan:

- Operadores aritméticos
- Operadores relacionales
- Operadores lógicos
- Paréntesis y agrupación

---

## ✅ ¿Qué operaciones reconoce?

### Aritméticas
- `+` Suma
- `-` Resta
- `*` Multiplicación
- `/` División
- `^` Potenciación

### Relacionales
- `<`, `>`, `<=`, `>=`, `==`, `!=`

### Lógicas
- `&` o `AND`
- `|` o `OR`
- `¬` o `NOT`

---

## ⚙️ ¿Cómo funciona?

La gramática utilizada está estructurada para cumplir con el formato **LL(1)**, lo que permite que cada función recursiva represente una producción.

Se usan funciones como:

```java
NoTerminal S()
NoTerminal ELO()
NoTerminal EL2()
NoTerminal ER()
NoTerminal E()
...
```

Cada función evalúa una parte de la expresión y retorna un objeto `NoTerminal` con:

- Resultado numérico o lógico
- Indicador de si es una expresión relacional (para validar correctamente operadores `AND` y `OR`)

---

## 🔒 Validaciones semánticas

Este analizador no solo valida la **sintaxis**, sino también la **semántica**:

- ✅ `5 > 3 & 2 < 4` → válido
- ❌ `5 & 3` → inválido (el operador `&` requiere que ambos lados sean resultados de comparaciones)

---

## ✍️ Ejemplos de uso

Entrada:

```
(10 + 5) > 12 & 3 < 8 | 4 == 4
```

Salida esperada:

```
✅ Expresión válida.
Resultado lógico: true
```

---

Entrada:

```
5 + 2 * 3 - 1
```

Salida esperada:

```
✅ Expresión válida.
Resultado numérico: 10.0
```

---

## 📦 Archivos principales

- `Parser.java`: Implementación del analizador recursivo
- `NoTerminal.java`: Clase para transportar resultados y validaciones
- `Lexer.java`, `Token.java`, `TokenType.java`: Compartidos (en el paquete `shared`)

---

## 📚 Recomendaciones

- Usa paréntesis para asegurar la precedencia cuando sea necesario.
- Asegúrate de que los operadores lógicos solo combinen expresiones comparativas (relacionales).
