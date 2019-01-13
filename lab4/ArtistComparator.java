import java.util.List;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;


public class ArtistComparator implements Comparator<Song>
{
	@Override
	public int compare(Song a, Song b)
	{
		int compare = a.getArtist().compareTo(b.getArtist());
		if(compare >=  0)
		{
			// a comes after b
			return 1;
		}
		else if(compare < 0)
		{
			// a comes before b
			return -1;
		}
		return 0;
	}
}