/*
 */
package edu.netosoft.java8.gradle.files;

import org.junit.Test;
import static edu.netosoft.java8.gradle.files.TestConstants.*;

/**
 *
 * @author Ernesto
 */
public class PDFWatcherTest{
	
	/**
	 * Test of watchForNewPdf method, of class PDFWatcher.
	 */
	@Test
	public void testWatchForNewPdf(){
		System.out.println("watchForNewPdf");
		PDFWatcher watcher = new PDFWatcher();
		
		watcher.setDownloadDir(DEFAULT_DOWNLOAD_DIR);
		watcher.setRepoDir(DEFAULT_REPO_DIR);
		watcher.setWaitSeconds(DEFAULT_WAIT_SECCONDS);
		
		watcher.watchForNewPdf();
	}
}
