import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
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

final class EventScheduler
{
    public PriorityQueue<Event> eventQueue;
    public Map<Entity, List<Event>> pendingEvents;
    public double timeScale;

       

    public EventScheduler(double timeScale)
    {
        this.eventQueue = new PriorityQueue<>(new EventComparator());
        this.pendingEvents = new HashMap<>();
        this.timeScale = timeScale;
    }

    //+scheduleEvent()
    public void scheduleEvent(Entity entity, Action action, long afterPeriod)
    {
        long time = System.currentTimeMillis() + (long)(afterPeriod * this.timeScale);
        Event event = new Event(action, time, entity);

        this.eventQueue.add(event);

        // update list of pending events for the given entity
        List<Event> pending = this.pendingEvents.getOrDefault(entity, new LinkedList<>());
        pending.add(event);
        this.pendingEvents.put(entity, pending);
    }
	//+unscheduleAllEvents()  
    public void unscheduleAllEvents(Entity entity)
    {
        List<Event> pending = this.pendingEvents.remove(entity);

        if (pending != null)
        {
            for (Event event : pending)
            {
                this.eventQueue.remove(event);
            }
        }
    }

	//+removePendingEvent() 
	public void removePendingEvent(Event event)
    {
        List<Event> pending = this.pendingEvents.get(event.entity);

        if (pending != null)
        {
            pending.remove(event);
        }
    }    
	//+updateOnTime()

	public void updateOnTime(long time)
    {
        while (!this.eventQueue.isEmpty() && this.eventQueue.peek().time < time)
        {
            Event next = this.eventQueue.poll();
            
            removePendingEvent(next);
            
            next.action.executeAction(this);
        }
    }
}
