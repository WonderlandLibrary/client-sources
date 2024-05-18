package rina.turok.turok.task;

public class TurokRect {
   private String tag;
   private int x;
   private int y;
   private int width;
   private int height;

   public TurokRect(String tag, int width, int height) {
      this.tag = tag;
      this.width = width;
      this.height = height;
   }

   public void transform(int x, int y) {
      this.x = x;
      this.y = y;
   }

   public void transform(int x, int y, int width, int height) {
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
   }

   public boolean event_mouse(int mx, int my) {
      return mx >= this.get_x() && my >= this.get_y() && mx <= this.get_screen_width() && my <= this.get_screen_height();
   }

   public String get_tag() {
      return this.tag;
   }

   public int get_x() {
      return this.x;
   }

   public int get_y() {
      return this.y;
   }

   public int get_width() {
      return this.width;
   }

   public int get_height() {
      return this.height;
   }

   public int get_screen_width() {
      return this.x + this.width;
   }

   public int get_screen_height() {
      return this.y + this.height;
   }
}
