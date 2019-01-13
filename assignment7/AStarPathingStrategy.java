import java.util.List;
import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;


public class AStarPathingStrategy implements PathingStrategy
{

	static final int BIG_VALUE = 1000000000;

	@Override
	public List<Point> computePath(final Point start, final Point end, Predicate<Point> canPassThrough, Function<Point, List<Point>> potentialNeighbors)
	{
		//astar code
		// fucntion returns a list of nodes
		List<Point> result = new ArrayList<Point>(); 

		//visited nodes (already evaluated)
        HashSet<Point> closedset = new HashSet<>();

        List<Point> closedset2 = new ArrayList<>();

        //setof nodes not evaluated yet
        HashSet<Point> openset = new HashSet<>();
        openset.add(start);

        List<Point> openset2 = new ArrayList<>();
        openset2.add(start);


        //most efficient neighbor
        HashMap<Point, Point> cameFrom = new HashMap<>();

        //contains cost of going from Point to another point
        HashMap<Point, Integer> gscore = new HashMap<>();

        // set the cost of going to start to start 0
        gscore.put(start, 0);

        // cost of going from start node to goal
        HashMap<Point, Integer> fscore = new HashMap<>();

        // set the first node's cost to value
        fscore.put(start, heuristic_cost(start, end));
        

        while(!openset.isEmpty())
        {
        	// lowest f-score value
        	//Point current = getLowest(openset, fscore);

        	Point current = openset2.get(0);
        	int lowest = fscore.get(openset2.get(0));
        	//System.out.println("Current: "+ current);
        	//System.out.println("My name is jeff");

        	//System.out.println("Current: " + current + " end: " + end);

        	for(Point p: openset2)
        	{
        		if(fscore.get(p) < lowest)
        		{
        			current = p;
        			lowest = fscore.get(p);
        		}
        	}

        	if(current.equals(end))
        	{
        		// never reaches this spot
        		//System.out.println("Banaananana Batman!!!!!!!");
        		List<Point> reconstructed =  reconstruct_path(cameFrom, current);
        		Collections.reverse(reconstructed);
        		reconstructed.remove(0);
        		//reconstructed.remove(reconstructed.size());
        		return reconstructed;
        	}
        	openset2.remove(current);
        	closedset2.add(current);

        	for(Point neighbor : potentialNeighbors.apply(current))
        	{
        		if(closedset2.contains(neighbor))
        		{
        			continue;
        		}

        		if(!neighbor.equals(end) && !canPassThrough.test(neighbor))
        		{
        			continue;
        		}
        		if(!openset2.contains(neighbor))
        		{
        			openset2.add(neighbor);
        		}

        		//System.out.println(neighbor);

        		int tentative_gscore = gscore.getOrDefault(current, BIG_VALUE) + (int) dist_between(current, neighbor);
        		if(tentative_gscore >= gscore.getOrDefault(neighbor, BIG_VALUE))
        		{
        			continue;
        		}

        		// best available path
        		cameFrom.put(neighbor, current);
        		gscore.put(neighbor, tentative_gscore);
        		fscore.put(neighbor, gscore.getOrDefault(neighbor, BIG_VALUE) + heuristic_cost(neighbor, end));
        	}
        }
        //return failure
        return result;
	}

	private int dist_between(Point current, Point neighbor)
	{
		return Math.abs(current.x - neighbor.x) + Math.abs(current.y - neighbor.y);
	}

	private Point getLowest(HashSet<Point> set, HashMap<Point, Integer> map)
	{
		int minFscore = BIG_VALUE;
		Point current = null;
		//int count = 0;

		for(Point p : set)
		{
			//count += 1;

			//System.out.println("start fscore":  + map.putIfAbsent(p, Integer.MAX_VALUE));
			if(map.getOrDefault(p, BIG_VALUE) < minFscore)
			{
				//count ++;
				//System.out.println(p);
				minFscore = map.getOrDefault(p, BIG_VALUE);
				current = p;
			}
		}
		//System.out.println("Count: " + count);

		return current;
		/*for(Map.Entry<Point, Integer> entry : map.entrySet())
		{
			if(min == null || min.getValue() > entry.getValue())
			{
				min = entry;
			}
		}
		return min.getKey();*/
	}

	private int heuristic_cost(Point current, Point goal)
	{
		// heuristic cost function (4 directional movement)
    	int dx = Math.abs(current.x - goal.x);
    	int dy = Math.abs(current.y - goal.y);

    	return 1* (dx + dy);
	}


	private Point checkWithin(HashSet<Point> set, Point current)
	{
		if(set.contains(current))
		{
			return current;
		}
		return null;
	}

	private List<Point> getKeys(HashMap<Point, Point> cameFrom)
	{
		List<Point> keys = new ArrayList<>();
		for(Point p : cameFrom.keySet())
		{
			keys.add(p);
		}
		return keys;
	}

	private List<Point> reconstruct_path(HashMap<Point, Point> cameFrom, Point current)
	{
		List<Point>  total_path = new ArrayList<>();
		
		total_path.add(current);
		while(cameFrom.containsKey(current))
		{
			//System.out.println("Hello ");
			current = cameFrom.get(current);
			total_path.add(current);
			
		} 
		List<Point> reverse = new ArrayList<>();
		return total_path;
	}
}