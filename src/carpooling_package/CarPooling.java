package carpooling_package;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * interface that are resposible for detremining if you can subscribe or not
 *
 * @author Beeka
 */
interface Isubscribable { // interface segregation principle ( solid principle )
    int miniage = 20;// static minimum age to subscibe;
    int minitrips = 5;// static minmum trips you can subsribe to

    /**
     * check if the passenger can subscribe or not
     *
     * @param age   age of the passenger
     * @param Trips number of trips of the passenger
     * @return true if he can subscribe , false otherwise
     */
    boolean cansubscribe(int age, int Trips);// overriding
}

/**
 * route that has a start Location and end Location
 *
 * @author Beeka
 */
class Route {
    private String startLocation;
    private String endLocation;

    /**
     * initialize route object with start location and end loaction
     *
     * @param Start start location of the route
     * @param End   end location of the route
     */
    public Route(String Start, String End) {
        this.startLocation = Start;
        this.endLocation = End;
    }

    /**
     * @return start location of the route
     */
    public String getStartLocation() {
        return this.startLocation;
    }

    /**
     * set start location of the route
     *
     * @param startLocation start location of the route
     */
    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    /**
     * @return end location of the route
     */
    public String getEndLocation() {
        return this.endLocation;
    }

    /**
     * set end location of the route
     *
     * @param endLocation end location of the route
     */
    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

}

/**
 * car that has codenumber , drivername ,capacity ,trips in each day
 *
 * @author Beeka
 */
class Car {
    private final String Codenumber;// private final
    private final String driverName;// private final
    private final int capacity;// private final
    public ArrayList<String> CarReview;
    protected int TripsPerDay;// private

    /**
     * initialize car object with code number , drivername , capacity , trips in day
     *
     * @param Codenumber  car code number
     * @param driverName  driver name
     * @param capacity    capacity of the car
     * @param TripsPerDay number of trips in a day
     */
    public Car(String Codenumber, String driverName, int capacity, int TripsPerDay) {
        this.Codenumber = Codenumber;
        this.capacity = capacity;
        this.driverName = driverName;
        this.TripsPerDay = TripsPerDay;
        CarReview = new ArrayList<String>();
    }

    /**
     * @return return car code number
     */

    public String getCarCode() {
        return Codenumber;
    }

    /**
     * @return trips in a day
     */

    public int getTripsPerDay() {
        return TripsPerDay;
    }

    /**
     * @return capacity of the car
     */
    public int getCapacity() {
        return capacity;
    }

    public void addReview(String review) {
        this.CarReview.add(review);
    }

    public void GetCarReviews() {
        for (int i = 0; i < CarReview.size(); i++) {

            System.out.printf("Review%d: %s \n\n", i + 1, CarReview.get(i));

        }
    }
}

/**
 * Ticket that has a price and Ride as each ticket belong to one ride
 *
 * @author Beeka
 */
class Ticket {
    private final Ride ride;// private final
    private final String carcode;// private final
    private double price;// private final

    /**
     * intalize ticket with price , Ride refrence
     *
     * @param price ticket price
     * @param ride  car code
     */
    public Ticket(double price, Ride ride) {
        this.price = price;
        this.ride = ride;
        carcode = ride.getCar().getCarCode();
    }

    // overloading
    public Ticket(double price, Ride ride, String CarCode) {
        this.price = price;
        this.ride = ride;
        carcode = CarCode;
    }

    /**
     * @return ticket price
     */
    public double getPrice() {
        return price;
    }

    /**
     * set ticket price
     *
     * @param price ticket price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return car code
     */
    public String CarCode() {
        return this.carcode;
    }

    public Ride getRide() {
        return ride;
    }
}

/**
 * a ride is just what customers pay for to use carpooling system any trip must
 * have a rout and a car and available tickets that passengers can reserve it
 * and passengers and a price of course ( the ride price is different between
 * ticket price as ticket price can be less than ride price due to subscription
 * or it can equall ride price if passenger doesnt have subscription)
 *
 * @author Beeka
 */
class Ride {
    private final Route route;
    private final Car car;
    private final ArrayList<Passenger> passngers;// array list of passengers
    private final double price;// price of ride not the ticket
    private int availTickets;// the availabe tickets for the ride

