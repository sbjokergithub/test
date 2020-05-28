package server;

import java.util.*;

public class logSet {
//    HashSet[] s = new HashSet[3];
    ArrayList<HashSet<Integer>> s;
    int block_n;

    public logSet() {
        s = new ArrayList<>();
        for (int i=0; i<3; i++) {
            s.add(new HashSet<Integer>());
        }
            //s[i] = new HashSet<Integer>();
        block_n = 0;
    }

    public int size(int t){
        return s.get(t).size();
    }

    public void add(int t, int num){
        //System.out.println("add" + t + ":" + num);
        s.get(t).add(num);
    }

    public void clear(int t){
        s.get(t).clear();
    }

    public void clearAll(){
        for (int i=0; i<3; i++)
            s.get(i).clear();
        this.addBlock();
    }

    public void addBlock(){
        block_n += 1;
    }


}
