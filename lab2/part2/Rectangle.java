public class Rectangle
{
	private final Point topleft;
	private final Point bottomright;

	public Rectangle(Point topleft, Point bottomright)
	{
		this.topleft = topleft;
		this.bottomright = bottomright;
	}
	public Point getTopLeft()
	{
		return topleft;
	}
	public Point getBottomRight()
	{
		return bottomright;
	}
	public double getHeight()
	{
		return (topleft.getY() - bottomright.getY());
	}
	public double getWidth()
	{
		return (topleft.getX() - bottomright.getX());
	}
	public double perimeter()
	{
		return (2*getHeight() + 2*getWidth());
	}

}