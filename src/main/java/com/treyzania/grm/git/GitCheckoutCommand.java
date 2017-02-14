package com.treyzania.grm.git;

import java.io.IOException;

public class GitCheckoutCommand extends GitExecWrapper<Void> {
	
	private SourceCommit commit; 
	
	public GitCheckoutCommand(SourceCommit commit) {
		
		super(commit.repo);
		
		this.commit = commit;
		
	}

	@Override
	public Void execute() throws IOException {
		
		ProcessBuilder pb = this.builder();
		pb.command(this.git(), "checkout", this.commit.hash);
		
		System.out.println("CHECKING OUT : " + this.commit.hash + " in " + this.repo.root.getAbsolutePath());
		Process p = pb.start();
		
		try {
			
			int code = p.waitFor();
			if (code != 0) throw new IOException("Checkout failed with code: " + code);
			
		} catch (InterruptedException e) {
			throw new IOException("Interrupted while waiting for subprocess to complete.", e);
		}
		
		return null;
		
	}
	
}
