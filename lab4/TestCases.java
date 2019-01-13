import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.util.Comparator;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.Before;

public class TestCases
{
    private static final Song[] songs = new Song[] 
    {
            new Song("Decemberists", "The Mariner's Revenge Song", 2005),
            new Song("Rogue Wave", "Love's Lost Guarantee", 2005),
            new Song("Avett Brothers", "Talk on Indolence", 2006),
            new Song("Gerry Rafferty", "Baker Street", 1998),
            new Song("City and Colour", "Sleeping Sickness", 2007),
            new Song("Foo Fighters", "Baker Street", 1997),
            new Song("Queen", "Bohemian Rhapsody", 1975),
            new Song("Gerry Rafferty", "Baker Street", 1978)
    };

    @Test
    public void testArtistComparator()
    {
        Song song1 = new Song("Decemberists", "The Mariner's Revenge Song", 2005);
        Song song2 = new Song("Queen", "Bohemian Rhapsody", 1975);
        ArtistComparator c = new ArtistComparator();
        int result = c.compare(song1, song2);
        assertEquals(-1, result);
    }

    @Test
    public void testLambdaTitleComparator()
    {
        Comparator<Song> comparator = (Song a, Song b) -> (a.getTitle().compareTo(b.getTitle()));
        
        //System.out.println(comparator.compare(songs[0], songs[0]));
        //System.out.println(comparator.compare(songs[0], songs[1]));
        assertEquals(0, comparator.compare(songs[0], songs[0]));
        assertTrue(comparator.compare(songs[0], songs[1] ) > 0);
        assertTrue(comparator.compare(songs[1], songs[0]) < 0);
        
    }

    @Test
    public void testYearExtractorComparator()
    {
        Comparator<Song> comparator = (Song a, Song b) -> {
            if(a.getArtist().compareTo(b.getArtist()) == 0)
            {
                return Integer.compare(a.getYear(), b.getYear());
            }
            return a.getArtist().compareTo(b.getArtist());
            };
        assertEquals(0, comparator.compare(songs[0], songs[0]));
        //System.out.println(comparator.compare(songs[0], songs[1]));
        assertTrue(comparator.compare(songs[0], songs[1] ) < 0);
        assertTrue(comparator.compare(songs[1], songs[0]) > 0);
    }

    @Test
    public void testComposedComparator()
    {
        Comparator<Song> comparator = (Song a, Song b) -> (a.getTitle().compareTo(b.getTitle()));
        Comparator<Song> comparator2 = (Song a, Song b) -> (a.getArtist().compareTo(b.getArtist()));
        ComposedComparator c = new ComposedComparator(comparator, comparator2);
        System.out.println(c.compare(songs[0], songs[1]));
    }

    @Test
    public void runSort()
    {
        //use to sort the entire songlist by artist, then title, then year
        List<Song> songList = new ArrayList<>(Arrays.asList(songs));
        //List<Song> songList2 = new ArrayList<>();
        List<Song> expectedList = Arrays.asList(
            new Song("Avett Brothers", "Talk on Indolence", 2006),
            new Song("City and Colour", "Sleeping Sickness", 2007),
            new Song("Decemberists", "The Mariner's Revenge Song", 2005),
            new Song("Foo Fighters", "Baker Street", 1997),
            new Song("Gerry Rafferty", "Baker Street", 1978),
            new Song("Gerry Rafferty", "Baker Street", 1998),
            new Song("Queen", "Bohemian Rhapsody", 1975),
            new Song("Rogue Wave", "Love's Lost Guarantee", 2005)
            );

        songList.sort(
            // pass comparator here
            (Song a, Song b) -> 
            {
                if(a.getArtist().compareTo(b.getArtist()) == 0)
                {
                    if(a.getTitle().compareTo(b.getTitle()) == 0)
                    {
                        return Integer.compare(a.getYear(), b.getYear());
                    }
                    return a.getTitle().compareTo(b.getTitle());
                }
                return a.getArtist().compareTo(b.getArtist());
            }

            );

        assertEquals(songList, expectedList);
    }
}
