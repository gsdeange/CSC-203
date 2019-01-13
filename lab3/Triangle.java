import java.awt.Color;
import java.awt.Point;
import java.util.List;
import java.util.ArrayList;

public class Triangle implements Shape
{
	Point a;
	Point b;
	Point c;
	Color color;
	List<Point> points = new ArrayList<Point>();

	public Triangle(Point point, Point point2, Point point3, Color color)
	{
		a = point;
		b = point2;
		c = point3;
		this.color = color;
		points.add(a);
		points.add(b);
		points.add(c);
	}
	public Point getVertexA()
	{
		return a;
	}
	public Point getVertexB()
	{
		return b;
	}
	public Point getVertexC()
	{
		return c; 
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
		double area = 0.0;
		int j = points.size()-1 ;
		for(int i = 0; i<points.size(); i++)
		{
			area = area + ((points.get(j).x + points.get(i).x) * (points.get(j).y - points.get(i).y));
			j = i;
		}
		return Math.abs(area/2);

	}
	public double getPerimeter()
	{
		double distance = 0.0;
		int len = points.size();
		for(int i = 0; i<len; i++)
		{
			distance += getdistance(points.get(i).x, points.get(i).y, points.get((i+1)%len).x, points.get((i+1)%len).y);
		}
		return distance;
	}
	public double getdistance(int x1, int y1, int x2, int y2)
	{
		return Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2));
	}
	public void translate(int x, int y)
	{
		for(int i = 0; i<points.size(); i++)
		{
			points.get(i).translate(x, y);
		}
	}
}