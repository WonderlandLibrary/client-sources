package rina.turok.turok;

import rina.turok.turok.draw.TurokGL;
import rina.turok.turok.task.TurokFont;

public class Turok {
   private String tag;
   private TurokFont font_manager;

   public Turok(String tag) {
      this.tag = tag;
   }

   public void resize(int x, int y, float size) {
      TurokGL.resize(x, y, size);
   }

   public void resize(int x, int y, float size, String tag) {
      TurokGL.resize(x, y, size, "end");
   }

   public TurokFont get_font_manager() {
      return this.font_manager;
   }
}
