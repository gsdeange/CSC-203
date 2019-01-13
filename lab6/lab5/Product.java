
public class Product
{
	public String productId;
	public int price;

	public Product(String productId, int price)
	{
		this.price = price; 
		this.productId = productId;
	}

	public void getId()
	{
		return productId;
	}
	public void getPrice()
	{
		return price;
	}

	@Override
	public String toString()
	{
		return ("productId = " + productId + " price = " + price);
	}

	@Override
	public int hashCode()
	{
		return productId.hashCode()*31 + price;
	}

	@Override
	public boolean equals(Product other)
	{
		if(productId.equals(other.productId) && price == other.price)
		{
			return true;
		}
		return false;
	}






}
