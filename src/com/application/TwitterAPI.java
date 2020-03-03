package com.application;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

/**
 * @category Twitter API
 *
 */
public class TwitterAPI {

	private static Twitter twitter;

	public TwitterAPI() throws Exception {
		try {
			System.out.println("Creating new twitter api instance...");
			twitter = new TwitterFactory().getInstance();
			twitter.setOAuthConsumer(Properties.consumerAPIKey, Properties.consumerAPISecretKey);
			twitter.setOAuthAccessToken(new AccessToken(Properties.accessToken, Properties.accessTokenSecret));
			System.out.println("Successfully created twitter api instance...");
		} catch (Exception e) {
			throw new Exception("Exception in creating Twitter API instance -> " + e.getMessage());
		}
	}

	/**
	 * This method checks whether instance available or not, if not, it'll create
	 * new object
	 * @return Twitter instance
	 * @throws Exception
	 */
	public static Twitter getTwitterInstance() throws Exception {
		System.out.println("Checking for twitter api instance created already or not...");
		if (twitter == null) {
			synchronized (TwitterAPI.class) {
				new TwitterAPI();
			}
		} else {
			System.out.println("Already twitter api instance created...!!");
		}
		return twitter;
	}
}
