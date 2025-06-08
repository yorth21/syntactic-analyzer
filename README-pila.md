# ⚙️ Analizador con Pila (No Recursivo)

Este analizador evalúa **expresiones aritméticas** utilizando una **máquina de pila** manual (sin recursión), implementada desde cero en Java.

---

## 🧠 ¿Qué hace este analizador?

Interpreta expresiones aritméticas como:

```
(5 + 2) * 3 - 1
```

Y construye al mismo tiempo:

- ✅ El resultado numérico
- 🧾 La notación infija
- 🧮 La notación postfija
- 🔁 La notación prefija

---

## ✨ ¿Qué operadores reconoce?

### ✔️ Aritméticos
- `+` Suma
- `-` Resta
- `*` Multiplicación
- `/` División
- `^` Potenciación (asociativa por la derecha)

### ✔️ Paréntesis
- `(`, `)` para agrupar expresiones

---

## 🧱 ¿Cómo funciona?

Este analizador simula una **máquina LL(1)**:

1. Usa una **pila de símbolos** (`TERMINAL`, `NO_TERMINAL`, `ACCION`)
2. Utiliza una **tabla de control** basada en la gramática
3. Lee los tokens usando un `Lexer`
4. Ejecuta **acciones semánticas** como `{suma}`, `{mul}`, `{exp}`, etc.
5. Utiliza una pila paralela para los **atributos** (`ResultadoParcial`) que guarda:
    - El valor
    - La expresión infija
    - La postfija
    - La prefija

---

## 🔎 Ejemplo de uso

Entrada:

```
5 + 2 * 3
```

Salida:

```
✅ Expresión válida.
Resultado: 11.0
Infijo:    (5 + (2 * 3))
Postfijo:  5 2 3 * +
Prefijo:   + 5 * 2 3
```

---

## ⚠️ Validaciones

- Todos los identificadores deben ser números (por ahora no hay variables)
- El analizador rechaza expresiones mal formadas, como:
    - `5 + * 3`
    - `((2 + 3)`

---

## 📂 Archivos clave

- `AnalizadorPila.java`: Máquina de pila con tabla de control y ciclo principal
- `ResultadoParcial.java`: Clase que contiene el valor y las notaciones
- `Simbolo.java`, `TipoSimbolo.java`: Representan los símbolos de análisis
- `Lexer.java`, `Token.java`, `TokenType.java`: Compartidos desde el paquete `shared`
