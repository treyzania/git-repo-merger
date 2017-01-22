package com.treyzania.grm.git;

import java.io.IOException;

public class GitListCommitsCommand extends GitExecBuilder {
	
	private String delimiter;
	
	public GitListCommitsCommand(Repository repo, String delim) {
		
		super(repo);
		
		this.delimiter = delim;
		
	}
	
	@Override
	public Process execute() throws IOException {
		
		ProcessBuilder pb = this.builder();
		pb.command(this.git(), "log",
			"--pretty=format:" +
			"HASH %H%n" +
			"AUTHOR_UNIX %at%n" +
			"AUTHOR_EMAIL %ae%n" + 
			"AUTHOR_NAME %an%n" +
			"COMMITTER_UNIX %ct%n" +
			"COMMITTER_EMAIL %ce%n" +
			"COMMITTER_NAME %cn%n" +
			"BODY%n%b%n" +
			this.delimiter);
		
		return pb.start();
		
	}
	
}
