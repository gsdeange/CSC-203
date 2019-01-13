public class Runner
{
	public static void main(String[] args)
	{
	    TestCases test = new TestCases();
	   	
	   	//artist comparator
	    test.testArtistComparator();
	    // testLamda
	    test.testLambdaTitleComparator();
	    //testYear extractor
	    test.testYearExtractorComparator();
	    
	    // testComposed Comparator
	    test.testComposedComparator();

	    //testSort
	    test.runSort();
	}
}