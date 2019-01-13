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

public class VeinEntity extends ActionEntity
{
	

	private static final String VEIN_KEY = "vein";
    private static final int VEIN_NUM_PROPERTIES = 5;
    private static final int VEIN_ID = 1;
    private static final int VEIN_COL = 2;
    private static final int VEIN_ROW = 3;
    private static final int VEIN_ACTION_PERIOD = 4;

    private static final String ORE_ID_PREFIX = "ore -- ";
    private static final int ORE_CORRUPT_MIN = 20000;
    private static final int ORE_CORRUPT_MAX = 30000;
    private static final String ORE_KEY = "ore";

    public static final Random rand = new Random();


    public VeinEntity(String id, Point position, List<PImage> images, int actionPeriod)
	{
		super(id, position, images, actionPeriod);
	}

	//executeActivity check
	public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Point> openPt = position.findOpenAround(world);

        if (openPt.isPresent())
        {
            OreEntity ore = new OreEntity(ORE_ID_PREFIX + id,
                openPt.get(),
                imageStore.getImageList(ORE_KEY), ORE_CORRUPT_MIN +
                    rand.nextInt(ORE_CORRUPT_MAX - ORE_CORRUPT_MIN));
            world.addEntity(ore);
            ore.scheduleActions(scheduler, world, imageStore);
        }

        scheduler.scheduleEvent(this, createActivityAction(world, imageStore), actionPeriod);
    }
	


	
}