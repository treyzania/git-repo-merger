package com.treyzania.grm.git;

import java.io.IOException;

public class GitCheckoutCommand extends GitExecBuilder {
	
	private SourceCommit commit; 
	
	public GitCheckoutCommand(SourceCommit commit) {
		
		super(commit.repo);
		
		this.commit = commit;
		
	}

	@Override
	public Process execute() throws IOException {
		
		ProcessBuilder pb = this.builder();
		pb.command(this.git(), "checkout", this.commit.hash);
		
		return pb.start();
		
	}
	
}
