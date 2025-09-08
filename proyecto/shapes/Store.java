
import java.util.Arrays;
import java.util.HashSet;
import java.util.ArrayList;
/**
 * Write a description of class Store here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

public class Store {
    private int location;
    private int tenges;
    private String color;
    private Rectangle wall;
    private Triangle roof;
    public static ArrayList<String> availableColors = new ArrayList<>(
        Arrays.asList("red", "blue", "green", "yellow", "purple", "orange", "pink", "cyan")
    );
    private static HashSet<String> usedColors = new HashSet<>();

    /**
     * Constructor for objects of class Store
     */
    public Store(int location, int tenges) {
        this.location = location;
        this.tenges = tenges;

        // Asignar un color único
        if (availableColors.isEmpty()) {
            throw new RuntimeException("No hay más colores disponibles para los Stores.");
        }
        // Tomar el primer color disponible
        this.color = availableColors.remove(0);
        usedColors.add(this.color);

        // Crear la pared
        wall = new Rectangle();
        wall.changeColor(this.color);
        wall.moveVertical(90);
        wall.moveHorizontal(70);
        wall.changeSize(10, 10);
        wall.makeVisible();

        // Crear el techo
        roof = new Triangle();
        roof.changeColor(this.color);
        roof.moveVertical(80);
        roof.changeSize(10, 10);
        roof.moveHorizontal(5);
        roof.makeVisible();
    }
    
    public int getLocation(){
        return location;
    }
    
    public int getTenges(){
        return tenges;
    }
    
    public void setTenges(int tenges){
        this.tenges = tenges;
    }
    
    public int collect(){
        int amount = tenges;
        tenges = 0;
        return amount;
    }
    
    public void makeVisible(){
        wall.makeVisible();
        roof.makeVisible();
    }
    
    public void makeInvisible(){
        wall.makeInvisible();
        roof.makeInvisible();
    }
    
    public void move(int x, int y){
        wall.moveHorizontal(x);
        wall.moveVertical(y);
        roof.moveHorizontal(x);
        roof.moveVertical(y);
    }
}
