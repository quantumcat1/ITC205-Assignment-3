package library.test;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner
{
	public static void main(String[] args)
	{
		Result result = JUnitCore.runClasses(EntityTest.class);
		for(Failure failure : result.getFailures())
		{
			System.out.println(failure.toString());
		}
		System.out.println("Entity unit testing success: " + result.wasSuccessful());

		result = JUnitCore.runClasses(DAOTest.class);
		for(Failure failure : result.getFailures())
		{
			System.out.println(failure.toString());
		}
		System.out.println("DAO unit testing success: " + result.wasSuccessful());

		result = JUnitCore.runClasses(EntityDAOIntegrationTest.class);
		for(Failure failure : result.getFailures())
		{
			System.out.println(failure.toString());
		}
		System.out.println("Entity DAO integration testing success: " + result.wasSuccessful());

		result = JUnitCore.runClasses(CTLIntegrationTest.class);
		for(Failure failure : result.getFailures())
		{
			System.out.println(failure.toString());
		}
		System.out.println("CTL integration testing success: " + result.wasSuccessful());
	}
}