    /**
     * initialize a ride with route , car , price of the ride (note that price of
     * the ride is different between ticket price as ticket price can be less than
     * ride price due to subscription or it can equall ride price if passenger
     * doesnt have subscription) , available tickets is just calculated from the car
     * object capacity
     *
     * @param route route of the ride
     * @param car   car of the ride
     * @param price price of the ride
     */
    public Ride(Route route, Car car, double price) {
        this.route = route;
        this.car = car;
        this.price = price;

        availTickets = car.getCapacity();

        passngers = new ArrayList<Passenger>();
    }

    public boolean canAdd() {
        return (availTickets > 0);
    }

    public void addPassenger(Passenger p) {

        double ticketPrice = Discount.getDiscount(this, p);
        Ticket newTicket = new Ticket(ticketPrice, this);
        p.addTicket(newTicket);// final method

        passngers.add(p);

        availTickets--;// remove ticket from avail tickets object
    }

    /**
     * @return car object
     */
    public Car getCar() {
        return car;
    }

    /**
     * @return route object
     */
    public Route getRoute() {
        return route;
    }

    /**
     * @return the Avail places in this ride
     */
    public int getAvailTickets() {
        return availTickets;
    }

    public double getPrice() {
        return price;
    }
}

/**
 * interface resposible for determining ticket price
 *
 * @author Beeka
 */

/**
 * abstract class that represents passenger , each passenger can have one ticket
 * or multiple tickets
 *
 * @author Beeka
 */
abstract class Passenger {

    protected ArrayList<Ticket> tickets;

    /**
     * initialize passenger object with empty array of tickets
     */
    public Passenger() {
        tickets = new ArrayList<Ticket>();
    }

    /**
     * abstract function that are responsible for setting the discount amount
     *
     * @return ticket discount for the classes that extends that abstract class
     */
    abstract public double discount();

    /**
     * add a ticket to this passenger
     *
     * @param ticket ticket of the passenger
     */
    protected final void addTicket(Ticket ticket) {// final method
        tickets.add(ticket);
    }

    /**
     * function that displays the data of passenger object
     */
    abstract public void DisplayData();

}

/**
 * class that is responsible for calculating the discount for passengers
 * depending on their types this class calculate the discount manually by just
 * taking an object form passenger abstract
 *
 * @author Beeka
 */
abstract class Discount { // open closed principle ( solid principle )

    // this is abstract because i dont want to make an object from this Discount
    // class

    /**
     * function to getDiscount by taking object of the passenger to know its type
     * automatically by calling discount in each passenger class if he subscribe or
     * he didn't subscribe then takes the ticket
     *
     * @param r         ride object
     * @param passenger object of the type of passenger
     * @return discount of the ticket
     */
    public static double getDiscount(Ride r, Passenger passenger) {// static function
        return (r.getPrice() - passenger.discount() * r.getPrice());
    }
}

/**
 * non subscriber passenger class
 *
 * @author Beeka
 */
class NonSubscriber extends Passenger implements Isubscribable {
    /**
     * initialize non subscriber object with empty array of rides that can buy
     */
    public NonSubscriber() {
        super();
    }

    /**
     * discount for the non subscriber passenger class
     */
    @Override // override
    public double discount() {
        // there is no discount for the nonsubscriber class so the discount will be 0
        return 0;
    }

    /**
     * check if this passenger can subscribe or not
     */
    @Override // override
    public boolean cansubscribe(int age, int Trips) { // overriding

        // static data member
        return this.miniage <= age && this.minitrips <= Trips;

    }

    @Override // override
    public void DisplayData() {

        System.out.print("Type:non subscriber \n");

        System.out.print("rides that this passenger took:\n");
        for (int i = 0; i < this.tickets.size(); i++) {
            Ticket ticket = tickets.get(i);
            Ride ride = ticket.getRide();
            Route route = ride.getRoute();
            Car car = ride.getCar();

            System.out.printf("start location : %s - end location: %s - CarCode %s ", route.getStartLocation(),
                    route.getEndLocation(), car.getCarCode());
            System.out.printf("ride Price :%f - Ticket Price(after discount):%f \n", ride.getPrice(),
                    ticket.getPrice());

        }
    }
}

/**
 * subscriber passenger class
 *
 * @author Beeka
 */
class Subscriber extends Passenger {
    public final String name;// final
    public int age;
    int reservedTrips;

