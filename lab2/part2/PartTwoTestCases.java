import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

public class PartTwoTestCases
{
    public static final double DELTA = 0.00001;

    @Test
    public void testCircleImplSpecifics()
        throws NoSuchMethodException
    {
        final List<String> expectedMethodNames = Arrays.asList(
            "getCenter", "getRadius", "perimeter");

        final List<Class> expectedMethodReturns = Arrays.asList(
            Point.class, double.class, double.class);

        final List<Class[]> expectedMethodParameters = Arrays.asList(
            new Class[0], new Class[0], new Class[0]);

        verifyImplSpecifics(Circle.class, expectedMethodNames,
            expectedMethodReturns, expectedMethodParameters);
    }

    @Test
    public void testRectangleImplSpecifics()
        throws NoSuchMethodException
    {
        final List<String> expectedMethodNames = Arrays.asList(
            "getTopLeft", "getBottomRight", "perimeter");

        final List<Class> expectedMethodReturns = Arrays.asList(
            Point.class, Point.class, double.class);

        final List<Class[]> expectedMethodParameters = Arrays.asList(
            new Class[0], new Class[0], new Class[0]);

        verifyImplSpecifics(Rectangle.class, expectedMethodNames,
            expectedMethodReturns, expectedMethodParameters);
    }
    @Test
    public void testPerimPoly()
    {
        List<Point> points = new ArrayList<Point>();
        points.add(new Point(0, 0));
        points.add(new Point(3, 0));
        points.add(new Point(0, 4));
        Polygon p = new Polygon(points);
        assertEquals(12.0, p.perimeter(), DELTA);

    }
     @Test
    public void testwhichIsBigger()
    {
        List<Point> points = new ArrayList<Point>();
        points.add(new Point(0, 0));
        points.add(new Point(3, 0));
        points.add(new Point(0, 4));
        Polygon p1 = new Polygon(points);
        Circle c1 = new Circle(new Point(0, 0), 5.0);
        Rectangle r1 = new Rectangle(new Point(0, 9), new Point(9, 0));
        double result = Bigger.whichIsBigger(c1, r1, p1);
        assertEquals(36.0, result, DELTA);

    }

    @Test
    public void testPolygonImplSpecifics()
        throws NoSuchMethodException
    {
        final List<String> expectedMethodNames = Arrays.asList(
            "getPoints", "perimeter");

        final List<Class> expectedMethodReturns = Arrays.asList(
            List.class, double.class);

        final List<Class[]> expectedMethodParameters = Arrays.asList(
            new Class[0], new Class[0]);

        verifyImplSpecifics(Polygon.class, expectedMethodNames,
            expectedMethodReturns, expectedMethodParameters);
    }

    private static void verifyImplSpecifics(
        final Class<?> clazz,
        final List<String> expectedMethodNames,
        final List<Class> expectedMethodReturns,
        final List<Class[]> expectedMethodParameters)
        throws NoSuchMethodException
    {
        assertEquals("Unexpected number of public fields",
            0, clazz.getFields().length);

        final List<Method> publicMethods = Arrays.stream(
            clazz.getDeclaredMethods())
                .filter(m -> Modifier.isPublic(m.getModifiers()))
                .collect(Collectors.toList());

        assertEquals("Unexpected number of public methods",
            expectedMethodNames.size(), publicMethods.size());

        assertTrue("Invalid test configuration",
            expectedMethodNames.size() == expectedMethodReturns.size());
        assertTrue("Invalid test configuration",
            expectedMethodNames.size() == expectedMethodParameters.size());

        for (int i = 0; i < expectedMethodNames.size(); i++)
        {
            Method method = clazz.getDeclaredMethod(expectedMethodNames.get(i),
                expectedMethodParameters.get(i));
            assertEquals(expectedMethodReturns.get(i), method.getReturnType());
        }
    }
}
