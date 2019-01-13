import java.awt.Color;
import java.awt.Point;


//done
public class Rectangle implements Shape
{
	double width;
	double height;
	Point topLeft;
	Color color;

	public Rectangle(double width, double height, Point topLeft, Color color)
	{
		this.width = width;
		this.height = height;
		this.topLeft = topLeft;
		this.color = color;
	}
	public Color getColor()
	{
		return color;
	}
	public void setColor(int r, int g, int b)
	{
		color = new Color(r, g, b);
	}
	public double getArea()
	{
		return height*width;
	}
	public double getPerimeter()
	{
		return (height*2 + width*2);
	}
	public void translate(int x, int y)
	{
		topLeft.translate(x, y);
	}
	public double getWidth()
	{
		return width;
	}
	public void setWidth(double newval)
	{
		width = newval;
	}
	public double getHeight()
	{
		return height;
	}
	public void setHeight(double newval)
	{
		height = newval;
	}
	public Point getTopLeft()
	{
		return topLeft;
	}
}