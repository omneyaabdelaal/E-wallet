package services;

import models.Account;

import java.util.Scanner;

public class ApplicationServiceImpl implements ApplicationService {
    private final AccountServiceImpl accountService;
    private final Scanner scanner;
    private Account loggedInUser;
    private final ValidationService validationService;

    public ApplicationServiceImpl(AccountServiceImpl accountService, Scanner scanner, ValidationService validationService) {
        this.accountService = accountService;
        this.scanner = scanner;
        this.validationService = validationService;
    }

    @Override
    public void run() {
        System.out.println("\t\tWelcome to E-wallet Cash\t\t\n");

        // 1.TODO please apply multi choose for 4 times
        System.out.println("Choose the operation you want to perform");
        System.out.println("a.login     b.signup   c.exit");
        int maxAttemps = 4;
        while (maxAttemps != 0) {
            char choose = scanner.nextLine().charAt(0);
            choose = Character.toLowerCase(choose);
            if (choose == 'a') {
                login();
                break;
            } else if (choose == 'b') {
                signup();
                break;
            } else if (choose == 'c') {
                System.out.println("Exiting...");
                System.out.println("Thank you for using E-wallet Cash.");
                System.exit(0);
            } else {
                System.out.println("Invalid Choice, please enter 'a' for login, 'b' for signup or 'c' for exit ");
                maxAttemps--;
            }
            if (maxAttemps == 0) {
                System.out.println("You've exceeded the maximum number of attempts. Try again later.");
                System.exit(0);
            }

        }
    }

/// ///////////////////////

    /// /////////////////////////////////////

    private void signup() {
        System.out.println("Please Enter User name");
        String name = scanner.nextLine().trim();

        System.out.println("Please Enter password");
        String password = scanner.nextLine().trim();

        // 2.TODO Validation on UserName and Password
        if (!validationService.validateUserName(name)) { // "eslam"
            System.out.println("Invalid UserName");
            System.out.println("UserName length must be > 3 characters starting with capital letter");
            run();
        }

        if (!validationService.validatePassword(password)) {
            System.out.println("Password is invalid. It must contain:");
            System.out.println("- At least one capital letter, one small letter, one digit, and one special character");
            System.out.println("- A length of the password must be at least 6");
            run();
        }


        // 3.TODO SERVICE OF ACCOUNT TO CREATE ACCOUNT
        Account account = new Account(name, password);
        // 4.TODO   impl createAccount
        boolean isAccountCreated = accountService.createAccount(account);
        if (isAccountCreated) {
            System.out.println("\nAccount is created successfully.\n");
            loggedInUser = account;
            services();
        } else {
            System.out.println("This UaerName already exists\n\n");
            run();
        }
    }

    /// /////////////////////////

    private void login() {
        System.out.println("Please Enter User name");
        String name = scanner.nextLine().trim();

        System.out.println("Please Enter password");
        String password = scanner.nextLine().trim();

        // 2.TODO Validation on UserName and Password
        if (!validationService.validateUserName(name)) { // "eslam"
            System.out.println("Invalid UserName");
            System.out.println("UserName length must be > 3 characters starting with capital letter");
            run();
        }

        if (!validationService.validatePassword(password)) {
            System.out.println("Password is invalid. It must contain:");
            System.out.println("- At least one capital letter, one small letter, one digit, and one special character");
            System.out.println("- A length of the password must be at least 6");
            run();
        }


        // 8.TODO SERVICE OF ACCOUNT TO LOGIN
        if (accountService.loginAccount(new Account(name, password))) {
            System.out.println("Login Success");
            //created this object to keep track of currently logged user
            loggedInUser = accountService.getLoggedInAcc();
            services();
        } else {
            System.out.println("This account is not Exist\n\n");
            run();
        }
    }

    /// /////////////////////////////

    private void services() {

        // TODO create switch case such as on run function
        // TODO every case on switch call function  don't forget (Invalid choose)

        int maxAttempts = 7;
        while (true) {
            System.out.println("\t\tWelcome to our services\t\t");
            System.out.println("1.Deposit   2.Withdraw    3.Show Details    4.Transfer    5.Show Balance   6.Exit   7.Logout");
            char choose = scanner.nextLine().charAt(0);

            if (choose < '1' || choose > '7') {
                maxAttempts--;
                System.out.println("Invalid Choice. You have " + maxAttempts + " attempts left.");

                if (maxAttempts == 0) {
                    System.out.println("You've exceeded the maximum number of attempts. Try again later.");
                    return;
                }
                continue;
            }

            maxAttempts = 7;

            switch (choose) {
                case '1':
                    deposit(loggedInUser);
                    return;
                case '2':
                    withdraw(loggedInUser);
                    return;
                case '3':
                    showDetails(loggedInUser);
                    return;
                case '4':
                    transfer(loggedInUser);
                    return;
                case '5':
                    showBalance(loggedInUser);
                    return;
                case '6':
                    System.out.println("Exiting...");
                    System.out.println("Thank you for using E-wallet Cash.");
                    System.exit(0);
                case '7':
                    System.out.println("Logging out...");
                    run();
                    return;
            }
        }

    }

