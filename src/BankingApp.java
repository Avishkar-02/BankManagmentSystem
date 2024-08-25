import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class BankingApp {

    private static final String url = "jdbc:mysql://localhost:3306/banking_system";
    private static final String username = "root";
    private static final String password = "root";


    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.print(e.getMessage());
        }

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Scanner scanner = new Scanner(System.in);
            User user = new User(connection, scanner);
            Accounts accounts = new Accounts(connection, scanner);
            AccountManager accountManager = new AccountManager(connection, scanner);

            String email;
            long account_number;

            while (true) {
                System.out.println("Welcome to the banking system ");
                System.out.println();
                System.out.println("1.Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print("Enter Your Choice= ");
                int choice1 = scanner.nextInt();

                switch (choice1) {
                    case 1:
                        user.register();
                        break;
                    case 2:
                        email = user.login();
                        if (email != null) {
                            System.out.println();
                            System.out.println("User Successfully logged in ");

                            if (!accounts.account_exist(email)) {
                                System.out.println();
                                System.out.println("1.Open a new bank account");
                                System.out.println("2.Exit");
                                if (scanner.nextInt() == 1) {
                                    account_number = accounts.open_account(email);
                                    System.out.println("Account Created Successfully");
                                    System.out.println("Your account number is " + account_number);
                                } else {
                                    break;
                                }

                            }
                        } else {
                            System.out.println("Incorrect Email or password");
                        }

                        account_number = accounts.getAccount_number(email);
                        int choice2=0;
                        while ( choice2 != 5) {
                            System.out.println();
                            System.out.println("1.Debit Money");
                            System.out.println("2.Credit Money");
                            System.out.println("3.Transfer Money");
                            System.out.println("4.Check balance");
                            System.out.println("5.Exit ");
                            System.out.print("Enter Your Choice = ");
                             choice2 = scanner.nextInt();

                            switch (choice2) {
                                case 1:
                                    accountManager.debit_money(account_number);
                                    break;
                                case 2:
                                    accountManager.credit_money(account_number);
                                    break;
                                case 3:
                                    accountManager.transfer_money(account_number);
                                    break;
                                case 4:
                                    accountManager.getBalance(account_number);
                                    break;
                                case 5:
                                    break;
                                default:
                                    System.out.println("Enter valid Choice");
                                    break;

                            }
                        }

                    case 3:
                        System.out.println("Thank for using the banking system");
                        System.out.println("exiting the System");
                        break;
                    default:
                        System.out.println("Enter valid choice");
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
