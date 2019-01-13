import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import processing.core.*;
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

public abstract class ActionEntity implements Entity
{
	public String id;
	public Point position;
	public List<PImage> images;
	public int imageIndex;
	public int actionPeriod;

	public ActionEntity(String id, Point position, List<PImage> images, int actionPeriod)
	{
		this.id = id;
    	this.position = position;
    	this.images = images;
    	this.imageIndex = 0;
    	this.actionPeriod = actionPeriod;
	}


	//getImages
	public List<PImage> getImages()
	{
		return images;
	}
	public PImage getCurrentImage()
    {
        return images.get(imageIndex);
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
	//getImageIndex
	public int getImageIndex()
	{
		return imageIndex;
	}
	//setPosition
	public void setPosition(Point newposition)
	{
		position = newposition;
	}

	//getActionPeriod
	public int getActionPeriod()
	{
		return actionPeriod;
	}

	//executeActivity() --- abstract ---
	public abstract void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);

	//createActivityAction
    public Action createActivityAction(WorldModel world, ImageStore imageStore)
    {
    	return new ActivityAction(this, world, imageStore);
    }

	//scheduleActions
	public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore)
    {
        scheduler.scheduleEvent(this, createActivityAction(world, imageStore), actionPeriod);

    } 
}