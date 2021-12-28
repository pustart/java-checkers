package ru.vsu.pustoslov.java.tests;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        Ball s1 = new Ball("1");
        Ball s2 = new Ball("2");
        Ball s3 = new Ball("3");
        Ball s4 = new Ball("4");
        Ball s5 = new Ball("5");

        List<Ball> ballList = new ArrayList<>();

        ballList.add(s1);
        ballList.add(s2);
        ballList.add(s3);
        ballList.add(s4);

        List<Ball> ballList2 = new ArrayList<>(ballList); // =addAll
        List<Ball> ballList3 = new ArrayList<>(ballList2);


        //ballList = null;

        ballList2.get(0).setSize("Bullshit");

        ballList3.remove(2);

        System.out.println(ballList);
        System.out.println(ballList2);
        System.out.println(ballList3);



    }
}
