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



public abstract class MinerEntity extends AnimationEntity
{
	public int resourceCount;
	public int resourceLimit;

	public MinerEntity(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod, int resourceCount, int resourceLimit)
	{
		super(id, position, images, actionPeriod, animationPeriod);
		this.resourceLimit = resourceLimit;
		this.resourceCount = resourceCount;
	}

    public Point nextPositionMiner(WorldModel world, Point destPos)
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