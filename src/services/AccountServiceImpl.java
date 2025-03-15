package services;

import models.Account;
import models.Ewallet;

import java.util.Objects;

public class AccountServiceImpl implements AccountService {

    private final Ewallet ewallet = Ewallet.getInstance();
    private Account loggedInAcc=null;
    @Override
    public boolean createAccount(Account account) {
        // 5.TODO get List of Account on Ewallet and make sure that not any account with same user name
        // 6.TODO if not exist any account not has same username add account and return true
        // 7.TODO else return false

        for (Account acc : ewallet.getAccounts()) {
            if (Objects.equals(acc.getUserName(), account.getUserName())) {
                return false;
            }
        }
        ewallet.getAccounts().add(account);
        return true;
    }
    /// ////



    @Override
    public boolean loginAccount(Account account) {
        // TODO get List of Account on Ewallet and make sure that exist account with same user name and password
        // TODO if exist any account  has same username and password return true
        // TODO else return false
        for (Account acc : ewallet.getAccounts()) {
            if (Objects.equals(acc.getUserName(), account.getUserName()) &&
                    Objects.equals(acc.getPassword(), account.getPassword())) {
                    loggedInAcc=acc;
                return true;
            }
        }
        return false;

    }
    /// ///
    Account getLoggedInAcc(){
        return loggedInAcc;
    }
    /// //////////
    private boolean checkAccountStatus(Account user){
        if (!user.getActive()){
            System.out.println("\nYour account is inactive. Please contact customer service to activate it.");
            System.out.println("Welcome back to services menu.");
            return false;
        }
        return true;
    }


    // TODO create function with name deposit that return
    // TODO true if deposit success
    // TODO false if deposit fail
    // TODO check if account exist on wallet or not if not print account not exist
    // TODO check if account is active or not  if not print account not active
    // TODO make deposit

    public boolean deposit(double depositAmount, Account loggedInAccount) {
      /*if (!loggedInAccount.getActive()) {
            return false;
        }
        loggedInAccount.setBalance(loggedInAccount.getBalance()+depositAmount);
        return true;*/

        if(checkAccountStatus(loggedInAccount))
        {
            loggedInAccount.setBalance(loggedInAccount.getBalance()+depositAmount);
            return true;
        }

        return false;

    }

        // TODO without duplication
        // TODO make withdraw
        // TODO create function with name withdraw that return
        // TODO true if withdraw success
        // TODO false if withdraw fail
        // TODO check if account exist on wallet or not if not print account not exist
        // TODO check if account is active or not  if not print account not active
        // TODO check if account balance is greater than  money if not print can't deposit because ....
        // TODO make without
   public boolean withdraw(double withdrawAmount,Account loggedInUser){
        if(checkAccountStatus(loggedInUser)){
            if(loggedInUser.getBalance()>=withdrawAmount){
                loggedInUser.setBalance(loggedInUser.getBalance()-withdrawAmount);
                return true;

            }else{
                System.out.println("\nInvalid withdraw your balance is less than $"+withdrawAmount+"\n");
                return false;
            }
        }
        return false;
   }


        // Transfer Account depositAccount, Account withdrawAccount, int money
        // TODO without duplication
        // TODO make Transfer
        // TODO create function with name transfer that return
        // TODO true if transfer success
        // TODO false if transfer fail
        // TODO check if depositAccount and withdrawAccount exist on wallet or not if not print account not exist
        // TODO check if depositAccount and withdrawAccount is active or not  if not print account not active
        // TODO check if withdrawAccount balance is greater than money if not print can't deposit because ....

    public boolean transfer(Account loggedInUser ,String userName, double transferAmount) {
    for (Account acc : ewallet.getAccounts()) {
        if (Objects.equals(acc.getUserName(), userName) &&loggedInUser.getBalance()>=transferAmount) {
           acc.setBalance(acc.getBalance()+transferAmount);
           loggedInUser.setBalance(loggedInUser.getBalance()-transferAmount);
           return true;
        }
    }
      return false;
}
        // TODO SHOW Account Details



        // TODO SHOW show Balance
}
