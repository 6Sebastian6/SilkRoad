import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.ArrayList;

public class SilkRoadCC2Test {
    private SilkRoad silkRoad;

    @BeforeEach
    public void setUp() {
        silkRoad = new SilkRoad(5); // crea un SilkRoad de longitud 5
        silkRoad.cleanColors();
    }
    
    @AfterEach
    public void cleanUp() {
    if (silkRoad != null) {
        silkRoad.cleanColors(); // Llama al método que borra todo
        silkRoad = null;
    }
    }
    
    
    @Test
    public void accordingCGtestPlaceStore() {
        silkRoad.placeStore(0, 50);
        int[][] result = silkRoad.stores();

        assertEquals(1, result.length, "Debe haber una tienda");
        assertEquals(0, result[0][0], "La tienda debe estar en la posición 0");
        assertEquals(50, result[0][1], "La tienda debe tener 50 tenges");
        assertTrue(silkRoad.ok(), "La última operación debe ser correcta");
    }
    

    @Test
    public void accordingCGtestRemoveStore() {
        silkRoad.placeStore(1, 20);
        silkRoad.removeStore(1);

        int[][] result = silkRoad.stores();
        assertEquals(0, result.length, "No debe haber tiendas después de remover");
        assertTrue(silkRoad.ok(), "La operación de eliminar debe ser correcta");
    }

    @Test
    public void accordingCGtestRemoveStoreInEmptyLocation() {
        silkRoad.removeStore(3);
        assertFalse(silkRoad.ok(), "Debe marcar error al intentar eliminar tienda inexistente");
    }

    @Test
    public void accordingCGtestEmptiedStoresReturnMatrix() {
        silkRoad.placeStore(0, 10);
        silkRoad.placeStore(2, 15);

        int[][] result = silkRoad.emptiedStores();

        assertEquals(2, result.length, "Debe devolver una fila por cada tienda");
        assertEquals(0, result[0][0], "Primera tienda debe estar en posición 0");
    }

    @Test
    public void accordingCGtestConsultProfitPrintsMatrix() {
        silkRoad.placeStore(0, 10);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        silkRoad.consultProfit();

        System.setOut(originalOut);
        String printed = output.toString().trim();

        assertTrue(printed.startsWith("La posición de las tiendas"), "Debe imprimir el mensaje esperado");
        assertTrue(printed.contains("[[0,"), "Debe mostrar la matriz con la posición de la tienda");
    }

    @Test
    public void accordingCGtestPlaceRobotAndMove() {
        silkRoad.placeRobot(0);
        assertTrue(silkRoad.ok(), "Debe permitir colocar robot en posición 0");

        silkRoad.moveRobot(0, 1);
        assertTrue(silkRoad.ok(), "Debe permitir mover el robot si la posición es válida");
    }
    
public void testRobotMovesToRichestStore() {
    SilkRoad road = new SilkRoad(10);
    road.placeStore(3, 50);  // Tienda con menos tenges
    road.placeStore(7, 200); // Tienda con más tenges
    road.placeRobot(5);
    
    road.moveRobot(5, 0); // Robot decide automáticamente
    // Verificar que el robot se movió a la ubicación 7 (la más rica)
}

public void testRobotStaysWhenNoStores() {
    SilkRoad road = new SilkRoad(10);
    road.placeRobot(5);
    
    road.moveRobot(5, 0); // Intentar decisión automática
    // Verificar que el robot permanece en su ubicación
    // y lastOperationOk es false
}

public void testRobotProfitPerMovement() {
    SilkRoad road = new SilkRoad(10);
    road.placeStore(5, 100);
    road.placeStore(7, 200);
    road.placeRobot(4);
    
    road.moveRobot(4, 1); // Recolectar 100 de tienda 5
    road.moveRobot(5, 2); // Recolectar 200 de tienda 7
    
    // Verificar ganancias por movimiento:
    // Movimiento 1: 100 tenges
    // Movimiento 2: 200 tenges
    // Total: 300 tenges
}

public void testRobotProfitWithEmptyStore() {
    SilkRoad road = new SilkRoad(10);
    road.placeStore(5, 0); // Tienda ya vacía
    road.placeRobot(4);
    
    road.moveRobot(4, 1); // Moverse a tienda vacía
    // Verificar que ganancia = 0 para este movimiento
}

public void testRichestRobotIdentification() {
    SilkRoad road = new SilkRoad(10);
    road.placeStore(3, 100);
    road.placeStore(7, 200);
    road.placeRobot(2); // Robot 1
    road.placeRobot(6); // Robot 2
    
    road.moveRobot(2, 1); // Robot 1 recolecta 100
    road.moveRobot(6, 1); // Robot 2 recolecta 200
    
    Robot richest = road.richestRobot();
    // Verificar que el robot en ubicación 7 es el más rico
}

public void testNoBlinkingWhenInvisible() {
    SilkRoad road = new SilkRoad(10);
    road.placeStore(5, 100);
    road.placeRobot(4);
    
    // Mantener invisible y mover robot
    road.moveRobot(4, 1);
    // Verificar que no hay excepciones por parpadeo en modo invisible
}

