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


final class Entity
{
    public EntityKind kind;
    public String id;
    public Point position;
    public List<PImage> images;
    public int imageIndex;
    public int resourceLimit;
    public int resourceCount;
    public int actionPeriod;
    public int animationPeriod;

     public static final String BLOB_KEY = "blob";
    public static final String BLOB_ID_SUFFIX = " -- blob";
    public static final int BLOB_PERIOD_SCALE = 4;
    public static final int BLOB_ANIMATION_MIN = 50;
    public static final int BLOB_ANIMATION_MAX = 150;

    public static final String ORE_ID_PREFIX = "ore -- ";
    public static final int ORE_CORRUPT_MIN = 20000;
    public static final int ORE_CORRUPT_MAX = 30000;
    public static final String ORE_KEY = "ore";
    

    public static final String QUAKE_KEY = "quake";
    public static final String QUAKE_ID = "quake";
    Q

    public static final Random rand = new Random();



    public Entity(EntityKind kind, String id, Point position,
        List<PImage> images, int resourceLimit, int resourceCount,
        int actionPeriod, int animationPeriod)
    {
        this.kind = kind;
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
        
    }

    //+getCurrentImage(): PImage 
    public PImage getCurrentImage()
    {
        return images.get(imageIndex);
    }


    //+getAnimationPeriod(): int  
    public int getAnimationPeriod()
    {
        switch (this.kind)
        {
            case MINER_FULL:
            case MINER_NOT_FULL:
            case ORE_BLOB:
            case QUAKE:
                return animationPeriod;
            default:
                throw new UnsupportedOperationException(String.format("getAnimationPeriod not supported for %s",this.kind));
        }
    }

    //+nextImage() 
    public void nextImage()
    {
        this.imageIndex = (this.imageIndex + 1) % this.images.size();
    }

