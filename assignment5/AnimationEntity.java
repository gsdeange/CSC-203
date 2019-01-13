import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import processing.core.*;
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

public abstract class AnimationEntity extends ActionEntity
{
	public int animationPeriod;

	public AnimationEntity(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod)
    {
    	super(id, position, images, actionPeriod);
    	this.animationPeriod = animationPeriod;
    }

	//public int getAnimationPeriod();
    public int getAnimationPeriod()
    {
    	return animationPeriod;
    }
	//public void createAnimationAction();
	public Action createAnimationAction(int repeatCount)
	{
		return new AnimationAction(this, null, null, repeatCount);
	}

	public void nextImage()
    {
        this.imageIndex = (this.imageIndex + 1) % this.images.size();
    }

	//executeActivity() --- abstract ---
	public abstract void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);

}
