
import java.time.Year;
import java.util.Arrays;
/* here I used java.time.Year to Get the current year dynamically.
and java.util.Arrays to provide utilities for working with arrays .*/

// Enum for Memory Card Class
enum MemoryCardClass {
    CLASS_4, CLASS_6, CLASS_10;
}

// MemoryCard class
class MemoryCard {
    private String manufacturer;
    private int capacity;
    private MemoryCardClass cardClass;

    public MemoryCard(String manufacturer, int capacity, MemoryCardClass cardClass) {
        this.manufacturer = manufacturer;
        this.capacity = capacity;
        this.cardClass = cardClass;
    }
/* Here , Determines the quality of the memory card based on its class:
Class 10 → capacity / 3
Class 6 → capacity / 5
Class 4 → capacity / 13
Higher class = Better quality. */

    public int determineQuality() {
        switch (cardClass) {
            case CLASS_10: return capacity / 3;
            case CLASS_6: return capacity / 5;
            case CLASS_4: return capacity / 13;
            default: return 0;
        }
    }

    // Getter methods
    public String getManufacturer() { return manufacturer; }
    public int getCapacity() { return capacity; }
    public MemoryCardClass getCardClass() { return cardClass; }

    @Override
    public String toString() {
        return "MemoryCard[Manufacturer: " + manufacturer + ", Capacity: " + capacity + "GB, Class: " + cardClass + "]";
    }
}

// Abstract Product class
abstract class Product {
    protected String productCode;
    protected double price;
    protected int yearOfManufacture;

    public Product(String productCode, double price, int yearOfManufacture) {
        this.productCode = productCode;
        this.price = price;
        this.yearOfManufacture = yearOfManufacture;
    }

    public abstract double getSuitability();
}

// Dictaphone class extending Product
class Dictaphone extends Product {
    private String manufacturer;
    private String model;
    private int batteryCapacity;
    private MemoryCard memoryCard;

    public Dictaphone(String productCode, double price, int yearOfManufacture, String manufacturer, String model, int batteryCapacity, MemoryCard memoryCard) {
        super(productCode, price, yearOfManufacture);
        this.manufacturer = manufacturer;
        this.model = model;
        this.batteryCapacity = batteryCapacity;
        this.memoryCard = memoryCard;
    }

    public Dictaphone(String productCode, double price, int yearOfManufacture, String manufacturer, String model, int batteryCapacity, String cardManufacturer, int cardCapacity, MemoryCardClass cardClass) {
        this(productCode, price, yearOfManufacture, manufacturer, model, batteryCapacity, new MemoryCard(cardManufacturer, cardCapacity, cardClass));
    }

    @Override
    public double getSuitability() {
        int currentYear = Year.now().getValue();
        return (1 - (1.0 / batteryCapacity)) *
               ((memoryCard.determineQuality() * memoryCard.getCapacity()) /
               (100.0 * Math.sqrt(currentYear - yearOfManufacture)));
    }

    // Getter methods
    public String getManufacturer() { return manufacturer; }
    public String getModel() { return model; }
    public int getBatteryCapacity() { return batteryCapacity; }
    public MemoryCard getMemoryCard() { return memoryCard; }

    @Override
    public String toString() {
        return "Dictaphone[Manufacturer: " + manufacturer + ", Model: " + model + ", Battery: " + batteryCapacity + "mAh, Year: " + yearOfManufacture + ", " + memoryCard + "]";
    }
}

// Main class
public class OOP_2022230514 {
    public static void main(String[] args) {
        System.out.println(" Weam Mahjoub , 2022230514, " + java.time.LocalDate.now());
        
        // Creating an array of Dictaphones
        Dictaphone[] dictaphones = new Dictaphone[] {
            new Dictaphone("D1001", 99.99, 2014, "Sony", "D-Voice", 3000, "Sandisk", 64, MemoryCardClass.CLASS_10),
            new Dictaphone("D1002", 129.99, 2016, "Panasonic", "PX900", 2500, "Kingston", 32, MemoryCardClass.CLASS_6),
            new Dictaphone("D1003", 79.99, 2013, "Olympus", "WS-853", 2000, "Samsung", 16, MemoryCardClass.CLASS_4),
            new Dictaphone("D1004", 149.99, 2018, "Zoom", "H1n", 3500, "Lexar", 128, MemoryCardClass.CLASS_10),
            new Dictaphone("D1005", 89.99, 2012, "Tascam", "DR-05X", 1800, "Transcend", 8, MemoryCardClass.CLASS_4)
        };
        
        // Display dictaphones manufactured before 2015
        System.out.println("\nDictaphones manufactured before 2015:");
        for (Dictaphone d : dictaphones) {
            if (d.yearOfManufacture < 2015) {
                System.out.println(d);
            }
        }
        
        // Find and display dictaphone with highest suitability
        Dictaphone best = Arrays.stream(dictaphones).max((d1, d2) -> Double.compare(d1.getSuitability(), d2.getSuitability())).orElse(null);
        
        System.out.println("\nDictaphone with the highest suitability:");
        System.out.println(best);
        
        // Display all dictaphones sorted by suitability
        System.out.println("\nDictaphones sorted by suitability:");
        Arrays.stream(dictaphones)
              .sorted((d1, d2) -> Double.compare(d2.getSuitability(), d1.getSuitability()))
              .forEach(d -> System.out.println(d + " -> Suitability: " + d.getSuitability()));
    }
}
