package me.vaziak.sensation.utils.math;

import java.util.Random;

public class PatternUtil {

    public double[] pattern;

    private int currentPlace = -1;



    public PatternUtil(double... value) {

        pattern = value;

    }


    public double nextValue() {

        if(currentPlace >= pattern.length - 1) {

            currentPlace = 0;

        } else {

            currentPlace++;

        }

        return pattern[currentPlace];

    }


    public double randomValue() {

        Random random = new Random();

        if(currentPlace >= pattern.length - 1) {

            currentPlace = 0;

        } else {

            currentPlace++;

        }

        return pattern[random.nextInt(pattern.length - 1)];

    }


    public int getCurrentPlace() {

        return currentPlace;

    }

}