/*
 */
package edu.netosoft.java8.gradle.files;

import org.junit.Test;
import static edu.netosoft.java8.gradle.files.TestConstants.*;

/**
 *
 * @author Ernesto
 */
public class LastPdfAddedTest{
	
	@Test
	public void testMove(){
		LastPdfAdded lastAdded = new LastPdfAdded();
		lastAdded.setDownloadDir(DEFAULT_DOWNLOAD_DIR);
		lastAdded.setRepoDir(DEFAULT_REPO_DIR);
		lastAdded.move();
	}
	
}
