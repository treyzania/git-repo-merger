package com.treyzania.grm.tools;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.treyzania.grm.git.Repository;
import com.treyzania.grm.git.SourceCommit;

public class CommitDumper {

	public static void main(String[] args) {
		
		Repository repo = new Repository(new File(args[0]));
		
		try {
			
			List<SourceCommit> cl = repo.getCommits();
			
			cl.forEach(c -> {
				
				System.out.println(c.hash);
				System.out.println(c.body);
				
			});
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
