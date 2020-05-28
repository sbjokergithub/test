package server;

import java.util.*;

public class logSet {
    Set[] s = new Set[3];
    int block_n;

    public logSet() {
        for (int i=0; i<3; i++)
            s[i] = new HashSet<Integer>();
        block_n = 0;
    }

    public int size(int t){
        return s[t].size();
    }

    public void add(int t, int num){
        //System.out.println("add" + t + ":" + num);
        s[t].add(num);
    }

    public void clear(int t){
        s[t].clear();
    }

    public void clearAll(){
        for (int i=0; i<3; i++)
            s[i].clear();
        this.addBlock();
    }

    public void addBlock(){
        block_n += 1;
    }


}
