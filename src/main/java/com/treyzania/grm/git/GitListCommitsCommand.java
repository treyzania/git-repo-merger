package com.treyzania.grm.git;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class GitListCommitsCommand extends GitExecWrapper<List<SourceCommit>> {
	
	private static final String KEY_HASH = "hash";
	private static final String KEY_AUTHOR_TIME = "AUTHOR_UNIX";
	private static final String KEY_AUTHOR_EMAIL = "AUTHOR_EMAIL";
	private static final String KEY_AUTHOR_NAME = "AUTHOR_NAME";
	private static final String KEY_COMMITTER_TIME = "COMMITTER_UNIX";
	private static final String KEY_COMMITTER_EMAIL = "COMMITTER_EMAIL";
	private static final String KEY_COMMITTER_NAME = "COMMITTER_NAME";
	private static final String HEADER_BODY = "BODY...";
	
	private static final String DELIMITER = "----GRM-COMMIT-END----";
	
	public GitListCommitsCommand(Repository repo) {
		super(repo);
	}
	
	@Override
	public List<SourceCommit> execute() throws IOException {
		
		ProcessBuilder pb = this.builder();
		pb.command(this.git(), "log",
			"--pretty=format:" +
			KEY_HASH + " %H%n" +
			KEY_AUTHOR_TIME + " %at%n" +
			KEY_AUTHOR_EMAIL + " %ae%n" + 
			KEY_AUTHOR_NAME + " %an%n" +
			KEY_COMMITTER_TIME + " %ct%n" +
			KEY_COMMITTER_EMAIL + " %ce%n" +
			KEY_COMMITTER_NAME + " %cn%n" +
			HEADER_BODY + "%n%s%n%b%n" +
			DELIMITER);
		
		Process p = pb.start();
		
		List<SourceCommit> commits = new ArrayList<>();
		
		Scanner blocks = new Scanner(new BufferedInputStream(p.getInputStream()));
		blocks.useDelimiter(DELIMITER);
		while (blocks.hasNext()) {
			
			String block = blocks.next();
			int len = block.length();
			
			// It's probably some mix up that we want to avoid.
			if (len < 10) continue;
			
			Scanner blockLines = new Scanner(new StringReader(block));
			
			String hash = null;
			Date aTime = null;
			String aEmail = null;
			String aName = null;
			Date cTime = null;
			String cEmail = null;
			String cName = null;
			StringBuilder body = new StringBuilder();
			
			while (blockLines.hasNextLine()) {
				
				String line = blockLines.nextLine();
				String[] parts = line.split(" ");
				
				switch (parts[0]) {
				
				case KEY_HASH:
					hash = parts[1];
					break;
				
				case KEY_AUTHOR_TIME:
					aTime = new Date(Long.parseLong(parts[1]));
					break;
				
				case KEY_AUTHOR_EMAIL:
					aEmail = parts[1];
					break;
				
				case KEY_AUTHOR_NAME:
					aName = parts[1];
					break;
				
				case KEY_COMMITTER_TIME:
					cTime = new Date(Long.parseLong(parts[1]));
					break;
				
				case KEY_COMMITTER_EMAIL:
					cEmail = parts[1];
					break;
				
				case KEY_COMMITTER_NAME:
					cName = parts[1];
					break;
				
				case HEADER_BODY: {
					
					while (blockLines.hasNextLine()) {
						body.append(blockLines.nextLine().trim());
					}
					
					break;
					
				}
				
				}
				
			}
			
			// Verify populated.  I should be using a builder for commits but I'm too lazy.
			if (hash == null) continue;
			if (aTime == null) continue;
			if (aEmail == null) continue;
			if (aName == null) continue;
			if (cTime == null) continue;
			if (cEmail == null) continue;
			if (cName == null) continue;
			
			// Cleanup and storage.
			String fixedBody = body.toString().trim();
			SourceCommit sc = new SourceCommit(this.repo, hash, aName, cName, aEmail, cEmail, aTime, cTime, fixedBody);
			commits.add(sc);
			System.out.println("Found commit : " + hash + " in " + this.repo.root.getAbsolutePath());
			blockLines.close();
			
		}
		
		blocks.close();
		return commits;
		
	}
	
}
