package xyz.mathax.mathaxclient.systems.hud;

import xyz.mathax.mathaxclient.utils.Utils;
import xyz.mathax.mathaxclient.utils.misc.ISerializable;
import xyz.mathax.mathaxclient.utils.render.Alignment;
import org.json.JSONObject;

public class BoundingBox implements ISerializable<BoundingBox> {
    public Alignment.X x = Alignment.X.Left;
    public Alignment.Y y = Alignment.Y.Top;

    public double xOffset, yOffset;
    public double width, height;

    public double alignX(double width) {
        return switch (this.x) {
            case Left -> 0;
            case Center -> this.width / 2.0 - width / 2.0;
            case Right -> this.width - width;
        };
    }

    public void addPos(double deltaX, double deltaY) {
        xOffset += (deltaX);
        yOffset += (deltaY);

        double xPos = getX();
        double yPos = getY();

        // X
        switch (x) {
            case Left -> {
                double leftSide = Utils.getWindowWidth() / 3.0;

                if (xPos >= leftSide - width / 2.0) {
                    // Element is closer to center than left
                    x = Alignment.X.Center;
                    xOffset = (-leftSide / 2.0 + xPos - leftSide + width / 2.0);
                }
            }
            case Center -> {
                double center = Utils.getWindowWidth() / 3.0;
                double centerRight = Utils.getWindowWidth() / 3.0 * 2;

                if (xPos > centerRight - width / 2.0) {
                    // Element is closer to right than center
                    x = Alignment.X.Right;
                    xOffset = (-(center - width) + (center - (Utils.getWindowWidth() - xPos)));
                } else if (xPos < center - width / 2.0) {
                    // Element is closer to left than center
                    x = Alignment.X.Left;
                    xOffset = (xPos);
                }
            }
            case Right -> {
                double right = Utils.getWindowWidth() / 3.0;
                double rightLeft = Utils.getWindowWidth() / 3.0 * 2;

                if (xPos <= rightLeft - width / 2.0) {
                    // Element is closer to center than right
                    x = Alignment.X.Center;
                    xOffset = (-right / 2.0 + xPos - right + width / 2.0);
                }
            }
        }

        if (x == Alignment.X.Left && xOffset < 0) {
            xOffset = 0;
        } else if (x == Alignment.X.Right && xOffset > 0) {
            xOffset = 0;
        }

        // Y
        switch (y) {
            case Top -> {
                double top = Utils.getWindowHeight() / 3.0;

                if (yPos >= top - height / 2.0) {
                    // Element is closer to center than top
                    y = Alignment.Y.Center;
                    yOffset = (-top / 2.0 + yPos - top + height / 2.0);
                }
            }
            case Center -> {
                double center = Utils.getWindowHeight() / 3.0;
                double centerBottom = Utils.getWindowHeight() / 3.0 * 2;

                if (yPos > centerBottom - height / 2.0) {
                    // Element is closer to bottom than center
                    y = Alignment.Y.Bottom;
                    yOffset = (-(center - height) + (center - (Utils.getWindowHeight() - yPos)));
                } else if (yPos < center - height / 2.0) {
                    // Element is closer to top than center
                    y = Alignment.Y.Top;
                    yOffset = (yPos);
                }
            }
            case Bottom -> {
                double bottom = Utils.getWindowHeight() / 3.0;
                double bottomLeft = Utils.getWindowHeight() / 3.0 * 2;

                if (yPos <= bottomLeft - height / 2.0) {
                    // Element is closer to center than bottom
                    y = Alignment.Y.Center;
                    yOffset = (-bottom / 2.0 + yPos - bottom + height / 2.0);
                }
            }
        }

        if (y == Alignment.Y.Top && yOffset < 0) {
            yOffset = 0;
        } else if (y == Alignment.Y.Bottom && yOffset > 0) {
            yOffset = 0;
        }
    }

    public void setSize(double width, double height) {
        this.width = (width);
        this.height = (height);
    }

    public double getX() {
        return switch (x) {
            case Left -> xOffset;
            case Center -> (Utils.getWindowWidth() / 2.0 - width / 2.0 + xOffset);
            case Right -> Utils.getWindowWidth() - width + xOffset;
        };
    }

    public void setX(int x) {
        addPos(x - getX(), 0);
    }

    public double getY() {
        return switch (y) {
            case Top -> yOffset;
            case Center -> (Utils.getWindowHeight() / 2.0 - height / 2.0 + yOffset);
            case Bottom -> Utils.getWindowHeight() - height + yOffset;
        };
    }

    public void setY(int y) {
        addPos(0, y - getY());
    }

    public boolean isOver(double x, double y) {
        double sx = getX();
        double sy = getY();
        return x >= sx && x <= sx + width && y >= sy && y <= sy + height;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("x", x.name());
        json.put("y", y.name());
        json.put("x-offset", xOffset);
        json.put("y-offset", yOffset);
        return json;
    }

    @Override
    public BoundingBox fromJson(JSONObject json) {
        x = Alignment.X.valueOf(json.getString("x"));
        y = Alignment.Y.valueOf(json.getString("y"));
        xOffset = json.getDouble("x-offset");
        yOffset = json.getDouble("y-offset");

        return this;
    }
}