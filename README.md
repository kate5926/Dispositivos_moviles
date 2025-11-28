# Proyecto Final: "GASTOS" - App de Gestión de Finanzas Personales

## Información General

**Curso:** Dispositivos Moviles 
**Profesor:** Marco Antonio Camacho Alatrista  


### Estudiantes
*   Esther Chunga Pacheco
*   Katherine Saico Ccahuana

---

## Objetivo de la Aplicación

Desarrollar una aplicación móvil llamada **“GASTOS”** que permita a los usuarios llevar un control detallado de sus finanzas personales mediante el registro de ingresos y gastos, establecimiento de presupuestos por categorías y visualización de reportes mensuales. El objetivo principal es ayudar a los usuarios a mantener unas finanzas saludables mediante una herramienta intuitiva y eficaz.

---

##  Funcionalidades Principales

### 1. Registro de Transacciones (Ingresos y Gastos)
- **Registro completo:** Los usuarios pueden registrar transacciones indicando monto, categoría, fecha y una descripción opcional.
- **Clasificación automática:** Las transacciones se clasifican fácilmente como ingreso o gasto.
- **Interfaz intuitiva:** Formularios validados para garantizar que los datos ingresados sean correctos y completos.

### 2. Límites de Presupuesto por Categoría
- **Presupuestos personalizados:** Los usuarios pueden establecer límites de gasto mensuales por categorías (Ej: Comida, Transporte, Entretenimiento).
- **Seguimiento visual:** La aplicación muestra el progreso del gasto en tiempo real frente al presupuesto establecido.
- **Alertas inteligentes:** Notificaciones o alertas visuales informan al usuario cuando se aproxima o excede su presupuesto.

### 3. Reportes Mensuales con Gráficos
- **Análisis visual:** Generación de reportes que muestran la distribución de gastos por categoría.
- **Gráficos dinámicos:** Se utilizan gráficos circulares (pie charts) y de barras para comparar ingresos vs. gastos de forma clara.
- **Historial financiero:** Filtros por mes y año para realizar un análisis histórico del comportamiento financiero.

---

## Tecnologías y Contenidos Aplicados

- **Lenguaje de Programación:** Kotlin
- **Entorno de Desarrollo:** Android Studio / Flutter
- **Arquitectura de Navegación:** Navigation Component para gestionar la navegación entre Fragments.
- **Visualización de Listas:** RecyclerView para mostrar el historial de transacciones de manera eficiente.
- **Gestión del Ciclo de Vida:** Componentes `Lifecycle-aware` para un manejo robusto y seguro de los datos.

---

## Diseño de Interfaces (Avance 2)

### 1. Pantalla Principal (`fragment_home.xml`)
- **Resumen Financiero:** Es la pantalla de inicio. Muestra un resumen del saldo disponible y proporciona acceso rápido a las funciones principales.
- **Componentes:**
  - Título y saldo actual.
  - Botones de acceso a: "Agregar Transacción", "Ver Historial", "Gestionar Presupuestos" y "Ver Reportes".
- **Controlador:** `HomeFragment.kt` se encarga de calcular el saldo y gestionar la navegación.

### 2. Pantalla de Agregar Transacción (`fragment_transaction.xml`)
- **Formulario de Registro:** Permite al usuario registrar un nuevo ingreso o gasto.
- **Componentes:**
  - Selector para "Ingreso" o "Gasto".
  - Campos para monto, fecha y descripción.
  - Menú desplegable (Spinner) para seleccionar la categoría.
  - Botón "Guardar".
- **Controlador:** `AddTransactionFragment.kt` valida los datos y los guarda en la base de datos antes de regresar a la pantalla principal.

### 3. Pantalla de Historial (`fragment_transaction_list.xml`)
- **Lista de Movimientos:** Muestra todas las transacciones registradas, ordenadas de la más reciente a la más antigua.
- **Componentes:**
  - Título de la pantalla.
  - Una lista (`RecyclerView`) que muestra el monto, categoría, fecha y descripción de cada transacción.
- **Controlador:** `TransactionListFragment.kt` carga los datos desde la base de datos para mostrarlos en la lista.

### 4. Pantalla de Presupuestos (`fragment_budgets.xml`)
- **Control de Gastos:** Permite al usuario definir un presupuesto mensual para una categoría específica y visualizar su progreso.
- **Componentes:**
  - Menú para seleccionar la categoría.
  - Campo de texto para ingresar el monto del presupuesto.
  - Barra de progreso que refleja el gasto actual vs. el límite.
  - Botón "Guardar".
- **Controlador:** `BudgetsFragment.kt` gestiona la lógica para guardar/actualizar el presupuesto y calcular el porcentaje consumido.

### 5. Pantalla de Reportes (`fragment_reports.xml`)
- **Análisis Gráfico:** Muestra estadísticas visuales de las finanzas del usuario.
- **Componentes:**
  - Filtros para seleccionar el mes y el año a analizar.
  - Un contenedor para mostrar gráficos circulares o de barras.
- **Controlador:** `ReportsFragment.kt` se encargará de generar los gráficos utilizando una librería externa para una visualización de datos efectiva.

# 💰 Controla tus Finanzas - App Móvil

Una aplicación móvil diseñada para ayudarte a organizar mejor tu dinero de manera **sencilla, intuitiva y visual**.

## ✨ Características Principales

- **Registro de transacciones**: Controla ingresos y gastos fácilmente
- **Gestión de presupuestos**: Establece límites mensuales y monitorea tu progreso  
- **Reportes visuales**: Gráficos claros sobre el uso de tu dinero
- **Historial completo**: Consulta todas tus transacciones organizadas

##  Pantallas Desarrolladas

###  Home Principal
- Vista rápida de tu saldo disponible
- Acceso rápido a todas las funciones principales
- Navegación intuitiva entre módulos

###  Agregar Transacciones
- Registro de ingresos y gastos
- Categorización de movimientos
- Fecha, monto y descripción

###  Historial
- Lista completa de transacciones
- Interfaz dinámica con RecyclerView
- Fácil navegación y consulta

###  Presupuestos
- Establecimiento de límites mensuales
- Seguimiento visual con barras de progreso
- Control de gastos por categorías

### Reportes
- Filtros por mes y año
- Gráficos estadísticos
- Análisis visual de finanzas

##  Navegación

Todas las pantallas están conectadas mediante un sistema de navegación fluido, permitiendo moverse entre módulos con un solo clic.



