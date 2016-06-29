import java.util.HashMap;
import java.util.Map;

public class Main {
	private static Map<String, Route> routingTable = new HashMap<String, Route>();
	private static int routeNumber = 0;
	
	public static void main(String[] args) {
		Route test = new Route(routeNumber + 1, "1.0.0.0", 1000 + 1, true);
		System.out.println(test.toString());
		System.out.println();
		routingTable.put(test.getDestAddress(), test);
		System.out.println(routingTable.toString());
		System.out.println();
		System.out.println(routingTable.get("1.0.0.0").toString());
	}

}