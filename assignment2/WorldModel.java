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


final class WorldModel
{
    public static final String MINER_KEY = "miner";
    public static final int MINER_NUM_PROPERTIES = 7;
    public static final int MINER_ID = 1;
    public static final int MINER_COL = 2;
    public static final int MINER_ROW = 3;
    public static final int MINER_LIMIT = 4;
    public static final int MINER_ACTION_PERIOD = 5;
    public static final int MINER_ANIMATION_PERIOD = 6;

    public static final String OBSTACLE_KEY = "obstacle";
    public static final int OBSTACLE_NUM_PROPERTIES = 4;
    public static final int OBSTACLE_ID = 1;
    public static final int OBSTACLE_COL = 2;
    public static final int OBSTACLE_ROW = 3;

    
    public static final int ORE_NUM_PROPERTIES = 5;
    public static final int ORE_ID = 1;
    public static final int ORE_COL = 2;
    public static final int ORE_ROW = 3;
    public static final int ORE_ACTION_PERIOD = 4;

    public static final String SMITH_KEY = "blacksmith";
    public static final int SMITH_NUM_PROPERTIES = 4;
    public static final int SMITH_ID = 1;
    public static final int SMITH_COL = 2;
    public static final int SMITH_ROW = 3;

    public static final String VEIN_KEY = "vein";
    public static final int VEIN_NUM_PROPERTIES = 5;
    public static final int VEIN_ID = 1;
    public static final int VEIN_COL = 2;
    public static final int VEIN_ROW = 3;
    public static final int VEIN_ACTION_PERIOD = 4;

    public static final int BGND_NUM_PROPERTIES = 4;
    public static final int BGND_ID = 1;
    public static final int BGND_COL = 2;
    public static final int BGND_ROW = 3;

    public static final int KEYED_IMAGE_MIN = 5;
    private static final int KEYED_RED_IDX = 2;
    private static final int KEYED_GREEN_IDX = 3;
    private static final int KEYED_BLUE_IDX = 4;

    public static final int PROPERTY_KEY = 0;

    public static final String BGND_KEY = "background";






    public int numRows;
    public int numCols;
    public Background background[][];
    public Entity occupancy[][];
    public Set<Entity> entities;

    public WorldModel(int numRows, int numCols, Background defaultBackground)
    {
        this.numRows = numRows;
        this.numCols = numCols;
        this.background = new Background[numRows][numCols];
        this.occupancy = new Entity[numRows][numCols];
        this.entities = new HashSet<>();

        for (int row = 0; row < numRows; row++)
        {
            Arrays.fill(this.background[row], defaultBackground);
        }
    }
    //+processImageLine()  
    public static void processImageLine(Map<String, List<PImage>> images, String line, PApplet screen)
    {
        String[] attrs = line.split("\\s");
        if (attrs.length >= 2)
        {
            String key = attrs[0];
            PImage img = screen.loadImage(attrs[1]);
            if (img != null && img.width != -1)
            {
                List<PImage> imgs = Functions.getImages(images, key);
                imgs.add(img);

                if (attrs.length >= KEYED_IMAGE_MIN)
                {
                    int r = Integer.parseInt(attrs[KEYED_RED_IDX]);
                    int g = Integer.parseInt(attrs[KEYED_GREEN_IDX]);
                    int b = Integer.parseInt(attrs[KEYED_BLUE_IDX]);
                    WorldView.setAlpha(img, screen.color(r, g, b), 0);
                }
            }
        }
    } 

    //+processLine(): boolean   
    public boolean processLine(String line, ImageStore imageStore)
    {
        String[] properties = line.split("\\s");
        if (properties.length > 0)
        {
            switch (properties[PROPERTY_KEY])
            {
            case BGND_KEY:
                return parseBackground(properties, imageStore);
            case MINER_KEY:
                return parseMiner(properties, imageStore);
            case OBSTACLE_KEY:
                return parseObstacle(properties, imageStore);
            case ORE_KEY:
                return parseOre(properties, imageStore);
            case SMITH_KEY:
                return parseSmith(properties, imageStore);
            case VEIN_KEY:
                return parseVein(properties, imageStore);
            }
        }

        return false;
    }              
    //+parseBackground(): boolean   

    public boolean parseBackground(String [] properties, ImageStore imageStore)
    {
        if (properties.length == BGND_NUM_PROPERTIES)
        {
            Point pt = new Point(Integer.parseInt(properties[BGND_COL]),
            Integer.parseInt(properties[BGND_ROW]));
            String id = properties[BGND_ID];
            setBackground(pt, new Background(id, imageStore.getImageList(id)));
        }

        return properties.length == BGND_NUM_PROPERTIES;
    }
    //+parseMiner(): boolean 

    public boolean parseMiner(String [] properties, ImageStore imageStore)
    {
        if (properties.length == MINER_NUM_PROPERTIES)
        {
            Point pt = new Point(Integer.parseInt(properties[MINER_COL]),
            Integer.parseInt(properties[MINER_ROW]));
            Entity entity = Entity.createMinerNotFull(properties[MINER_ID],
            Integer.parseInt(properties[MINER_LIMIT]), pt, 
            Integer.parseInt(properties[MINER_ACTION_PERIOD]),
            Integer.parseInt(properties[MINER_ANIMATION_PERIOD]),
            imageStore.getImageList(MINER_KEY));
            entity.tryAddEntity(this);
        }

        return properties.length == MINER_NUM_PROPERTIES;
    }  

