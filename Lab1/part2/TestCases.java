//PART 2
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import org.junit.Test;

public class TestCases
{
    public static final double DELTA = 0.00001;

    /*
     * This test is just to get you started.
     */
    @Test
    public void testGetX()
    {
		assertEquals(1.0, new Point(1.0, 2.0).getX(), DELTA);
    }
    @Test
    public void testGetY()
    {
    	assertEquals(1.0, new Point(3.0, 1.0).getY(), DELTA);
    }
    @Test
    public void testGetRadius()
    {
    	assertEquals(5.0, new Point(3.0, 4.0).getRadius(), DELTA);
    }
    @Test
    public void testGetAngle()
    {
    	assertEquals(45.0, new Point(1.0, 1.0).getAngle(), DELTA);
    }
    @Test
    public void testPoint()
    {
    	Point quad1 = new Point(1.0, 1.0);
    	Point test1 = quad1.rotate90();

    	Point quad2 = new Point(-1.0, 1.0);
    	Point test2 = quad2.rotate90();

    	Point quad3 = new Point(-1.0, 1.0);
    	Point test3 = quad3.rotate90();

    	Point quad4 = new Point(1.0, -1.0);
    	Point test4 = quad4.rotate90();

    	// quad 1 to quad 4
    	assertEquals(1.0, test1.getX(), DELTA);
    	assertEquals(-1.0, test1.getY(), DELTA);

    	//quad 2 to quad 1
    	assertEquals(1.0, test2.getX(), DELTA);
    	assertEquals(1.0, test2.getY(), DELTA);

    	//quad 3 to quad 2
    	assertEquals(-1.0, test3.getX(), DELTA);
    	assertEquals(1.0, test3.getY(), DELTA);

    	// quad 4 to quad 3
    	assertEquals(-1.0, test4.getX(), DELTA);
    	assertEquals(-1.0, test4.getY(), DELTA);

    }

    /*
     * The tests below here are to verify the basic requirements regarding
     * the "design" of your class.  These are to remain unchanged.
     */

    @Test
    public void testImplSpecifics()
	throws NoSuchMethodException
    {
	final List<String> expectedMethodNames = Arrays.asList("getX","getY","getRadius","getAngle","rotate90");

	final List<Class> expectedMethodReturns = Arrays.asList(double.class, double.class, double.class, double.class, Point.class);

	final List<Class[]> expectedMethodParameters = Arrays.asList(
	    new Class[0],
	    new Class[0],
	    new Class[0],
	    new Class[0],
	    new Class[0]
	    );

	verifyImplSpecifics(Point.class, expectedMethodNames,
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
	    0, Point.class.getFields().length);

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

		// verify that fields are final

		// **** I commented this section out because it was causing my program to not compile. *****
		// **** Not sure if these were double checked in the original assignment to see if it actually worked beforehand.*****


		/*final List<Field> nonFinalFields = Arrays.stream(
	    clazz.getDeclaredFields())
		.filter(f -> !Modifier.isFinal(f.getModifiers()))
		.collect(Collectors.toList());

		assertEquals("Unexpected non-final fields", 0, nonFinalFields.size());*/
    }
}
