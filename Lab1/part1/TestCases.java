import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
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
    private static final double DELTA = 0.00001;

    @Test
    public void testSimpleIf1()
    {
	   assertEquals(1.7, SimpleIf.max(1.2, 1.7), DELTA);
    }

    @Test
    public void testSimpleIf2()
    {
	   assertEquals(9.0, SimpleIf.max(9.0, 3.2), DELTA);
    }

    @Test
    public void testSimpleIf3()
    {
	   //fail("Missing SimpleIf3");
	/* TO DO: Write one more valid test case. */
        assertEquals(5.6, SimpleIf.max(5.6, 3.2), DELTA);
    }

    @Test
    public void testSimpleLoop1()
    {
	   assertEquals(7, SimpleLoop.sum(3, 4));
    }

    @Test
    public void testSimpleLoop2()
    {
	   assertEquals(7, SimpleLoop.sum(-2, 4));
    }

    @Test
    public void testSimpleLoop3()
    {
	   //fail("Missing SimpleLoop3");
        assertEquals(9, SimpleLoop.sum(2, 4));
    }

    @Test
    public void testSimpleArray1()
    {
	   assertArrayEquals(new int[] {1, 4, 9}, SimpleArray.squareAll(new int[] {1, 2, 3}));
    }
    @Test
    public void testSimpleArray2()
    {
	   assertArrayEquals(new int[] {49, 25}, SimpleArray.squareAll(new int[] {7, 5}));
    }

    @Test
    public void testSimpleArray3()
    {
	   //fail("Missing SimpleArray3");
       assertArrayEquals(new int[] {36, 16}, SimpleArray.squareAll(new int[] {6, 4}));
    }

    @Test
    public void testSimpleList1()
    {
	   List<Integer> input = new LinkedList<Integer>(Arrays.asList(new Integer[] {1, 2, 3}));
	   List<Integer> expected = new ArrayList<Integer>(Arrays.asList(new Integer[] {1, 4, 9}));

	   assertEquals(expected, SimpleList.squareAll(input));
    }

    @Test
    public void testSimpleList2()
    {
        //fail("Missing SimpleList2")
        List<Integer> input = new LinkedList<Integer>(Arrays.asList(new Integer[] {4, 5, 6}));
        List<Integer> expected = new ArrayList<Integer>(Arrays.asList(new Integer[] {16, 25, 36}));

        assertEquals(expected, SimpleList.squareAll(input));
    }

    @Test
    public void testBetterLoop1()
    {
	   assertTrue(BetterLoop.contains(new int[] {7, 5}, 5));
    }

    @Test
    public void testBetterLoop2()
    {
	   assertTrue(BetterLoop.contains(new int[] {7, 5, 2, 4}, 4));
    }

    @Test
    public void testBetterLoop3()
    {
	   //fail("Missing BetterLoop3");
        assertFalse(BetterLoop.contains(new int[] {7, 5, 2, 4}, 3));
    }

    @Test
    public void testExampleMap1()
    {
	List<String> expected = Arrays.asList("Julie", "Zoe");
	Map<String, List<Course>> courseListsByStudent = new HashMap<>();

	courseListsByStudent.put("Julie",
	    Arrays.asList(
		new Course("CPE 123", 4),
		new Course("CPE 101", 4),
		new Course("CPE 202", 4),
		new Course("CPE 203", 4),
		new Course("CPE 225", 4)));
	courseListsByStudent.put("Paul",
	    Arrays.asList(
		new Course("CPE 101", 4),
		new Course("CPE 202", 4),
		new Course("CPE 203", 4),
		new Course("CPE 225", 4)));
	courseListsByStudent.put("Zoe",
	    Arrays.asList(
		new Course("CPE 123", 4),
		new Course("CPE 203", 4),
		new Course("CPE 471", 4),
		new Course("CPE 473", 4),
		new Course("CPE 476", 4),
		new Course("CPE 572", 4)));

	/*
	 * Why compare HashSets here?  Just so that the order of the
	 * elements in the list is not important for this test.
	 */
	   assertEquals(new HashSet<>(expected), new HashSet<>(ExampleMap.highEnrollmentStudents(courseListsByStudent, 16)));
    }

    @Test
    public void testExampleMap2()
    {
	   //fail("Missing ExampleMap2");
	   /* TO DO: Write another valid test case. */
       List<String> expected = Arrays.asList("Jack", "Jill");
       Map<String, List<Course>> courseListsByStudent = new HashMap<>();

       courseListsByStudent.put("Julie", Arrays.asList(
        new Course("CPE 101", 4),
        new Course("CPE 202", 4),
        new Course("CPE 203", 4),
        new Course("CPE 225", 4)));

       courseListsByStudent.put("Jack", Arrays.asList(
        new Course("CPE 123", 4),
        new Course("CPE 203", 4),
        new Course("CPE 471", 4),
        new Course("CPE 473", 4),
        new Course("CPE 476", 4),
        new Course("CPE 572", 4)));

       courseListsByStudent.put("Kyle", Arrays.asList(
        new Course("COMS 101", 4)));

       courseListsByStudent.put("Jill", Arrays.asList(
        new Course("CPE 123", 4),
        new Course("CPE 101", 4),
        new Course("CPE 202", 4),
        new Course("CPE 203", 4),
        new Course("CPE 225", 4)));


       assertEquals(new HashSet<>(expected), new HashSet<>(ExampleMap.highEnrollmentStudents(courseListsByStudent, 16)));


    }
}