    /**
     * initialize subscriber object that has name and age with empty array of rides
     * that can buy
     *
     * @param name          name of the passenger
     * @param age           age of the passenger
     * @param reservedTrips the reserved Trips of that passenger
     */
    public Subscriber(String name, int age, int reservedTrips) {

        this.name = name;
        this.age = age;
        this.reservedTrips = reservedTrips;

    }

    /**
     *
     * @return non subscriber passenger object after moving all the data to non
     *         subscriber
     */
    public Passenger unsubscribe() {
        Passenger p = new NonSubscriber();

        for (int i = 0; i < tickets.size(); i++) {

            Ticket t = tickets.get(i);
            p.addTicket(t);// final method

        }

        return p;

    }

    /**
     * get ticket discount for the subscriber passenger
     */
    @Override
    public double discount() {
        return 50.0 / 100;
    }

    @Override
    public void DisplayData() {
        System.out.printf("Type:subscriber name:%s - age:%s - subscribed trips:%d  \n", this.name, this.age,
                this.reservedTrips);

        System.out.print("rides that this passenger took:\n");
        for (int i = 0; i < this.tickets.size(); i++) {

            Ticket ticket = tickets.get(i);
            Ride ride = ticket.getRide();
            Route route = ride.getRoute();
            Car car = ride.getCar();

            System.out.printf("start location : %s - end location: %s - CarCode %s - ", route.getStartLocation(),
                    route.getEndLocation(), car.getCarCode());
            System.out.printf("ride Price :%f - Ticket Price(after discount):%f \n", ride.getPrice(), ticket.getPrice());
        }
    }
}

/**
 * AppRunner class is the class that is resposible for getting all the data to
 * work with it
 *
 * @author Beeka
 *
 */
class AppRunner {

    public static void getallroute(ArrayList<Route> routes) {
        System.out.println("choose the route you want to reverse a car in it ");

        for (int i = 0; i < routes.size(); i++) {
            System.out.printf("%d: start location: %s       end location: %s \n", i + 1,
                    routes.get(i).getStartLocation(), routes.get(i).getEndLocation());


        }

    }

    public static void RoutesTocomplain(ArrayList<Route> routes) {

        System.out.println("choose the route you want to review a car in it ");
        for (int i = 0; i < routes.size(); i++) {

            System.out.printf("%d: start location: %s       end location: %s \n", i + 1,
                    routes.get(i).getStartLocation(), routes.get(i).getEndLocation());

        }


    }

    public static void printOndeRide(Ride ride, Passenger p) {
        String StartLocation = ride.getRoute().getStartLocation();
        String EndLocation = ride.getRoute().getEndLocation();
        System.out.printf("carcode:%s - price:%f - price After Discount:%f - available tickets:%d \n",
                ride.getCar().getCarCode(), ride.getPrice(), Discount.getDiscount(ride, p), ride.getAvailTickets());

    }

    public static ArrayList<Ride> GetAvailRides(ArrayList<Ride> rides, Route r, Passenger p) {
        ArrayList<Ride> availrides = new ArrayList<Ride>();

        String start = r.getStartLocation();
        String end = r.getEndLocation();
        System.out.printf("Rides for Start:%s End:%s\n\n", start, end);

        int cnt = 1;
        for (int i = 0; i < rides.size(); i++) {

            String StartRide = rides.get(i).getRoute().getStartLocation();
            String EndRide = rides.get(i).getRoute().getEndLocation();

            if (start.equalsIgnoreCase(StartRide) && end.equalsIgnoreCase(EndRide)) {

                System.out.print(cnt + " :");
                printOndeRide(rides.get(i), p);

                availrides.add(rides.get(i));
                cnt++;

            }

        }
        return availrides;

    }

    public static Subscriber checkSubscrbtion(Scanner input, ArrayList<Passenger> passengers) {
        System.out.println("enter your name please to check your subscribtion");
        String name = input.next();

        for (int i = 0; i < passengers.size(); i++) {
            Passenger p = passengers.get(i);

            if (p instanceof Subscriber) {

                Subscriber s = (Subscriber) p;
                if (s.name.equalsIgnoreCase(name)) {
                    return s;
                }
            }

        }

        return null;

    }