    /// /////////////////////////////////
    // TODO create deposit function
    private void deposit(Account a) {
        // input int money
        // TODO pls validate money >= 100 and <= 20000

        while (true) {
            System.out.println("\nPlease enter deposit amount between 100 and 20000 or inter 'c' for cancellation");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("c")) {
                System.out.println("\nDeposit canceled. back to services menu");
                services();
                break;
            }

            try {
                double depositAmount = Double.parseDouble(input);
                if (depositAmount < 100 || depositAmount > 20000) {
                    System.out.println("\nInvalid amount. Deposit must be between 100 and 20000.\n");

                    continue;
                }
                if (accountService.deposit(depositAmount, a)) {
                    System.out.println("\nDeposit successful! Amount: $" + depositAmount + "\n");
                }
                services();
                break;
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid input! Please enter a valid number.\n");
            }
        }

    }


    /// ////////////////////////////////////
    // TODO create Withdraw function
    void withdraw(Account a) {
        // input int money
        // TODO pls validate money >= 100 and <= 8000
        while (true) {
            System.out.println("\nPlease enter withdraw amount between 100 and 8000 or inter 'c' for cancellation");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("c")) {
                System.out.println("\nWithdraw canceled. welcome back to services menu\n");
                services();
                break;
            }
            try {
                double withdrawAmount = Double.parseDouble(input);
                if (withdrawAmount < 100 || withdrawAmount > 8000) {
                    System.out.println("\nInvalid amount. withdraw must be between 100 and 8000.\n");
                    continue;
                }
                if (accountService.withdraw(withdrawAmount, a)) {
                    System.out.println("\nWithdraw successful! Amount: $" + withdrawAmount + "\n");
                    System.out.println("Welcome back to services menu");
                }
                services();
                break;

            } catch (NumberFormatException e) {
                System.out.println("\nInvalid input! Please enter a valid number.\n");

            }
        }
    }

    /// ///////////////////////
    private void handleUserChoice() {
        System.out.println("Enter 'b' to back to services menu or 'e' to exit ");
        while (true) {
            String input = scanner.nextLine().trim();  // Read input and remove spaces
            if (input.isEmpty()) {  // Prevent empty input errors
                System.out.println("Invalid choice. Please enter 'b' to go back or 'e' to exit.");
                continue;
            }
            char choose = Character.toLowerCase(input.charAt(0));
            switch (Character.toLowerCase(choose)) {
                case 'b':
                    services();
                    return;
                case 'e':
                    System.out.println("Exiting...");
                    System.out.println("Thank you for using E-wallet Cash");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice please enter 'b' to back to services menu or 'e' to exit ");
            }
        }
    }

    /// //////////////////////

    void showDetails(Account a) {
        System.out.println("\n\t\tAccount Details\t\t");
        System.out.println("Name: " + a.getUserName());
        System.out.println("Status: " + a.getActive());
        System.out.println("Balance: $" + a.getBalance() + "\n");
        handleUserChoice();

    }
    // TODO USER MUST give me user name of account that will transfer
    // TODO input Account depositAccount
    // TODO input int money
    void transfer(Account withdrawAccount) {
        while (true) {

            try {
                System.out.println("Please enter user_name of the account you want to transfer the amount to");
                String userName = scanner.nextLine().trim();
                if (!validationService.validateUserName(userName)) {
                    System.out.println("\nInvalid user name.\n");
                    continue;
                }
                System.out.println("\nPlease enter amount between 100 and 20000");
                String input = scanner.nextLine().trim();
                double transferAmount = Double.parseDouble(input);
                if (transferAmount < 100 || transferAmount > 20000) {
                    System.out.println("\nInvalid amount. it must be between 100 and 20000.\n");
                    continue;
                }

                    if (accountService.transfer(withdrawAccount, userName, transferAmount)) {
                        System.out.println("Successful transfer");
                        System.out.println("The amount " + transferAmount + " has been deposited into the account: " + userName);
                        System.out.println("Your current balance is: " + withdrawAccount.getBalance());
                    }else{
                        System.out.println("Your balance doesn't cover the transfer amount");
                    }

                services();
                break;
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid input! Please enter a valid number.\n");
            }
        }

    }


    void showBalance(Account a) {
        System.out.println("\n\t\tYour Balance Details\t\t");
        System.out.println("Balance: $" + a.getBalance() + "\n");
        handleUserChoice();
    }


}



