package com.example.czettergbor.nagyhf_mobweb.data;

import java.util.Date;

public class Goal {
    Date goalDate;
    int balanceGoal;

    public Goal(){}
    public Goal(Date _goalDate, int _balanceGoal) {

        goalDate = _goalDate;
        balanceGoal = _balanceGoal;

    }

    public int getBalanceGoal() {
        return balanceGoal;
    }

    public Date getGoalDate() {
        return goalDate;
    }

    public void setBalanceGoal(int balanceGoal) {
        this.balanceGoal = balanceGoal;
    }

    public void setGoalDate(Date goalDate) {
        this.goalDate = goalDate;
    }
}
