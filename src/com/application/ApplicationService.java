package com.application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class ApplicationService {

	/**
	 * @category - service
	 * @param
	 * @throws Exception
	 */
	public static void selectATopicOrExit() throws Exception {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out
					.println("Select A Topic to search for Tweets in Twitter or Enter 'exit' to Exit the application.");
			int i = 0;
			for (String topic : Properties.topics) {
				i++;
				System.out.println(i + ". " + topic);
			}
			System.out.println("Enter the Topic ID - ");
			String data = scanner.nextLine();

			if (data.equalsIgnoreCase("exit")) {
				System.out.println("Exiting the application....");
				break;
			} else if (!isNumericValue(data)) {
				System.out.println("Please Enter a Valid Input.");
				continue;
			} else {
				Integer topicId = Integer.parseInt(data);
				System.out.println("Entered the Topic ID is " + topicId);
				if (topicId > Properties.topics.size()) {
					System.out.println("Please enter a number that is present in the topic list..");
					continue;
				}
				String topicName = Properties.topics.get(topicId - 1);
				System.out.println("Starting search for the topic - " + topicName);
				try {
					searchTweetsAndStore(topicName);
				} catch (Exception e) {
					System.err.println("Unable to Search the Topic - " + topicName);
					e.printStackTrace();
				}
			}
		}
		scanner.close();
	}
	/**
	 * 
	 * @param topicName
	 * @return - returns boolean status whether tweets successfully stored or not
	 * @throws Exception
	 */
	public static boolean searchTweetsAndStore(String topicName) throws Exception {
		System.out.println("Trying to get twitter api instance....");
		Twitter twitter = TwitterAPI.getTwitterInstance();
		System.out.println("Searching for Tweets....");
		try {
			List<Status> tweets = searchTweets(twitter, topicName);
			if (tweets.size() == 0) {
				System.out.println("No Tweets Found for the topic - " + topicName);
				return false;
			} else {
				System.out.println(tweets.size() + " Tweets found..");
				String filePath = Properties.path + topicName + ".txt";
				System.out.println("File path to store tweets -> " + filePath);
				File file = new File(filePath);
				if (!file.exists()) {
					try {
						System.out.println("File doesn't exists.. Creating new...");
						file.createNewFile();
					} catch (IOException e) {
						System.err.println("Error in creating file --> " + e.getMessage());
						System.exit(1);
					}
				}

				System.out.println("Storing the tweets into file [ path : " + file.getPath());
				FileWriter fileWriter = new FileWriter(filePath, true);
				fileWriter.write("-------------- Created at " + LocalDateTime.now().toString() + " --------------\n\n");
				int i = 0;
				for (Status tweet : tweets) {
					i++;
					System.out.println("Storing tweet " + i + " .........!!");
					fileWriter.write("---------- Tweet " + i + " ------------\n");
					String tweetText, createdAt, userName, userUrl, place;
					tweetText = createdAt = userName = userUrl = place = "Not Available";
					try {
						if (tweet.getText() != null)
							tweetText = tweet.getText();
						if (tweet.getCreatedAt() != null)
							createdAt = tweet.getCreatedAt().toString();
						if (tweet.getUser() != null) {
							userName = tweet.getUser().getName();
							userUrl = tweet.getUser().getURL();
						}
						if (tweet.getPlace() != null) {
							place = tweet.getPlace().getFullName();
						}
					} catch (Exception e) {
						System.err.println("Exception in getting tweet info - " + e);
					}
					fileWriter.write("Tweet text - " + tweetText + "\n");
					fileWriter.write("Username - " + userName + "\n");
					fileWriter.write("Place - " + place + "\n");
					fileWriter.write("User URL - " + userUrl + "\n");
					fileWriter.write("Created Tweet at - " + createdAt + "\n");
				}
				System.out.println("All tweets are stored in the file path " + filePath);
				fileWriter.close();
				return true;
			}
		} catch (Exception e) {
			System.err.println(" Exception -> " + e.getMessage());
		}
		return false;
	}

	/**
	 * 
	 * @param twitter
	 * @param topicName
	 * @return returns all tweets for the topic name
	 * @throws TwitterException
	 */
	public static List<Status> searchTweets(Twitter twitter, String topicName) throws TwitterException {
		System.out.println("Querying the tweets with the search name " + topicName);
		Query query = new Query(topicName);
		QueryResult result = twitter.search(query);
		System.out.println("Query successful...!!");
		return result.getTweets();
	}

	/**
	 * This method checks whether string is numeric or not
	 * @param data
	 * @return boolean
	 */
	public static boolean isNumericValue(String data) {
		System.out.println("Checking whether numeric value or not...");
		try {
			Integer.parseInt(data);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}


}
