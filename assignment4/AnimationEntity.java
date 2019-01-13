public interface AnimationEntity extends Entity
{
	public int getAnimationPeriod();
	public Action createAnimationAction(int repeatCount);
	public void nextImage();
}