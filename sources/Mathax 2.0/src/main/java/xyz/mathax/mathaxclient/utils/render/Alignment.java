package xyz.mathax.mathaxclient.utils.render;

public class Alignment {
    public enum X {
        Left("Left"),
        Center("Center"),
        Right("Right");

        private final String name;

        X(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public enum Y {
        Top("Top"),
        Center("Center"),
        Bottom("Bottom");

        private final String name;

        Y(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
