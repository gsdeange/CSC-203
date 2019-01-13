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

final class Background
{
    public String id;
    public List<PImage> images;
    public int imageIndex;

    public Background(String id, List<PImage> images)
    {
        this.id = id;
        this.images = images;
    }

    public PImage getCurrentImage()
    {
    	return images.get(imageIndex);
        
    }
    @Override
   public boolean equals(Object o) 
   {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      Background that = (Background) o;

      return id != null ? id.equals(that.id) : that.id == null;
   }
}
