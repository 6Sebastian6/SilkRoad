import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class SilkRoad {
    private int length;
    private ArrayList<Cell> path; // Cambiar a lista de celdas del camino
    private ArrayList<Store> stores;
    private ArrayList<Robot> robots;
    private int totalProfit;
    private Rectangle profitBar;
    private boolean visible;
    private boolean lastOperationOk;
    
    // Clase interna para representar celdas del camino
    private class Cell {
        public int x;
        public int y;
        public Rectangle visual;
        public boolean occupied_robot;
        public boolean occupied_store;
        /**
         * Constructor de celda.
         * @param x coordenada x de la celda
         * @param y coordenada y de la celda
         */
        public Cell(int x, int y) {
            this.x = x;
            this.y = y;
            this.visual = new Rectangle();
            this.visual.changeSize(20, 20);
            this.visual.changeColor("black");
            this.visual.moveHorizontal(65 + x * 40);
            this.visual.moveVertical(100 + y * 40);
            this.visual.makeInvisible();
            this.occupied_store = false;
            this.occupied_robot = false;
        }
    }
    
    /**
     * Constructor de SilkRoad.
     * @param length la longitud del camino en espiral
     */
    public SilkRoad(int length) {
        this.length = length;
        this.path = new ArrayList<>(); // Inicializar lista del camino
        this.stores = new ArrayList<>();
        this.robots = new ArrayList<>();
        this.visible = false;
        this.lastOperationOk = true;
        this.totalProfit = 0;
        
        // Generar camino en espiral (solo las celdas necesarias)
        generateSpiralPath();
        
        // Iniciar la barra de ganancias
        profitBar = new Rectangle();
        profitBar.changeColor("green");
        profitBar.changeSize(20, 200);
        profitBar.moveHorizontal(-30);
        profitBar.moveVertical(200);
        profitBar.makeInvisible();
    }
    
    
    /**
     * Encuentra el robot con más tenges.
     * @return el robot más rico o null si no hay robots
     */
    public Robot richestRobot() {
        if (robots.isEmpty()) {
            return null;
        }
        Robot richest = robots.get(0);
        for (Robot r : robots) {
            if (r.getTenges() > richest.getTenges()) {
                richest = r;
            }
        }
        return richest;
    }

     /**
     * Hace parpadear visualmente el robot con mas tenges.
     * @param robot el robot que debe parpadear
     */
    private void blinkRobot(Robot robot) {
    if (robot == null) return; // No parpadear si no es visible
    try {
        for (int i = 0; i < 4; i++) {
            robot.makeInvisible();
            Thread.sleep(200);
            robot.makeVisible();
            Thread.sleep(200);
        }
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}
    
    /**
     * Genera el camino en espiral.
     */
    private void generateSpiralPath() {
        // Implementación mejorada para mostrar espiral cuadrada visible
        int x = 0, y = 0;
        int dx = 1, dy = 0;
        int segmentLength = 1;
        int segmentPassed = 0;
        
        for (int i = 0; i < length; i++) {
            path.add(new Cell(x, y));
            
            x += dx;
            y += dy;
            segmentPassed++;
            
            if (segmentPassed == segmentLength) {
                segmentPassed = 0;
                // Girar 90 grados a la derecha
                int temp = dx;
                dx = -dy;
                dy = temp;
                
                if (dy == 0) { // Cuando estamos en horizontal
                    segmentLength++;
                }
            }
        }
    }
    
    /**
     * Obtiene las coordenadas de una ubicación en el camino.
     * @param location la ubicación en el camino
     * @return array con coordenadas [x, y] o null si la ubicación es inválida
     */
    private int[] getCoordinates(int location) {
        if (location < 0 || location >= length) {
            return null;
        }
        
        Cell cell = path.get(location);
        return new int[]{cell.x, cell.y};
    }
    
    /**
     * Mueve visualmente un elemento a una ubicación específica.
     * @param element el elemento visual a mover
     * @param location la ubicación destino
     */
    private void moveElementToLocation(VisualElement element, int location) {
        int[] coords = getCoordinates(location);
        if (coords != null) {
            element.setPosition(140, 125);
            element.move(coords[0] * 40 , coords[1] * 40);
        }
           
    }
    
    /**
     * Coloca una tienda en una ubicación específica.
     * @param location la ubicación donde colocar la tienda
     * @param tenges la cantidad de tenges iniciales de la tienda
     */    
    public void placeStore(int location, int tenges) {
        // Verificar límites
        if (location < 0 || location >= length) {
            System.out.println("Ubicación fuera del límite");
            lastOperationOk = false;
            return;
        }
        
        // Verificar si la ubicación está ocupada
        if (path.get(location).occupied_store) {
            System.out.println("Ubicación ocupada");
            lastOperationOk = false;
            return;
        }
        
        // Crear nueva tienda
        Store newStore = new Store(location, tenges);
        moveElementToLocation(newStore, location);
        path.get(location).occupied_store = true;
        stores.add(newStore);
        if (visible) {
            newStore.makeVisible();
        }
        
        lastOperationOk = true;
    }
    
    /**
     * Elimina una tienda de una ubicación específica.
     * @param location la ubicación de la tienda a eliminar
     */
    public void removeStore(int location) {
        Store storeToRemove = null;
        
        for (Store store : stores) {
            if (store.getLocation() == location) {
                storeToRemove = store;
                break;
            }
        }
        
        if (storeToRemove == null) {
            System.out.println("No hay tienda en esta ubicación");
            lastOperationOk = false;
            return;
        }
        
        storeToRemove.makeInvisible();
        storeToRemove.releaseColor();
        stores.remove(storeToRemove);
        path.get(location).occupied_store = false;
        lastOperationOk = true;
    }
    
    /**
     * Coloca un robot en una ubicación específica.
     * @param location la ubicación donde colocar el robot
     */
    public void placeRobot(int location) {
        // Verificar límites
        Cell cell = path.get(location);
        
        if (location < 0 || location >= length) {
            System.out.println("Ubicación fuera del límite");
            lastOperationOk = false;
            return;
        }
        
        // Verificar si la ubicación está ocupada
        if (path.get(location).occupied_robot) {
            System.out.println("Ubicación ocupada");
            lastOperationOk = false;
            return;
        }
        
        // Crear nuevo robot
        Robot newRobot = new Robot(location);
        moveElementToLocation(newRobot, location);
        path.get(location).occupied_robot = true;
        robots.add(newRobot);
        if (visible) {
            newRobot.makeVisible();
        }
        
        lastOperationOk = true;
    }
    
    /**
     * Elimina un robot de una ubicación específica.
     * @param location la ubicación del robot a eliminar
     */
    public void removeRobot(int location) {
        Robot robotToRemove = null;
        
        for (Robot robot : robots) {
            if (robot.getLocation() == location) {
                robotToRemove = robot;
                break;
            }
        }
        
        if (robotToRemove == null) {
            System.out.println("No hay robot en esta ubicación");
            lastOperationOk = false;
            return;
        }
        
        robotToRemove.makeInvisible();
        robotToRemove.releaseColor();
        robots.remove(robotToRemove);
        path.get(location).occupied_robot = false;
        lastOperationOk = true;
    }
    
    /**
     * Busca un robot en una ubicación específica.
     * @param location la ubicación donde buscar el robot
     * @return el robot encontrado o null si no existe
     */
    private Robot foundRobot(int location){
        Robot robotToMove = null;
        for (Robot robot : robots) {
            if (robot.getLocation() == location) {
                robotToMove = robot;
                return robotToMove;
            }
        }
        return null;
    }
    
    /**
     * Verifica si un movimiento es válido.
     * @param newLocation la nueva ubicación a verificar
     * @return true si el movimiento es válido, false en caso contrario
     */
    private boolean isMoveValid(int newLocation) {
        if (newLocation < 0 || newLocation >= length) {
            System.out.println("Movimiento fuera del tablero");
            lastOperationOk = false;
            return false;
        }
        lastOperationOk = true;
        return true;
    }
    
   /**
     * Verifica si una posición está ocupada por otro robot.
     * @param newLocation la ubicación a verificar
     * @return true si la posición está libre, false si está ocupada
     */
   private boolean checkRobotPosition(int newLocation){
       if (path.get(newLocation).occupied_robot ) {
            System.out.println("Ubicación ocupada por otro robot");
              lastOperationOk = false;
              return false;
        }
        return true;
   }
   
    
   /**
     * Verifica si un robot llegó a una tienda y recolecta ganancias.
     * @param newLocation la ubicación a verificar
     */
     private void checkRobotRecollect(int newLocation) {
        Store currentStore = null;
        for (Store s : stores) {
            if (s.getLocation() == newLocation) {
                currentStore = s;
                break;
            }
        }
    
        Robot currentRobot = foundRobot(newLocation);
    
        if (currentStore != null && currentRobot != null) {
            int collected = currentStore.collect(); // obtiene la cantidad correcta
            currentRobot.addMovementProfit(collected); // Registrar ganancia
            currentRobot.setTenges(currentRobot.getTenges() + collected);
            totalProfit += collected;
            updateProfitBar(); 
            
            Robot richest = richestRobot();
            //blinkRobot(richest); // si quieres mantenerlo simple
        }
}

    /**
     * Verifica y actualiza el estado de una tienda cuando un robot está en su ubicación.
     * @param location la ubicación de la tienda
     * @param robot el robot en la ubicación
     */
    private void checkTimesStore(int location, Robot robot){
    if (robot.getLocation() == location){
        Store actualstore = null;
        for (Store s : stores) {
            if (s.getLocation() == location) {
                actualstore = s;
                break;
            }
        }
        if (actualstore != null) {
            actualstore.stepOneTime();
        }
    }
}
    
    /**
     * Mueve un robot a una nueva ubicación.
     *
     * @param robotToMove El robot que se va a mover.
     * @param meters Número de pasos a mover; si es 0, el robot decide automáticamente.
     */
    public void moveRobot(int location, int meters) {
        
        Robot robotToMove = foundRobot(location);
         
        if (robotToMove == null) {
            System.out.println("No hay robot en la ubicación indicada");
            lastOperationOk = false;
            return;
        }
        
        int newLocation = location + meters;
        
        if (isMoveValid(newLocation) == false){
            lastOperationOk = false;
            return;
        }
        
        if (meters == 0){
            robotBestProfit(location, robotToMove);
            return;
        }
        
        if (checkRobotPosition(newLocation) == false){
            lastOperationOk = false;
            return;
        }
    
        
        // Mover el robot visualmente
        moveElementToLocation(robotToMove, newLocation);
        robotToMove.setLocation(newLocation);
        path.get(newLocation).occupied_robot = true;
        path.get(location).occupied_robot = false;
        checkTimesStore(newLocation,robotToMove);
        checkRobotRecollect(newLocation);
        
        lastOperationOk = true;
    }

    /**
     * Mueve un robot automáticamente hacia la tienda con más ganancias.
     * @param location la ubicación actual del robot
     * @param robotToMove el robot a mover
     */
    private void robotBestProfit(int location, Robot robotToMove){
        int posmaxProfit = 0;
        int actualProfit = 0;
        
        if (stores.isEmpty()) {
            System.out.println("No hay tiendas disponibles para recoger ganancias");
            lastOperationOk = false;
            return;
        }
            
        for (Store store : stores) {
            if (store.getTenges() > actualProfit){
                actualProfit = store.getTenges();
                posmaxProfit = store.getLocation();
            }             
        }
        int newLocation = posmaxProfit;
        
        if ((isMoveValid(newLocation) == false)|| (checkRobotPosition(newLocation) == false)){
            lastOperationOk = false;
            return;
        }
        
        moveElementToLocation(robotToMove, newLocation);
        
        robotToMove.setLocation(newLocation);
        path.get(newLocation).occupied_robot = true;
        path.get(location).occupied_robot = false;
        checkTimesStore(newLocation,robotToMove);
        
        checkRobotRecollect(newLocation);  
    }
    
    /**
     * Reabastece todas las tiendas.
     */
    public void resupplyStores() {
        for (Store store : stores) {
            store.resupply();
        }
        lastOperationOk = true;
    }
    
    /**
     * Devuelve todos los robots a sus ubicaciones iniciales.
     */
    public void returnRobots() {
        for (Robot robot : robots) {
            int initialLocation = robot.getInitialLocation();
            moveElementToLocation(robot, initialLocation);
            robot.returnToInitialLocation();
        }
        lastOperationOk = true;
    }
    
    /**
     * Reinicia el sistema a su estado inicial.
     */
    public void reboot() {
        // Reiniciar tiendas
        for (Store store : stores) {
            store.resupplyToInitial();
        }
        
        // Devolver robots a sus posiciones iniciales
        returnRobots();
        
        // Reiniciar ganancias
        totalProfit = 0;
        updateProfitBar();
        
        lastOperationOk = true;
    }
    
    public ArrayList getstores(){
        return stores;
    }
    
    
    public void cleanColors() {
        for (Store store : stores) {
            store.releaseColor();
        }
    
    }
    
    /**
     * Obtiene las ganancias totales.
     * @return las ganancias totales acumuladas
     */
    public int profit() {
        return totalProfit;
    }
    
    /**
     * Obtiene información de todas las tiendas.
     * @return matriz con ubicación y tenges de cada tienda
     */
    public int[][] stores() {
        // Ordenar tiendas por ubicación
        Collections.sort(stores, Comparator.comparingInt(Store::getLocation));
        
        int[][] result = new int[stores.size()][2];
        for (int i = 0; i < stores.size(); i++) {
            Store store = stores.get(i);
            result[i][0] = store.getLocation();
            result[i][1] = store.getTenges();
        }
        return result;
    }
    
    /**
     * Obtiene información de tiendas vaciadas.
     * @return matriz con ubicación y veces vaciada de cada tienda
     */
    public int[][] emptiedStores() {
        // Ordenar tiendas por ubicación
        Collections.sort(stores, Comparator.comparingInt(Store::getTimes));
        
        int[][] emptiedstores = new int[stores.size()][2];
        for (int i = 0; i < stores.size(); i++) {
            Store store = stores.get(i);
            emptiedstores[i][0] = store.getLocation();
            emptiedstores[i][1] = store.getTimes();
        }
        return emptiedstores;
    }
    
    /**
     * Consulta y muestra información de ganancias por tienda.
     */
    public void consultProfit(){
        System.out.println("La posición de las tiendas junto con las veces que fueron desocupadas son :"+ Arrays.deepToString(emptiedStores()));
    }
    
    /**
     * Obtiene información de todos los robots.
     * @return matriz con ubicación de cada robot
     */
    public int[][] robots() {
        // Ordenar robots por ubicación
        Collections.sort(robots, Comparator.comparingInt(Robot::getLocation));
        
        int[][] result = new int[robots.size()][2];
        for (int i = 0; i < robots.size(); i++) {
            Robot robot = robots.get(i);
            result[i][0] = robot.getLocation();
            result[i][1] = 0; // Los robots no tienen tenges
        }
        return result;
    }
    
    /**
     * Hace visible.
     */
    public void makeVisible() {
        this.visible = true;
        Canvas canvas = Canvas.getCanvas();
        canvas.setVisible(true);
        profitBar.makeVisible();
        
        // Hacer visibles solo las celdas del camino (no toda una matriz)
        for (Cell cell : path) {
            cell.visual.makeVisible();
        }
        
        // Hacer visibles tiendas y robots
        for (Store store : stores) {
            store.makeVisible();
        }
        for (Robot robot : robots) {
            robot.makeVisible();
        }
    }
    
    /**
     * Hace invisible.
     */
    public void makeInvisible() {
        this.visible = false;
        profitBar.makeInvisible();
        
        // Ocultar solo las celdas del camino
        for (Cell cell : path) {
            cell.visual.makeInvisible();
        }
        
        // Ocultar tiendas y robots
        for (Store store : stores) {
            store.makeInvisible();
        }
        
        for (Robot robot : robots) {
            robot.makeInvisible();
        }
        }
    
    /**
     * Finaliza.
     */
    public void finish() {
        System.exit(0);
    }
    
    /**
     * Verifica si la última operación fue exitosa.
     * @return true si la última operación fue exitosa, false en caso contrario
     */
    public boolean ok() {
        return lastOperationOk;
    }
    
    /**
     * Actualiza la barra de ganancias.
     */
    private void updateProfitBar() {
        int maxPossibleProfit = calculateMaxPossibleProfit();
        int barHeight = (maxPossibleProfit > 0) ? (totalProfit * 200 / maxPossibleProfit) : 0;
        profitBar.changeSize(20, Math.max(barHeight, 5));
    }
    
    /**
     * Calcula el máximo de ganancias posibles.
     * @return la suma de todas las ganancias iniciales de las tiendas
     */
    private int calculateMaxPossibleProfit() {
        int maxProfit = 0;
        for (Store store : stores) {
            maxProfit += store.getInitialTenges();
        }
        return maxProfit;
    }
    
    /**
     * Interfaz para elementos visuales.
     */
    public interface VisualElement {
        void move(int x, int y);
        void makeVisible();
        void makeInvisible();
        void setPosition(int x, int y);
    }
    
    
    /**
     * debuelve una matriz con la hubicacion y ganancia de cada robot
     * @return matriz con ubicación y ganancias de cada robot
     */
    public int[][] robotProfits() {
        Collections.sort(robots, Comparator.comparingInt(Robot::getTotalProfit).reversed());
        
        int[][] result = new int[robots.size()][2];
        for (int i = 0; i < robots.size(); i++) {
            Robot robot = robots.get(i);
            result[i][0] = robot.getLocation();
            result[i][1] = robot.getTotalProfit();
        }
        return result;
    }
    
    /**
     * Consulta y muestra las ganancias por robot.
     */
    public void consultRobotProfits() {
        System.out.println("Ganancias por robot:");
        int[][] profits = robotProfits();
        for (int i = 0; i < profits.length; i++) {
            System.out.println("Robot en ubicación " + profits[i][0] + 
                             ": " + profits[i][1] + " tenges");
        }
    }
    
    /**
     * Consulta y muestra las ganancias por movimiento de un robot específico.
     * @param robotLocation la ubicación del robot a consultar
     */
    public void consultMovementProfits(int robotLocation) {
        Robot robot = foundRobot(robotLocation);
        if (robot != null) {
            int[][] movementProfits = robot.getMovementProfits();
            System.out.println("Ganancias por movimiento del robot en " + robotLocation + ":");
            for (int i = 0; i < movementProfits.length; i++) {
                System.out.println("Movimiento " + movementProfits[i][0] + 
                                 ": " + movementProfits[i][1] + " tenges");
            }
        }
    }
    
    
}
    