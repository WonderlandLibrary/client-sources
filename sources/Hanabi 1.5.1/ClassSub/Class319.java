package ClassSub;

private class Class319
{
    public Class220 image;
    public int duration;
    public int x;
    public int y;
    final Class260 this$0;
    
    
    public Class319(final Class260 this$0, final Class220 image, final int duration) {
        this.this$0 = this$0;
        this.x = -1;
        this.y = -1;
        this.image = image;
        this.duration = duration;
    }
    
    public Class319(final Class260 this$0, final int duration, final int x, final int y) {
        this.this$0 = this$0;
        this.x = -1;
        this.y = -1;
        this.image = Class260.access$000(this$0).getSubImage(x, y);
        this.duration = duration;
        this.x = x;
        this.y = y;
    }
}
