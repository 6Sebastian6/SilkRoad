import java.util.Arrays;
import java.util.ArrayList;

public class Store implements SilkRoad.VisualElement {
    private int location;
    private int tenges;
    private int initialTenges; // Añadir este campo
    private String color;
    private Rectangle wall;
    private Triangle roof;
    private int times;
    private boolean isVisible; // Nuevo 
    public static ArrayList<String> availableColors = new ArrayList<>(
        Arrays.asList("red", "blue", "green", "yellow","magenta","white")
    );

    /**
     * Constructor para objetos de la clase Store.
     * @param location la ubicación de la tienda en el camino
     * @param tenges la cantidad inicial de tenges en la tienda
     */
    public Store(int location, int tenges) {
        this.location = location;
        this.tenges = tenges;
        this.initialTenges = tenges; // Inicializar
        this.times = 0;
        this.isVisible = false; // Inicialmente invisible

        // Asignar un color único
        if (availableColors.isEmpty()) {
            throw new RuntimeException("No hay más colores disponibles para los Stores.");
        }
        this.color = availableColors.remove(0);

        // Crear la pared
        wall = new Rectangle();
        wall.changeColor(this.color);
        wall.moveVertical(90);
        wall.moveHorizontal(70);
        wall.changeSize(10, 10);
        wall.makeInvisible(); // Cambiar a invisible por defecto

        // Crear el techo
        roof = new Triangle();
        roof.changeColor(this.color);
        roof.moveVertical(80);
        roof.changeSize(10, 10);
        roof.moveHorizontal(5);
        roof.makeInvisible(); // Cambiar a invisible por defecto
    }
    
    /**
     * Establece la posición de la tienda.
     * @param x la coordenada x para posicionar la tienda
     * @param y la coordenada y para posicionar la tienda
     */
    public void setPosition(int x, int y) {
        this.wall.xPosition = x;
        this.wall.yPosition = y-20;
        this.roof.xPosition = x+5;
        this.roof.yPosition = y-30;
    }
    
    /**
     * Incrementa el contador de veces que la tienda ha sido visitada.
     */
    public void stepOneTime(){
        this.times = times+1;
    }
    
    /**
     * Obtiene la ubicación de la tienda.
     * @return la ubicación de la tienda
     */
    public int getLocation(){
        return location;
    }
    
    /**
     * Obtiene la cantidad actual de tenges en la tienda.
     * @return la cantidad de tenges actual
     */
    public int getTenges(){
        return tenges;
    }
    
    /**
     * Obtiene el número de veces que la tienda ha sido visitada.
     * @return el contador de visitas
     */
    public int getTimes(){
        return times;
    }
    
    /**
     * Obtiene la cantidad inicial de tenges de la tienda.
     * @return la cantidad inicial de tenges
     */
    public int getInitialTenges() {
        return initialTenges;
    }
    
    /**
     * Establece la cantidad de tenges en la tienda.
     * @param tenges la cantidad de tenges
     */
    public void setTenges(int tenges){
        this.tenges = tenges;
    }
    
    /**
     * Recolecta todos los tenges disponibles en la tienda.
     * @return la cantidad de tenges recolectados
     */
    public int collect(){
        int amount = tenges;
        tenges = 0;
        if (isVisible){//Cambiar color cuano se desocupa
            updateAppearance();
        }
        return amount;
    }
    
    /**
     * Hace visible la tienda.
     */
    public void makeVisible(){
        wall.makeVisible();
        roof.makeVisible();
        this.isVisible = true;
    }
    
    /**
     * Hace invisible la tienda.
     */
    public void makeInvisible(){
        wall.makeInvisible();
        roof.makeInvisible();
        this.isVisible = false;
    }
    
    /**
     * Cambia el color del tracho en negro si no tiene dinero disponible.
     */
    private void updateAppearance() {
        if (tenges ==0){
            roof.changeColor("black");
        } else  {
            wall.changeColor(this.color);
            roof.changeColor(this.color);
        }
    }
    
    /**
     * Mueve la tienda visualmente.
     * @param x la distancia horizontal a mover
     * @param y la distancia vertical a mover
     */
    public void move(int x, int y){
        wall.moveHorizontal(x);
        wall.moveVertical(y);
        roof.moveHorizontal(x);
        roof.moveVertical(y);
    }
    
    /**
     * Reabastece la tienda con 100 tenges adicionales.
     */
    public void resupply() {
        tenges += 100;
    }
    
    /**
     * Reabastece la tienda a su cantidad inicial de tenges.
     */
    public void resupplyToInitial() {
        tenges = initialTenges;
    }
    
    /**
     * Libera el color de la tienda para que pueda ser reutilizado.
     */
    public void releaseColor() {
        availableColors.add(this.color);
    }
}
