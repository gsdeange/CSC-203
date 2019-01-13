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

final class Event
{
    public Action action;
    public long time;
    public Entity entity;

    public Event(Action action, long time, Entity entity)
    {
        this.action = action;
        this.time = time;
        this.entity = entity;
    }
}
