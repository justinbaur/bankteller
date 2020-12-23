package org.justinbaur.bankteller.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

public class Account {

    public Integer id;
    public Integer balance;

    public Account(Integer id, Integer balance){
        this.id = id;
        this.balance = balance;
    }

    public Integer getId(){
        return this.id;
    }

    public Integer getBalance(){
        return this.balance;
    }

    public String toString(){
        return "Account [ id: "+id+", balance: "+ balance+ " ]";
     }	
}
