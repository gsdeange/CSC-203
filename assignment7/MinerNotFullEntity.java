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

public class MinerNotFullEntity extends MinerEntity
{
	private static final String MINER_KEY = "miner";
    private static final int MINER_NUM_PROPERTIES = 7;
    private static final int MINER_ID = 1;
    private static final int MINER_COL = 2;
    private static final int MINER_ROW = 3;
    private static final int MINER_LIMIT = 4;
    private static final int MINER_ACTION_PERIOD = 5;
    private static final int MINER_ANIMATION_PERIOD = 6;

    // ghost info
    private static final String GHOST_KEY = "ghost";
    private static final int GHOST_NUM_PROPERTIES = 6;
    private static final int GHOST_ID = 1;
    private static final int GHOST_COL = 2;
    private static final int GHOST_ROW = 3; 
    private static final int GHOST_ACTION_PERIOD = 4;
    private static final int GHOST_ANIMATION_PERIOD = 5;

    private static final String SCOOBY_KEY = "scooby1";
    private static final String SCOOBY_ID = "scooby";
    private static final int SCOOBY_ACTION_PERIOD = 5;
    private static final int SCOOBY_ANIMATION_PERIOD = 6;

    private boolean collision = false;

    public MinerNotFullEntity(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod, int resourceCount, int resourceLimit)
    {
    	super(id, position, images, actionPeriod, animationPeriod, resourceCount, resourceLimit);
    	
    }

	//executeActivity
	public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
         Optional<Entity> notFullTarget = world.findNearest(position, OreEntity.class);

         Background miner_background = new Background("cloud", imageStore.getImageList("cloud"));
         //System.out.println(this.getPosition());
         if(world.getBackgroundCell(this.position).equals(miner_background) && !collision)
         {
            collision = true;
            //System.out.println("We've hit the storm, trigger actions");
            // if the miner enters this background, we set it to look like scooby

            setImageList(imageStore.getImageList(SCOOBY_KEY)); 
            this.actionPeriod = this.actionPeriod/3; // will cause scooby to run like a maniac
            this.resourceLimit = 100000; // set the resource count to unlimited

            
            //create ghost1
            Point nextPos = new Point(getPosition().x, getPosition().y);
            nextPos = position.findNearby(world);
            
            GhostEntity ghost = new GhostEntity(GHOST_KEY, nextPos, imageStore.getImageList(GHOST_KEY), GHOST_ACTION_PERIOD, GHOST_ANIMATION_PERIOD);
            world.addEntity(ghost);
            ghost.scheduleActions(scheduler, world, imageStore);

            // create ghost2
            Point nextPos2 = new Point(getPosition().x + 20 , getPosition().y + 20);
            nextPos2 = position.findNearby(world);
         
            GhostEntity ghost2 = new GhostEntity(GHOST_KEY, nextPos2, imageStore.getImageList(GHOST_KEY), GHOST_ACTION_PERIOD, GHOST_ANIMATION_PERIOD);
            world.addEntity(ghost2);
            ghost2.scheduleActions(scheduler, world, imageStore);

         }

        if (!notFullTarget.isPresent() || !moveToNotFull(world, notFullTarget.get(), scheduler) ||
            !transformNotFull(world, scheduler, imageStore))
        {
            scheduler.scheduleEvent(this, createActivityAction(world, imageStore), actionPeriod);
        }
    }

    public void createGhost(WorldModel world, ImageStore imageStore, EventScheduler scheduler, int number)
    {
        for(int i = 0; i<number; i++)
        {
            Point nextPos = new Point(getPosition().x, getPosition().y);
            if(position.findNearby(world) != null && !world.isOccupied(nextPos) && world.withinBounds(nextPos))
            {
                nextPos = position.findNearby(world);
            }   
            GhostEntity ghost = new GhostEntity(GHOST_KEY, nextPos, imageStore.getImageList(GHOST_KEY), GHOST_ACTION_PERIOD, GHOST_ANIMATION_PERIOD);
            world.addEntity(ghost);
            ghost.scheduleActions(scheduler, world, imageStore);
        }
    }
	
    public void setImageList(List<PImage> pList)
    {

        this.images = pList;
    }
	

    //getResourceLimit()
    public int getResourceLimit()
    {
    	return resourceLimit;
    }

    //transformNotFullMiner()
    public boolean transformNotFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore)
    {
        if (resourceCount >= resourceLimit)
        {
            MinerFullEntity miner = new MinerFullEntity(id, position, images, actionPeriod, animationPeriod, resourceLimit, resourceLimit);

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(miner);
            miner.scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    } 
    public void transformScooby(WorldModel world, EventScheduler scheduler, ImageStore imageStore)
    {   
            // replace the miner with scooyby
            ScoobyEntity scoob = new ScoobyEntity(SCOOBY_KEY, position, imageStore.getImageList(SCOOBY_KEY), actionPeriod/2 , animationPeriod, 0, 1000);

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(scoob);
            scoob.scheduleActions(scheduler, world, imageStore);
    }   

    //moveToNotFull()
    public boolean moveToNotFull(WorldModel world, Entity target, EventScheduler scheduler)
    {
        if (Point.adjacent(position, target.getPosition()))
        {
            resourceCount += 1;
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);

            return true;
        }
        else
        {
            Point nextPos = nextPositionMiner(world, target.getPosition());

            if (!position.equals(nextPos))
            {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent())
                {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(nextPos, this);
            }
            return false;
        }
    }
}