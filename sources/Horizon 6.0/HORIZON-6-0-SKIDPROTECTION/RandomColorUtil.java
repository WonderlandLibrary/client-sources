package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class RandomColorUtil
{
    public static int HorizonCode_Horizon_È() {
        final int r = new Random().nextInt(15);
        if (r == 0) {
            return -16777046;
        }
        if (r == 1) {
            return -16733696;
        }
        if (r == 2) {
            return -16733526;
        }
        if (r == 3) {
            return -5636096;
        }
        if (r == 4) {
            return -5635926;
        }
        if (r == 5) {
            return -22016;
        }
        if (r == 6) {
            return -5592406;
        }
        if (r == 7) {
            return -11184811;
        }
        if (r == 8) {
            return -11184641;
        }
        if (r == 9) {
            return -11141291;
        }
        if (r == 10) {
            return -11141121;
        }
        if (r == 11) {
            return -43691;
        }
        if (r == 12) {
            return -43521;
        }
        if (r == 13) {
            return -171;
        }
        if (r == 14) {
            return -1;
        }
        return -1;
    }
}
