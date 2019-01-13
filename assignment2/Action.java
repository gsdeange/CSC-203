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



final class Action
{
    public ActionKind kind;
    public Entity entity;
    public WorldModel world;
    public ImageStore imageStore;
    public int repeatCount;

    public Action(ActionKind kind, Entity entity, WorldModel world, ImageStore imageStore, int repeatCount)
    {
        this.kind = kind;
        this.entity = entity;
        this.world = world;
        this.imageStore = imageStore;
        this.repeatCount = repeatCount;
    }

    public void executeAnimationAction(EventScheduler scheduler)
    {
        entity.nextImage();

        if (repeatCount != 1)
        {
            scheduler.scheduleEvent(entity, entity.createAnimationAction(Math.max(repeatCount - 1, 0)), entity.getAnimationPeriod());
        }
    }

    public void executeActivityAction(EventScheduler scheduler)
    {
        switch (this.entity.kind)
        {
        case MINER_FULL:
            entity.executeMinerFullActivity(this.world,this.imageStore, scheduler);
            break;

        case MINER_NOT_FULL:
            entity.executeMinerNotFullActivity(this.world,this.imageStore, scheduler);
            break;

        case ORE:
            entity.executeOreActivity( this.world, this.imageStore,scheduler);
            break;

        case ORE_BLOB:
            entity.executeOreBlobActivity( this.world,this.imageStore, scheduler);
            break;

        case QUAKE:
            entity.executeQuakeActivity(this.world, this.imageStore,scheduler);
            break;

        case VEIN:
            entity.executeVeinActivity(this.world, this.imageStore,scheduler);
            break;

        default:
            throw new UnsupportedOperationException(
                String.format("executeActivityAction not supported for %s",this.entity.kind));
        }
    }

    public void executeAction(EventScheduler scheduler)
    {
        switch (this.kind)
        {
        case ACTIVITY:
            executeActivityAction(scheduler);
            break;

        case ANIMATION:
            executeAnimationAction(scheduler);
            break;
        }
    }
}
