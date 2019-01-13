import processing.core.PApplet;
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

final class WorldView
{
    public PApplet screen;
    public WorldModel world;
    public int tileWidth;
    public int tileHeight;
    public Viewport viewport;


    private static final String VEIN_KEY = "vein";
    private static final int VEIN_NUM_PROPERTIES = 5;
    private static final int VEIN_ID = 1;
    private static final int VEIN_COL = 2;
    private static final int VEIN_ROW = 3;
    private static final int VEIN_ACTION_PERIOD = 4;

    public static final int COLOR_MASK = 0xffffff;

    public WorldView(int numRows, int numCols, PApplet screen, WorldModel world,
        int tileWidth, int tileHeight)
    {
        this.screen = screen;
        this.world = world;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.viewport = new Viewport(numRows, numCols);
    }

    /*
      Called with color for which alpha should be set and alpha value.
      setAlpha(img, color(255, 255, 255), 0));
    */
    
    //shiftView()

    public void shiftView(int colDelta, int rowDelta)
    {
        int newCol = clamp(viewport.col + colDelta, 0, world.numCols - viewport.numCols);
        int newRow = clamp(viewport.row + rowDelta, 0, world.numRows - viewport.numRows);

        viewport.shift(newCol, newRow);
    }

    //drawBackground()

    public void drawBackground()
    {
        for (int row = 0; row < viewport.numRows; row++)
        {
            for (int col = 0; col < viewport.numCols; col++)
            {
                Point worldPoint = viewport.viewportToWorld(col, row);
                Optional<PImage> image = world.getBackgroundImage(worldPoint);
                if (image.isPresent())
                {
                    screen.image(image.get(), col * tileWidth, row * tileHeight);
                }
            }
        }
    }

    public void drawScooby(int x, int y, ImageStore imageStore)
    {
        List<PImage> imageList = imageStore.getImageList("cloud");
        
        world.drawScoobyWorld(x, y, viewport, imageList);
        world.drawScoobyWorld(x+1, y, viewport, imageList);
        world.drawScoobyWorld(x-1, y, viewport, imageList);
        world.drawScoobyWorld(x, y+1, viewport, imageList);
        world.drawScoobyWorld(x, y-1, viewport, imageList);
        world.drawScoobyWorld(x+1, y+1, viewport, imageList);
        world.drawScoobyWorld(x-1, y+1, viewport, imageList);
        world.drawScoobyWorld(x+1, y-1, viewport, imageList);
        world.drawScoobyWorld(x-1, y-1, viewport, imageList);

        drawEntities();
        //System.out.println(" my name is jeff");
    }
    public void drawViewport()
    {
        drawBackground();
        drawEntities();

    }
    public void drawEntities()
    {
        for (Entity entity : world.entities)
        {
            Point pos = entity.getPosition();

            if(viewport.contains(pos))
            {
                Point viewPoint = viewport.worldToViewport(pos.x, pos.y);
                screen.image(entity.getCurrentImage(), viewPoint.x * tileWidth, viewPoint.y * tileHeight);
            }
        }
    }

    public int clamp(int value, int low, int high)
    {
        return Math.min(high, Math.max(value, low));
    }

}
