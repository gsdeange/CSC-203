
import java.util.List;
import java.util.ArrayList;


// TODO:  Fill this in
List<Session> sessionlist;
//CustomerID id;

public class Customer 
{
	public Customer()
	{
		sessionlist = new ArrayList();
		//id = new CustomerID(region, customernumber);
	}

    public List<Session> getSessions() 
    {
        return sessionlist;
    }

    public void addSession(Session session) 
    {
        sessionlist.add(session);
    }

    /*public CustomerID getId()
    {
    	return id;
    }*/
}
