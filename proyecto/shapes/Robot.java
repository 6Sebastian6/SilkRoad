import java.util.HashSet;

/**
 * Write a description of class Robot here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */


public class Robot
{
    // instance variables - replace the example below with your own
    private int location;
    public int actualLocation;
    private int cantTenges;
    private String color;
    private Rectangle body;
    private static HashSet<String> usedColors = new HashSet<>();

    

    /**
     * Constructor for objects of class Robot
     */
    public Robot(int location)
    {
    // Posición del personaje
    this.location = location;
    this.actualLocation = location;
    
    // Cuerpo
    body = new Rectangle();
    body.changeSize(10, 10);  
    body.moveHorizontal(70);    
    body.moveVertical(110);
    
    if (Store.availableColors.isEmpty()) {
            throw new RuntimeException("No hay más colores disponibles para los Stores.");
        }
    // Tomar el primer color disponible
    this.color = Store.availableColors.remove(0);
    usedColors.add(this.color);

    // Hacer visibles
    
    body.makeVisible();

}

    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public int getInitLocation(){
        return location;
    }
    
    public int getLocation(){
        return actualLocation;
    }
    
    public void returnLocation(){
        actualLocation = location ;   
    }   
    
    public void makeVisible(){
        body.makeVisible();
    }
    
    public void move(int x, int y) {
    body.moveHorizontal(x);
    body.moveVertical(y);
    }

    public void makeInvisible(){
    body.makeInvisible();
    }
}   