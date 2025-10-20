# Juego de Colores - Examen Parcial

## Descripción del Proyecto
Aplicación móvil desarrollada en Kotlin para Android que implementa un juego interactivo donde el usuario debe identificar y seleccionar colores coincidentes bajo un límite de tiempo.

## Objetivo Académico
Desarrollar una aplicación que demuestre competencias en:
- Navegación entre Fragments
- Gestión del ciclo de vida de componentes Android
- Implementación de lógica de juego interactiva
- Almacenamiento de datos con SharedPreferences
- Diseño de UI responsiva con ConstraintLayout

## Funcionalidades Implementadas

### Fragmento de Bienvenida (WelcomeFragment)
- Título del juego y mensaje de bienvenida
- AlertDialog automático con instrucciones del juego
- Botón "Iniciar Juego" con animación de pulsación
- Navegación al juego principal

### Fragmento de Juego (GameFragment)
- Cuadro de color aleatorio (rojo, verde, azul, amarillo, morado, naranja)
- Seis botones de colores para selección
- Temporizador de 30 segundos con cuenta regresiva
- Sistema de puntuación en tiempo real
- Animaciones:  vibración en errores

### Fragmento de Resultados (ResultFragment)
- Puntaje final de la partida actual
- Mejor puntaje
- Historial de partidas de la sesión (ListView)
- Botón "Jugar Otra Vez" 

## Tecnologías y Patrones Utilizados

### Arquitectura y Navegación
- Navigation Component para gestión de fragments
- Paso de datos entre fragments mediante Bundle
- Ciclo de vida de fragments correctamente gestionado
- ConstraintLayout para diseño responsivo
- Recursos externalizados (strings.xml, colors.xml)
- Animaciones nativas de Android
- Feedback visual inmediato al usuario

### Lógica del Juego
- CountDownTimer para temporizador
- Generación aleatoria de colores
- Validación de respuestas del usuario


### Funcionalidad Adicional
- Animaciones en interacciones
- Efectos visuales en errores

## Instalación y Ejecución

1. Clonar o descargar el proyecto
2. Abrir en Android Studio
3. Sincronizar dependencias con Gradle
4. Ejecutar en emulador o dispositivo físico
5. API mínima requerida: Android 5.0 (API 21)




### Estructura del Proyecto