    //+parseObstacle(): boolean  
    public boolean parseObstacle(String [] properties, ImageStore imageStore)
    {
        if (properties.length == OBSTACLE_NUM_PROPERTIES)
        {
            Point pt = new Point(Integer.parseInt(properties[OBSTACLE_COL]), Integer.parseInt(properties[OBSTACLE_ROW]));
            Entity entity = Entity.createObstacle(properties[OBSTACLE_ID],
            pt, imageStore.getImageList(OBSTACLE_KEY));
            entity.tryAddEntity(this);
        }

        return properties.length == OBSTACLE_NUM_PROPERTIES;
    } 
    //+parseOre(): boolean  
    public boolean parseOre(String [] properties, ImageStore imageStore)
    {
        if (properties.length == ORE_NUM_PROPERTIES)
        {
            Point pt = new Point(Integer.parseInt(properties[ORE_COL]),
                Integer.parseInt(properties[ORE_ROW]));
            Entity entity = Entity.createOre(properties[ORE_ID],
                pt, Integer.parseInt(properties[ORE_ACTION_PERIOD]),
                imageStore.getImageList(ORE_KEY));
            entity.tryAddEntity(this);
        }

        return properties.length == ORE_NUM_PROPERTIES;
    } 

    //+parseSmith(): boolean 

    public boolean parseSmith(String [] properties, ImageStore imageStore)
    {
        if (properties.length == SMITH_NUM_PROPERTIES)
        {
            Point pt = new Point(Integer.parseInt(properties[SMITH_COL]),
                Integer.parseInt(properties[SMITH_ROW]));
            Entity entity = Entity.createBlacksmith(properties[SMITH_ID],
                pt, imageStore.getImageList(SMITH_KEY));
            entity.tryAddEntity(this);
        }

        return properties.length == SMITH_NUM_PROPERTIES;
    }
    //+parseVein(): boolean    
    public boolean parseVein(String [] properties, ImageStore imageStore)
    {
        if (properties.length == VEIN_NUM_PROPERTIES)
        {
            Point pt = new Point(Integer.parseInt(properties[VEIN_COL]),
                Integer.parseInt(properties[VEIN_ROW]));
            Entity entity = Entity.createVein(properties[VEIN_ID],pt,
                Integer.parseInt(properties[VEIN_ACTION_PERIOD]),
                imageStore.getImageList(VEIN_KEY));
            entity.tryAddEntity(this);
        }

        return properties.length == VEIN_NUM_PROPERTIES;
    }

    //+withinBounds(): boolean 
    public boolean withinBounds(Point pos)
    {
        return pos.y >= 0 && pos.y < numRows &&
            pos.x >= 0 && pos.x < numCols;
    }

    //+isOccupied(): boolean 
    public boolean isOccupied(Point pos)
    {
        return withinBounds(pos) && getOccupancyCell(pos) != null;
    }
   
    //+nearestEntity(): Optional<Entity>
    public Optional<Entity> nearestEntity(List<Entity> entities, Point pos)
    {
        if (entities.isEmpty())
        {
            return Optional.empty();
        }
        else
        {
            Entity nearest = entities.get(0);
            int nearestDistance = Point.distanceSquared(nearest.position, pos);

            for (Entity other : entities)
            {
                int otherDistance = Point.distanceSquared(other.position, pos);

                if (otherDistance < nearestDistance)
                {
                    nearest = other;
                    nearestDistance = otherDistance;
                }
            }

            return Optional.of(nearest);
        }
    }
     
    //+findNearest(): Optional<Entity>
    public Optional<Entity> findNearest(Point pos, EntityKind kind)
    {
        List<Entity> ofType = new LinkedList<>();
        for (Entity entity : entities)
        {
            if (entity.kind == kind)
            {
                ofType.add(entity);
            }
        }

        return nearestEntity(ofType, pos);
    }
    //+addEntity()   
    /*
        Assumes that there is no entity currently occupying the
        intended destination cell.
    */
    public void addEntity(Entity entity)
    {
        if (withinBounds(entity.position))
        {
            setOccupancyCell(entity.position, entity);
            entities.add(entity);
        }
    }
   
    //+removeEntity() 
    public void removeEntity(Entity entity)
    {
        removeEntityAt(entity.position);
    }  
    //+removeEntityAt()  

    public void removeEntityAt(Point pos)
    {
        if (withinBounds(pos)
            && getOccupancyCell(pos) != null)
        {
            Entity entity = getOccupancyCell(pos);

            /* this moves the entity just outside of the grid for
                debugging purposes */
            entity.position = new Point(-1, -1);
            entities.remove(entity);
            setOccupancyCell(pos, null);
        }
    }
                       
    //+getBackgroundImage(): Optional<PImage>
    public Optional<PImage> getBackgroundImage(Point pos)
    {
        if (withinBounds(pos))
        {
            return Optional.of(getBackgroundCell(pos).getCurrentImage());
        }
        else
        {
            return Optional.empty();
        }
    }
    //+setBackground()   
    public void setBackground(Point pos, Background background)
    {
        if (withinBounds(pos))
        {
            setBackgroundCell(pos, background);
        }
    }
    //+getOccupant(): Optional<Entity>
    public Optional<Entity> getOccupant(Point pos)
    {
        if (isOccupied(pos))
        {
            return Optional.of(getOccupancyCell(pos));
        }
        else
        {
            return Optional.empty();
        }
    }      
    //+getOccupancyCell(): Entity 
    public Entity getOccupancyCell( Point pos)
    {
        return occupancy[pos.y][pos.x];
    }

     
    //+setOccupancyCell()  
    public void setOccupancyCell(Point pos, Entity entity)
    {
        occupancy[pos.y][pos.x] = entity;
    }
    
    //+getBackgroundCell(): Background 
    public Background getBackgroundCell(Point pos)
    {
        return background[pos.y][pos.x];
    }
         
    //+setBackgroundCell()
    public void setBackgroundCell(Point pos, Background background)
    {
        this.background[pos.y][pos.x] = background;
    }
                    
                                
                                               
                                                                                  

}
