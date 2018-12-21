package logic.level;

import logic.brick.*;

import java.util.*;

public class RealLevel extends Observable implements Level {
    private int obtainablePoins;            // Puntos máximos obtenidobles (destuir wooden, glass)
    private int currentPonts;               // Puntos actuales ganados
    private String name;                    // Nombre del nivel
    private ArrayList<Brick> levelBricks;   // Lista de Bricks del nivel actual
    private Level nextLevel;                // Nivel siguiente
    private boolean lastBrickWasMetal;      // Valor booleano: Dice si el último Brick destuido fue de metal (necesario para el notify level->game)

    public RealLevel(){
        this.obtainablePoins = 0;
        this.currentPonts = 0;
        this.name = "";
        this. levelBricks = new ArrayList<>();
        this.lastBrickWasMetal = false;
    }
    /**
     * Constructor de un RealLevel. Crea un nivl con GlassBrick, MetalBrick y WoodenBrick basado en probabilidades.
     * 'numberOfBricks' especifica los bricks entre wood y glass. Los bricks de metal se agregan como extra.
     * Parte como base en el constructor padre. Luego cambia parámetros.
     *
     * @param name Nombre del nivel a crear
     * @param numberOfBricks número de Bricks totales entre glass y wooden
     * @param probOfGlass probabilidad de general bricks de glass
     * @param probOfMetal probabilidad de general bricks de metal
     * @param seed parámetro para utilizar correctamente la clase Random
     */
    public RealLevel(String name, int numberOfBricks, double probOfGlass, double probOfMetal, int seed) {
        this();
        ArrayList<Brick> bricks = new ArrayList<>();
        Random randomObject = new Random(seed);
        for(int i = 0;i < numberOfBricks;i++){
            double randomNumber = randomObject.nextDouble();
            if(randomNumber < probOfGlass){
                GlassBrick glassBrick = new GlassBrick();
                bricks.add(glassBrick);
                this.sumToObtainablePoints(glassBrick.getScore());
                glassBrick.addObserver(this);
            }else{
                WoodenBrick woodenBrick = new WoodenBrick();
                bricks.add(woodenBrick);
                this.sumToObtainablePoints(woodenBrick.getScore());
                woodenBrick.addObserver(this);
            }
        }
        for(int j = 0; j < numberOfBricks;j++){
            double randomNumber = randomObject.nextDouble();
            if(randomNumber < probOfMetal){
                MetalBrick metalBrick = new MetalBrick();
                bricks.add(metalBrick);
                this.sumToObtainablePoints(metalBrick.getScore()); // Inútil ahora, pero útil para escalabilidad
                metalBrick.addObserver(this);
            }
        }
        this.setName(name);
        this.setLevelBricks(bricks);
        this.setNextLevel(new NullLevel());
    }

    /**
     * Constructor de un RealLevel. Crea un nivl con GlassBrick y WoodenBrick basado en probabilidades.
     * 'numberOfBricks' especifica los bricks entre wood y glass.
     * Parte como base en el constructor padre. Luego cambia parámetros.
     *
     * @param name Nombre del nivel a crear
     * @param numberOfBricks número de Bricks totales entre glass y wooden
     * @param probOfGlass probabilidad de general bricks de glass
     * @param seed parámetro para utilizar correctamente la clase Random
     */
    public RealLevel(String name, int numberOfBricks, double probOfGlass, int seed){
        this();
        ArrayList<Brick> bricks = new ArrayList<>();
        Random randomObject = new Random(seed);
        for(int i = 0;i < numberOfBricks;i++){
            double randomNumber = randomObject.nextDouble();
            if(randomNumber < probOfGlass){
                GlassBrick glassBrick = new GlassBrick();
                bricks.add(glassBrick);
                this.sumToObtainablePoints(glassBrick.getScore());
                glassBrick.addObserver(this);
            }else{
                WoodenBrick woodenBrick = new WoodenBrick();
                bricks.add(woodenBrick);
                this.sumToObtainablePoints(woodenBrick.getScore());
                woodenBrick.addObserver(this);
            }
        }
        this.setName(name);
        this.setLevelBricks(bricks);
        this.setNextLevel(new NullLevel());
    }

    /**
     * Un RealLevel es siempre jugable
     *
     * @return true
     */
    @Override
    public boolean isPlayableLevel() {
        return true;
    }

    /**
     * Retorna true si el nivel siguiente nivel es jugable
     *
     * @return true si el nivel siguiente es jugable
     */
    @Override
    public boolean hasNextLevel() {
        return this.getNextLevel().isPlayableLevel();
    }

    /**
     * Suma a 'obtainablePoins' los puntos específicados.
     * Utilizado en el constructor de RealLevel.
     * @param points: puntos a sumar
     */
    public void sumToObtainablePoints(int points){
        this.obtainablePoins += points;
    }

