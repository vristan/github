import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ATMSystem extends JFrame implements ActionListener {
    private List<Account> accounts;
    private JTextField accountField, pinField, amountField;
    private JLabel messageLabel, balanceLabel;

    public ATMSystem() {
        // Load accounts from file
        accounts = FileStructure.readAccounts();

        setTitle("ATM System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        add(mainPanel);

        // Account number panel
        JPanel accountPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        accountPanel.add(new JLabel("Account Number:"));
        accountField = new JTextField(15);
        accountPanel.add(accountField);
        mainPanel.add(accountPanel);

        // PIN panel
        JPanel pinPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pinPanel.add(new JLabel("PIN:"));
        pinField = new JTextField(15);
        pinPanel.add(pinField);
        mainPanel.add(pinPanel);

        // Action buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        buttonPanel.add(loginButton);

        JButton createAccountButton = new JButton("Create Account");
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String accountNumber = accountField.getText();
                String pin = pinField.getText();
                // Check if account already exists
                boolean accountExists = false;
                for (Account acc : accounts) {
                    if (acc.getAccountNumber().equals(accountNumber)) {
                        accountExists = true;
                        break;
                    }
                }
                if (accountExists) {
                    messageLabel.setText("Account already exists.");
                } else {
                    Account newAccount = new Account(accountNumber, pin, 0);
                    accounts.add(newAccount);
                    FileStructure.writeAccounts(accounts);
                    messageLabel.setText("New account created successfully.");
                }
            }
        });
        buttonPanel.add(createAccountButton);

        mainPanel.add(buttonPanel);

        // Message label
        messageLabel = new JLabel("Enter account number and PIN to login or create account.");
        mainPanel.add(messageLabel);

        // Balance display panel
        JPanel balancePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        balanceLabel = new JLabel("");
        balancePanel.add(balanceLabel);
        mainPanel.add(balancePanel);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String accountNumber = accountField.getText();
        String pin = pinField.getText();

        // Find account in the list
        Account currentAccount = null;
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber) && account.getPin().equals(pin)) {
                currentAccount = account;
                break;
            }
        }

        if (currentAccount != null) {
            showTransactionMenu(currentAccount);
        } else {
            messageLabel.setText("Invalid account number or PIN.");
        }
    }

    // Display transaction menu
    private void showTransactionMenu(Account account) {
        JFrame transactionFrame = new JFrame("ATM System - " + account.getAccountNumber());
        transactionFrame.setSize(400, 300);
        transactionFrame.setLocationRelativeTo(null);

        JPanel transactionPanel = new JPanel();
        transactionPanel.setLayout(new BoxLayout(transactionPanel, BoxLayout.Y_AXIS));
        transactionFrame.add(transactionPanel);

        JLabel nameLabel = new JLabel("Welcome, " + account.getAccountNumber());
        transactionPanel.add(nameLabel);

        JPanel amountPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        amountPanel.add(new JLabel("Amount:"));
        amountField = new JTextField(15);
        amountPanel.add(amountField);
        transactionPanel.add(amountPanel);

        JPanel transactionButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton checkBalanceButton = new JButton("Check Balance");
        checkBalanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double balance = account.getBalance();
                balanceLabel.setText("Current Balance: $" + balance);
            }
        });
        transactionButtonPanel.add(checkBalanceButton);

        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double amount = Double.parseDouble(amountField.getText());
                    if (amount > 0 && amount <= account.getBalance()) {
                        account.setBalance(account.getBalance() - amount);
                        FileStructure.writeAccounts(accounts);
                        balanceLabel.setText("Withdrawn: $" + amount);
                    } else {
                        balanceLabel.setText("Invalid amount or insufficient balance.");
                    }
                } catch (NumberFormatException ex) {
                    balanceLabel.setText("Invalid amount format.");
                }
            }
        });
        transactionButtonPanel.add(withdrawButton);

        JButton depositButton = new JButton("Deposit");
        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double amount = Double.parseDouble(amountField.getText());
                    if (amount > 0) {
                        account.setBalance(account.getBalance() + amount);
                        FileStructure.writeAccounts(accounts);
                        balanceLabel.setText("Deposited: $" + amount);
                    } else {
                        balanceLabel.setText("Invalid amount.");
                    }
                } catch (NumberFormatException ex) {
                    balanceLabel.setText("Invalid amount format.");
                }
            }
        });
        transactionButtonPanel.add(depositButton);

        transactionPanel.add(transactionButtonPanel);

        transactionFrame.setVisible(true);
    }

    public static void main(String[] args) {
        new ATMSystem();
    }
}
