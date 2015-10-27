package mil.idf.af.ofek;

public enum TestResources {
  ;
  public static final String   MSG            = "shambalulu";
  public static final String   FAKE_CYPHER    = "balbashushu";
  public static final String   RESOURCE_DIR   = "src/test/resources";
  public static final String   PLAIN_FILE     = RESOURCE_DIR + "/hello.txt";
  public static final String   ENCRYPTED_FILE = RESOURCE_DIR + "/e.txt";
  public static final String   DECRYPTED_FILE = RESOURCE_DIR + "/d.txt";
  public static final String   KEY_FILE       = RESOURCE_DIR + "/k.txt";
  
  public static final String[] PLAIN_FILES    = { PLAIN_FILE, PLAIN_FILE + "2" };
  public static final String[] CRYPT_FILES    = { ENCRYPTED_FILE,
      ENCRYPTED_FILE + "2"                   };
  public static final String[] DECRYPT_FILES  = { DECRYPTED_FILE,
      DECRYPTED_FILE + "2"                   };
  public static final String   CRYPT_DIR      = RESOURCE_DIR + "/cryptme";
  public static final String   ALGORITHM      = "capt. crypto";
  
}
