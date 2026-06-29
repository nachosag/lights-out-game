# Lights Out Game

> **Java + Swing** — Proyecto universitario · UNGS · 2023

Juego clásico **Lights Out** implementado en Java con interfaz gráfica Swing. El objetivo es apagar todas las luces de un tablero cuadrado. Al presionar una celda se alterna su estado y el de toda su fila y columna.

## Cómo funciona

1. El tablero comienza con un conjunto de luces encendidas generado aleatoriamente
2. Presionás una celda → se apagan o encienden todas las luces de esa fila y columna
3. El objetivo es dejar **todas las luces apagadas**
4. Cada movimiento suma un click — tratá de ganar con la menor cantidad posible

## Características

- **Tablero de dificultad variable**: 3×3 a 6×6 (botones "Aumentar dificultad" / "Disminuir dificultad")
- **Tableros siempre resolubles**: El algoritmo inverse shuffle garantiza que todo tablero generado tenga solución
- **Contador de clicks y récord**: Seguí tu mejor marca
- **Sistema de recomendaciones**: Si te trabás, apretá "Pedir ayuda" y te marca una celda sugerida en violeta
- **Detección de victoria**: Al apagar todas las luces, muestra un mensaje de felicitaciones y reinicia automáticamente

## Stack

| Tecnología | Uso |
|-----------|-----|
| Java 17 | Lenguaje |
| Java Swing | Interfaz gráfica |
| Eclipse (WindowBuilder) | IDE |

## Estructura del proyecto

```
ligths-out-game/
├── src/
│   ├── Juego.java                  # Lógica del juego (modelo)
│   ├── Interfaz.java               # UI Swing + controladores
│   └── CoordenadaRecomendada.java  # Objeto coordenada para recomendaciones
├── .classpath                      # Configuración Eclipse (JavaSE-17)
├── .project                        # Proyecto Eclipse
└── .settings/
    └── org.eclipse.jdt.core.prefs  # Preferencias del compilador
```

## Arquitectura (MVC)

```
Juego (Model)          → Estado del tablero, lógica de juego, algoritmo de mezcla
Interfaz (View)        → Ventana Swing, botones, labels, menú
Listener (Controller)  → Inner class que conecta eventos de UI con el modelo
```

Capa | Responsabilidad
-----|---------------
**Model** (`Juego.java`) | Matriz de luces, toggle, inverse shuffle, verificación de victoria, dificultad, recomendaciones
**View/Controller** (`Interfaz.java`) | `JFrame` + `JPanel` con `GridLayout`, matriz de `JButton`, labels, botones de menú, `ActionListener` interno
**Helper** (`CoordenadaRecomendada.java`) | Value object fila/columna con `equals` para comparación posicional

## Algoritmo: Inverse Shuffle

En lugar de generar un estado aleatorio y verificar si tiene solución, se parte de un estado resuelto (todo apagado) y se aplican **N toggles aleatorios** en coordenadas distintas. Como cada toggle es su propia inversa, la secuencia completa es reversible → el tablero siempre tiene solución.

## Sistema de recomendaciones

El juego trackea cada celda que presionás. Si presionás una celda dos veces, se cancela (porque dos toggles = no-op). Cuando pedís ayuda, el juego sugiere una celda al azar de las que trackeó para guiarte hacia atrás. No es un solver óptimo, pero ayuda a salir de un atasco.

## Cómo ejecutar

### Desde Eclipse

1. Abrí Eclipse → `File → Import → Existing Projects into Workspace`
2. Seleccioná la carpeta del proyecto
3. Ejecutá `Interfaz.java` → `Run As → Java Application`

### Desde terminal

```bash
# Compilar
javac -d bin src/lightsOut/*.java

# Ejecutar
java -cp bin lightsOut.Interfaz
```

## Sobre el proyecto

Fue desarrollado como proyecto universitario para la materia **Programación de Objetos Distribuidos II** (o materia afín) en la **UNGS**, durante el segundo semestre de 2023. El objetivo era aplicar el patrón **MVC** con Java Swing y desarrollar un algoritmo de generación de niveles funcional.
