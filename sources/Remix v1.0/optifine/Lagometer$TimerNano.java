package optifine;

public static class TimerNano
{
    public long timeStartNano;
    public long timeNano;
    
    public TimerNano() {
        this.timeStartNano = 0L;
        this.timeNano = 0L;
    }
    
    public void start() {
        if (Lagometer.active && this.timeStartNano == 0L) {
            this.timeStartNano = System.nanoTime();
        }
    }
    
    public void end() {
        if (Lagometer.active && this.timeStartNano != 0L) {
            this.timeNano += System.nanoTime() - this.timeStartNano;
            this.timeStartNano = 0L;
        }
    }
    
    private void reset() {
        this.timeNano = 0L;
        this.timeStartNano = 0L;
    }
}
