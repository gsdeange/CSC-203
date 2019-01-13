public class Circle
{
	private final Point center;
	private final double radius;
	public Circle(Point center, double radius)
	{
		this.center = center;
		this.radius = radius;
	}
	public Point getCenter()
	{
		return center;
	}
	public double getRadius()
	{
		return radius;
	}
	public double perimeter()
	{
		// perimeter of a circle is pi*2radius
		return 2*3.14*getRadius();
	}
	
}