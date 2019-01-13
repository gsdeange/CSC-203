import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.*;




class ExampleMap
{

    public static List<String> highEnrollmentStudents(Map<String, List<Course>> courseListsByStudentName, int unitThreshold)
    {
		List<String> overEnrolledStudents = new LinkedList<>();


	/*
	    Build a list of the names of students currently enrolled
	    in a number of units strictly greater than the unitThreshold.
	*/
		//Set set = courseListsByStudentName.entrySet();
		//Iterator i = set.iterator();

		for(Map.Entry<String, List<Course>> entry : courseListsByStudentName.entrySet())
		{
			
			List<Course> courseList  = entry.getValue();
			
			int unitcount = 0;
			for(Course c : courseList)
			{
				unitcount += c.getNumUnits();
			}
				
			if(unitcount > unitThreshold)
			{
				overEnrolledStudents.add(entry.getKey());
			}


		}

	 	return overEnrolledStudents;	     
    }
}
