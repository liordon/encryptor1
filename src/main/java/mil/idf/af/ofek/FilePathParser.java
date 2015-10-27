package mil.idf.af.ofek;

public class FilePathParser {
  private static final String ENC_SUFFIX = "_encrypted";
  private static final String DEC_SUFFIX = "_decrypted";
  private String path, name, extention;
  
  public FilePathParser(String filePath) {
    String[] segments = filePath.split("/");
    StringBuilder pathBuilder = new StringBuilder();
    for (int i = 0; i < segments.length - 1; ++i) {
      pathBuilder.append(segments[i] + "/");
    }
    path = pathBuilder.toString();
    
    String[] fileParts = segments[segments.length - 1].split("\\.");
    name = fileParts[0];
    if (name.endsWith(ENC_SUFFIX) || name.endsWith(DEC_SUFFIX))
      name=name.substring(0, name.length()-ENC_SUFFIX.length());
    
    extention = "."+fileParts[1];
  }
  
  public String getPath() {
    return path;
  }
  
  public String getFileName() {
    return name;
  }

  public String getExtention() {
    return extention;
  }

  public String getEncryptedName() {
    return path+name+ENC_SUFFIX+extention;
  }

  public String getDecryptedName() {
    return path+name+DEC_SUFFIX+extention;
  }

  public String getKeyName() {
    return path+"key.txt";
  }
}
