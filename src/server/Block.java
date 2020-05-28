package server;

import java.io.*;
import java.util.*;

public class Block implements Serializable {
    int number;
    int k;
    String s;
    List list;
    int block_n;

    public Block(int number, int k, String s, List list, int block_n) {
        this.number = number;
        this.k = k;
        this.s = s;
        this.list = list;
        this.block_n = block_n;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getBlock_n() {
        return block_n;
    }

    public void setBlock_n(int block_n) {
        this.block_n = block_n;
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }


    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }
}