    /**
     * Suma a 'currentPonts' los puntos específicados.
     * Utilizado al destruirse un bloque en las visitas a los bloques.
     * @param points: puntos a sumar
     */
    public void sumToCurrentPoints(int points){
        this.currentPonts += points;
    }

    /**
     * Impone la lista de Bricks sobre el nivel
     * @param listaBricks: lista de Bricks a imponer
     */
    public void setLevelBricks(ArrayList<Brick> listaBricks){
        this.levelBricks = listaBricks;
    }

    /**
     * Impone el nombre a utilizar por un Nivel
     * @param name: nombre a imponer
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Retorna true si el último Brick destruido en el nivel fue de metal.
     * Utilizado en la notificación de Level a Game para ver condición de bolas extra
     * @return variable 'lastBrickWasMetal' del nivel
     */
    public boolean isLastBrickMetal(){
        return this.lastBrickWasMetal;
    }

    /**
     * Declara con 'valor' la variable 'lastBrickWasMetal' que guarda si el último birck destruido fue de metal.
     * @param valor: valor a setear 'lastBrickWasMetal'
     */
    public void setLastBrickWasMetal(boolean valor){
        this.lastBrickWasMetal = valor;
    }

    /**
     * Retorna los puntos ganados en un nivel y sólo los de ese nivel.
     * Siempre menor o igual a los obtenibles. Los que calcula getPoints()
     *
     * @return los puntos ganados hasta el momento en un nivel.
     */
    @Override
    public int getCurrentPoints() {
        return this.currentPonts;
    }

    /**
     * Gets the level's name. Each level must have a name.
     *
     * @return the table's name
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Gets the number of {@link Brick} in the level.
     *
     * @return the number of Bricks in the level
     */
    @Override
    public int getNumberOfBricks() {
        int n = 0;
        for(Brick b : this.levelBricks){    // ONLY BRICKS ALIVE!
            if(!b.isDestroyed())
                n +=1;
        }
        return n;
    }

    /**
     * Gets the {@link List} of {@link Brick}s in the level.
     *
     * @return the bricks in the level
     */
    @Override
    public List<Brick> getBricks() {
        return levelBricks;
    }

    /**
     * Gets the next level of a level object. Each level have a reference to the next level to play.
     *
     * @return the next level
     */
    @Override
    public Level getNextLevel() {
        return this.nextLevel;
    }

    /**
     * Gets the total number of points obtainable in level.
     *
     * @return the number of points in the current level
     */
    @Override
    public int getPoints() {
        return this.obtainablePoins;
    }

    /**
     * Adds a level to the list of levels. This adds the level in the last position of the list.
     *
     * @param level the level to be added
     */
    @Override
    public Level addPlayingLevel(Level level) {
        this.nextLevel = this.nextLevel.addPlayingLevel(level);
        return this;
    }

    /**
     * Sets the reference for the next level of a level object.
     *
     * @param level the next level of a level object
     */
    @Override
    public void setNextLevel(Level level) {
        this.nextLevel = level;
    }

    @Override
    public void addRealObserver(Observer o) {
        this.addObserver(o);
    }

    /**
     * Al visitar un brick de glass suma los puntos al nivel.
     * Notifica a Game sólo si el juego está ganado
     * @param b: GlassBrick a visitar
     */
    @Override
    public void visitGlassBrick(GlassBrick b) {
        sumToCurrentPoints(b.getScore());
        if(this.currentPonts == this.obtainablePoins) { // Condición para pasar de nivel
            setChanged();
            notifyObservers();  // Notifica a Game que un brick se destruyó
        }
    }

    /**
     * Al visitar, Suma los puntos de metal a Level (0, pero se hace así para que sea escalable).
     * Cambia la variable 'lastBrickWasMetal' por true.
     * Notifica siempre a Game.
     * @param b: MetalBrick a visitar
     */
    @Override
    public void visitMetalBrick(MetalBrick b) {
        sumToCurrentPoints(b.getScore());
        setLastBrickWasMetal(true);
        setChanged();
        notifyObservers();  // Notifica a Game que un brick se destruyó
    }

    /**
     * Al visitar un brick de wooden suma los puntos al nivel.
     * Notifica a Game sólo si el juego está ganado
     * @param b: WoodenBrick a visitar
     */
    @Override
    public void visitWoodenBrick(WoodenBrick b) {
        sumToCurrentPoints(b.getScore());
        if(this.currentPonts == this.obtainablePoins) { // Condición para pasar de nivel
            setChanged();
            notifyObservers();  // Notifica a Game que un brick se destruyó
        }
    }

    /**
     * Método que se ejecuta cuando un Brick notifica a Level.
     * Brick sólo notifica cuando ha sido destruido.
     * Luego level Visita a Brick para saber el tipo.
     * @param observable Brick que notifica a Level
     * @param o Objeto que envía Brick (null para efectos del juego)
     */
    @Override
    public void update(Observable observable, Object o) {
        if( observable instanceof Brick){
            ((Brick) observable).accept(this);
        }
    }

}
