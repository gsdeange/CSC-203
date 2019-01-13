
// TODO:  Fill this in
public class CustomerID 
{
	private String region;
	private String number;

	public CustomerID(String region, String number)
	{
			this.region = region;
			this.number = number;
	}

	public boolean equals(Object other)
	{
		CustomerID customer = (CustomerID)other;

		return this.region == customer.region && this.number == customer.number;
	}
	public int hashCode()
	{
		return 1;
	}


}
