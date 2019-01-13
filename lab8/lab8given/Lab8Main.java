

import java.util.function.Consumer;
import java.util.function.BiConsumer;
import java.util.Comparator;
import java.util.List;
import java.util.Collections;

public class Lab8Main 
{


    //
    // Do not modify this static field declaration.  An automated test
    // will use it.  This map holds a count of the number of times each
    // point is seen.
    //
    public static MyMap<Point, Integer> pointCounts;

    /**
     * Adds one to the count for point p in pointCounts.  If p wasn't
     * in the map, adds it (with a count of 1).  This method will be
     * called by an automated test.
     */
    public static void addPoint(Point p) 
    {
        // TODO:  Implement this

    	//MyMapEntry<Point, Integer> entry = new MyMapEntry<Point, Integer>(p, pointCounts.get(p));
    	//int kval = p.hashCode()%pointCounts.getSize() + pointCounts.getSize();
        //int points = 0;

    	if(pointCounts.get(p) != null)
    	{
            //System.out.println("This point is already in the map: " + p);
            //System.out.println("Before modifying the value is: " + pointCounts.get(p));
    		int points = pointCounts.get(p) + 1;
    		//pointCounts.put(p, points)
    		pointCounts.put(p, points);
    	}
    	else
    	{
            //System.out.println("This point is not already in the map: " + p);
    		pointCounts.put(p, 1);
    	}
    }

    /**
     * Calls consumer.accept() on every entry in pointCounts.  Do not
     * modify this method.
     */
    public static void 
    visitPointCounts(Consumer<MyMapEntry<Point, Integer>> consumer) 
    {
        for (MyMapEntry<Point, Integer> entry : pointCounts.getEntries()) 
        {
            consumer.accept(entry);
        }
    }

    /**
     * Calls consumer.accept on every entry in pointCounts, sorted
     * by the given comparator.
     */
    public static void
    visitPointCountsSorted(Consumer<MyMapEntry<Point, Integer>> consumer, Comparator<MyMapEntry<Point, Integer>> comparator) 
    {
        // TODO:  Implement this

        //sort the list by entry
        List<MyMapEntry<Point, Integer>> entrylist = pointCounts.getEntries();
        Collections.sort(entrylist, comparator);

        for(MyMapEntry<Point, Integer> entry : entrylist)
        {
        	consumer.accept(entry);
        }
    }

    /**
     * Calls consumer.accept on every bucket of your map in order, starting
     * from bucket 0.
     */
    public static void visitBuckets(BiConsumer<Integer, List<MyMapEntry<Point, Integer>>> consumer)
    {
        // TODO:  Implement this
        List<List<MyMapEntry<Point, Integer>>> buckets = pointCounts.getBuckets();
        int count = 0; 

        for(List<MyMapEntry<Point, Integer>> bucket : buckets)
        {
            consumer.accept(count, bucket);
        }  
		
    }
    //
    // Main method.  You may modify this.
    //
    public static void main(String[] args) 
    {
            // You might care to see what happens if you increase the
            // number of buckets to 20, or 40.
        pointCounts = new MyMap<Point, Integer>(10);
        for (int i = 0; i < 5; i++) 
        {
            for (int j = 0; j < 5; j++) 
            {
                addPoint(new Point(i, j));
                if (i == j) 
                {
                    for (int k = 0; k < i; k++) 
                    {
                        addPoint(new Point(i, j));
                    }
                }
            }
        }

        System.out.println();
        System.out.println("Visiting point counts:");
        visitPointCounts(
            (MyMapEntry<Point, Integer> entry) -> {
                System.out.println("    " + entry.key + " seen " + 
                                   entry.value + " times.");
            }
        );

        System.out.println();
        System.out.println("Visiting point counts, sorted by y, then x:");
        visitPointCountsSorted(
            (MyMapEntry<Point, Integer> entry) -> {
                System.out.println("    " + entry.key + " seen " + 
                                   entry.value + " times.");
            },
            (MyMapEntry<Point, Integer> a, MyMapEntry<Point, Integer> b) -> {
                int byY = Integer.compare(a.key.y, b.key.y);
                if (byY != 0) {
                    return byY;
                }
                return Integer.compare(a.key.x, b.key.x);
            }
        );

        System.out.println();
        System.out.println("Visiting buckets:");
        visitBuckets(
            (Integer bucketNum, List<MyMapEntry<Point, Integer>> contents) -> {
                System.out.println("    bucket " + bucketNum +
                                   " contains " + contents);
            }
        );

        System.out.println();
        System.out.println("Done.");
        System.out.println();
    }
}
