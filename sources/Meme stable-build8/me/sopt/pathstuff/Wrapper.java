package me.sopt.pathstuff;

import java.text.*;

public class Wrapper {
	   public static int removeDecimals(final double number) {
	        final DecimalFormat decimalFormat = new DecimalFormat("####################################");
	        return Integer.parseInt(decimalFormat.format(number));
	    }
}
