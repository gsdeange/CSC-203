import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import processing.core.PImage;
import processing.core.PApplet;

public class Obstacle implements Entity
{
	private String id;
	private Point position;
	private List<PImage> images;
	private int imageIndex = 0;

	public Obstacle(String id, Point position, List<PImage> images)
	{
		this.id = id;
    	this.position = position;
    	this.images = images;
	}
	public List<PImage> getImages()
	{
		return images;
	}
	//getID()
	public String getId()
	{
		return id;
	}
	//getPosition
	public Point getPosition()
	{
		return position;
	}
	public void setPosition(Point newposition)
	{
		position = newposition;
	}
	public PImage getCurrentImage()
    {
        return images.get(imageIndex);
    }



}