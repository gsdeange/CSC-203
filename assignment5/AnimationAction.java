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


public class AnimationAction implements Action
{
	public AnimationEntity entity;
    public WorldModel world;
    public ImageStore imageStore;
    public int repeatCount;

	public AnimationAction(AnimationEntity entity, WorldModel world, ImageStore imageStore, int repeatCount)
	{
		this.entity = entity;
		this.world = world;
		this.imageStore = imageStore;
		this.repeatCount = repeatCount;
	}

	public void executeAction(EventScheduler scheduler)
    {
        entity.nextImage();

        if (repeatCount != 1)
        {
            scheduler.scheduleEvent(entity, entity.createAnimationAction(Math.max(repeatCount - 1, 0)), entity.getAnimationPeriod());
        }
    }

}