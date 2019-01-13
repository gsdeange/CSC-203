import java.util.Arrays;
import java.util.ArrayList;
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
import java.util.function.Function;
import java.util.function.Predicate;

public class OreBlobEntity extends AnimationEntity
{

	private static final String QUAKE_KEY = "quake";
	public static final String QUAKE_ID = "quake";
	public static final int QUAKE_ACTION_PERIOD = 1100;
    public static final int QUAKE_ANIMATION_PERIOD = 100;
    public static final int QUAKE_ANIMATION_REPEAT_COUNT = 10;



   public OreBlobEntity(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod)
    {
        super(id, position, images, actionPeriod, animationPeriod);
    }
	
    
	//executeActivity
	public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> blobTarget = world.findNearest(position, VeinEntity.class);
        long nextPeriod = actionPeriod;

        if (blobTarget.isPresent())
        {
            Point tgtPos = blobTarget.get().getPosition();

            if (moveToOreBlob(world, blobTarget.get(), scheduler))
            {
                QuakeEntity quake = new QuakeEntity(QUAKE_ID, tgtPos, imageStore.getImageList(QUAKE_KEY), QUAKE_ACTION_PERIOD, QUAKE_ANIMATION_PERIOD);

                world.addEntity(quake);
                nextPeriod += actionPeriod;
                //System.out.println("action: " + actionPeriod);
                quake.scheduleActions(scheduler, world, imageStore);
            }
        }

        scheduler.scheduleEvent(this, createActivityAction(world, imageStore), nextPeriod);
    }
    private Point nextPositionOreBlob(WorldModel world, Point destPos)
    {
        SingleStepPathingStrategy strat = new SingleStepPathingStrategy();

        AStarPathingStrategy astar = new AStarPathingStrategy();

        Function<Point, List<Point>> getNeighbors = (start) -> {
            List<Point> potentialNeighbors = new ArrayList<>();
            potentialNeighbors.add(new Point(start.x + 1, start.y));
            potentialNeighbors.add(new Point(start.x, start.y + 1));
            potentialNeighbors.add(new Point(start.x - 1, start.y));
            potentialNeighbors.add(new Point(start.x, start.y -1));
            return potentialNeighbors;
        };

        Predicate<Point> passThrough = (point) -> {
            
            Optional<Entity> occupant = world.getOccupant(point);
            /*if(!world.isOccupied(point))
            {
                return false;
            }
            else
            {
                return (occupant.isPresent() && (occupant.get() instanceof OreEntity));
            }*/
            return !(world.isOccupied(point) || occupant.isPresent() && occupant.get() instanceof OreEntity);
        };

        List<Point> posList = strat.computePath(getPosition(), destPos, passThrough, getNeighbors);
        //List<Point> posList = astar.computePath(getPosition(), destPos, passThrough, getNeighbors);
       
        if(posList.size() == 0)
        {
            return getPosition();
        }
        else
        {
            return posList.get(0);
        }

    }

    //moveToOreBlob
    private boolean moveToOreBlob(WorldModel world, Entity target, EventScheduler scheduler)
    {
        if (Point.adjacent(position, target.getPosition()))
        {
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);
            return true;
        }
        else
        {
            Point nextPos = nextPositionOreBlob(world, target.getPosition());

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