package us.dev.direkt.util.math;

import java.util.Random;

public final class MathUtils {
	
	private static final Random rng = new Random();
	
	private MathUtils() {}
	
	public static Random getRng() {
		return rng;
	}
	
    /**
     * Gets a random float number.
     * @return
     */
    public static float getRandom() {
    	return rng.nextFloat();
    }
    
    /**
     * Gets a random number between 0 and a specified cap
     * @returns A random number between 0 and the cap
     * @param cap at which the number will be limited to
     */
    public static int getRandom(int cap){
        return rng.nextInt(cap);
    }

    /**
     * Gets a random number between a specified floor and cap
     * @param floor
     * @param cap
     * @return
     */
    public static int getRandom(int floor, int cap){
        return floor +  rng.nextInt(cap - floor + 1);
    }

    /**
	 * Returns a pseudo-random number between min and max, inclusive.
	 * The difference between min and max can be at most
	 * <code>Integer.MAX_VALUE - 1</code>.
	 *
	 * @param min Minimum value
	 * @param max Maximum value.  Must be greater than min.
	 * @return Integer between min and max, inclusive.
	 * @see Random#nextInt(int)
	 */
	public static int randInt(int min, int max) {
	    return new Random().nextInt((max - min) + 1) + min;
	}
    
    /**
     * Clamps a value between a range. If a number is less than the range,
     * the minimum is given, if it is over the range then the max is given,
     * otherwise the number is returned.
     * @param value number to be clamped
     * @param floor minimum value
     * @param cap maximum value
     * @return
     */
    public static float clampValue(float value, float floor, float cap){
        if(value < floor) {
        	return floor;
        }
        
        if(value > cap) {
        	return cap;
        }
        
        return value;
    }
    
    public static float getSimilarity(String string1, String string2) {

        //get half the length of the string rounded up - (this is the distance used for acceptable transpositions)
        int halflen = ((Math.min(string1.length(), string2.length())) / 2) + ((Math.min(string1.length(), string2.length())) % 2);

        //get common characters
        StringBuffer common1 = getCommonCharacters(string1, string2, halflen);
        StringBuffer common2 = getCommonCharacters(string2, string1, halflen);

        //check for zero in common
        if (common1.length() == 0 || common2.length() == 0) {
            return 0.0f;
        }

        //check for same length common strings returning 0.0f is not the same
        if (common1.length() != common2.length()) {
            return 0.0f;
        }

        //get the number of transpositions
        int transpositions = 0;
        int n=common1.length();
        
        for (int i = 0; i < n; i++) {
            if (common1.charAt(i) != common2.charAt(i)) {
                transpositions++;
            }
        }
        
        transpositions /= 2.0f;

        //calculate jaro metric
        return (common1.length() / ((float) string1.length()) +
                common2.length() / ((float) string2.length()) +
                (common1.length() - transpositions) / ((float) common1.length())) / 3.0f;
    }
    
    private static StringBuffer getCommonCharacters(final String string1, final String string2, final int distanceSep) {
        //create a return buffer of characters
        StringBuffer returnCommons = new StringBuffer();
        //create a copy of string2 for processing
        StringBuilder copy = new StringBuilder(string2);
        //iterate over string1
        int n = string1.length();
        int m = string2.length();
        
        for (int i = 0; i < n; i++) {
            char ch = string1.charAt(i);
            //set boolean for quick loop exit if found
            boolean foundIt = false;
            //compare char with range of characters to either side

            for (int j = Math.max(0, i - distanceSep); !foundIt && j < Math.min(i + distanceSep, m - 1); j++) {
                //check if found
                if (copy.charAt(j) == ch) {
                    foundIt = true;
                    //append character found
                    returnCommons.append(ch);
                    //alter copied string2 for processing
                    copy.setCharAt(j, (char)0);
                }
            }
        }
        
        return returnCommons;
    }

}