    public static void displayPassengersData(ArrayList<Passenger> passengers) {
        for (int i = 0; i < passengers.size(); i++) {
            System.out.printf("passenger:%d\n", i + 1);
            passengers.get(i).DisplayData();
            System.out.print("\n\n");
        }
    }

    public static Car SearchForCar(String CarCode, ArrayList<Ride> rides) throws WrongInputException {
        CarCode = checkInput(CarCode, false, true);

        for (int i = 0; i < rides.size(); i++) {
            Ride ride = rides.get(i);
            if (ride.getCar().getCarCode().equalsIgnoreCase(CarCode)) {
                return ride.getCar();
            }
        }
        return null;
    }

    public static void choosereview(Scanner input, ArrayList<Ride> rides) {
        while (true) {
            try {
                System.out.println("choose what you want to do \n");
                System.out.println("1-:add review");
                System.out.println("2-:Get review");

                String choice = input.next();
                choice = checkInput(choice, false, true);
                int cho = Integer.parseInt(choice);
                if (INrange(cho, 1, 2) == false)
                    throw new NotInRangeException("Exception: not in range excaption \n\n");

                if (cho == 1) {
                    System.out.print("type car code  number \n");
                    String carCode = input.next();
                    Car m = SearchForCar(carCode, rides);
                    if (m == null)
                        throw new Exception("No car with that code\n");

                    System.out.println("please enter your review \n");
                    String rv = input.next();
                    m.addReview(rv);

                } else {
                    System.out.print("type car code  number \n");
                    String carCode = input.next();
                    Car m = SearchForCar(carCode, rides);
                    if (m == null)
                        throw new Exception("exception: No car with that code\n");

                    m.GetCarReviews();
                }
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("type any key to go back or type (y/Y) to try again \n");
                String cont = input.next();
                if (cont.equalsIgnoreCase("y")) {
                    continue;
                } else {
                    break;
                }
            }
        }
    }

    public static void addnewSbuscriber(Scanner input, ArrayList<Passenger> passengers) {
        NonSubscriber temp = new NonSubscriber();
        char choice = 'y';

        while (choice == 'y') {
            try {
                System.out.printf("minimum number of age and trips to subscribe age=%d  trips=%d \n", temp.miniage,
                        temp.minitrips);
                System.out.println("type your name please");
                String name = input.next();
                name = checkInput(name, true, false);

                System.out.println("type your age please");
                String TEmpAgeInput = input.next();
                TEmpAgeInput = checkInput(TEmpAgeInput, false, true);
                int age = Integer.parseInt(TEmpAgeInput);

                System.out.println("type your number of trips you want to reserve please");
                String TempNumInput = input.next();
                TempNumInput = checkInput(TempNumInput, false, true);
                int numoftrips = Integer.parseInt(TempNumInput);

                if (temp.cansubscribe(age, numoftrips) == true) {// override

                    Subscriber newSubscriber = new Subscriber(name, age, numoftrips);
                    passengers.add(newSubscriber);
                    System.out.println("     successfully subscribed \n\n");

                    return;
                } else {

                    System.out.println("you cant subscribe (age or trips is less than the minimum) ");
                    System.out.println("press y to try again or c to cancel");
                    choice = input.next().charAt(0);

                }
            } catch (WrongInputException e) {

                System.out.println(e.getMessage());

                System.out.println("type any key to go back or type (y/Y) to try again ");
                String cont = input.next();
                if (cont.equalsIgnoreCase("y")) {
                    continue;
                } else {
                    break;
                }

            }
        }

    }

    public static ArrayList<Passenger> GetSubscibed(ArrayList<Passenger> passengers) {
        System.out.println("Subscribed passngers list :- \n");
        ArrayList<Passenger> SubscribedPassengers = new ArrayList<Passenger>();
        int cnt = 1;
        for (int i = 0; i < passengers.size(); i++) {
            Passenger p = passengers.get(i);
            if (p instanceof Subscriber) {

                Subscriber s = (Subscriber) p;
                SubscribedPassengers.add(p);
                System.out.printf("%d: name:%s\n", cnt, s.name);
                cnt++;
            }
        }
        return SubscribedPassengers;
    }

