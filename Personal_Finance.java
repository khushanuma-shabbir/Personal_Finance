import java.util.*;

// Base class representing a Transaction
class Transaction {
    protected String description;
    protected double amount;
    protected Date date;
 
    public Transaction(String description, double amount) {
        this.description = description;
        this.amount = amount;
        this.date = new Date(); // Automatically set to current date
    }
 
    public double getAmount() {
        return amount;
    }
 
    public String getDescription() {
        return description;
    }
 
    public Date getDate() {
        return date;
    }
 
    @Override
    public String toString() {
        return "Transaction: " + description + " | Amount: " + amount + " | Date: " + date;
    }
}
 
// Derived class for Expenses
class Expense extends Transaction {
    private final String category;
 
    public Expense(String description, double amount, String category) {
        super(description, amount);
        this.category = category;
    }
 
    public String getCategory() {
        return category;
    }
 
    @Override
    public String toString() {
        return "Expense: " + description + " | Amount: " + amount + " | Category: " + category + " | Date: " + date;
    }
}
 
// Class for Personal Finance Management
class FinanceManager {
    private double balance;
    private final List<Transaction> transactions;
 
    public FinanceManager(double initialBalance) {
        this.balance = initialBalance;
        this.transactions = new ArrayList<>();
    }
 
    public void addExpense(String description, double amount, String category) {
        if (amount > balance) {
            System.out.println("Insufficient balance!");
            return;
        }
        Expense expense = new Expense(description, amount, category);
        transactions.add(expense);
        balance -= amount;
        System.out.println("Expense added successfully!");
    }
 
    public void addIncome(double amount) {
        balance += amount;
        transactions.add(new Transaction("Income", amount));
        System.out.println("Income added successfully!");
    }
 
    public void showTransactions() {
        System.out.println("Transaction History:");
        for (Transaction t : transactions) {
            System.out.println(t.toString());
        }
    }
 
    public void showBalance() {
        System.out.println("Current Balance: " + balance);
    }
 
    public void deleteTransaction(int index) {
        if (index < 0 || index >= transactions.size()) {
            System.out.println("Invalid transaction index!");
            return;
        }
        Transaction removed = transactions.remove(index);
        if (removed instanceof Expense) {
            balance += removed.getAmount();
        } else {
            balance -= removed.getAmount();
        }
        System.out.println("Transaction deleted successfully!");
    }
 
    public void filterTransactionsByCategory(String category) {
        System.out.println("Transactions under category: " + category);
        for (Transaction t : transactions) {
            if (t instanceof Expense && ((Expense) t).getCategory().equalsIgnoreCase(category)) {
                System.out.println(t.toString());
            }
        }
    }
}
 
// Main class
public class PersonalFinanceApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter initial balance: ");
        double initialBalance = scanner.nextDouble();
        FinanceManager manager = new FinanceManager(initialBalance);
 
        while (true) {
            System.out.println("\n1. Add Income");
            System.out.println("2. Add Expense");
            System.out.println("3. View Transactions");
            System.out.println("4. Check Balance");
            System.out.println("5. Delete Transaction");
            System.out.println("6. Filter Transactions by Category");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
 
            switch (choice) {
                case 1 -> {
                    System.out.print("Enter income amount: ");
                    double income = scanner.nextDouble();
                    manager.addIncome(income);
                }
                case 2 -> {
                    System.out.print("Enter expense description: ");
                    String desc = scanner.nextLine();
                    System.out.print("Enter amount: ");
                    double amount = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.print("Enter category: ");
                    String category = scanner.nextLine();
                    manager.addExpense(desc, amount, category);
                }
                case 3 -> manager.showTransactions();
                case 4 -> manager.showBalance();
                case 5 -> {
                    System.out.print("Enter transaction index to delete: ");
                    int index = scanner.nextInt();
                    manager.deleteTransaction(index);
                }
                case 6 -> {
                    System.out.print("Enter category to filter: ");
                    String filterCategory = scanner.nextLine();
                    manager.filterTransactionsByCategory(filterCategory);
                }
                case 7 -> {
                    System.out.println("Exiting application. Goodbye!");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
    }
}  

