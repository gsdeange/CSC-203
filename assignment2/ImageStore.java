import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import processing.core.PImage;
import processing.core.PApplet;

final class ImageStore
{
    public Map<String, List<PImage>> images;
    public List<PImage> defaultImages;

    public ImageStore(PImage defaultImage)
    {
        this.images = new HashMap<>();
        defaultImages = new LinkedList<>();
        defaultImages.add(defaultImage);
    }
    //getImageList()
    public List<PImage> getImageList(String key)
    {
        return images.getOrDefault(key, defaultImages);
    }
    // loadImages()

    public void loadImages(Scanner in, PApplet screen)
    {
        int lineNumber = 0;
        while (in.hasNextLine())
        {
            try
            {
                WorldModel.processImageLine(images, in.nextLine(), screen);
            }
            catch (NumberFormatException e)
            {
                System.out.println(String.format("Image format error on line %d",
                    lineNumber));
            }
            lineNumber++;
        }
    }
}
