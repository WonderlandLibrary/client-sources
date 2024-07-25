package club.bluezenith.ui.button.elements;

import club.bluezenith.util.math.Vec2d;

import static club.bluezenith.util.render.RenderUtil.*;
import static java.lang.Math.abs;
import static java.lang.Math.sqrt;
import static org.lwjgl.opengl.GL11.*;

public class CollidableTriangle {
    private double area = -1; //cached area
    private float angle, hoverAlpha;
    private final Vec2d[] points, originPoints; //points are edited when you perform rotating, so we need to store origin
    private final int color;
    private final boolean filled;
    private boolean isMouseOver;

    public CollidableTriangle(int color, boolean filled, Vec2d... threePoints) {
        if(threePoints.length != 3) throw new IllegalArgumentException("CollidableTriangle must have 3 points exact!");
        this.points = this.originPoints = threePoints;
        this.color = color;
        this.filled = filled;
        this.hoverAlpha = 1;
    }

    public void draw(int mouseX, int mouseY) {
        glEnable(GL_POLYGON_SMOOTH);
        glHint(GL_POLYGON_SMOOTH_HINT, GL_NICEST);
        glShadeModel(GL_SMOOTH);
        start2D(!filled ? GL_LINE_STRIP : GL_TRIANGLES);

        final boolean isHovering = isMouseOver = isMouseOver(mouseX, mouseY);
        if(isHovering) hoverAlpha = animate(0.7F, hoverAlpha, 0.08F);
        else hoverAlpha = animate(1, hoverAlpha, 0.1F);

        glColor4f(hoverAlpha, hoverAlpha, hoverAlpha, 1);
        for (Vec2d point : this.points) {
            glVertex2d(point.x, point.y);
        }

        if(!filled)
        glVertex2d(points[0].x, points[0].y); //connect the first & the last points

        end2D();
        glShadeModel(GL_FLAT);
        glDisable(GL_POLYGON_SMOOTH);
    }

    public boolean isMouseOver() {
        return isMouseOver; //updated every frame (triangle needs to change color upon hover, thats why)
    }

    private boolean isMouseOver(final int mouseX, final int mouseY) {
        final boolean needCalcArea = this.area == -1;
        final double[] sides = needCalcArea ? getSides(points) : new double[0]; //the sides of this triangle. calculated only when area wasnt calculated yet
        final double area = needCalcArea ? this.area = getArea(sides, calculateHalfPerimeter(sides)) : this.area; //compute area if hadn't yet

        final double[] areas = new double[points.length]; //areas of the 3 triangles made with 2 points of this triangle and the mouse point
        final Vec2d mouseVec = new Vec2d(mouseX, mouseY);

        final Vec2d[][] pairs = getPointPairs(points); //get 3 point pairs of this triangle

        for (int index = 0; index < pairs.length; index++) { //compute 3 areas: each area is of a new triangle consisting of this triangle's 2 points and the mouse point
            final Vec2d[] pair = pairs[index]; //get the current pair
            final Vec2d[] smallTriangle = new Vec2d[] { pair[0], pair[1], mouseVec }; //create a small triangle: 2 points of this triangle + mouse point

            final double[] smallTriangleSides = getSides(smallTriangle); //get sides of the small triangle
            areas[index] = getArea(smallTriangleSides, calculateHalfPerimeter(smallTriangleSides)); //calculate it's area
        }

        return abs(area - (areas[0] + areas[1] + areas[2])) < 0.05; //if the small triangles' areas are equal/close to this triangle's area, the mouse is hovering over it
    }

    private Vec2d[][] getPointPairs(Vec2d[] points) {
        final Vec2d[][] pairs = new Vec2d[points.length][2]; //3 pairs of points of this triangle

        for (int index = 0; index < points.length; index++) {
            final Vec2d point = points[index]; //get the current point

            int nextIndex = index + 1; //next point's index
            if(nextIndex >= points.length) nextIndex = 0; //if the next index is too large (which means we're iterating over the last point) get the first point
            final Vec2d nextPoint = points[nextIndex]; //get the point on needed index

            pairs[index] = new Vec2d[] { point, nextPoint };
        }

        return pairs;
    }

    private double[] getSides(Vec2d[] points) {
        final double[] sides = new double[points.length];
        final Vec2d[][] pairs = getPointPairs(points);

        for (int index = 0; index < pairs.length; index++) {
            final Vec2d[] pair = pairs[index];
            sides[index] = pair[0].distance(pair[1]);
        }

        return sides;
    }

    private double calculateHalfPerimeter(double[] sides) {
        double result = 0;

        for (double side : sides) {
            result += side;
        }

        return result / 2; //divide by 2 as we need half of the perimeter (for heron's formula)
    }

    private double getArea(double[] sides, double halfPerimeter) { //calculates the area using heron's formula
        double[] elements = new double[3]; //the (p - side) elements

        for (int index = 0; index < sides.length; index++) {
            elements[index] = halfPerimeter - sides[index]; //compute elements: p - side
        }

        return sqrt(halfPerimeter * elements[0] * elements[1] * elements[2]);
    }

    public CollidableTriangle rotate(float angle) { //todo rotate points' x and y with sin/cos functions
        this.angle = angle;
        return this;
    }

    public static Vec2d[] getEquilateralPoints(double x1, double y1, double width, double height) {
        final Vec2d[] points = new Vec2d[3];
        final double x2 = x1 + width/2D,
                     x3 = x1 + width,
                     y2 = y1 + height;
        points[0] = new Vec2d(x1, y1);
        points[1] = new Vec2d(x2, y2);
        points[2] = new Vec2d(x3, y1);

        return points;
    }

}
