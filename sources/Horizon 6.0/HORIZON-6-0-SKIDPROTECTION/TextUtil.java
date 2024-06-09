package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class TextUtil
{
    private static final char[] HorizonCode_Horizon_È;
    private final Random Â;
    private final char[] Ý;
    
    static {
        final StringBuilder tmp = new StringBuilder();
        for (char ch = '0'; ch <= '9'; ++ch) {
            tmp.append(ch);
        }
        for (char ch = 'a'; ch <= 'z'; ++ch) {
            tmp.append(ch);
        }
        HorizonCode_Horizon_È = tmp.toString().toCharArray();
    }
    
    public TextUtil(int length) {
        this.Â = new Random();
        if (length < 1) {
            length = 0;
        }
        this.Ý = new char[length];
    }
    
    public String HorizonCode_Horizon_È() {
        for (int idx = 0; idx < this.Ý.length; ++idx) {
            this.Ý[idx] = TextUtil.HorizonCode_Horizon_È[this.Â.nextInt(TextUtil.HorizonCode_Horizon_È.length)];
        }
        return new String(this.Ý);
    }
}
