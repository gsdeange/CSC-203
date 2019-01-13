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

public class MinerFullEntity extends MinerEntity
{
	private static final String MINER_KEY = "miner";
    private static final int MINER_NUM_PROPERTIES = 7;
    private static final int MINER_ID = 1;
    private static final int MINER_COL = 2;
    private static final int MINER_ROW = 3;
    private static final int MINER_LIMIT = 4;
    private static final int MINER_ACTION_PERIOD = 5;
    private static final int MINER_ANIMATION_PERIOD = 6;

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

    private boolean collision = true;

    public MinerFullEntity(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod, int resourceCount, int resourceLimit)
    {
    	super(id, position, images, actionPeriod, animationPeriod, resourceCount, resourceLimit);
    }
	//executeActivity
	public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
       Optional<Entity> fullTarget = world.findNearest(this.getPosition(), Blacksmith.class);

         Background miner_background = new Background("cloud", imageStore.getImageList("cloud"));
        // System.out.println(this.getPosition());
         if(world.getBackgroundCell(this.position).equals(miner_background) && collision)
         {
            //System.out.println("We've hit the storm, trigger actions");
            // if the miner enters this background, we set it to look like scooby

            setImageList(imageStore.getImageList(SCOOBY_KEY)); 
            this.actionPeriod = this.actionPeriod/2; // will cause scooby to run like a maniac
            this.resourceLimit = 100000; // set the resource count to unlimited
            collision = false;
            // create ghosts
            Point nextPos = new Point(getPosition().x+20, getPosition().y+25);
            GhostEntity ghost = new GhostEntity(GHOST_KEY, nextPos, imageStore.getImageList(GHOST_KEY), GHOST_ACTION_PERIOD, GHOST_ANIMATION_PERIOD);
            world.addEntity(ghost);
            ghost.scheduleActions(scheduler, world, imageStore);

         }

        if (fullTarget.isPresent() && moveToFull(world, fullTarget.get(), scheduler))
        {
            transformFull(world, scheduler, imageStore);
        }
        else
        {
            scheduler.scheduleEvent(this, createActivityAction(world, imageStore), this.actionPeriod);
        }
    }

    public void setImageList(List<PImage> pList)
    {

        this.images = pList;
    }
	
    //nextPositonMiner
    //moveToFull()
    public boolean moveToFull(WorldModel world, Entity target, EventScheduler scheduler)
    {
        if (Point.adjacent(position, target.getPosition()))
        {
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

    //transformFull()
    public void transformFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore)
    {
        MinerNotFullEntity miner = new MinerNotFullEntity(id, position, images, actionPeriod, animationPeriod, 0, resourceLimit);

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        world.addEntity(miner);
        miner.scheduleActions(scheduler, world, imageStore);
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
}