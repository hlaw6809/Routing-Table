package router;

import java.util.List;
import java.util.Random;

public class RouterMain {
    private static final int ADD_WAIT = 200;           //Time to wait in ms
    private static final int INITIAL_TABLE_SIZE = 5;   //Initial size of routing table
    private static final int MAX_MASK_SIZE = 24;        //Maximum size of address mask
    private static final int MIN_MASK_SIZE = 12;        //Minimum size of address mask
    private static final int NUM_PORTS = 16;            //Number of ports on the router
    private static final int ADD_LOOPS = 6;            //Number of times to add a route to the table

    private static RoutingTable table;


    /**
     * Makes a route with a random destination address and port number.
     * @return random route
     */
    private static Route makeRandomRoute() {
        Random rand = new Random();
        int maskSize = rand.nextInt(MAX_MASK_SIZE - MIN_MASK_SIZE) + MIN_MASK_SIZE; //Generate random mask size between 12 and 24
        String dest = Integer.toString(rand.nextInt(255)) + '.' + rand.nextInt(255) + '.' + rand.nextInt(255) + '.' + rand.nextInt(255) + '/' + maskSize; //Generate random IP address
        int port = rand.nextInt(NUM_PORTS);
        return new Route(dest, port, true);
    }


    /**
     * Initializes and populated the routing table
     */
    private static void initializeTable() {
        //Create Table
        table = new RoutingTable();

        //Add number of random routes to table
        for (int i = 0; i < INITIAL_TABLE_SIZE; i++) {
            table.addRoute(makeRandomRoute());
        }

        //Print table
        System.out.println("Initial Table:");
        System.out.println(table.toString());
    }


    public static void main(String[] args) {
        initializeTable();
        for (int i = 0; i < ADD_LOOPS; i++) {
            //Wait to add
            try {
                Thread.sleep(ADD_WAIT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //If third add set random route inactive
            if (i % 3 == 2) {
                Random rand = new Random();
                List<Route> routes = table.getRouteList();
                int index = Math.abs(rand.nextInt() % routes.size());
                table.updateRoute(routes.get(index).getSeqNumber(), -1);
                System.out.println("Route set Innactive: " + routes.get(index).toString());
            }

            //Add new Route
            Route randRoute = makeRandomRoute();
            table.addRoute(randRoute);

            //Print information
            System.out.println("Route Added: " + randRoute.toString());
            System.out.println(table.toString());
        }

        //Test best route search for an IP
        table.addRoute(new Route("255.255.255.0/16", 1, true));
        String testIP = "255.255.255.111";
        System.out.println("Searching for best route for " + testIP);
        Route best = table.findBestRoute(testIP);
        System.out.println("Best Route for " + testIP + ": " + best.toString());
    }

}