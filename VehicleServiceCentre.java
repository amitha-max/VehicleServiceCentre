import java.util.*;
import java.text.SimpleDateFormat;

// ------------------- Custom Exceptions -------------------
class InvalidServiceException extends Exception {
    public InvalidServiceException(String message) {
        super(message);
    }
}
class InvalidPaymentException extends Exception {
    public InvalidPaymentException(String message) {
        super(message);
    }
}

// ------------------- Customer Class -------------------
class Customer {
    private int id;
    private String name;
    private String phone;
    private String email;
    private String address;

    public Customer(int id, String name, String phone, String email, String address) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }

    @Override
    public String toString() {
        return "Customer ID: " + id + "\nName: " + name +
               "\nPhone: " + phone + "\nEmail: " + email +
               "\nAddress: " + address;
    }
}

// ------------------- Vehicle Class -------------------
class Vehicle {
    private String vehicleNumber;
    private String vehicleType;
    private String vehicleModel;

    public Vehicle(String vehicleNumber, String vehicleType, String vehicleModel) {
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
        this.vehicleModel = vehicleModel;
    }

    public String getVehicleNumber() { return vehicleNumber; }
    public String getVehicleType() { return vehicleType; }
    public String getVehicleModel() { return vehicleModel; }

    @Override
    public String toString() {
        return "Vehicle Number: " + vehicleNumber +
               "\nVehicle Type: " + vehicleType +
               "\nVehicle Model: " + vehicleModel;
    }
}

// ------------------- Service Class -------------------
class Service {
    private int serviceId;
    private String serviceName;
    private String description;
    private double cost;
    private int duration; // in minutes
    private String mechanicAssigned;

    public Service(int serviceId, String serviceName, String description,
                   double cost, int duration, String mechanicAssigned) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.description = description;
        this.cost = cost;
        this.duration = duration;
        this.mechanicAssigned = mechanicAssigned;
    }

    public int getServiceId() { return serviceId; }
    public String getServiceName() { return serviceName; }
    public String getDescription() { return description; }
    public double getCost() { return cost; }
    public int getDuration() { return duration; }
    public String getMechanicAssigned() { return mechanicAssigned; }

    @Override
    public String toString() {
        return serviceId + " | " + serviceName + " | " + description +
               " | ₹" + cost + " | " + duration + " mins | " + mechanicAssigned;
    }
}

// ------------------- Transaction Class -------------------
class Transaction {
    private static int invoiceCounter = 1000;

    private int invoiceNumber;
    private Customer customer;
    private Vehicle vehicle;
    private List<Service> services;
    private double totalAmount;
    private double gst;
    private double finalAmount;
    private String paymentMethod;
    private String date;

    public Transaction(Customer customer, Vehicle vehicle, List<Service> services, String paymentMethod)
            throws InvalidPaymentException {
        if (!paymentMethod.equalsIgnoreCase("Cash") &&
            !paymentMethod.equalsIgnoreCase("Card") &&
            !paymentMethod.equalsIgnoreCase("UPI")) {
            throw new InvalidPaymentException("Payment method not accepted: " + paymentMethod);
        }

        this.invoiceNumber = invoiceCounter++;
        this.customer = customer;
        this.vehicle = vehicle;
        this.services = services;
        this.paymentMethod = paymentMethod;
        this.totalAmount = calculateTotal();
        this.gst = totalAmount * 0.18; // 18% GST
        this.finalAmount = totalAmount + gst;

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        this.date = sdf.format(new Date());
    }

    private double calculateTotal() {
        double sum = 0;
        for (Service s : services) {
            sum += s.getCost();
        }
        return sum;
    }

    public void printInvoice() {
        System.out.println("\n================ SERVICE INVOICE ================");
        System.out.println("Invoice No: " + invoiceNumber + " | Date: " + date);

        System.out.println("\n--- Customer Details ---");
        System.out.println(customer);

        System.out.println("\n--- Vehicle Details ---");
        System.out.println(vehicle);

        // Table format for services
        System.out.println("\n--- Services Availed ---");
        System.out.printf("%-5s %-20s %-10s %-15s\n", "ID", "Service Name", "Cost", "Mechanic");
        System.out.println("-----------------------------------------------------");
        for (Service s : services) {
            System.out.printf("%-5d %-20s ₹%-9.2f %-15s\n",
                              s.getServiceId(), s.getServiceName(), s.getCost(), s.getMechanicAssigned());
        }

        // Billing summary
        System.out.println("-----------------------------------------------------");
        System.out.printf("%-25s : ₹%.2f\n", "Subtotal", totalAmount);
        System.out.printf("%-25s : ₹%.2f\n", "GST (18%)", gst);
        System.out.printf("%-25s : ₹%.2f\n", "Final Amount", finalAmount);
        System.out.printf("%-25s : %s\n", "Payment Method", paymentMethod);
        System.out.println("=====================================================\n");
    }
}

