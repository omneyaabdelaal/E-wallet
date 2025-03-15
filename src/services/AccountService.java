package services;

import models.Account;

public interface AccountService {

    boolean createAccount(Account account);
    boolean loginAccount(Account account);
}
