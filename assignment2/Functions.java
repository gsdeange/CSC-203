import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import processing.core.PImage;
import processing.core.PApplet;

final class Functions
{
   
//------------------------------------------------------------------------------



    public static List<PImage> getImages(Map<String, List<PImage>> images,
        String key)
    {
        List<PImage> imgs = images.get(key);
        if (imgs == null)
        {
            imgs = new LinkedList<>();
            images.put(key, imgs);
        }
        return imgs;
    }

    public static void load(Scanner in, WorldModel world, ImageStore imageStore)
    {
        int lineNumber = 0;
        while (in.hasNextLine())
        {
            try
            {
                if (!world.processLine(in.nextLine(), imageStore))
                {
                    System.err.println(String.format("invalid entry on line %d",
                        lineNumber));
                }
            }
            catch (NumberFormatException e)
            {
                System.err.println(String.format("invalid entry on line %d",
                    lineNumber));
            }
            catch (IllegalArgumentException e)
            {
                System.err.println(String.format("issue on line %d: %s",
                    lineNumber, e.getMessage()));
            }
            lineNumber++;
        }
    }


    public static int clamp(int value, int low, int high)
    {
        return Math.min(high, Math.max(value, low));
    }

    

    


   

    

    
    
    



    
   
}
