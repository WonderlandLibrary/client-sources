package hawk.access;

import java.awt.Container;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class DisplayValid extends JFrame {
   JLabel information = new JLabel("The key is valid ! You can now use private modules.");

   public DisplayValid() {
      super("Valid key");
      this.setSize(500, 500);
      this.setDefaultCloseOperation(1);
      this.setVisible(true);
      Container var1 = this.getContentPane();
      FlowLayout var2 = new FlowLayout();
      var1.setLayout(var2);
      var1.add(this.information);
      this.setContentPane(var1);
   }
}
