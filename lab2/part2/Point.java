public class Point
{
	double x;
	double y;

	public Point(double x, double y)
	{
		this.x = x;
		this.y = y;

	}
	public double getX()
	{
		return x; 
	}
	public double getY()
	{
		return y;
	}
	public double getRadius()
	{
		double rad = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		return rad;
	}
	public double getAngle()
	{
		return Math.atan(x/y);
	}
	public Point rotate90()
	{
		//based on the quadrant that the point is in, we will perform different operations
		Point newpt = new Point(0, 0);

		if(x == 0 && y == 0)// origin case
		{
			newpt = new Point(0, 0);
		}
		else if(x>0 && y>0) //first quadrant
		{
			// change to fourth quadrant
			newpt = new Point(x, y*(-1));
		}
		else if(x>0 && y<0)//fourth quadrant
		{
			//change to third quadrant
			newpt = new Point(x*(-1), y);
		}
		else if(x<0 && y<0)//third quadrant
		{
			// change to second quadrant
			newpt = new Point(x, y*(-1));
		}
		else if(x<0 && y>0)// second quadrant
		{
			// change to first quadrant
			newpt = new Point(x*(-1), y);
		}
		return newpt;
	}

}