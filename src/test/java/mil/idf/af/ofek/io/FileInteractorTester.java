package mil.idf.af.ofek.io;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import mil.idf.af.ofek.io.FileInteractor;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileInteractorTester {
  private static final String DATA = "shambalulu";
  private static final String FILE_NAME = "file.txt";
  private static final String READY_FILE = "src/test/resources/hello.txt";
  
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
  }
  
  @Before
  public void setup() {
    
  }
  
  @After
  public void teardown() {
    try {
      Files.deleteIfExists(Paths.get(FILE_NAME));
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  @AfterClass
  public static void tearDownAfterClass() throws Exception {
  }
  
  @Test
  public void canWriteSomething() {
    try {
      new FileInteractor().writeData(DATA, FILE_NAME);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      fail("could not create file");
    }
  }
  
  @Test
  public void canReadPreexistingFile() {
    try {
      String data = new FileInteractor().readData(READY_FILE);
      assertEquals(DATA, data);
    } catch (IOException e) {
      e.printStackTrace();
      fail("could not read file");
    }
  }
  
  @Test
  public void writtenFileCanBeRead() {
    try {
      FileInteractor $ = new FileInteractor();
      $.writeData(DATA, FILE_NAME);
      String data = $.readData(FILE_NAME);
      assertEquals(DATA, data);
    } catch (IOException e) {
      e.printStackTrace();
      fail("could not reread file");
    }
  }
}
