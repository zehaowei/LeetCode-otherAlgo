package com.isswhu;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Test t = new Test();
    }
}

class Test {
    ArrayList<Integer> list = new ArrayList<>();
    static int a;
    final int b =3 ;

    void func() {
        list.add(1);
        a = 3;
    }
}
