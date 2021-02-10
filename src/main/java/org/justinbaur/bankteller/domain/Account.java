package org.justinbaur.bankteller.domain;

/**
 * Account POJO. Accounts are part of the profile and hold a balance.
 */
public class Account {

    private String accountName;
    private String accountType;
    private Integer balance;

    public Account() {

    }

    public Account(String accountName, String accountType, Integer balance) {
        this.accountName = accountName;
        this.accountType = accountType;
        this.balance = balance;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account [accountName=" + accountName + ", accountType=" + accountType + ", balance=" + balance + "]";
    }

}
