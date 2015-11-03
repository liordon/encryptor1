package mil.idf.af.ofek.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileInteractor {
  
  public FileInteractor() {}
  
  public void writeToFile(String data, String fileName)
      throws FileNotFoundException {
    FileOutputStream f = new FileOutputStream(fileName);
    
    try {
      for (char c : data.toCharArray())
        f.write(c);
      f.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  public String readFromFile(String fileName) throws IOException {
    return new String(Files.readAllBytes(Paths.get(fileName)));
  }
  
  public boolean isFile(String srcFilePath) {
    return new File(srcFilePath).isFile();
  }
  
}