// ------------------- Thread Class -------------------
class ServiceProcessor implements Runnable {
    private Customer customer;
    private List<Service> services;

    public ServiceProcessor(Customer customer, List<Service> services) {
        this.customer = customer;
        this.services = services;
    }

    @Override
    public void run() {
        System.out.println("\nProcessing services for " + customer.getName() + "...");
        try {
            for (Service s : services) {
                System.out.println(" - " + s.getServiceName() + " started by " + s.getMechanicAssigned());
                Thread.sleep(s.getDuration() * 100);
                System.out.println("   ✔ " + s.getServiceName() + " completed!");
            }
            System.out.println("All services completed for " + customer.getName() + "!\n");
        } catch (InterruptedException e) {
            System.out.println("Service interrupted: " + e.getMessage());
        }
    }
}

// ------------------- Main Class -------------------
public class VehicleServiceCentre {
    private static Map<Integer, Customer> customers = new HashMap<>();
    private static List<Service> availableServices = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);

        // Predefined services
        availableServices.add(new Service(1, "Oil Change", "Engine oil replacement", 1500, 15, "Raj"));
        availableServices.add(new Service(2, "Car Wash", "Full body water wash", 500, 10, "Sam"));
        availableServices.add(new Service(3, "Engine Checkup", "Detailed engine diagnostics", 2500, 30, "Anil"));
        availableServices.add(new Service(4, "Wheel Alignment", "Tyre balance & alignment", 1200, 20, "Kiran"));
        availableServices.add(new Service(5, "AC Service", "Cooling efficiency check", 1800, 25, "Ravi"));

        while (true) {
            System.out.println("\n=== Vehicle Service Centre Management System ===");
            System.out.println("1. Add Customer & Service");
            System.out.println("2. List All Customers");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(sc.nextLine());

            if (choice == 1) {
                addCustomerService(sc);
            } else if (choice == 2) {
                listAllCustomers();
            } else if (choice == 3) {
                System.out.println("Exiting...");
                break;
            } else {
                System.out.println("Invalid choice!");
            }
        }
        sc.close();
    }

    private static void addCustomerService(Scanner sc) throws InterruptedException {
        try {
            System.out.print("Enter Customer ID: ");
            int id = Integer.parseInt(sc.nextLine());
            System.out.print("Enter Name: ");
            String name = sc.nextLine();
            System.out.print("Enter Phone: ");
            String phone = sc.nextLine();
            System.out.print("Enter Email: ");
            String email = sc.nextLine();
            System.out.print("Enter Address: ");
            String address = sc.nextLine();

            Customer customer = new Customer(id, name, phone, email, address);
            customers.put(id, customer);

            System.out.print("Enter Vehicle Number: ");
            String vehicleNumber = sc.nextLine();
            System.out.print("Enter Vehicle Type: ");
            String vehicleType = sc.nextLine();
            System.out.print("Enter Vehicle Model: ");
            String vehicleModel = sc.nextLine();

            Vehicle vehicle = new Vehicle(vehicleNumber, vehicleType, vehicleModel);

            // Show available services
            System.out.println("\nAvailable Services:");
            System.out.printf("%-5s %-20s %-25s %-10s %-10s %-15s\n",
                              "ID", "Service Name", "Description", "Cost", "Time", "Mechanic");
            System.out.println("-------------------------------------------------------------------------------");
            for (Service s : availableServices) {
                System.out.printf("%-5d %-20s %-25s ₹%-9.2f %-10d %-15s\n",
                                  s.getServiceId(), s.getServiceName(), s.getDescription(),
                                  s.getCost(), s.getDuration(), s.getMechanicAssigned());
            }

            System.out.print("\nEnter service IDs (comma separated): ");
            String[] serviceChoices = sc.nextLine().split(",");
            List<Service> selectedServices = new ArrayList<>();
            for (String choice : serviceChoices) {
                int sid = Integer.parseInt(choice.trim());
                boolean found = false;
                for (Service s : availableServices) {
                    if (s.getServiceId() == sid) {
                        selectedServices.add(s);
                        found = true;
                        break;
                    }
                }
                if (!found) throw new InvalidServiceException("Invalid service ID: " + sid);
            }

            Thread t = new Thread(new ServiceProcessor(customer, selectedServices));
            t.start();
            t.join();

            System.out.print("Enter Payment Method (Cash/Card/UPI): ");
            String paymentMethod = sc.nextLine();

            Transaction transaction = new Transaction(customer, vehicle, selectedServices, paymentMethod);
            transaction.printInvoice();
        } catch (InvalidServiceException | InvalidPaymentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected Error: " + e.getMessage());
        }
    }

    private static void listAllCustomers() {
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
        } else {
            System.out.println("\n--- All Customers ---");
            for (Customer c : customers.values()) {
                System.out.println(c);
                System.out.println("----------------------");
            }
        }
    }
}
