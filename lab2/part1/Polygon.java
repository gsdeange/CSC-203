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

}