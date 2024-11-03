package vestige.util.animation;

import java.util.function.Function;
import org.apache.commons.lang3.StringUtils;

public enum Easing {
   LINEAR((x) -> {
      return x;
   }),
   EASE_IN_QUAD((x) -> {
      return x * x;
   }),
   EASE_OUT_QUAD((x) -> {
      return x * (2.0D - x);
   }),
   EASE_IN_OUT_QUAD((x) -> {
      return x < 0.5D ? 2.0D * x * x : -1.0D + (4.0D - 2.0D * x) * x;
   }),
   EASE_IN_CUBIC((x) -> {
      return x * x * x;
   }),
   EASE_OUT_CUBIC((x) -> {
      return (x - 1.0D) * (x - 1.0D) * (x - 1.0D) + 1.0D; // Corrected
   }),
   EASE_IN_OUT_CUBIC((x) -> {
      return x < 0.5D ? 4.0D * x * x * x : (x - 1.0D) * (2.0D * x - 2.0D) * (2.0D * x - 2.0D) + 1.0D;
   }),
   EASE_IN_QUART((x) -> {
      return x * x * x * x;
   }),
   EASE_OUT_QUART((x) -> {
      return 1.0D - (x - 1.0D) * (x - 1.0D) * (x - 1.0D) * (x - 1.0D); // Corrected
   }),
   EASE_IN_OUT_QUART((x) -> {
      return x < 0.5D ? 8.0D * x * x * x * x : 1.0D - 8.0D * (x - 1.0D) * (x - 1.0D) * (x - 1.0D) * (x - 1.0D);
   }),
   EASE_IN_QUINT((x) -> {
      return x * x * x * x * x;
   }),
   EASE_OUT_QUINT((x) -> {
      return 1.0D + (x - 1.0D) * (x - 1.0D) * (x - 1.0D) * (x - 1.0D) * (x - 1.0D); // Corrected
   }),
   EASE_IN_OUT_QUINT((x) -> {
      return x < 0.5D ? 16.0D * x * x * x * x * x : 1.0D + 16.0D * (x - 1.0D) * (x - 1.0D) * (x - 1.0D) * (x - 1.0D) * (x - 1.0D);
   }),
   EASE_IN_SINE((x) -> {
      return 1.0D - Math.cos(x * Math.PI / 2.0D);
   }),
   EASE_OUT_SINE((x) -> {
      return Math.sin(x * Math.PI / 2.0D);
   }),
   EASE_IN_OUT_SINE((x) -> {
      return 1.0D - Math.cos(Math.PI * x / 2.0D);
   }),
   EASE_IN_EXPO((x) -> {
      return x == 0.0D ? 0.0D : Math.pow(2.0D, 10.0D * x - 10.0D);
   }),
   EASE_OUT_EXPO((x) -> {
      return x == 1.0D ? 1.0D : 1.0D - Math.pow(2.0D, -10.0D * x);
   }),
   EASE_IN_OUT_EXPO((x) -> {
      return x == 0.0D ? 0.0D : (x == 1.0D ? 1.0D : (x < 0.5D ? Math.pow(2.0D, 20.0D * x - 10.0D) / 2.0D : (2.0D - Math.pow(2.0D, -20.0D * x + 10.0D)) / 2.0D));
   }),
   EASE_IN_CIRC((x) -> {
      return 1.0D - Math.sqrt(1.0D - x * x);
   }),
   EASE_OUT_CIRC((x) -> {
      return Math.sqrt(1.0D - (x - 1.0D) * (x - 1.0D)); // Corrected
   }),
   EASE_IN_OUT_CIRC((x) -> {
      return x < 0.5D ? (1.0D - Math.sqrt(1.0D - 4.0D * x * x)) / 2.0D : (Math.sqrt(1.0D - 4.0D * (x - 1.0D) * x) + 1.0D) / 2.0D;
   }),
   SIGMOID((x) -> {
      return 1.0D / (1.0D + Math.exp(-x));
   }),
   EASE_OUT_ELASTIC((x) -> {
      return x == 0.0D ? 0.0D : (x == 1.0D ? 1.0D : Math.pow(2.0D, -10.0D * x) * Math.sin((x * 10.0D - 0.75D) * 2.0943951023931953D) * 0.5D + 1.0D);
   }),
   EASE_IN_BACK((x) -> {
      return 2.70158D * x * x * x - 1.70158D * x * x;
   });
   private Function<Double, Double> function = null;

   private Easing(final Function<Double, Double> param3) {
      this.function = function;
   }

   public Function<Double, Double> getFunction() {
      return this.function;
   }

   public String toString() {
      return StringUtils.capitalize(super.toString().toLowerCase().replace("_", " "));
   }

   // $FF: synthetic method
   private static Easing[] $values() {
      return new Easing[]{LINEAR, EASE_IN_QUAD, EASE_OUT_QUAD, EASE_IN_OUT_QUAD, EASE_IN_CUBIC, EASE_OUT_CUBIC, EASE_IN_OUT_CUBIC, EASE_IN_QUART, EASE_OUT_QUART, EASE_IN_OUT_QUART, EASE_IN_QUINT, EASE_OUT_QUINT, EASE_IN_OUT_QUINT, EASE_IN_SINE, EASE_OUT_SINE, EASE_IN_OUT_SINE, EASE_IN_EXPO, EASE_OUT_EXPO, EASE_IN_OUT_EXPO, EASE_IN_CIRC, EASE_OUT_CIRC, EASE_IN_OUT_CIRC, SIGMOID, EASE_OUT_ELASTIC, EASE_IN_BACK};
   }
}
