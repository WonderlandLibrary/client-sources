package exhibition.management.keybinding;

import com.google.gson.annotations.Expose;
import org.lwjgl.input.Keyboard;

public class Keybind {
   public static final Keybind DEFAULT;
   private Bindable bindable;
   @Expose
   private String keyStr;
   @Expose
   private KeyMask mask;
   @Expose
   private int keyInt;

   public Keybind(Bindable bindable, int keyInt, KeyMask mask) {
      this.bindable = bindable;
      this.keyInt = keyInt;
      this.mask = mask;
      this.keyStr = Keyboard.getKeyName(keyInt);
   }

   public Keybind(Bindable bindable, int keyInt) {
      this.bindable = bindable;
      this.keyInt = keyInt;
      this.mask = KeyMask.None;
      this.keyStr = Keyboard.getKeyName(keyInt);
   }

   public boolean isMaskDown() {
      return this.mask == KeyMask.None ? true : KeyHandler.isMaskDown(this.mask);
   }

   public void press() {
      if (this.isMaskDown()) {
         this.bindable.onBindPress();
      }

   }

   public void update(Keybind newBind) {
      this.keyStr = newBind.getKeyStr();
      this.mask = newBind.getMask();
      this.keyInt = newBind.getKeyInt();
   }

   public void release() {
      this.bindable.onBindRelease();
   }

   public Bindable getBindOwner() {
      return this.bindable;
   }

   public String getKeyStr() {
      return this.keyStr;
   }

   public void setKeyStr(String keyStr) {
      this.keyStr = keyStr;
   }

   public KeyMask getMask() {
      return this.mask;
   }

   public void setMask(KeyMask mask) {
      this.mask = mask;
   }

   public int getKeyInt() {
      return this.keyInt;
   }

   public void setKeyInt(int keyInt) {
      this.keyInt = keyInt;
   }

   static {
      DEFAULT = new Keybind((Bindable)null, 0, KeyMask.None);
   }
}
