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

public class QuakeEntity extends AnimationEntity
{
    private static final String QUAKE_ID = "quake";
    private static final int QUAKE_ACTION_PERIOD = 1100;
    private static final int QUAKE_ANIMATION_PERIOD = 100;
    private static final int QUAKE_ANIMATION_REPEAT_COUNT = 10;

    public QuakeEntity(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod)
    {
    	super(id, position, images, actionPeriod, animationPeriod);
    }

	//executeActivity
	public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
       scheduler.unscheduleAllEvents(this);
       world.removeEntity(this);
    }
	
	
}