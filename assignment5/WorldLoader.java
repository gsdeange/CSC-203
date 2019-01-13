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


public class WorldLoader
{
	// load()
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


	//processLine()

	//parseBackground()
	//parseMiner()
	//parseObstacle()
	//parseOre()
	//parse Blacksmith()


	//parse Vein()
}