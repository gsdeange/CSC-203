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

public class GhostEntity extends AnimationEntity
{
	private static final String GHOST_KEY = "ghost";

	private static final String SCOOBY_KEY = "scooby";
	private static final String SCOOBY_ID = "scooby";
	private static final int SCOOBY_ACTION_PERIOD = 5;
    private static final int SCOOBY_ANIMATION_PERIOD = 6;



	public GhostEntity(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod)
    {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
    	//locate the nearest scooby doo
        Optional<Entity> scoobTarget = world.findNearest(position, OreBlobEntity.class);
        //this.actionPeriod = actionPeriod;

        long nextPeriod = actionPeriod;

        if(scoobTarget.isPresent())
        {
            Point tgtPos = scoobTarget.get().getPosition();

            if (moveToGhost(world, scoobTarget.get(), scheduler))
            {
            	// if we are close enough to scooby doo, we will finish him actions
            	//scoobTarget = (ScoobyEntity) scoobTarget;

            	//System.out.println("The ghost has caught scooby doo!!!");
            	//System.out.println("The ghost has caught the miner");
            	world.removeEntity(scoobTarget.get());
            	scheduler.unscheduleAllEvents(scoobTarget.get());

                //QuakeEntity quake = new QuakeEntity(QUAKE_ID, tgtPos, imageStore.getImageList(QUAKE_KEY), QUAKE_ACTION_PERIOD, QUAKE_ANIMATION_PERIOD);

                //world.addEntity(quake);

                nextPeriod += actionPeriod;
                //quake.scheduleActions(scheduler, world, imageStore);
            }
        }

        scheduler.scheduleEvent(this, createActivityAction(world, imageStore), nextPeriod);
    }

    private boolean moveToGhost(WorldModel world, Entity target, EventScheduler scheduler)
    {
        if (Point.adjacent(position, target.getPosition()))
        {
        	// remove scooby doo if at adjacent point
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);
            return true;
        }
        else
        {
            Point nextPos = nextPositionGhost(world, target.getPosition());

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

    public Point nextPositionGhost(WorldModel world, Point destPos)
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

        Predicate<Point> passThrough = (point) -> {return !world.isOccupied(point);};

        List<Point> posList = strat.computePath(getPosition(), destPos, passThrough, getNeighbors);
        //List<Point> posList = astar.computePath(getPosition(), destPos, passThrough, getNeighbors);
       
        /*for(Point p : posList)
        {
            System.out.println("Path: " + p);
        }*/
        
        if(posList.size() == 0)
        {
            return getPosition();
            //return destPos;
        }
        else
        {
            return posList.get(0);
        }
    }
}