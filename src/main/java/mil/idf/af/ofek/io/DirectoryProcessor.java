package mil.idf.af.ofek.io;

public interface DirectoryProcessor {
  
  public abstract void encryptFolder(String srcDir, String targetDir);
  
  public abstract void decryptFolder(String srcDir, String targetDir);
}
