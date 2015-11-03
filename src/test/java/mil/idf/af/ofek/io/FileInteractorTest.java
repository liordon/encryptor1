package mil.idf.af.ofek.io;

import static org.junit.Assert.*;
import static mil.idf.af.ofek.TestResources.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import mil.idf.af.ofek.io.FileInteractor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FileInteractorTest {
  private static final FileInteractor $         = new FileInteractor();
  private static final String         FILE_NAME = "file.txt";
  
  @Before
  public void setup() {
    
  }
  
  @After
  public void teardown() {
    try {
      Files.deleteIfExists(Paths.get(FILE_NAME));
      Files.deleteIfExists(Paths.get(CRYPT_DIR));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  @Test
  public void canWriteSomething() {
    try {
      $.writeToFile(MSG, FILE_NAME);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      fail("could not create file");
    }
  }
  
  @Test
  public void canReadPreexistingFile() {
    try {
      String data = $.readFromFile(PLAIN_FILE + EXTENSION);
      assertEquals(MSG, data);
    } catch (IOException e) {
      e.printStackTrace();
      fail("could not read file");
    }
  }
  
  @Test
  public void writtenFileCanBeRead() {
    try {
      $.writeToFile(MSG, FILE_NAME);
      String data = $.readFromFile(FILE_NAME);
      assertEquals(MSG, data);
    } catch (IOException e) {
      e.printStackTrace();
      fail("could not reread file");
    }
  }
}
