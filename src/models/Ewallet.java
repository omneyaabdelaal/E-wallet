package models;

import java.util.ArrayList;
import java.util.List;


// pls apply singleton
public class Ewallet {
    private static final Ewallet ewallet=new Ewallet();
    private String name = "EraaSoft Cash";

    private List<Account> accounts = new ArrayList<>();

    private  Ewallet(){};

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public static Ewallet getInstance(){
        return ewallet;
    }
}