     public void executeQuakeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        scheduler.unscheduleAllEvents(this);
        world.removeEntity(this);
    }

    //+executeMinerFullActivity()
    public void executeMinerFullActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> fullTarget = world.findNearest(this.position,EntityKind.BLACKSMITH);

        if (fullTarget.isPresent() && moveToFull(this, world, fullTarget.get(), scheduler))
        {
            transformFull(world, scheduler, imageStore);
        }
        else
        {
            scheduler.scheduleEvent(this, createActivityAction(world, imageStore), this.actionPeriod);
        }
    }         
        //+executeMinerNotFullActivity()

    public void executeMinerNotFullActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> notFullTarget = world.findNearest(position, EntityKind.ORE);

        if (!notFullTarget.isPresent() || !moveToNotFull(this, world, notFullTarget.get(), scheduler) ||
            !transformNotFull(world, scheduler, imageStore))
        {
            scheduler.scheduleEvent(this, createActivityAction(world, imageStore), actionPeriod);
        }
    }
        //+executeOreActivity()   
    public void executeOreActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Point pos = position;    // store current position before removing

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        Entity blob = createOreBlob(id + BLOB_ID_SUFFIX, pos, actionPeriod / BLOB_PERIOD_SCALE, 
            BLOB_ANIMATION_MIN + rand.nextInt(BLOB_ANIMATION_MAX - BLOB_ANIMATION_MIN), imageStore.getImageList( BLOB_KEY));

        world.addEntity(blob);
        scheduleActions(scheduler, world, imageStore);
    }
        //+executeOreBlobActivity() 

    public void executeOreBlobActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> blobTarget = world.findNearest(position, EntityKind.VEIN);
        long nextPeriod = actionPeriod;

        if (blobTarget.isPresent())
        {
            Point tgtPos = blobTarget.get().position;

            if (moveToOreBlob(this, world, blobTarget.get(), scheduler))
            {
                Entity quake = createQuake(tgtPos, imageStore.getImageList(QUAKE_KEY));

                world.addEntity(quake);
                nextPeriod += actionPeriod;
                scheduleActions(scheduler, world, imageStore);
            }
        }

        scheduler.scheduleEvent(this, createActivityAction(world, imageStore), nextPeriod);
    } 
        //+executeVeinActivity()  
    public void executeVeinActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Point> openPt = position.findOpenAround(world);

        if (openPt.isPresent())
        {
            Entity ore = createOre(ORE_ID_PREFIX + id,
                openPt.get(), ORE_CORRUPT_MIN +
                    rand.nextInt(ORE_CORRUPT_MAX - ORE_CORRUPT_MIN),
                imageStore.getImageList(ORE_KEY));
            world.addEntity(ore);
            scheduleActions(scheduler, world, imageStore);
        }

        scheduler.scheduleEvent(this, createActivityAction(world, imageStore), actionPeriod);
    }   

        //+scheduleActions()    
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore)
    {
        switch (kind)
        {
        case MINER_FULL:
            scheduler.scheduleEvent(this, createActivityAction(world, imageStore), actionPeriod);
            scheduler.scheduleEvent(this, createAnimationAction(0), getAnimationPeriod());
            break;

        case MINER_NOT_FULL:
            scheduler.scheduleEvent(this ,createActivityAction(world, imageStore), actionPeriod);
            scheduler.scheduleEvent(this, createAnimationAction( 0), getAnimationPeriod());
            break;

        case ORE:
            scheduler.scheduleEvent(this, createActivityAction(world, imageStore), actionPeriod);
            break;

        case ORE_BLOB:
            scheduler.scheduleEvent(this, createActivityAction(world, imageStore), actionPeriod);
            scheduler.scheduleEvent(this, createAnimationAction(0), getAnimationPeriod());
            break;

        case QUAKE:
            scheduler.scheduleEvent(this, createActivityAction(world, imageStore), actionPeriod);
            scheduler.scheduleEvent(this, createAnimationAction(QUAKE_ANIMATION_REPEAT_COUNT), getAnimationPeriod());
            break;

        case VEIN:
            scheduler.scheduleEvent(this, createActivityAction(world, imageStore), actionPeriod);
            break;

        default:
        }
    } 

        //+transformNotFull(): boolean  
    public boolean transformNotFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore)
    {
        if (resourceCount >= resourceLimit)
        {
            Entity miner = createMinerFull(id, resourceLimit, position, actionPeriod, animationPeriod, images);

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(miner);
            miner.scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }    

    //+transformFull()  
     public void transformFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore)
    {
        Entity miner = createMinerNotFull(id, resourceLimit, position, actionPeriod, animationPeriod, images);

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        world.addEntity(miner);
        miner.scheduleActions(scheduler, world, imageStore);
    }
        //+moveToNotFull(): boolean  
    public boolean moveToNotFull(Entity miner, WorldModel world, Entity target, EventScheduler scheduler)
    {
        if (Point.adjacent(miner.position, target.position))
        {
            miner.resourceCount += 1;
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);

            return true;
        }
        else
        {
            Point nextPos = nextPositionMiner(world, target.position);

            if (!miner.position.equals(nextPos))
            {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent())
                {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                moveEntity(world, nextPos);
            }
            return false;
        }
    }        
        //+moveToFull(): boolean   
    public boolean moveToFull(Entity miner, WorldModel world, Entity target, EventScheduler scheduler)
    {
        if (Point.adjacent(miner.position, target.position))
        {
            return true;
        }
        else
        {
            Point nextPos = nextPositionMiner(world, target.position);

            if (!miner.position.equals(nextPos))
            {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent())
                {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                moveEntity(world, nextPos);
            }
            return false;
        }
    }

        //+moveToOreBlob(): boolean  
    public boolean moveToOreBlob(Entity blob, WorldModel world, Entity target, EventScheduler scheduler)
    {
        if (Point.adjacent(blob.position, target.position))
        {
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);
            return true;
        }
        else
        {
            Point nextPos = nextPositionOreBlob(world, target.position);

            if (!blob.position.equals(nextPos))
            {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent())
                {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                blob.moveEntity(world,nextPos);
            }
            return false;
        }
    }       
        //+nextPositionMiner(): Point 

    public Point nextPositionMiner(WorldModel world, Point destPos)
    {
        int horiz = Integer.signum(destPos.x - position.x);
        Point newPos = new Point(position.x + horiz, position.y);

        if (horiz == 0 || world.isOccupied(newPos))
        {
            int vert = Integer.signum(destPos.y - position.y);
            newPos = new Point(position.x, position.y + vert);

            if (vert == 0 || world.isOccupied(newPos))
            {
                newPos = position;
            }
        }

        return newPos;
    }   
        //+nextPositionOreBlob(): Point  
    public Point nextPositionOreBlob(WorldModel world, Point destPos)
    {
        int horiz = Integer.signum(destPos.x - position.x);
        Point newPos = new Point(position.x + horiz, position.y);

        Optional<Entity> occupant = world.getOccupant(newPos);

        if (horiz == 0 ||
            (occupant.isPresent() && !(occupant.get().kind == EntityKind.ORE)))
        {
            int vert = Integer.signum(destPos.y - position.y);
            newPos = new Point(position.x, position.y + vert);
            occupant = world.getOccupant(newPos);

            if (vert == 0 ||
                (occupant.isPresent() && !(occupant.get().kind == EntityKind.ORE)))
            {
                newPos = position;
            }
        }

        return newPos;
    }
        //+tryAddEntity() 
    public void tryAddEntity(WorldModel world)
    {
        if (world.isOccupied(position))
        {
            // arguably the wrong type of exception, but we are not
            // defining our own exceptions yet
            throw new IllegalArgumentException("position occupied");
        }

        world.addEntity(this);
    }                            
        //+moveEntity()  
    public void moveEntity(WorldModel world, Point pos)
    {
        Point oldPos = position;
        if (world.withinBounds(pos) && !pos.equals(oldPos))
        {
            world.setOccupancyCell(oldPos, null);
            world.removeEntityAt(pos);
            world.setOccupancyCell(pos, this);
            position = pos;
        }
    }

        //+createAnimationAction(): Action

    public Action createAnimationAction(int repeatCount)
    {
        return new Action(ActionKind.ANIMATION, this, null, null, repeatCount);
    }

        //+createActivityAction(): Action
    public Action createActivityAction(WorldModel world,
        ImageStore imageStore)
    {
        return new Action(ActionKind.ACTIVITY, this, world, imageStore, 0);
    }
        //+createBlacksmith(): Entity 
    public static Entity createBlacksmith(String id, Point position, List<PImage> images)
    {
        return new Entity(EntityKind.BLACKSMITH, id, position, images, 0, 0, 0, 0);
    }  
        //+createMinerFull(): Entity  
    public static Entity createMinerFull(String id, int resourceLimit, Point position, int actionPeriod, int animationPeriod, List<PImage> images)
    {
        return new Entity(EntityKind.MINER_FULL, id, position, images, resourceLimit, resourceLimit, actionPeriod, animationPeriod);
    }
           
        //+createMinerNotFull(): Entity 
    public static Entity createMinerNotFull(String id, int resourceLimit, Point position, int actionPeriod, int animationPeriod, List<PImage> images)
    {
        return new Entity(EntityKind.MINER_NOT_FULL, id, position, images, resourceLimit, 0, actionPeriod, animationPeriod);
    }
  
        //+createObstacle(): Entity   
    public static Entity createObstacle(String id, Point position, List<PImage> images)
    {
        return new Entity(EntityKind.OBSTACLE, id, position, images, 0, 0, 0, 0);
    } 
    //+createQuake(): Entity 



    // createOre() 
    public static Entity createOre(String id, Point position, int actionPeriod, List<PImage> images)
    {
        return new Entity(EntityKind.ORE, id, position, images, 0, 0, actionPeriod, 0);
    }

    //+createVein(): Entity

    public static Entity createVein(String id, Point position, int actionPeriod, List<PImage> images)
    {
        return new Entity(EntityKind.VEIN, id, position, images, 0, 0, actionPeriod, 0);
    }
    
    //+createOreBlob(): Entity 
    public static Entity createQuake(Point position, List<PImage> images)
    {
        return new Entity(EntityKind.QUAKE, QUAKE_ID, position, images, 0, 0, QUAKE_ACTION_PERIOD, QUAKE_ANIMATION_PERIOD);
    }


    public static Entity createOreBlob(String id, Point position, int actionPeriod, int animationPeriod, List<PImage> images)
    {
        return new Entity(EntityKind.ORE_BLOB, id, position, images, 0, 0, actionPeriod, animationPeriod);
    }







}
