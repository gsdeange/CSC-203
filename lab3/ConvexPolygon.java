import java.util.List;
import java.awt.Color;
import java.awt.Point;

public class ConvexPolygon implements Shape
{
	Point[] vertices;
	Color color;
	public ConvexPolygon(Point[] vertices, Color color)
	{
		this.vertices = vertices;
		this.color = color;
	}
	public Point getVertex(int index)
	{
		return vertices[index];
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
		int j = vertices.length-1 ;
		for(int i = 0; i<vertices.length; i++)
		{
			area = area + ((vertices[j].x + vertices[i].x) * (vertices[j].y - vertices[i].y));
			j = i;
		}
		return Math.abs(area/2.0);
	}
	public double getPerimeter()
	{
		double distance = 0.0;
		int len = vertices.length;
		for(int i = 0; i<len; i++)
		{
			distance += getdistance(vertices[i].x, vertices[i].y, vertices[(i+1)%len].x, vertices[(i+1)%len].y);
		}
		return distance;
	}
	public double getdistance(int x1, int y1, int x2, int y2)
	{
		return Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2));
	}
	public void translate(int x, int y)
	{
		for(int i = 0; i<vertices.length; i++)
		{
			vertices[i].translate(x, y);
		}
	}
}