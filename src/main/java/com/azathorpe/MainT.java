package com.azathorpe;

import com.azathorpe.utils.struct.list.IntArray;

import java.util.stream.IntStream;

public class MainT {
    public static void main(String[] args) {
        IntArray ia = new IntArray();
        IntStream.range(0, 10).forEach(ia::add);
        for(int i = 0;i < ia.size();i++) {
            System.out.println(ia.get(i));
        }

        ia.removeAt(0);
        System.out.println("_--____---_--");

        for(int i = 0;i < ia.size();i++) {
            System.out.println(ia.get(i));
        }

    }
}
