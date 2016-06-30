package router;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hlawr_000 on 6/29/2016.
 */
public class RoutingTable {

    private static List<Route> table;

    public RoutingTable() {
        table = new ArrayList<Route>();
    }


    /**
     * Adds a route to the table
     * @param toAdd Route to add to the table
     */
    public void addRoute(Route toAdd) {
        table.add(toAdd);
        update();
    }


    /**
     * This method is used to simulate an update of a route on a nearby router.
     * @param portNum   Port number the updated router is on
     * @param destAddr  Destination Address that was updated
     * @param cost      New cost of the route. -1 will set the route inactive
     */
    public void updateRoute(int portNum, String destAddr, int cost) {
        //Find route with destination address
        boolean routeFound = false;
        for (int i = 0; i < table.size(); i++) {
            Route route = table.get(i);
            if (route.getPortNumber() == portNum && route.getDestAddress().equals(destAddr)) {
                route.setDestCost(cost);
                route.setTimeStamp(new Date());
            }
        }

        //If no route found create new one
        if (!routeFound) {
            table.add(new Route(destAddr, portNum, true));
        }

        //Update route table now that it has new information
        update();
    }


    /**
     * Updates entries on the table
     */
    private void update() {
        aggregate();
        //Other updates
    }


    /**
     * Reduces entries on table by combining entries on the same port using CIDR aggregation
     */
    private void aggregate() {
        //To be implemented
    }


    @Override
    public String toString() {
        String result = "Routing Table: \n";
        for (int index = 0; index < table.size(); index++) {
            result += "    Table Entry " + index + ": " + table.get(index).toString() + "\n";
        }
        return result;
    }
}
