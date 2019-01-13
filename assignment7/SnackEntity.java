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

final class SnackEntity extends ActionEntity
{
	
    private static final int ORE_NUM_PROPERTIES = 5;
    private static final int ORE_ID = 1;
    private static final int ORE_COL = 2;
    private static final int ORE_ROW = 3;
    private static final int ORE_ACTION_PERIOD = 4;

    private static final String BLOB_KEY = "blob";
    private static final String BLOB_ID_SUFFIX = " -- blob";
    private static final int BLOB_PERIOD_SCALE = 4;
    private static final int BLOB_ANIMATION_MIN = 50;
    private static final int BLOB_ANIMATION_MAX = 150;

    private static final String ORE_ID_PREFIX = "ore -- ";
    private static final int ORE_CORRUPT_MIN = 20000;
    private static final int ORE_CORRUPT_MAX = 30000;
    private static final String ORE_KEY = "ore";


    private static final String SNACK_SUFFIX = " -- snacks";
    private static final String  SNACK_KEY = "scoobysnax"; // image name


    public static final Random rand = new Random();


    // resembles veinEntity

    public SnackEntity(String id, Point position, List<PImage> images, int actionPeriod)
	{
		super(id, position, images, actionPeriod);
	}
    
	//executeActivity
	public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        // scooby snacks will remain there until picked up

        // generates a random point that is open throughout the map
        Optional<Point> openPt = position.findOpenAround(world);

        if (openPt.isPresent())
        {   OreEntity ore = new OreEntity(ORE_ID_PREFIX + id, openPt.get(), imageStore.getImageList(SNACK_KEY), ORE_CORRUPT_MIN + 
                rand.nextInt(ORE_CORRUPT_MAX - ORE_CORRUPT_MIN));
            world.addEntity(ore);
            ore.scheduleActions(scheduler, world, imageStore);
        }

        scheduler.scheduleEvent(this, createActivityAction(world, imageStore), actionPeriod);

    }
	


}