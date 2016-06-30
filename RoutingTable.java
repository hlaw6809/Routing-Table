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
                if (cost == -1) {
                    route.setActiveFlag(false);
                } else {
                    route.setDestCost(cost);
                    route.setActiveFlag(true);
                }
                route.setTimeStamp(new Date());
            }
        }

        //If no route found create new one
        if (!routeFound && cost != -1) {
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


    public Route findBestRoute(String address) {
        Route bestRoute = null;
        for (Route route : table) {
            if (route.isActiveFlag() && compareAddress(address, route.getDestAddress())) {
                System.out.println("    Route Found: " + route.toString());
                if (bestRoute == null || getMaskSize(route.getDestAddress()) < getMaskSize(bestRoute.getDestAddress())) {
                    bestRoute = route;
                }
            }
        }
        return bestRoute;
    }


    private boolean compareAddress(String destAddress, String routeAddress) {
        int maskSize = getMaskSize(routeAddress);
        int routeIP = getIPInt(routeAddress);
        int destIP = getIPInt(destAddress);
        int bitMask = 0x80000000;
        for (int i = 0; i < maskSize; i++) {
            if ((destIP & bitMask) != (routeIP & bitMask)) {
                return false;
            }
            bitMask = bitMask >> 1;
        }
        return true;
    }


    private int getIPInt(String address) {
        String[] addrInfo = address.split("/");
        String[] ipStrings = addrInfo[0].split("\\.", 4);
        int ip =  (Integer.parseInt(ipStrings[0]) << 24)
                + (Integer.parseInt(ipStrings[1]) << 16)
                + (Integer.parseInt(ipStrings[2]) << 8)
                + Integer.parseInt(ipStrings[3]);
        return ip;
    }

    private int getMaskSize(String address) {
        String[] addrInfo = address.split("/");
        return Integer.parseInt(addrInfo[1]);
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
