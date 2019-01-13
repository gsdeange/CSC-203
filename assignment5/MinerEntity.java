public abstract class MinerEntity extends AnimationEntity
{
	public int resourceCount;
	public int resourceLimit;

	public MinerEntity(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod, int resourceCount, int resourceLimit)
	{
		super(id, position, images, actionPeriod, animationPeriod);
		this.resourceLimit = resourceLimit;
		this.resourceCount = resourceCount;
	}

	public Point nextPositionMiner(WorldModel world, Point destPos)
    {
        int horiz = Integer.signum(destPos.x - position.x);
        Point newPos = new Point(position.x + horiz, position.y);

        if (horiz == 0 || world.isOccupied(newPos))
        {
            int vert = Integer.signum(destPos.y - position.y);
            newPos = new Point(position.x, position.y + vert);

            if (vert == 0 || world.isOccupied(newPos))
            {
                newPos = position;
            }
        }

        return newPos;
    }


}