    @Test
    public void accordingCGSilkroadAcceptanceTest() {
        SilkRoad road = new SilkRoad(10);

         road.placeStore(2, 50);
        assertTrue(road.ok(), "La tienda no se pudo colocar en la posición 2");

        road.placeRobot(0);
        assertTrue(road.ok(), "El robot no se pudo colocar en la posición 0");
    
        road.moveRobot(0, 2);
        assertTrue(road.ok(), "El robot no se pudo mover hasta la tienda");

        assertTrue(road.profit() > 0, "No se recolectaron ganancias en la tienda");

        road.consultRobotProfits();

        road.resupplyStores();
        assertTrue(road.ok(), "Las tiendas no se pudieron reabastecer");

        road.reboot();
        assertEquals(0, road.profit(), "Las ganancias no se reiniciaron correctamente");
    }

    @Test
public void accordingCGSilkroadAcceptanceTestCycle2() {
    SilkRoad road = new SilkRoad(10);

    // 1. Configuración básica
    road.placeStore(2, 50);
    assertTrue(road.ok(), "La tienda no se pudo colocar en la posición 2");

    road.placeStore(5, 100);
    assertTrue(road.ok(), "La tienda no se pudo colocar en la posición 5");

    road.placeRobot(0);
    assertTrue(road.ok(), "El robot no se pudo colocar en la posición 0");

    // 2. Movimiento automático (nueva funcionalidad del ciclo 2)
    road.moveRobot(0, 0); // Robot decide automáticamente
    assertTrue(road.ok(), "El robot no pudo decidir movimiento automático");
    
    // Verificar que se movió a la tienda más rica (posición 5)
    int[][] robots = road.robots();
    boolean movedToRichestStore = false;
    for (int[] robot : robots) {
        if (robot[0] == 5) {
            movedToRichestStore = true;
            break;
        }
    }
    assertTrue(movedToRichestStore, "El robot no se movió a la tienda más rica");

    // 3. Verificar ganancias
    assertTrue(road.profit() > 0, "No se recolectaron ganancias");

    // 4. Verificar tracking de tiendas desocupadas (nueva funcionalidad)
    int[][] emptiedStores = road.emptiedStores();
    assertTrue(emptiedStores.length > 0, "No se registraron tiendas desocupadas");

    // 5. Consultar ganancias de robots (nueva funcionalidad)
    road.consultRobotProfits();
    assertTrue(road.ok(), "Error al consultar ganancias de robots");

    // 6. Reabastecimiento y reinicio
    road.resupplyStores();
    assertTrue(road.ok(), "Las tiendas no se pudieron reabastecer");

    road.reboot();
    assertEquals(0, road.profit(), "Las ganancias no se reiniciaron correctamente");
}
    
}

