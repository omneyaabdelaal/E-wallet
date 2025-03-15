import services.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final AccountServiceImpl accountService = new AccountServiceImpl();
        final Scanner scanner = new Scanner(System.in);
        final ValidationService validationService=new ValidationServiceImpl();
        new ApplicationServiceImpl(accountService,scanner,validationService).run();

    }
}
