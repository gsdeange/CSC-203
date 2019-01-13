
import java.util.List;
import java.util.ArrayList;




public class Customer 
{
    // TODO:  Fill this in
    List<Session> sessionlist;
    //CustomerID id;
    public Customer()
    {
        sessionlist = new ArrayList<Session>();
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
