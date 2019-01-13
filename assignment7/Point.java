import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import processing.core.PImage;
import processing.core.PApplet;
import java.util.Random;


final class Point
{
    public final int x;
    public final int y;

    public static final int ORE_REACH = 1;

    public Point(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public String toString()
    {
        return "(" + x + "," + y + ")";
    }

    public boolean equals(Object other)
    {
        return other instanceof Point &&
            ((Point)other).x == this.x &&
            ((Point)other).y == this.y;
    }

    public int hashCode()
    {
        int result = 17;
        result = result * 31 + x;
        result = result * 31 + y;
        return result;
    }
    //adjacent
    public static boolean adjacent(Point p1, Point p2)
    {
        return (p1.x == p2.x && Math.abs(p1.y - p2.y) == 1) || (p1.y == p2.y && Math.abs(p1.x - p2.x) == 1);
    }
    //findOpenAround
    public Optional<Point> findOpenAround(WorldModel world)
    {
        for (int dy = -ORE_REACH; dy <= ORE_REACH; dy++)
        {
            for (int dx = -ORE_REACH; dx <= ORE_REACH; dx++)
            {
                Point newPt = new Point(x + dx, y + dy);
                if (world.withinBounds(newPt) && !world.isOccupied(newPt))
                {
                    return Optional.of(newPt);
                }
            }
        }

        return Optional.empty();
    }

    // this method needs to find a point nearby to generate the scooby snacks at
    /*public Optional<Point> findOpenAroundPoint(WorldModel world, Point center)
    {
        for (int dy = -ORE_REACH; dy <= ORE_REACH; dy++)
        {
            for (int dx = -ORE_REACH; dx <= ORE_REACH; dx++)
            {
                Point newPt = new Point(x + dx, y + dy);
                if (world.withinBounds(newPt) && !world.isOccupied(newPt))
                {
                    // try to generate a point within the vicinity of the mouse click
                    Optional<Point> test1 = Optional.of(newPt);
                    if(Math.sqrt(distanceSquared(test1, center)) <= 20);
                    {
                        int i = 0; // placeholder

                    }

                }
            }
        }

        return Optional.empty();
    }*/

    private int findNearbyHelp(Boolean norp, int distance)
    {
        if(norp)
        {
            return (-1)*distance;
        }
        else
        {
            return distance;
        }
    }

    public Point findNearby(WorldModel world)
    {
        Random rand = new Random();
        int randomdistance = rand.nextInt(10);
        boolean norp = rand.nextBoolean();

        int randx = findNearbyHelp(norp, randomdistance);

        int randomdistance1 = rand.nextInt(10);
        boolean norp1 = rand.nextBoolean();
        int randy = findNearbyHelp(norp1, randomdistance1);

        Point newPt = new Point(this.x + randx, this.y + randy);
        if(world.withinBounds(newPt) && !world.isOccupied(newPt))
        {
            return newPt;
        }
        return new Point(this.x, this.y);

    }

    //distanceSquared
    public static int distanceSquared(Point p1, Point p2)
    {
        int deltaX = p1.x - p2.x;
        int deltaY = p1.y - p2.y;

        return deltaX * deltaX + deltaY * deltaY;
    }

}
