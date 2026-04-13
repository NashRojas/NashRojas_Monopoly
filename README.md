# Monopolio – JavaFX

## 1. Descripcion del proyecto

Aplicacion de escritorio que implementa una version simplificada del juego Monopolio para multiples jugadores. El juego permite comprar propiedades, pagar rentas y gestionar el dinero hasta que quede un solo jugador.

## 2. Funcionalidades implementadas

- Menu inicial para configurar la partida
- Seleccion de cantidad de jugadores reales
- Seleccion de cantidad de bots
- Tablero grafico en JavaFX
- Movimiento de jugadores por turnos
- Lanzamiento de dado
- Compra de propiedades
- Compra de servicios
- Pago de rentas
- Mejora de propiedades
- Casillas especiales:
  - Salida
  - Impuesto
  - Carcel
  - Ir a carcel
  - Evento
  - Oportunidad de negocio
- Bots con acciones automaticas
- Paneles de informacion por jugador
- Log de acciones del juego
- Condicion de victoria y finalizacion de partida
- Interfaz personalizada con estilo visual propio

## 3. Requisitos previos

* Java JDK 17 o superior
* JavaFX SDK
* IDE recomendado: IntelliJ IDEA

-Tambien es necesario configurar correctamente JavaFX en el IDE.

## 4. Como ejecutar el proyecto

1. Abrir el proyecto en el IDE
2. Configurar el SDK de Java (JDK 17 o superior)
3. Configurar JavaFX (agregar las librerias)
4. Ejecutar la clase principal (Main)


## 5. Estructura del proyecto

* src/: código fuente del juego
 Dentro de src el proyecto esta organizado en paquetes principales:

### model
Contiene la logica del juego:
- Juego: controla turnos, jugadores y flujo general
- Jugador: representa a cada jugador
- Casilla: clase base del tablero
- Propiedad: casillas comprables con renta
- Servicio: casillas con renta basada en cantidad
- CasillaEvento: eventos aleatorios
- Otras casillas: Salida, Impuesto, Carcel, IrACarcel, OportunidadNegocio

### controller
Controla la interaccion entre la vista y la logica:
- GameController: controla el flujo del juego y la interfaz principal
- MenuController: maneja el menu inicial
- TableroController: controla la visualizacion del tablero

### view (FXML)
Contiene la interfaz grafica:
- MenuView.fxml
- MainView.fxml
- TableroView.fxml

--

## 6. Decisiones de diseño

- Se utilizo una separacion tipo MVC para dividir la logica del juego, la interfaz y el control de eventos.
- Se implemento herencia en las casillas para permitir diferentes comportamientos usando el metodo ejecutarAccion.
- Se centralizo la logica del juego en la clase Juego para evitar mezclar logica con la interfaz.
- Se utilizaron listas para manejar colecciones de jugadores, propiedades y servicios.
- Se implementaron bots con logica automatica para simular jugadores.
- La interfaz fue diseñada en FXML para mantener separada la estructura visual del codigo.

---

## 7. Autor

- Nash Juan Rojas Brito – 2026
- Fecha de entrega: Lunes 13 de abril del 2026 (13/04/2026).