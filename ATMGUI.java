import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

class ATM {
    private double balance;
    private ArrayList<String> transactions;
    private static final String USER_DATA_FILE = "users.txt";
    
    static {
        try {
            File file = new File(USER_DATA_FILE);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public ATM(double initialBalance) {
        this.balance = initialBalance;
        this.transactions = new ArrayList<>();
    }
    
    public static boolean register(String acc, String pinInput) {
        if (isUserExists(acc)) {
            return false;
        }
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(USER_DATA_FILE, true)))) {
            out.println(acc + "," + pinInput);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
    
    public static boolean authenticate(String acc, String pinInput) {
        try (BufferedReader br = new BufferedReader(new FileReader(USER_DATA_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] credentials = line.split(",");
                if (credentials.length == 2 && credentials[0].equals(acc) && credentials[1].equals(pinInput)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    private static boolean isUserExists(String acc) {
        try (BufferedReader br = new BufferedReader(new FileReader(USER_DATA_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] credentials = line.split(",");
                if (credentials.length == 2 && credentials[0].equals(acc)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}

class ATMGUI {
    private JFrame frame;
    private JTextArea display;
    private JPanel mainPanel;
    
    public ATMGUI() {
        frame = new JFrame("ATM System");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        
        welcomeScreen();
        frame.setVisible(true);
    }
    
    private void welcomeScreen() {
        JPanel panel = new JPanel(new GridLayout(2, 1));
        JButton loginBtn = new JButton("Login");
        JButton signupBtn = new JButton("Sign Up");
        
        panel.add(loginBtn);
        panel.add(signupBtn);
        frame.getContentPane().removeAll();
        frame.add(panel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
        
        loginBtn.addActionListener(e -> loginScreen());
        signupBtn.addActionListener(e -> signupScreen());
    }
    
    private void signupScreen() {
        JPanel panel = new JPanel(new GridLayout(3, 2));
        JLabel accLabel = new JLabel("New Account Number:");
        JTextField accField = new JTextField();
        JLabel pinLabel = new JLabel("New PIN:");
        JPasswordField pinField = new JPasswordField();
        JButton signupBtn = new JButton("Sign Up");
        
        panel.add(accLabel);
        panel.add(accField);
        panel.add(pinLabel);
        panel.add(pinField);
        panel.add(signupBtn);
        
        frame.getContentPane().removeAll();
        frame.add(panel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
        
        signupBtn.addActionListener(e -> {
            String acc = accField.getText().trim();
            String pin = new String(pinField.getPassword()).trim();
            if (acc.isEmpty() || pin.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Account and PIN cannot be empty!");
                return;
            }
            if (ATM.register(acc, pin)) {
                JOptionPane.showMessageDialog(frame, "Sign Up Successful! Please Log In.");
                loginScreen();
            } else {
                JOptionPane.showMessageDialog(frame, "Account already exists! Try again.");
            }
        });
    }
    
    private void loginScreen() {
        JPanel panel = new JPanel(new GridLayout(3, 2));
        JLabel accLabel = new JLabel("Account Number:");
        JTextField accField = new JTextField();
        JLabel pinLabel = new JLabel("PIN:");
        JPasswordField pinField = new JPasswordField();
        JButton loginBtn = new JButton("Login");
        
        panel.add(accLabel);
        panel.add(accField);
        panel.add(pinLabel);
        panel.add(pinField);
        panel.add(loginBtn);
        
        frame.getContentPane().removeAll();
        frame.add(panel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
        
        loginBtn.addActionListener(e -> {
            String acc = accField.getText().trim();
            String pin = new String(pinField.getPassword()).trim();
            if (acc.isEmpty() || pin.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Account and PIN cannot be empty!");
                return;
            }
            if (ATM.authenticate(acc, pin)) {
                JOptionPane.showMessageDialog(frame, "Login Successful!");
                showATMMenu();
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid Credentials! Try Again.");
            }
        });
    }
    
    private void showATMMenu() {
        mainPanel = new JPanel(new BorderLayout());
        display = new JTextArea();
        display.setEditable(false);
        mainPanel.add(new JScrollPane(display), BorderLayout.CENTER);
        
        JPanel panel = new JPanel(new GridLayout(3, 2));
        JButton exitBtn = new JButton("Exit");
        panel.add(exitBtn);
        mainPanel.add(panel, BorderLayout.SOUTH);
        
        exitBtn.addActionListener(e -> frame.dispose());
        
        frame.getContentPane().removeAll();
        frame.add(mainPanel);
        frame.revalidate();
        frame.repaint();
    }
    
    public static void main(String[] args) {
        new ATMGUI();
    }
}