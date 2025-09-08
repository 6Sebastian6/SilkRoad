
/**
 * Write a description of class SilkRoad here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.awt.Point;
import java.util.ArrayList;

public class SilkRoad {
    private int length;
    private ArrayList<Rectangle> path;
    private ArrayList<Point> spiralPoints;//coordenadas de cada punto
    private ArrayList<Store> stores;
    private ArrayList<Robot> robots;
    private int totalProfit;
    private Rectangle profitBar; 
    private boolean visible;
    private boolean lastOperationOk; //para saber si es correcta la operacion
    
    /**
     * Constructor
     */
    public SilkRoad(int length) {
        this.length = length;
        path = new ArrayList<>();
        this.spiralPoints = new ArrayList<>();//guara las coordenadas
        stores = new ArrayList<>();
        robots = new ArrayList<>();
        visible = false;
        this.lastOperationOk = true;
        
        //genera el camino en espiral para la construccion del tablero
        int direction = 0; // 0=derecha, 1=abajo, 2=izquierda, 3=arriba
        int steps = 1;     // pasos antes de girar
        int stepCount = 0; // pasos dados en esta dirección
        int turnCount = 0; // número de giros
        int x = 30;
        int y = 40;

        for (int i = 0; i < length; i++) {
            spiralPoints.add(new Point(x,y)); // guarda las coordenadas
            
            Rectangle cell = new Rectangle();
            cell.changeSize(20, 20);
            cell.makeInvisible();
            cell.moveHorizontal(35);
            cell.moveVertical(60);
            cell.moveHorizontal(x);
            cell.moveVertical(y);
            cell.changeColor("black");
            path.add(cell);

            // Mover según dirección
            switch(direction) {
                case 0 -> x += 40; // derecha
                case 1 -> y += 40; // abajo
                case 2 -> x -= 40; // izquierda
                case 3 -> y -= 40; // arriba
            }

            stepCount++;

            // Cada tramo termina después de 'steps' pasos
            if(stepCount == steps) {
                stepCount = 0;
                direction = (direction + 1) % 4; // girar
                turnCount++;
                // Aumenta pasos cada dos giros
                if(turnCount % 2 == 0) steps++;
            }
        }
        
        //Iniciar la barra 
        profitBar = new Rectangle();
        profitBar.changeColor("green");
        profitBar.changeSize(20,200);
        profitBar.moveHorizontal(-30);
        profitBar.moveVertical(200);
        profitBar.makeInvisible();
        
    }
    
    public void placeStore(int location, int tenges){
        
        //verificaa si se sale de la posicion 
        if(location < 0 || location >= length){
            System.out.println("Estas marcando una ubicación fuera del limite");
            return; // esto sale del metodo si hacer nada
        }
        
        //verifica si la ubicacion  esta ocupada
        boolean occupied = false;
        for(Store store : stores){
            if (store.getLocation() == location){
                occupied = true;
                break;
            }
        }
        for (Robot robot : robots){
            if(robot.getLocation() == location){
                occupied = true;
                break;
            }
        }
        if (occupied){
            System.out.println("Esta ubicación esta ocupada");
            return;
        }
        
        Store newStore = new Store(location, tenges); 
        
        int direction = 0; // 0=derecha, 1=abajo, 2=izquierda, 3=arriba
        int steps = 1;     // pasos antes de girar
        int stepCount = 0; // pasos dados en esta dirección
        int turnCount = 0; // número de giros
        for (int i = 0; i < location; i++) {
            int x = 0;
            int y = 0;
            // Mover según dirección
            switch(direction) {
                case 0 -> x += 40; // derecha
                case 1 -> y += 40; // abajo
                case 2 -> x -= 40; // izquierda
                case 3 -> y -= 40; // arriba
            }

            stepCount++;

            // Cada tramo termina después de 'steps' pasos
            if(stepCount == steps) {
                stepCount = 0;
                direction = (direction + 1) % 4; // girar
                turnCount++;
                // Aumenta pasos cada dos giros
                if(turnCount % 2 == 0) steps++;
            }
            newStore.move(x,y);
        }
        stores.add(newStore);
        if (visible){
            newStore.makeVisible();
        }
        lastOperationOk = true;
        
    }
    
    public void removeStore(int location){
        Store storeToRemove = null;
        
        for (Store store : stores) {
            if (store.getLocation() == location) {
                storeToRemove = store;
                break;
            }
        }
        
        if(storeToRemove == null){
            System.out.println("En esta ubicacion no hay una tienda");
            return;
        }
        
        storeToRemove.makeInvisible();
        stores.remove(storeToRemove);
        lastOperationOk = true;
    }
    
    public void placeRobot(int location){
        
        //verificaa si se sale de la posicion 
        if(location < 0 || location >= length){
            System.out.println("Estas marcando una ubicación fuera del limite");
            return; // esto sale del metodo si hacer nada
        }
        
        //verifica si la ubicacion  esta ocupada
        boolean occupied = false;
        for(Store store : stores){
            if (store.getLocation() == location){
                occupied = true;
                break;
            }
        }
        for (Robot robot : robots){
            if(robot.getLocation() == location){
                occupied = true;
                break;
            }
        }
        if (occupied){
            System.out.println("Esta ubicación esta ocupada");
            return;
        }
        
        Robot newRobot = new Robot(location);
        int direction = 0; // 0=derecha, 1=abajo, 2=izquierda, 3=arriba
        int steps = 1;     // pasos antes de girar
        int stepCount = 0; // pasos dados en esta dirección
        int turnCount = 0; // número de giros
        for (int i = 0; i < location; i++) {
            int x = 0;
            int y = 0;
            // Mover según dirección
            switch(direction) {
                case 0 -> x += 40; // derecha
                case 1 -> y += 40; // abajo
                case 2 -> x -= 40; // izquierda
                case 3 -> y -= 40; // arriba
            }

            stepCount++;

            // Cada tramo termina después de 'steps' pasos
            if(stepCount == steps) {
                stepCount = 0;
                direction = (direction + 1) % 4; // girar
                turnCount++;
                // Aumenta pasos cada dos giros
                if(turnCount % 2 == 0) steps++;
            }
            newRobot.move(x,y);
        }
        robots.add(newRobot);
        if (visible){
            newRobot.makeVisible();
        }
        lastOperationOk = true;
    }
    
    public void removeRobot(int location){
        Robot robotToRemove = null;
        
        for (Robot robot : robots) {
            if (robot.getLocation() == location) {
                robotToRemove = robot;
                break;
            }
        }
        
        if(robotToRemove == null){
            System.out.println("En esta ubicacion no hay un robot");
            return;
        }
        
        robotToRemove.makeInvisible();
        robots.remove(robotToRemove);
        lastOperationOk = true;
    }
    
    public void moveRobot(int location, int meters){
        Robot robotToMove = null;
        

    // Buscar el robot en la lista
    for (Robot robot : robots) {
        if (robot.getLocation() == location) {
            robotToMove = robot;
            break;
        }
    }

    if (robotToMove == null) {
        System.out.println("No hay robot en la ubicación indicada");
        lastOperationOk = false;
        return;
    }

    int newLocation = location + meters;

    // Verificar límites
    if (newLocation < 0 || newLocation >= length) {
        System.out.println("Movimiento fuera del tablero");
        lastOperationOk = false;
        return;
    }

    for (Robot robot : robots) {
        if (robot.getLocation() == newLocation) {
            System.out.println("No se puede mover: la celda está ocupada por otro robot");
            lastOperationOk = false;
            return;
        }
    }
    
    int direction = location % 4 ; // 0=derecha, 1=abajo, 2=izquierda, 3=arriba
        int steps = 1;     // pasos antes de girar
        int stepCount = 0; // pasos dados en esta dirección
        int turnCount = 0; // número de giros
        for (int i = location; i < newLocation; i++) {
            int x = 0;
            int y = 0;
            // Mover según dirección
            switch(direction) {
                case 0 -> x += 40; // derecha
                case 1 -> y += 40; // abajo
                case 2 -> x -= 40; // izquierda
                case 3 -> y -= 40; // arriba
            }

            stepCount++;

            // Cada tramo termina después de 'steps' pasos
            if(stepCount == steps) {
                stepCount = 0;
                direction = (direction + 1) % 4; // girar
                turnCount++;
                // Aumenta pasos cada dos giros
                if(turnCount % 2 == 0) steps++;
            }
            robotToMove.move(x,y);
        }
        
        robotToMove.actualLocation = newLocation;
        robots.add(robotToMove);
        
        if (visible){
            robotToMove.makeVisible();
        }
 
    lastOperationOk = true;
        
    }
    
    public void resupplyStores(){
    
    }
    
    public void returnRobots(){
    
    }
    
    public void reboot(){
    
    }
    
    public int profit(){
        return 0;
    }
    
    public int[][] stores(){
        return new int[0][0];
    }
    
    public int[][] robots(){
        return new int[0][0];
    }
    
    public void makeVisible() {
        Canvas canvas = Canvas.getCanvas();
        canvas.setVisible(true);
        profitBar.makeVisible();
        
        for (Rectangle rect : path) {
            rect.makeVisible();
        }
    }
    
    public void makeinVisible() {
        Canvas canvas = Canvas.getCanvas();
        canvas.setVisible(visible);
        profitBar.makeInvisible();
        
        for (Rectangle rect : path) {
            rect.makeInvisible();
        }
    }
    
    public void finish(){
    
    }
    
    public boolean ok(){
        return true;
    }
    
    
}
    
    