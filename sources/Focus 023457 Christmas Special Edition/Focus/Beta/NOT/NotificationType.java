package Focus.Beta.NOT;

import java.awt.Color;

public enum NotificationType {
  ERROR(new Color(255, 62, 62), "c"),
  INFO(new Color(255, 255, 255), "b"),
  WARNING(new Color(253, 253, 69), "c"),
  SUCCESS(new Color(74, 252, 74), "a");
  
  private final Color color;
  
  public String err;
  
  NotificationType(Color color, String f) {
    this.color = color;
    this.err = f;
  }
  
  public Color getColor() {
    return this.color;
  }
}
