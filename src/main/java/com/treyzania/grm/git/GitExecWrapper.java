package com.treyzania.grm.git;

import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;

public abstract class GitExecWrapper<R> {
	
	private static String gitCommand = "git";
	
	protected final Repository repo;
	
	public GitExecWrapper(Repository repo) {
		this.repo = repo;
	}
	
	protected ProcessBuilder builder() {
		
		ProcessBuilder pb = new ProcessBuilder();
		
		pb.directory(this.repo.getRepoFile());
		pb.command(gitCommand); // Does nothing bad by default.
		pb.redirectError(Redirect.INHERIT); // We want the errors to pass through.
		
		return pb;
		
	}
	
	protected final String git() {
		return gitCommand;
	}
	
	public abstract R execute() throws IOException;
	
	public static void setGitBaseCommand(String cmd) {
		gitCommand = cmd;
	}
	
	public static String getGitBaseCommand() {
		return gitCommand;
	}
	
	
	
}
