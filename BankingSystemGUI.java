import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class BankAccount {
    private int accountNumber;
    private double balance;

    public BankAccount(int accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
        } else {
            JOptionPane.showMessageDialog(null, "Insufficient funds!");
        }
    }

    public void transfer(BankAccount destinationAccount, double amount) {
        if (balance >= amount) {
            withdraw(amount);
            destinationAccount.deposit(amount);
            JOptionPane.showMessageDialog(null, "Transfer successful. New balance: " + getBalance());
        } else {
            JOptionPane.showMessageDialog(null, "Insufficient funds for transfer!");
        }
    }
}

public class BankingSystemGUI extends JFrame {
    private BankAccount currentAccount;
    private BankAccount destinationAccount;

    private JTextField accountField;
    private JTextArea resultArea;

    public BankingSystemGUI() {
        setTitle("Login");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initializeLoginComponents();

        setVisible(true);
    }

    //set log-in UI, and set the initial balance to 1000
    private void initializeLoginComponents() {
        JPanel panel = new JPanel(new GridLayout(2, 2));

        JLabel accountLabel = new JLabel("Enter Your Account Number:");
        accountField = new JTextField();

        JButton loginButton = new JButton("Login");
        JButton exitButton = new JButton("Exit");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int accountNumber = Integer.parseInt(accountField.getText());
                currentAccount = new BankAccount(accountNumber, 1000.0); // Example account
                initializeBankingComponents();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        panel.add(accountLabel);
        panel.add(accountField);
        panel.add(loginButton);
        panel.add(exitButton);

        add(panel, BorderLayout.CENTER);
    }

    /*set account UI, with 5 buttoms: deposit,withdraw, transfer,check balance and
    exit, and realize their function
     */
    private void initializeBankingComponents() {
        setTitle("Banking System");
        setSize(400, 300);
        getContentPane().removeAll();
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(0, 1)); // Display buttons in a single column

        JLabel accountLabel = new JLabel("Your Account Number:");
        accountField = new JTextField();
        accountField.setText(String.valueOf(currentAccount.getAccountNumber()));
        accountField.setEditable(false);

        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdraw");
        JButton transferButton = new JButton("Transfer");
        JButton balanceButton = new JButton("Check Balance");
        JButton exitButton = new JButton("Exit");

        resultArea = new JTextArea();
        resultArea.setEditable(false);

        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performDeposit();
            }
        });

        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performWithdraw();
            }
        });

        transferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performTransfer();
            }
        });

        balanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayBalance();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        panel.add(accountLabel);
        panel.add(accountField);
        panel.add(depositButton);
        panel.add(withdrawButton);
        panel.add(transferButton);
        panel.add(balanceButton);
        panel.add(exitButton);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    // create a dialogue window to let user deposit
    private void performDeposit() {
        String depositAmount = JOptionPane.showInputDialog(null, "Enter deposit amount:");
        if (depositAmount != null && !depositAmount.isEmpty()) {
            double amount = Double.parseDouble(depositAmount);
            currentAccount.deposit(amount);
            resultArea.setText("Deposit successful. New balance: " + currentAccount.getBalance());
        }
    }

    //create a dialogue window to let user withdraw
    private void performWithdraw() {
        String withdrawAmount = JOptionPane.showInputDialog(null, "Enter withdrawal amount:");
        if (withdrawAmount != null && !withdrawAmount.isEmpty()) {
            double amount = Double.parseDouble(withdrawAmount);
            currentAccount.withdraw(amount);
            resultArea.setText("Withdrawal successful. New balance: " + currentAccount.getBalance());
        }
    }

    // create a dialogue window to let user transfer money
    private void performTransfer() {
        String recipientAccountNumber = JOptionPane.showInputDialog(null, "Enter recipient's account number:");
        if (recipientAccountNumber != null && !recipientAccountNumber.isEmpty()) {
            int recipientAccount = Integer.parseInt(recipientAccountNumber);
            destinationAccount = new BankAccount(recipientAccount, 0); // Example destination account
            String transferAmount = JOptionPane.showInputDialog(null, "Enter transfer amount:");
            if (transferAmount != null && !transferAmount.isEmpty()) {
                double amount = Double.parseDouble(transferAmount);
                if (amount > 0 && amount <= currentAccount.getBalance()) {
                    currentAccount.transfer(destinationAccount, amount);
                    // The result message is displayed within the transfer method to avoid duplicate messages.
                }else{
                    JOptionPane.showMessageDialog(null, "Insufficient funds. ");
                    }
                }
        }
    }


    private void displayBalance() {
        resultArea.setText("Current balance: " + currentAccount.getBalance());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BankingSystemGUI());
    }
}
