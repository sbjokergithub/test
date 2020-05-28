package server;

import java.io.Serializable;

public class Transaction implements Serializable {
    int trade_initiator, trade_taker, money;

    public Transaction(int trade_initiator, int trade_taker, int money) {
        this.trade_initiator = trade_initiator;
        this.trade_taker = trade_taker;
        this.money = money;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "name0=" + trade_initiator +
                ", name1=" + trade_taker +
                ", money=" + money +
                '}';
    }
}
