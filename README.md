# Examen Parcial: Juego de Colores

Este proyecto es una aplicación de Android desarrollada en Kotlin como parte del examen parcial del curso de Programación para Dispositivos Móviles.

## 🎯 Objetivo

El objetivo de este juego es que el usuario presione el botón que coincida con el color que se muestra en la pantalla. El jugador tiene 30 segundos para conseguir la mayor cantidad de aciertos posible. La aplicación también registrará el historial de puntajes de la sesión actual.

## 🧩 ¿Qué debe tener la aplicación?

La aplicación se compone de tres fragmentos principales:

### 1. Fragmento de bienvenida (WelcomeFragment)
- **Título del juego:** Muestra el nombre del juego.
- **Mensaje de bienvenida y reglas:** Un `AlertDialog` muestra las reglas del juego.
- **Botón “Iniciar juego”:** Permite al usuario comenzar una nueva partida.
- **Interfaz de usuario:** Diseño libre que puede incluir imágenes para mejorar la experiencia del usuario.

### 2. Fragmento del juego (GameFragment)
- **Visualización de colores:** Un cuadro en la pantalla muestra un color aleatorio (rojo, verde, azul, amarillo, etc.).
- **Botones de respuesta:** Varios botones, cada uno de un color diferente, para que el usuario seleccione su respuesta.
- **Sistema de puntuación:** Cada acierto suma un punto al marcador y cambia el color a adivinar.
- **Temporizador:** Una cuenta regresiva de 30 segundos.
- **Información en pantalla:** Muestra el puntaje actual y el tiempo restante.
- **Fin del juego:** El juego termina cuando el temporizador llega a cero.

### 3. Fragmento de resultados (ResultFragment)
- **Puntaje final:** Muestra el puntaje obtenido en la partida recién terminada.
- **Puntaje más alto:** Muestra el puntaje más alto registrado en el dispositivo, utilizando `SharedPreferences`.
- **Historial de puntajes:** Un `RecyclerView` muestra el historial de los puntajes obtenidos durante la sesión actual (sin persistencia en base de datos).
- **Botón “Volver a jugar”:** Permite al usuario iniciar una nueva partida.

## 🎨 Funcionalidades Adicionales

- **Animaciones:** Se pueden aplicar animaciones a los botones o a los colores que aparecen en pantalla para hacer la interfaz más dinámica.

## 🧪 ¿Qué se debe practicar?

Este proyecto está diseñado para poner en práctica una variedad de conceptos de desarrollo de Android, incluyendo:

- **Navegación:** Uso de `Navigation Component` para moverse entre Fragments y pasar datos.
- **Temporizador:** Implementación de un `CountDownTimer`.
- **Interacción con el usuario:** Manejo de eventos de clic en botones y proporcionar feedback visual.
- **Lógica de la aplicación:** Manejo de estados y lógica condicional.
- **Diseño de la interfaz de usuario:** Uso de `ConstraintLayout` para crear interfaces de usuario responsivas.
- **Buenas prácticas:** Organización del código, validación de entradas y uso de recursos de Android.
- **Recursos visuales:** Uso adecuado de colores, imágenes y otros recursos gráficos.
- **Almacenamiento de datos:**
  - `SharedPreferences` para almacenar datos simples como el puntaje más alto.
  - `AlertDialog` para mostrar información al usuario.
- **(Opcional) Almacenamiento avanzado:** Uso de `Room` para persistencia de datos.
- **(Opcional) Multimedia:** Incorporación de sonidos o animaciones.

## 🧠 Tips útiles

- **Recursos de cadenas:** Utiliza `strings.xml` para gestionar todos los textos de la aplicación.
- **Recursos de colores:** Define los colores de la aplicación en `colors.xml`.
- **Generación de colores:** Crea una función para generar colores aleatorios.
- **Temporizador:** La clase `CountDownTimer` es ideal para la cuenta regresiva del juego.
- **Puntaje más alto:** `SharedPreferences` es una forma sencilla y eficaz de guardar el puntaje más alto.
