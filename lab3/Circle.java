import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Point;

 
//done
public class Circle implements Shape
{
	double radius;
	Point center;
	Color color;

	public Circle(double radius, Point center, Color color)
	{
		this.radius = radius; 
		this.center = center;
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
		return Math.PI*Math.pow(radius, 2);
	}
	public double getPerimeter()
	{
		return Math.PI*2*radius;
	}
	public void translate(int x, int y)
	{
		center.translate(x, y);
	}
	public double getRadius()
	{
		return radius;
	}
	public void setRadius(double newval)
	{	
		radius = newval;
	}
	public Point getCenter()
	{
		return center;
	}

}