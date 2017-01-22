package com.treyzania.grm.git;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class Repository {
	
	private File root;
	private SourceCommit currentCommit = null;
	
	public Repository(File f) {
		
		this.root = f;
		
	}
	
	public File getRepoFile() {
		return this.root;
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
		
		if (commit.repo == this) throw new IllegalArgumentException("This commit isn't from this repo.");
		if (commit == this.currentCommit) return true;
		
		GitCheckoutCommand cmd = new GitCheckoutCommand(commit);;
		
		Process p = cmd.execute();
		
		try {
			
			int res = p.waitFor();
			if (res != 0) throw new IOException("Subprocess checkout failed with exit code " + res);
			
		} catch (InterruptedException e) {
			throw new IOException("Failed to wait for process to finish.", e);
		}
		
		return false;
		
	}
	
	public List<File> getTrackedFiles() throws IOException {
		
		if (this.currentCommit == null) throw new IllegalStateException("Undefined current commit, use a real one before listing tracked files.");
		
		GitListFilesCommand cmd = new GitListFilesCommand(this);
		Process p = cmd.execute();
		
		Scanner lines = new Scanner(new BufferedInputStream(p.getInputStream()));
		List<File> files = new ArrayList<>();
		while (lines.hasNextLine()) {
			
			String line = lines.nextLine();
			File f = new File(this.root, line);
			
			if (f.exists()) {
				
				files.add(f);
				
			} else {
				System.out.println("Git reported a tracked file that does not exist.");
			}
			
		}
		
		lines.close();
		return files;
		
	}
	
	public List<SourceCommit> getCommits() throws IOException {
		
		String delimiter = "----GRM-COMMIT-END----";
		GitListCommitsCommand cmd = new GitListCommitsCommand(this, delimiter);
		Process p = cmd.execute();
		
		Scanner blocks = new Scanner(new BufferedInputStream(p.getInputStream()));
		blocks.useDelimiter(delimiter);
		List<SourceCommit> commits = new ArrayList<>();
		while (blocks.hasNext()) {
			
			String block = blocks.next();
			
			int len = block.length();
			
			// It's probably some mix up that we want to avoid.
			if (len < 10) continue;
			
			Scanner blockLines = new Scanner(new StringReader(block));
			
			while (blockLines.hasNextLine()) {
				
				String line = blockLines.nextLine();
				
				// TODO Process commits.
				
			}
			
		}
		
		return commits;
		
	}
	
}
