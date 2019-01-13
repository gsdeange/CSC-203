public class Bigger
{
	public static double whichIsBigger(Circle c, Rectangle r, Polygon p)
	{
		// returns the largest perimeter of the objects.
		double permc = c.perimeter();
		double permr = r.perimeter();
		double permp = p.perimeter();

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