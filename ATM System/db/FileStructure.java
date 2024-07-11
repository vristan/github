import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileStructure {
    private static final String FILE_NAME = "C:\\Users\\Lenovo\\Desktop\\github\\ATM System\\db\\accounts.txt";

    // Function to read accounts from file
    public static List<Account> readAccounts() {
        List<Account> accounts = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String accountNumber = parts[0];
                    String pin = parts[1];
                    double balance = Double.parseDouble(parts[2]);
                    Account account = new Account(accountNumber, pin, balance);
                    accounts.add(account);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading accounts file: " + e.getMessage());
        }
        return accounts;
    }

    // Function to write accounts to file
    public static void writeAccounts(List<Account> accounts) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Account account : accounts) {
                writer.write(account.getAccountNumber() + "," + account.getPin() + "," + account.getBalance() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing accounts file: " + e.getMessage());
        }
    }
}
