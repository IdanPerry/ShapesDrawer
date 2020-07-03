package com.idan.utils;

import com.idan.drawables.Oval;
import com.idan.drawables.Rectangle;
import com.idan.drawables.Shape;
import java.awt.Graphics;
import java.awt.Point;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class GeometryTest {
    private Shape shape;
    private Rectangle rect;
    private Oval oval;

    @Before
    public void setUp() throws Exception {
        rect = new Rectangle(10, 10, 100, 100, 0, null, false);
        oval= new Oval(10, 10, 100, 100, 0, null, false);
        shape = new Shape() {
            @Override
            public void move(Point origin, Point dest, int currentX, int currentY) {
            }
            @Override
            public void draw(Graphics g) {
            }
        };

        shape.setOriginX(0);
        shape.setOriginY(0);
        shape.setDestX(100);
        shape.setDestY(100);
    }

    @Test
    public void distance() {
        Point p1 = new Point(shape.getOriginX(), shape.getOriginY());
        Point p2 = new Point(shape.getDestX(), shape.getDestY());

        assertEquals(Geometry.distance(p1, p2), 141.42, 0.01);
        assertNotEquals(Geometry.distance(p1, p2), 140, 0.01);
        assertNotEquals(Geometry.distance(p1, p2), 142.5, 0.01);
    }

    @Test
    public void isBetween() {
        Point p1 = new Point(shape.getOriginX(), shape.getOriginY());
        Point p2 = new Point(shape.getDestX(), shape.getDestY());

        assertTrue(Geometry.isBetween(p1, p2, new Point(0, 0)));
        assertTrue(Geometry.isBetween(p1, p2, new Point(50, 50)));
        assertTrue(Geometry.isBetween(p1, p2, new Point(100, 100)));

        assertFalse(Geometry.isBetween(p1, p2, new Point(50, 54)));
        assertFalse(Geometry.isBetween(p1, p2, new Point(54, 50)));
        assertFalse(Geometry.isBetween(p1, p2, new Point(105, 105)));
    }

    @Test
    public void isOnRect() {
        Point p1 = new Point(rect.getOriginX(), rect.getOriginY());
        Point p2 = new Point(rect.getWidth(), rect.getOriginY());
        Point p3 = new Point(rect.getOriginX(), rect.getHeight());
        Point p4 = new Point(rect.getWidth(), rect.getHeight());

        assertTrue(Geometry.isOnRect(p1, p2, p3, p4, new Point(71, 10)));
        assertTrue(Geometry.isOnRect(p1, p2, p3, p4, new Point(100, 44)));
        assertTrue(Geometry.isOnRect(p1, p2, p3, p4, new Point(49, 100)));
        assertTrue(Geometry.isOnRect(p1, p2, p3, p4, new Point(10, 82)));

        assertFalse(Geometry.isOnRect(p1, p2, p3, p4, new Point(71, 14)));
        assertFalse(Geometry.isOnRect(p1, p2, p3, p4, new Point(104, 44)));
        assertFalse(Geometry.isOnRect(p1, p2, p3, p4, new Point(49, 104)));
        assertFalse(Geometry.isOnRect(p1, p2, p3, p4, new Point(14, 82)));
//        assertFalse(Geometry.isOnRect(p1, p2, p3, p4, new Point(100, 100)));
    }

    @Test
    public void ellipseCenter() {
        int x = oval.getOriginX();
        int y = oval.getOriginY();
        int width = oval.getWidth();
        int height = oval.getHeight();

        assertEquals(Geometry.ellipseCenter(x, y, width, height), new Point(60, 60));
        assertNotEquals(Geometry.ellipseCenter(x, y, width, height), new Point(65, 60));
        assertNotEquals(Geometry.ellipseCenter(x, y, width, height), new Point(60, 65));
    }

    @Test
    public void isOnEllipse() {
        int x = oval.getOriginX();
        int y = oval.getOriginY();
        int width = oval.getWidth();
        int height = oval.getHeight();

        assertTrue(Geometry.isOnEllipse(x, y, width, height, new Point(60, 10)));
        assertTrue(Geometry.isOnEllipse(x, y, width, height, new Point(110, 60)));
        assertTrue(Geometry.isOnEllipse(x, y, width, height, new Point(60, 110)));
        assertTrue(Geometry.isOnEllipse(x, y, width, height, new Point(10, 60)));

        assertFalse(Geometry.isOnEllipse(x, y, width, height, new Point(60, 5)));
        assertFalse(Geometry.isOnEllipse(x, y, width, height, new Point(115, 60)));
        assertFalse(Geometry.isOnEllipse(x, y, width, height, new Point(60, 105)));
        assertFalse(Geometry.isOnEllipse(x, y, width, height, new Point(5, 60)));
    }

    @Test
    public void isOnPolygon() {
    }
}