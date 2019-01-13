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


public class ActivityAction implements Action
{
	public ActiveEntity entity;
    public WorldModel world;
    public ImageStore imageStore;
    
	public ActivityAction(ActiveEntity entity, WorldModel world, ImageStore imageStore)
	{
		this.entity = entity;
		this.world = world;
		this.imageStore = imageStore;
	}
	public void executeAction(EventScheduler scheduler)
    {	
       	entity.executeActivity(world, imageStore, scheduler);
    }
}
