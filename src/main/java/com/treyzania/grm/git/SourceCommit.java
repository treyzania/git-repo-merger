package com.treyzania.grm.git;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class SourceCommit implements Comparable<SourceCommit> {
	
	public final Repository repo;
	public final String hash;
	public final String author, committer;
	public final String authorEmail, commiterEmail;
	public final String authoredDate, committedDate;
	public final String body;
	
	public SourceCommit(Repository repo, String hash, String author, String committer, String authorEmail, String committerEmail, String authoredDate, String committedDate, String body) {
		
		this.repo = repo;
		this.hash = hash;
		
		this.author = author;
		this.authorEmail = authorEmail;
		this.authoredDate = authoredDate;
		this.committer = committer;
		this.commiterEmail = committerEmail;
		this.committedDate = committedDate;
		
		this.body = body;
		
	}
	
	@Override
	public int compareTo(SourceCommit o) {
		
		// Tue Jan 24 01:25:18 2017 -0500
		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy Z");
		
		try {
			
			Date mine = sdf.parse(this.authoredDate);
			Date theirs = sdf.parse(o.authoredDate);
			
			return Long.compare(mine.getTime(), theirs.getTime());
			
		} catch (ParseException e) {
			throw new IllegalStateException("Oops.", e);
		}
		
	}
	
}