    public static void removeSubscibtion(ArrayList<Passenger> passengers, Passenger p) {
        for (int i = 0; i < passengers.size(); i++) {
            Passenger toberemoved = passengers.get(i);

            if (toberemoved instanceof Subscriber) {
                Subscriber p1 = (Subscriber) toberemoved;
                Subscriber p2 = (Subscriber) p;

                if (p1.name.equals(p2.name)) {
                    passengers.remove(i);
                    passengers.add(i, p1.unsubscribe());

                    return;
                }

            }

        }
    }

    public static void SearchForRoute(String StartLocation, String Endlocation, ArrayList<Route> routes,
                                      ArrayList<Ride> rides) {
        for (int i = 0; i < routes.size(); i++) {
            Route route = routes.get(i);
            if (route.getStartLocation().equalsIgnoreCase(StartLocation)
                    && route.getEndLocation().equalsIgnoreCase(Endlocation)) {
                System.out.println("rides for this route is \n ");
                GetAvailRides(rides, route, new NonSubscriber());
                return;
            }
        }
        System.out.println("There is no rides for this destination\n\n");
    }

    public static boolean INrange(int x, int min, int max) {
        return (x >= min && x <= max);
    }

    public static Passenger firstchoice(Scanner input, Passenger newpassenger, ArrayList<Passenger> passengers)
            throws WrongInputException {

        System.out.println("do you have a subscription ? (y/n)");
        String subscritption = input.next();

        if (subscritption.equalsIgnoreCase("y")) {

            newpassenger = AppRunner.checkSubscrbtion(input, passengers);
            return newpassenger;

        } else if (subscritption.equalsIgnoreCase("n")) {

            newpassenger = new NonSubscriber();
            return newpassenger;
        } else {

            throw new WrongInputException("exception: wrong input exceptoin \n");

        }
    }

    public static boolean CheckifInteger(String input) {
        for (int i = 0; i < input.length(); i++) {
            char s = input.charAt(i);
            if (s < 48 || s > 57) // check ascii of each character
                return false;
        }

        return true;
    }

    public static boolean CheckIFString(String input) {
        for (int i = 0; i < input.length(); i++) {
            char s = input.charAt(i);
            if (s >= 48 && s <= 57) // check ascii of each character
                return false;
        }

        return true;
    }

    public static String checkInput(String input, boolean STRING, boolean Integer) throws WrongInputException {

        if (STRING == false && Integer == true) {
            if (CheckifInteger(input))
                return input;
            else
                throw new WrongInputException("Exception: Wrong input please type integer only \n\n");
        } else {
            if (CheckIFString(input))
                return input;
            else
                throw new WrongInputException("Exception: Wrong input please type string only \n\n");

        }

    }

    public static boolean AddCardNumber(Scanner input) {
        while (true) {
            System.out.println("Type your credit card number please to take the money \n");
            String credit = input.next();
            try {
                credit = checkInput(credit, false, true);
                return true;
            } catch (Exception e) {
                System.out.println(e.getMessage());// exception
                System.out.println("type any key to go back or type (y/Y) to try again \n ");
                String cont = input.next();
                if (cont.equalsIgnoreCase("y")) {
                    continue;
                } else {
                    return false;
                }
            }

        }
    }

}

/**
 * exception that is responsible for checking range of number
 *
 * @author Beeka
 *
 */
class NotInRangeException extends Exception {

    public NotInRangeException(String msg) {
        super(msg);
    }

}

/**
 * exception that is responsible for checking input exception
 *
 * @author Beeka
 *
 */
class WrongInputException extends Exception {

    public WrongInputException(String msg) {
        super(msg);
    }

}

/**
 * exception that is responsible for checking if there is no passenger with that
 * name
 *
 * @author Beeka
 *
 */
class NoPassengerException extends Exception {

    public NoPassengerException(String msg) {
        super(msg);
    }

}

/**
 * main class
 *
 * @author Beeka
 *
 */
public class CarPooling {

