package com.bluejay.core;


import java.util.Date;

/**
 * This is the Asset to be used with GSON. It does not required getters and setters.
 * The toString was implemented just to test and display on the screen
 * 
 */
public class Github {

	public String login;
	
	public int id;
	
	public String avatar_url;

	public String avatar_id;
	
	public String url;

	public String html_url;
	
	public String fllowers_url;
	
	public String following_url;
	
	public String gists_url;
	
	public String starred_url;
	
	public String subscriptions_url;
	
	public String organizations_url;
	
	public String repos_url;
	
	public String events_url;
	
	public String received_events_url;
	
	public String type;
	
	public Boolean site_admin;
	
	public String name;
	
	public String company;
	
	public String blog;
	
	public String location;
	
	public String email;
	
	public String hireable;

	public String bio;
	
	public int public_repos;
	
	public int public_gists;
	
	public int followers;
	
	public int following;
	
	public Date created_at;
	
	public Date updated_at;
	
	
	
	@Override
	public String toString() {
		return "GitHub [login=" + login + "]";
	}
	
	
}
