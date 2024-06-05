package tk.rektsky.Utils.Display;

public class ColorUtil
{
    public static float getRainbowHue(final float speedDelay, final int delay) {
        return (System.currentTimeMillis() + Math.round((float)delay)) % (int)speedDelay / speedDelay;
    }
    
    public enum NotificationColors
    {
        GREEN(10944353, -10092710), 
        YELLOW(16252672, -2228480), 
        RED(16736609, -43434);
        
        int color;
        int titleColor;
        
        private NotificationColors(final int color, final int titleColor) {
            this.color = color;
            this.titleColor = titleColor;
        }
        
        public int getColor() {
            return this.color;
        }
        
        public int getTitleColor() {
            return this.titleColor;
        }
    }
}