    public static void main(String[] args) {
        ArrayList<Passenger> passengers;
        ArrayList<Route> routes;
        ArrayList<Car> cars;
        ArrayList<Ride> rides;
        Scanner input;

        passengers = new ArrayList<Passenger>();
        passengers.add(new Subscriber("ahmed", 23, 2));// Subscriber1
        passengers.add(new Subscriber("isaac", 14, 3));// Subscriber2

        passengers.add(new NonSubscriber());// Subscriber3
        passengers.add(new NonSubscriber());// Subscriber4

        routes = new ArrayList<Route>(); // routes
        routes.add(new Route("nasr city", "shoubra"));
        routes.add(new Route("abbasia", "october city"));
        routes.add(new Route("alex", "mansoura"));
        routes.add(new Route("shoubra", "nasr city"));
        routes.add(new Route("october city", "abbasia"));

        cars = new ArrayList<Car>();// car

        cars.add(new Car("2020198465", "abdelwahab", 4, 2));
        cars.get(0).addReview("Great car");

        cars.add(new Car("2014356879", "mahmoud", 5, 2));
        cars.get(1).addReview("Not bad");

        cars.add(new Car("2013789456", "abanoub", 4, 2));
        cars.get(2).addReview("amazing car and driver");

        cars.add(new Car("2010354987", "ibrahim", 5, 2));
        cars.get(3).addReview("Great car");

        cars.add(new Car("2009064681", "salah", 5, 2));
        cars.get(4).addReview("really bad car");

        rides = new ArrayList<Ride>();// rides

        rides.add(new Ride(routes.get(0), cars.get(0), 125));// ride 1
        rides.get(0).addPassenger(passengers.get(0));//passenger 1 reserved ride 1
        rides.get(0).addPassenger(passengers.get(1));//passenger 2 reserved ride 1


        rides.add(new Ride(routes.get(1), cars.get(0), 75));//ride 2
        rides.get(1).addPassenger(passengers.get(1));//passenger 2 reserved ride 2
        rides.get(1).addPassenger(passengers.get(2));//passenger 3 reserved ride 2


        rides.add(new Ride(routes.get(0), cars.get(1), 100));//ride 3
        rides.get(2).addPassenger(passengers.get(2));//passenger 3 reserved ride 3


        rides.add(new Ride(routes.get(3), cars.get(3), 35));//ride 4
        rides.get(2).addPassenger(passengers.get(0));//passenger 0 reserved ride 4


        rides.add(new Ride(routes.get(2), cars.get(2), 35));//ride 5


        rides.add(new Ride(routes.get(4), cars.get(2), 35));//ride 6

        while (true) {
            System.out.println("enter (1 or 2 or 3 or .......) to choose ");
            System.out.println("1- reserve");
            System.out.println("2- subscribe");
            System.out.println("3- unsubscribe");
            System.out.println("4- Display Data");
            System.out.println("5- Search for route");
            System.out.println("6- Report Complaint/Review.");

            input = new Scanner(System.in);
            int choice = -1;
            try {
                String tempinp = input.next();
                tempinp = AppRunner.checkInput(tempinp, false, true);
                choice = Integer.parseInt(tempinp);

                if (AppRunner.INrange(choice, 1, 6) == false) {
                    throw new NotInRangeException("Exception: input must be between 1 and 6 \n\n");
                }

            } catch (WrongInputException e) {
                System.out.print(e.getMessage());
                continue;
            } catch (NotInRangeException e) {
                System.out.print(e.getMessage());
                continue;
            }

            Passenger newpassenger = null;

            if (choice == 1) {
                String cont = "y";
                while (cont.equalsIgnoreCase("y")) {

                    try {

                        newpassenger = AppRunner.firstchoice(input, newpassenger, passengers);
                        if (newpassenger == null)
                            throw new NoPassengerException("Exception: There is no passenger with that name \n");
                        break;

                    } catch (NoPassengerException e) {

                        System.out.println(e.getMessage());
                        System.out.println("type any key to go back or type (y/Y) to try again ");

                        cont = input.next();
                        if (cont.equalsIgnoreCase("y")) {
                            continue;
                        } else {
                            break;
                        }

                    } catch (WrongInputException e) {

                        System.out.println(e.getMessage());
                        System.out.println("type any key to go back or type (y/Y) to try again ");
                        cont = input.next();
                        if (cont.equalsIgnoreCase("y")) {
                            continue;
                        } else {
                            break;
                        }

                    }

                }
                if (cont.equalsIgnoreCase("y")) {
                    AppRunner.getallroute(routes);
                    int routeNumber = -1;
                    boolean conti = true;
                    while (true) {
                        try {
                            System.out.print("Enter route number : \n");
                            String TempRouteInput = input.next();
                            TempRouteInput = AppRunner.checkInput(TempRouteInput, false, true);
                            routeNumber = Integer.parseInt(TempRouteInput);

                            if (AppRunner.INrange(routeNumber, 1, routes.size()) == false)
                                throw new NotInRangeException(
                                        "Exception: not in range you must enter digit within the range \n");

                            break;
                        } catch (WrongInputException e) {

                            System.out.println(e.getMessage());

                            System.out.println("type any key to go back or type (y/Y) to try again ");
                            cont = input.next();
                            if (cont.equalsIgnoreCase("y")) {
                                continue;
                            } else {
                                conti = false;
                                break;
                            }

                        } catch (NotInRangeException e) {
                            System.out.println(e.getMessage());
                            System.out.println("type any key to go back or type (y/Y) to try again ");
                            cont = input.next();
                            if (cont.equalsIgnoreCase("y")) {
                                continue;
                            } else {
                                conti = false;
                                break;
                            }
                        }
                    }
                    if (conti) {
                        ArrayList<Ride> AvailRides = AppRunner.GetAvailRides(rides, routes.get(routeNumber - 1),
                                newpassenger);
                        while (true) {
                            try {
                                System.out.print("Enter ride number : ");
                                String TempRide = input.next();
                                TempRide = AppRunner.checkInput(TempRide, false, true);
                                int ridenumber = Integer.parseInt(TempRide);
                                if (AppRunner.INrange(ridenumber, 1, AvailRides.size()) == false)
                                    throw new NotInRangeException(
                                            "Exception: not in range you must enter digit within the range \n\n");

                                Ride r = AvailRides.get(ridenumber - 1);
                                if (r.canAdd()) {
                                    if (AppRunner.AddCardNumber(input) == false)
                                        break;

                                    r.addPassenger(newpassenger);
                                    if (newpassenger instanceof NonSubscriber)
                                        passengers.add(newpassenger);

                                    System.out.println("        reserved successfully     \n \n");
                                    break;
                                } else {
                                    System.out.println("There is no place in that ride\n");
                                    continue;
                                }
                            } catch (Exception e) {
                                System.out.print(e.getMessage());
                                System.out.println("type any key to go back or type (y/Y) to try again \n ");
                                cont = input.next();
                                if (cont.equalsIgnoreCase("y")) {
                                    continue;
                                } else {
                                    break;
                                }

                            }
                        }
                    }
                }
            } else if (choice == 4) {
                AppRunner.displayPassengersData(passengers);
            } else if (choice == 2) {
                AppRunner.addnewSbuscriber(input, passengers);
            } else if (choice == 3) {

                ArrayList<Passenger> SubscribedPassengers = AppRunner.GetSubscibed(passengers);

                while (true) {
                    try {

                        System.out.println("choose (1 or 2 or 3 or -------) remove its subscribtion");

                        String TempRempovdIndex = input.next();
                        TempRempovdIndex = AppRunner.checkInput(TempRempovdIndex, false, true);
                        int removedIndex = Integer.parseInt(TempRempovdIndex);
                        if (AppRunner.INrange(removedIndex, 1, SubscribedPassengers.size()) == false)
                            throw new NotInRangeException(
                                    "Exception: not in range you must enter digit within the range \n");

                        AppRunner.removeSubscibtion(passengers, SubscribedPassengers.get(removedIndex - 1));
                        break;
                    } catch (WrongInputException e) {
                        System.out.println(e.getMessage());

                        System.out.println("type any key to go back or type (y/Y) to try again \n");
                        String cont = input.next();
                        if (cont.equalsIgnoreCase("y")) {
                            continue;
                        } else {
                            break;
                        }

                    } catch (NotInRangeException e) {
                        System.out.println(e.getMessage());

                        System.out.println("type any key to go back or type (y/Y) to try again \n");
                        String cont = input.next();
                        if (cont.equalsIgnoreCase("y")) {
                            continue;
                        } else {
                            break;
                        }
                    }
                }

            } else if (choice == 5) {

                System.out.print("Enter start location: ");
                String startlocation = input.next();
                System.out.print("\nEnter end location: ");
                String endlocation = input.next();
                AppRunner.SearchForRoute(startlocation, endlocation, routes, rides);

            } else if (choice == 6) {
                AppRunner.choosereview(input, rides);

            }

        }
    }
}