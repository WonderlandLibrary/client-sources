package hawk.access;

import hawk.Client;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class DisplayOnScreen extends JFrame implements ActionListener {
   JTextField text = new JTextField("", 25);
   public static String CorrectKey = "RFWU4OTRHUY49HUGNRE5HUOIU5JY048764UGTDH";
   public static String KeyTyped;
   JLabel information = new JLabel("If you want to access to all settings and all modules, enter the key here");
   JButton button = new JButton("Register key");

   public void actionPerformed(ActionEvent var1) {
      KeyTyped = this.text.getText();
      if (KeyTyped.equals(CorrectKey)) {
         new DisplayValid();
         Client.IsRegistered = true;

         try {
            PrintWriter var2 = new PrintWriter(new FileOutputStream("gie5hg8u4toihu45.hawkclient"));
            var2.print(CorrectKey);
            var2.close();
         } catch (IOException var3) {
            var3.printStackTrace();
         }
      } else {
         new DisplayInvalid();
      }

   }

   public DisplayOnScreen() {
      super("Register key for premium modules (private)");
      this.setSize(500, 500);
      this.setDefaultCloseOperation(1);
      this.setVisible(true);
      Container var1 = this.getContentPane();
      FlowLayout var2 = new FlowLayout();
      var1.setLayout(var2);
      this.button.addActionListener(this);
      var1.add(this.information);
      var1.add(this.text);
      var1.add(this.button);
      this.setContentPane(var1);
   }
}
