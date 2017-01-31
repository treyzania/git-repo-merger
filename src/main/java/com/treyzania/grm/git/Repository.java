package com.treyzania.grm.git;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Repository {
	
	public final File root;
	
	private SourceCommit currentCommit = null;
	private List<SourceCommit> commits = null;
	
	public Repository(File f) {
		this.root = f;
	}
	
	public SourceCommit getCurrentCommit() {
		return this.currentCommit;
	}
	
	/**
	 * Checks out the specified commit.
	 * 
	 * @param commit
	 * @return <code>true</code> if the commit was already checked out
	 */
	public boolean checkout(SourceCommit commit) throws IOException {
		
		if (commit.repo != this) throw new IllegalArgumentException("This commit isn't from this repo.");
		if (commit == this.currentCommit) return true;
		
		GitCheckoutCommand cmd = new GitCheckoutCommand(commit);
		cmd.execute(); // Just run it straight away.
		this.currentCommit = commit;
		return false;
		
	}
	
	public void assumeCheckedOutLast() {
		this.currentCommit = this.commits.get(this.commits.size() - 1);
	}
	
	public List<File> getTrackedFiles() throws IOException {
		
		if (this.currentCommit == null) throw new IllegalStateException("Undefined current commit, use a real one before listing tracked files.");
		
		GitListFilesCommand cmd = new GitListFilesCommand(this);
		return cmd.execute();
		
	}
	
	public List<SourceCommit> getCommits() throws IOException {
		
		if (this.commits == null) {
			
			GitListCommitsCommand cmd = new GitListCommitsCommand(this);
			this.commits = cmd.execute();
			
		}
		
		return this.commits;
		
	}

	public List<SourceCommit> getRefreshedCommits() throws IOException {
		
		this.commits = null;
		return this.getCommits();
		
	}
	
}
