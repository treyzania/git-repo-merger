package com.treyzania.grm.git;

import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GitCommitTransferredCommitCommand extends GitExecWrapper<Void> {
	
	private static final String ENV_VAR_GIT_COMMIT_DATE = "GIT_COMMITTER_DATE";
	private static final String ENV_VAR_GIT_AUTHOR_DATE = "GIT_AUTHOR_DATE";
	
	private SourceCommit source;
	private String firstLinePrefix;
	
	public GitCommitTransferredCommitCommand(Repository repo, SourceCommit src, String prefix) {
		
		super(repo);
		
		this.source = src;
		this.firstLinePrefix = prefix;
		
	}
	
	@Override
	public Void execute() throws IOException {
		
		ProcessBuilder pb = this.builder();
		
		// Specify the command itself.
		List<String> args = new ArrayList<>();
		args.add(this.git());
		args.add("commit");
		
		// Put together the environmental vars.
		Map<String, String> env = pb.environment();
		env.put(ENV_VAR_GIT_AUTHOR_DATE, this.source.authoredDate);
		env.put(ENV_VAR_GIT_COMMIT_DATE, this.source.committedDate);
		
		// Put together the settings arguments.
		args.add("--author=\"" + this.source.author + " <" + this.source.authorEmail + ">\"");
		
		// Put together the message.
		Iterator<String> iter = Arrays.asList(this.source.body.split("\\n")).iterator();
		String first = iter.next();
		args.add("-m");
		args.add("'" + this.firstLinePrefix + ": " + first + "'");
		iter.forEachRemaining(l -> {
			
			args.add("-m");
			args.add(l);
			
		});
		
		pb.command(args);
		pb.redirectOutput(Redirect.INHERIT);
		
		Process p = pb.start();
		
		try {
			
			int res = p.waitFor();
			//if (res != 0) throw new IOException("Subprocess to commit failed with exit code: " + res);
			
		} catch (InterruptedException e) {
			throw new IOException("Interrupted while executing subprocess.", e);
		}
		
		return null;
		
	}
	
}
