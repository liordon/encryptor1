package mil.idf.af.ofek.io;

import java.io.File;
import java.util.ArrayList;

import mil.idf.af.ofek.FilePathParser;

public class DirectoryInteractor {
  public boolean isFolder(String srcFilePath) {
    return new File(srcFilePath).isDirectory();
  }
  
  public void makeDirInPath(String path) {
    File f = new File(path);
    if (!f.isDirectory() && !f.mkdirs())
      throw new IllegalEncryptionTargetException();
  }
  
  private String[] contentOf(String srcFolderPath) {
    File f = new File(srcFolderPath);
    if (!f.isDirectory())
      throw new NoneExistingResourceException();
    return f.list();
  }
  
  public String[] getTextFilesFromFolder(String path) {
    ArrayList<String> $ = new ArrayList<String>();
    for (String f : contentOf(path)) {
      FilePathParser pathParser = new FilePathParser(f);
      if (pathParser.getExtention().equals(".txt"))
        $.add(f);
    }
    return $.toArray(new String[$.size()]);
  }
}
