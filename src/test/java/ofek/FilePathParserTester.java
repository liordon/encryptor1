package ofek;

import static org.junit.Assert.*;

import org.junit.Test;

public class FilePathParserTester {
  private static final String PATH = "src/test/resources/";
  private static final String NAME = "hello";
  private static final String EXTENTION = ".txt";
  private static final String ENC_SUFFIX = "_encrypted";
  private static final String DEC_SUFFIX = "_decrypted";
  
  FilePathParser $ = new FilePathParser(PATH+NAME+EXTENTION);
  
  @Test
  public void canTellPathFromFile() {
    String p = $.getPath();
    assertEquals(PATH,p);
  }
  
  @Test
  public void canTellFileNameFromExtention(){
    String f = $.getFileName();
    assertEquals(NAME,f);
  }
  
  @Test
  public void canTellFileNameFromEncryptionSuffix(){
    String f = new FilePathParser(NAME+ENC_SUFFIX+EXTENTION).getFileName();
    assertEquals(NAME,f);
  }
  
  @Test
  public void canTellFileExtentionFromTheRest(){
    String e = $.getExtention();
    assertEquals(EXTENTION,e);
  }
  
  @Test
  public void canGiveEncryptedSuffixToFilePath(){
    String enc = $.getEncryptedName();
    assertEquals(PATH+NAME+ENC_SUFFIX+EXTENTION, enc);
  }
  
  @Test
  public void canGiveDecryptedSuffixToFilePath(){
    String dec = $.getDecryptedName();
    assertEquals(PATH+NAME+DEC_SUFFIX+EXTENTION, dec);
  }
}
