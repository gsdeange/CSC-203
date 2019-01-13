import java.awt.Color;
import java.awt.Point;



interface Shape
{

	public Color getColor();

	public void setColor(int r, int g, int b);

	public double getArea();

	public double getPerimeter();
	
	public void translate(int x, int y);
	


}
