# Tarea 3: Breakout

Implementación de la lógica e interfaz gráfica del juego breakout implementando patrones de diseño vistos en clases.

## Cómo inciar el juego

Para inciar el juego basta descargar el repositorio cono .zip, luego importar el proyecto como Maven en IntelliJ, configurar java en la version 8 y luego ejecutar el método main de la clase App ubicada en src/main/java/ui.

## Controles del juego

N: Añadir un nivel al juego. Necesario para comenzar a jugar. En cualquier momento se pueden agregar niveles al juego
A: Mover plataforma a la izquierda
D: Mover plataforma a la derecha
SPACE: Lanzar la bola
R: Reiniciar el juego. Sólo accedible al ganar o perder el juego

## Implementación

El en el directorio '/src/main/java' se encuentran todos los archivos que implementan la lógica del juego. Desde aquí se distinguen varios paquetes principales:

**Controller:** Con su única clase 'Game', es el paquete controlador de la lógica del juego. Es un observador del nivel actual de juego y recibe notificaciones de él. Controla la cantidad de bolas y además decide cuando pasar de nivel, ganar o perder el juego. Se implementa inspirado en el 'Observer Pattern' visto en clases.

**Logic:** Implementa la lógica del juego, se distinguen varios sub-paquetes:

++**Brick:** Implementa el funcionamiento de los Bricks en el juego. Todos los Bricks son visitables por un Nivel. Esto para que el nivel pueda sumar puntos haciendo distinción del tipo de Brick visitado. Cuando un Brick es destruido, este envía un mensaje a Level para que lo visite y realize acciones. Se inspira en el 'Visitor Pattern' y 'Observer Pattern' Vsito en clases.
  
++**Level:** Implementa la lógica de los niveles del juego. Cada nivel tiene un nivel siguiente formando una lista enlazada de niveles. Por otro lado, un nivel es un Visitante que vistia Bricks como se mencionó anteriormente. El nivel informa al controlador Game cada vez que un cambio importante ocuure.

**Facade:** Oculta bajo sus métodos simples la lógica compleja del juego. Es utilizado en la interfaz gráfica para simplificar las cosas. Corresponde a la implementación del 'Facade Pattern'.

**UI:** Implementa la interfaz gráfica del juego. Relaciona la lógica implementada con la librería de javafx. También posee sub-paquetes y clases:
  
++**GameState:** Corresponde a una implementación del 'State Pattern' para la limitación de las acciones del jugador y de las entidades. Por ejemplo, en un estado de StandBy, la pelota debe estar sobre la plataforma y seguirla. También limita para ciertos estados las acciones de las teclas, por ejemplo, sólo se puede reiniciar el juego si este está ganado o perdido.
  
++**App:** Clase principal donde se inicial el juego. Se encarga de abrir la ventana y relacionar la lógica con la interfaz visual. Posee un estado que determina las acciones del juego. Posee handlers que están atentos al accionamiento de teclas. También se encarga de la implementación de las features del juego, y de las texturas del mismo.
  
++**EntityController:** Controlador de la física de las entidades gráficas del juego. Útil para la delegación de tareas básicas como mover a la derecha o la izquierda.
  
++**GameFactory:** Fábrica de entidades gráficas, inspirada en el 'Factory Pattern' visto en clases. Es su única función.

## Features implementadas
### Mayores

**Estado distinto:** Cuando un Brick es golpeado este se agrieta. Mientras más golpes reciba, más agrietado se encontrará. Esto aplica para l hhos MetalBricks y los WoodenBricks porque el GlassBrick se rompe con un golpe. Esto está implementado dentro del método hitUIBrick(Entity uiBrick) en la clase App. Está comentado pára su fácil localización.

**Mecanismo de testing:** Cuando se hace mantiene presionado el click principal del maouse sobre un brick en el juego, este se destruye. Esto sirve para verificar comportamientos en la interfaz. Se encuentra dentro del método initInput() en la clase App. También está comentado para localizarlo fácilmente.

### Menores

**Sonido al golpe:** Cuando se golpea un Brick este emite un sonido. El sonido es diferente para cada tipo de Brick, y cuando este es destruido, suena un sonido de destrucción transversal para todos los tipos de bricks. hitUIBrick(Entity uiBrick) en la clase App. Está comentado pára su fácil localización.

**Bolas con imágenes:** La visualización de vidas pasa a ser gráfica, se ve una cantidad de bola en la esquina superior derecha de la pantalla correspondiente a la cantidad de vidas restante. Se implementó hasta un máximo de 30 vidas. Sin embargo para evitar este límite, también se puso un contador. Implementado en el método updateBalls(int antidad, int ancho) en la clase App. Está comentado pára su fácil localización.


## Diagrama UML

Para ver de mejor forma el flujo de mensajes descrito se adjunta en la raíz del repositorio un diagrama UML simple y uno completo con todos los métodos.

## Authors

* **Juan Pablo Silva** - *Algunas interfaces base*
* **Maximiliano Vargas** - *Lógica del juego, gráfica del juego e implementación*
