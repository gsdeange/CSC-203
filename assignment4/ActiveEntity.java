public interface ActiveEntity extends Entity
{
	public int getActionPeriod();
	public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);
	public Action createActivityAction(WorldModel world, ImageStore imageStore);
	public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore);
}