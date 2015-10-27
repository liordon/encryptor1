package mil.idf.af.ofek.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileInteractor implements IDataWriter, IDataReader {
  
  public FileInteractor() {}
  
  @Override
  public void writeData(String data, String fileName)
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
  
  @Override
  public String readData(String fileName) throws IOException {
    return new String(Files.readAllBytes(Paths.get(fileName)));
  }
  
  public boolean isFile(String srcFilePath) {
    File f = new File(srcFilePath);
    return f.isFile();
  }
  
  public boolean isFolder(String srcFilePath) {
    File f = new File(srcFilePath);
    return f.isDirectory();
  }
  
  public void makeDir(String path) {
    boolean success = new File(path).mkdirs();
    if (!success)
      throw new IllegalEncryptionTargetException();
  }
  
  public String[] contentOf(String srcFolderPath) {
    File f = new File(srcFolderPath);
    if (!f.isDirectory())
      throw new IllegalEncryptionTargetException();
    return f.list();
  }
}
