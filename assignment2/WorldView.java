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
    public static void setAlpha(PImage img, int maskColor, int alpha)
    {
        int alphaValue = alpha << 24;
        int nonAlpha = maskColor & COLOR_MASK;
        img.format = PApplet.ARGB;
        img.loadPixels();
        for (int i = 0; i < img.pixels.length; i++)
        {
            if ((img.pixels[i] & COLOR_MASK) == nonAlpha)
            {
                img.pixels[i] = alphaValue | nonAlpha;
            }
        }
        img.updatePixels();
    }
    //shiftView()

    public void shiftView(int colDelta, int rowDelta)
    {
        int newCol = Functions.clamp(viewport.col + colDelta, 0, world.numCols - viewport.numCols);
        int newRow = Functions.clamp(viewport.row + rowDelta, 0, world.numRows - viewport.numRows);

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
    public void drawViewport()
    {
        drawBackground();
        drawEntities();
    }
    public void drawEntities()
    {
        for (Entity entity : world.entities)
        {
            Point pos = entity.position;

            if(viewport.contains(pos))
            {
                Point viewPoint = viewport.worldToViewport(pos.x, pos.y);
                screen.image(entity.getCurrentImage(),
                    viewPoint.x * tileWidth, viewPoint.y * tileHeight);
            }
        }
    }

}
