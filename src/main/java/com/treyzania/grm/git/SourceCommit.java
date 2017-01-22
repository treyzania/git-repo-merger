package com.treyzania.grm.git;

import java.util.Date;

public final class SourceCommit implements Comparable<SourceCommit> {
	
	public final Repository repo;
	public final String hash;
	public final String author, committer;
	public final String authorEmail, commiterEmail;
	public final Date authoredDate, committedDate;
	
	public SourceCommit(Repository repo, String hash, String author, String committer, String authorEmail, String committerEmail, Date authoredDate, Date committedDate, String reason) {
		
		this.repo = repo;
		this.hash = hash;
		
		this.author = author;
		this.authorEmail = authorEmail;
		this.authoredDate = authoredDate;
		this.committer = committer;
		this.commiterEmail = committerEmail;
		this.committedDate = committedDate;
		
	}
	
	@Override
	public int compareTo(SourceCommit o) {
		return Long.compare(this.committedDate.getTime(), o.committedDate.getTime());
	}
	
}
