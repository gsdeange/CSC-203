import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;


public class Polygon
{
	private final List<Point> points;
	public Polygon(List<Point> points)
	{
		this.points = points;
	}
	public List<Point> getPoints()
	{
		return points;
	}
	public double perimeter()
	{
		List<Point> pointlist = getPoints();
		
		double perimeter = 0;

		for(int i = 0; i<pointlist.size()-1; i++)
		{
			perimeter+=getDistance(pointlist.get(i), pointlist.get(i+1));
		}
		perimeter += getDistance(pointlist.get(0), pointlist.get(pointlist.size()));

		return perimeter;

	}
	public static double getDistance(Point p1, Point p2)
	{
		return Math.sqrt(Math.pow(p1.getY() - p2.getY(), 2) + Math.pow(p1.getX()-p2.getY(), 2));
	}

}