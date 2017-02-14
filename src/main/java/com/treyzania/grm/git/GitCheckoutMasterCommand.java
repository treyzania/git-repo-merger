package com.treyzania.grm.git;

import java.io.IOException;

public class GitCheckoutMasterCommand extends GitExecWrapper<Void> {

	public GitCheckoutMasterCommand(Repository repo) {
		super(repo);
	}

	@Override
	public Void execute() throws IOException {
		
		ProcessBuilder pb = this.builder();
		
		pb.command(this.git(), "checkout", "master");
		
		try {
			
			Process p = pb.start();
			int code = p.waitFor();
			
			if (code != 0) throw new IOException("Exit code for git checkout was " + code);
			
		} catch (InterruptedException e) {
			throw new IOException("Interrupted before exiting.", e);
		}
		
		return null;
		
	}

}
