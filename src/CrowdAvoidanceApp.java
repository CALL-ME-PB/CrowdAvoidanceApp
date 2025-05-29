import java.util.*;

// Abstract class for all types of users
abstract class User {
    protected String name;
    protected int crowdThreshold;
    protected int maxWalkingDistance;
    protected boolean accessibilityRequired;

    public User(String name, int crowdThreshold, int maxWalkingDistance, boolean accessibilityRequired) {
        this.name = name;
        this.crowdThreshold = crowdThreshold;
        this.maxWalkingDistance = maxWalkingDistance;
        this.accessibilityRequired = accessibilityRequired;
    }

    public abstract void displayPreferences();
}

// Regular commuter class (inherits User)
class Commuter extends User {
    public Commuter(String name, int crowdThreshold, int maxWalkingDistance, boolean accessibilityRequired) {
        super(name, crowdThreshold, maxWalkingDistance, accessibilityRequired);
    }

    @Override
    public void displayPreferences() {
        System.out.println("User: " + name);
        System.out.println("Crowding Threshold: " + crowdThreshold + "%");
        System.out.println("Max Walking Distance: " + maxWalkingDistance + "m");
        System.out.println("Accessibility Required: " + (accessibilityRequired ? "Yes" : "No"));
    }
}

// Class representing a Route
class Route {
    String routeName;
    int occupancyPercent;
    boolean isAccessible;
    int walkingDistance;

    public Route(String routeName, int occupancyPercent, boolean isAccessible, int walkingDistance) {
        this.routeName = routeName;
        this.occupancyPercent = occupancyPercent;
        this.isAccessible = isAccessible;
        this.walkingDistance = walkingDistance;
    }

    public void showRouteInfo() {
        String statusColor = occupancyPercent < 60 ? "Green" :
                             occupancyPercent <= 80 ? "Yellow" : "Red";

        System.out.println("Route: " + routeName);
        System.out.println("Occupancy: " + occupancyPercent + "% (" + statusColor + ")");
        System.out.println("Accessible: " + (isAccessible ? "Yes" : "No"));
        System.out.println("Walking Distance: " + walkingDistance + "m\n");
    }
}

// Core logic class
class TransportSystem {
    List<Route> routes = new ArrayList<>();

    public void addRoute(Route route) {
        routes.add(route);
    }

    // Suggest best route based on user preferences
    public void suggestRoutes(User user) {
        System.out.println("\nSuggested Routes:");
        int count = 0;

        for (Route r : routes) {
            if (r.occupancyPercent <= user.crowdThreshold &&
                r.walkingDistance <= user.maxWalkingDistance &&
                (!user.accessibilityRequired || r.isAccessible)) {

                r.showRouteInfo();
                count++;
                if (count == 3) break; // Max 3 suggestions
            }
        }

        if (count == 0) {
            System.out.println("⚠ No suitable routes found. Try adjusting preferences.");
        }
    }
}

// Main Class — entry point
public class CrowdAvoidanceApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Collect user preferences
        System.out.println("Enter your name:");
        String name = sc.nextLine();

        System.out.println("Enter crowding tolerance (e.g., 75 for 75%):");
        int crowdLimit = sc.nextInt();

        System.out.println("Enter max walking distance (in meters):");
        int walkDistance = sc.nextInt();

        System.out.println("Need accessible routes? (true/false):");
        boolean access = sc.nextBoolean();

        Commuter user = new Commuter(name, crowdLimit, walkDistance, access);
        user.displayPreferences();

        // Sample routes (could be API integrated later)
        TransportSystem system = new TransportSystem();
        system.addRoute(new Route("Blue Line", 65, true, 300));
        system.addRoute(new Route("Green Line", 85, false, 150));
        system.addRoute(new Route("Red Line", 45, true, 600));
        system.addRoute(new Route("Yellow Line", 70, false, 400));

        // Suggest routes
        system.suggestRoutes(user);

        sc.close();
    }
}