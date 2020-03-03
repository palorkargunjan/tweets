package com.application;

/**
 * @category - Java application
 *
 */
public class Application
{
	public static void main(String args[])
	{
		System.out.println("************** Welcome to the Application **************");
		try
		{
			ApplicationService.selectATopicOrExit();

		} catch (Exception e) {
			System.err.println("Exception in the application -> " + e);
		}
	}


}
