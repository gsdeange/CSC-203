

public class Point {

    public final double x;
    public final double y;
    public final double z;

    public Point(double x, double y, double z) 
	{
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String toString() {
        return "Point(" + x + ", " + y + ", " + z + ")";
    }   
	
	/*@Override
	public void translate(int dx, int dy, int dz)
	{
		this.x = x+dx;
		this.y = y+dy;
		this.z = z+dz;
	}*/
	
}
