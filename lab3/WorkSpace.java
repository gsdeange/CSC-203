import java.util.List;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Point;

public class WorkSpace 
{

	private List<Shape> shapes = new ArrayList<Shape>();

	public WorkSpace()
	{

	}
	public void add(Shape shape)
	{
		shapes.add(shape);
	}
	public Shape get(int index)
	{
		return shapes.get(index);
	}
	public int size()
	{
		return shapes.size();
	}
	public List<Circle> getCircles()
	{
		List<Circle> circles = new ArrayList<Circle>();
		for(int i = 0; i<shapes.size(); i++)
		{
			if((shapes.get(i) instanceof Circle) == true)
			{
				circles.add((Circle)shapes.get(i));
			}
		}
		return circles;
	}
	public List<Rectangle> getRectangles()
	{
		List<Rectangle> rectangles = new ArrayList<Rectangle>();

		for(int i = 0; i<shapes.size(); i++)
		{
			if((shapes.get(i) instanceof Rectangle) == true)
			{
				rectangles.add((Rectangle)shapes.get(i));
			}
		}
		return rectangles;
	}
	public List<Triangle> getTriangles()
	{
		List<Triangle> triangles = new ArrayList<Triangle>();
		for(int i = 0; i<shapes.size(); i++)
		{
			if((shapes.get(i) instanceof Triangle) == true)
			{
				triangles.add((Triangle)shapes.get(i));
			}
		}
		return triangles;
	}
	public List<ConvexPolygon> getConvexPolygons()
	{
		List<ConvexPolygon> polygons = new ArrayList<ConvexPolygon>();
		for(int i = 0; i<shapes.size(); i++)
		{
			if((shapes.get(i) instanceof ConvexPolygon) == true)
			{
				polygons.add((ConvexPolygon)shapes.get(i));
			}
		}
		return polygons;
	}
	public List<Shape> getShapesByColor(Color color) 
	{
		List<Shape> colorshapes = new ArrayList<Shape>();
		for(int i = 0; i<shapes.size(); i++)
		{
			Color shapecolor = shapes.get(i).getColor();
			if(shapecolor.equals(color))
			{
				colorshapes.add(shapes.get(i));
			}
		}
		return colorshapes;
	}
	public double getAreaOfAllShapes()
	{
		double sum = 0.0;
		for(int i = 0; i<shapes.size(); i++)
		{
			sum+= shapes.get(i).getArea();
		}
		return sum;
	}
	public double getPerimeterOfAllShapes()
	{
		double sum = 0.0;
		for(int i = 0; i<shapes.size(); i++)
		{
			sum+= shapes.get(i).getPerimeter();
		}
		return sum;
	}



}