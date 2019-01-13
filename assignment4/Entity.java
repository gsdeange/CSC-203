import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.Map;
import processing.core.PImage;

public interface Entity
{
	public List<PImage> getImages();
	public String getId();
	public Point getPosition();
	public void setPosition(Point newposition);
	public PImage getCurrentImage();
	
	


}