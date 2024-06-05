package ClassSub;

class Class46 extends Class204
{
    final Class281 this$0;
    
    
    Class46(final Class281 this$0, final String s, final boolean b) {
        this.this$0 = this$0;
        super(s, b);
    }
    
    @Override
    public void onPress() {
        this.this$0.mod.set(!this.this$0.mod.isEnabled());
        this.this$0.mod.modButton.toggle();
        super.onPress();
    }
}
