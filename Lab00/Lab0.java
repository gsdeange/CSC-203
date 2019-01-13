public class Lab0
{
    public static void main(String[] args)
    {
        int x = 5;
		String y = "hello";
		double z = 9.8;

		// printing the variables
		System.out.println("x: " + x + " y: " + y + " z: " + z);

		// creating the array
		int[] nums = new int[4];
		nums[0] = 3;
		nums[1] = 6;
		nums[2] = -1;
		nums[3] = 2;
		for(int i = 0; i<nums.length; i++)
		{
			System.out.println(nums[i]);
		}

		// call a function
		int numFound = char_count(y, 'l');
		System.out.println("Found: "+ numFound);


		// counting for loop
		for(int i = 1; i<11; i++)
		{
			System.out.print(i);
		}
		System.out.println();
		System.out.println();
    }
    public static int char_count(String s, char c)
	{
		char[] word = new char[s.length()];
		word = s.toCharArray();

		


		int count = 0;
		for(int i = 0; i<word.length; i++)
		{
			if(word[i] == c)
			{
				count++;
			}
		}
		return count;

	}
}
