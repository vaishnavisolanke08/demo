import java.io.*;
import java.util.*;

// ===== Stock Class =====
class Stock {
    String symbol;
    double price;

    Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }
}

// ===== Market Class =====
class Market {
    HashMap<String, Stock> stocks = new HashMap<>();

    Market() {
        stocks.put("TCS", new Stock("TCS", 3500));
        stocks.put("INFY", new Stock("INFY", 1500));
        stocks.put("RELIANCE", new Stock("RELIANCE", 2800));
    }

    void displayMarket() {
        System.out.println("\n📈 Market Data");
        for (Stock s : stocks.values()) {
            System.out.println(s.symbol + " : ₹" + s.price);
        }
    }
}

// ===== Portfolio Class =====
class Portfolio {
    HashMap<String, Integer> holdings = new HashMap<>();

    void buy(String symbol, int qty) {
        holdings.put(symbol, holdings.getOrDefault(symbol, 0) + qty);
    }

    boolean sell(String symbol, int qty) {
        if (holdings.containsKey(symbol) && holdings.get(symbol) >= qty) {
            holdings.put(symbol, holdings.get(symbol) - qty);
            return true;
        }
        return false;
    }

    void display() {
        System.out.println("\n📊 Portfolio");
        if (holdings.isEmpty()) {
            System.out.println("No stocks owned");
        }
        for (String s : holdings.keySet()) {
            System.out.println(s + " -> " + holdings.get(s) + " shares");
        }
    }
}

// ===== User Class =====
class User {
    String name;
    double balance;
    Portfolio portfolio;

    User(String name, double balance) {
        this.name = name;
        this.balance = balance;
        this.portfolio = new Portfolio();
    }
}

// ===== Main Class =====
public class StockTrading{

    // Calculate portfolio value
    static double portfolioValue(User user, Market market) {
        double total = 0;
        for (String s : user.portfolio.holdings.keySet()) {
            total += market.stocks.get(s).price * user.portfolio.holdings.get(s);
        }
        return total;
    }

    // Save portfolio to file (Optional)
    static void saveToFile(User user) {
        try {
            FileWriter fw = new FileWriter("portfolio.txt");
            fw.write("User: " + user.name + "\n");
            fw.write("Balance: " + user.balance + "\n");
            fw.write("Portfolio: " + user.portfolio.holdings.toString());
            fw.close();
        } catch (Exception e) {
            System.out.println("Error saving file");
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Market market = new Market();

        System.out.print("Enter User Name: ");
        User user = new User(sc.nextLine(), 100000);

        int choice;
        do {
            System.out.println("\n===== STOCK TRADING MENU =====");
            System.out.println("1. View Market Data");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. View Portfolio Value");
            System.out.println("6. Save & Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    market.displayMarket();
                    break;

                case 2:
                    System.out.print("Enter Stock Symbol: ");
                    String buyStock = sc.next();
                    System.out.print("Enter Quantity: ");
                    int buyQty = sc.nextInt();

                    if (market.stocks.containsKey(buyStock)) {
                        double cost = market.stocks.get(buyStock).price * buyQty;
                        if (user.balance >= cost) {
                            user.balance -= cost;
                            user.portfolio.buy(buyStock, buyQty);
                            System.out.println("✅ Stock Purchased");
                        } else {
                            System.out.println("❌ Insufficient Balance");
                        }
                    } else {
                        System.out.println("❌ Stock Not Found");
                    }
                    break;

                case 3:
                    System.out.print("Enter Stock Symbol: ");
                    String sellStock = sc.next();
                    System.out.print("Enter Quantity: ");
                    int sellQty = sc.nextInt();

                    if (market.stocks.containsKey(sellStock)
                            && user.portfolio.sell(sellStock, sellQty)) {
                        user.balance += market.stocks.get(sellStock).price * sellQty;
                        System.out.println("✅ Stock Sold");
                    } else {
                        System.out.println("❌ Cannot Sell Stock");
                    }
                    break;

                case 4:
                    user.portfolio.display();
                    System.out.println("💰 Balance: ₹" + user.balance);
                    break;

                case 5:
                    System.out.println("📈 Portfolio Value: ₹" +
                            portfolioValue(user, market));
                    break;

                case 6:
                    saveToFile(user);
                    System.out.println("Data Saved. Exiting...");
                    break;

                default:
                    System.out.println("Invalid Choice");
            }

        } while (choice != 6);

        sc.close();
    }
}
