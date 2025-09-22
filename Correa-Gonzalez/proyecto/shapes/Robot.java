import java.util.Arrays;
import java.util.ArrayList;

public class Robot implements SilkRoad.VisualElement {
    private int location;
    private int actualLocation;
    private String color;
    private Rectangle body;
    private int tenges;
    public static ArrayList<String> availableColors = new ArrayList<>(
        Arrays.asList("red", "blue", "green", "yellow","magenta","white"));
    private ArrayList<Integer> movementProfits; //Tener la lista de los movimientos del robot
    private int totalProfit;
    
    /**
     * Constructor for objects of class Robot
     */
    public Robot(int location) {
        this.location = location;
        this.actualLocation = location;
        
        // Asignar un color único
        if (availableColors.isEmpty()) {
            throw new RuntimeException("No hay más colores disponibles para los Robots.");
        }
        this.color = availableColors.remove(0);

        // Cuerpo
        body = new Rectangle();
        body.changeSize(10, 10);  
        body.changeColor(this.color);
        body.moveHorizontal(70);    
        body.moveVertical(110);
        body.makeInvisible(); // Cambiar a invisible por defecto
        this.movementProfits = new ArrayList<>();//para guardar este profit en la lista
        this.totalProfit = 0;//inicializa en cero 
    }
    
    public void addMovementProfit(int profit) {
        movementProfits.add(profit);
        totalProfit += profit;
    }
    
    public int[][] getMovementProfits() {
        int[][] result = new int[movementProfits.size()][2];
        for (int i = 0; i < movementProfits.size(); i++) {
            result[i][0] = i; // Número de movimiento
            result[i][1] = movementProfits.get(i); // Ganancia en ese movimiento
        }
        return result;
    }
    
    public int getTotalProfit() {
        return totalProfit;
    }
    
    public int getTenges(){
        return tenges;
    }
    
    public void setTenges(int tenges){
        this.tenges = tenges;
    }
    
    public int getInitLocation(){
        return location;
    }
    
    public int getLocation(){
        return actualLocation;
    }
    
    public void returnLocation(){
        actualLocation = location;   
    }   
    
    public void makeVisible(){
        body.makeVisible();
    }
    
    public void setPosition(int x, int y) {
        this.body.xPosition = x;
        this.body.yPosition = y;
    }
    
    public void makeInvisible(){
        body.makeInvisible();
    }
    
    public void move(int x, int y) {
        body.moveHorizontal(x);
        body.moveVertical(y);
    }
    
    public void setLocation(int location) {
        this.actualLocation = location;
    }
    
    public int getInitialLocation() {
        return location;
    }
    
    public void returnToInitialLocation() {
        this.actualLocation = location;
    }
    
    public void releaseColor() {
        availableColors.add(this.color);
    }
} 