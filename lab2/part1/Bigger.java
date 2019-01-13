public class Bigger
{
	public static double whichIsBigger(Circle c, Rectangle r, Polygon p)
	{
		// returns the largest perimeter of the objects.
		double permc = Util.perimeter(c);
		double permr = Util.perimeter(r);
		double permp = Util.perimeter(p);

		if(permc > permr && permc > permp)
		{
			return permc;
		}
		else if(permr > permc && permr > permp)
		{
			return permr;
		}
		else if(permp > permc && permp > permr)
		{
			return permp;
		}
		else
		{
			return 0.0;
		}

	}
}