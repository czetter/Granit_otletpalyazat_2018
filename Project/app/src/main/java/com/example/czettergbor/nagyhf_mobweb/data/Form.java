package com.example.czettergbor.nagyhf_mobweb.data;

import java.io.Serializable;

public class Form implements Serializable {
    String name;
    String accountNum;

    public Form(String _name, String _accNum){
        name = _name;
        accountNum = _accNum;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public String getName() {
        return name;
    }
}
