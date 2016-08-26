import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

class Triangle {

    private final long a;
    private final long b;
    private final long c;

    public static enum Type {
        EQUILATERAL {
            @Override
            boolean matches(Triangle t) {
                return t.a == t.b && t.b == t.c;
            }
        },
        ISOSCELES {
            @Override
            boolean matches(Triangle t) {
                return t.a == t.b || t.a == t.c || t.b == t.c;
            }
        },
        SCALENE {
            @Override
            boolean matches(Triangle t) {
                return !ISOSCELES.matches(t);
            }
        };

        abstract boolean matches(Triangle t);

        public static Type validate(Triangle t) {
            for (Type type : values()) {
                if (type.matches(t)) {
                    return type;
                }
            }
            throw new IllegalArgumentException();
        }
    }

    public Triangle(int a, int b, int c) {
        this.a = (long) a;
        this.b = (long) b;
        this.c = (long) c;
        if (!isInvalidArguments(this.a, this.b, this.c)) {
            throw new IllegalArgumentException("Length of all sides of a Triangle must be greater than  0");
        }
        if (!isValidTriangle(this.a, this.b, this.c)) {
            throw new IllegalArgumentException("Not a triangle: no side must be greater or equal to the sum of the other sides");
        }
    }

    private static boolean isInvalidArguments(long a, long b, long c) {
        return a > 0 && b > 0 && c > 0;
    }

    private static boolean isValidTriangle(long a, long b, long c) {
        return a < b + c && b < a + c && c < a + b;
    }
}

public class TriangleTest {
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidTriangleWithInCorrectLength() {
        int a = 1;
        int b = 2;
        int c = 3;
        new Triangle(a, b, c);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidTriangleWithNegativeLength() {
        new Triangle(1, 2, -5);
    }

    @Test
    public void testEquilateralTriangle() {
        assertEquals(Triangle.Type.EQUILATERAL, Triangle.Type.validate(new Triangle(10, 10, 10)));
        assertEquals(Triangle.Type.EQUILATERAL, Triangle.Type.validate(new Triangle(5, 5, 5)));
        assertNotEquals(Triangle.Type.EQUILATERAL, Triangle.Type.validate(new Triangle(20, 5, 20)));
        assertNotEquals(Triangle.Type.EQUILATERAL, Triangle.Type.validate(new Triangle(5, 10, 8)));
    }

    @Test
    public void testIsoscelesTriangle() {
        assertEquals(Triangle.Type.ISOSCELES, Triangle.Type.validate(new Triangle(5, 10, 10)));
        assertEquals(Triangle.Type.ISOSCELES, Triangle.Type.validate(new Triangle(8, 2, 8)));
        assertEquals(Triangle.Type.ISOSCELES, Triangle.Type.validate(new Triangle(5, 2, 5)));
    }
    
    @Test
    public void testScaleneTriangle() {
        assertEquals(Triangle.Type.SCALENE, Triangle.Type.validate(new Triangle(10,5, 8)));
        assertNotEquals(Triangle.Type.SCALENE, Triangle.Type.validate(new Triangle(10,10, 8)));
    }
}