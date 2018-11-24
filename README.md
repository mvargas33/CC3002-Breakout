# Tarea 2: Breakout

Implementación de la lógica del juego breakout implementando patrones de diseño vistos en clases.

## Distribución del código

El en el directorio '/src/main/java' se encuentran todos los archivos que implementan la lógica del juego. Lo que posteriormente se usará para administrar el juego será el paquete 'facade' que corresponde al patrón de diseño 'Facade Pattern' que básicamente oculta bajo sí lo complejo del juego y deja a la vista métodos simples. El resto de los paquetes se explicará a continuación.

### Descripción del funcionamiento y patrones de diseño

El paquete brick contiene la aimplementación de la lógica de los bricks de juego. Cada brick almacena parámetros como score y vidas. Al ser destruido un Brick debe informar al nivel en el que está inmerso que se destruyó. Aquí se aplica el 'Observer Pattern' visto en clases. El brick destruido envía un mensaje al nivel, luego el nivel debe añadir puntaje a su nivel, para esto debe saber qué tipo de brick se destruyó. Aquí se implementa el 'Visitor pattern' visto en clases, el nivel visita al brick destruido y mediante el double-dispatch sabe su tipo y aumenta los puntos.

Luego la clase nivel, además de anotar puntos debe avisar a Game cuando un juego está ganado o si hay que añadir una bola extra. Aquí se vuelve a utilizar el 'Observer pattern'. Finalmente Game, al recibir un mensaje de un nivel, verifica las condiciones de ganar el nivel (o el juego) y de añadir una bola extra o no.

Además, se implementa el 'Null Pattern' en el paquete level, ya que el Game debe iniciar con un nivel vacío. Además sirve para añadir niveles a la cola y determinar el final de un juego.

### Diagrama UML

Para ver de mejor forma el flujo de mensajes brevemente descrito se adjunta en la raíz del repositorio un diagrama UML.

## Authors

* **Juan Pablo Silva** - *Interfaces base*
* **Maximiliano Vargas** - *Lógica del juego e implementación*
