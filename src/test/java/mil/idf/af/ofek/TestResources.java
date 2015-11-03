package mil.idf.af.ofek;

public enum TestResources {
  ;
  public static final String   MSG             = "shambalulu";
  public static final String   FAKE_CYPHER     = "balbashushu";
  public static final String   RESOURCE_DIR    = "src/test/resources";
  public static final String   CRYPT_DIR       = RESOURCE_DIR + "/encrypted";
  public static final String   PLAIN_NAME      = "hello";
  public static final String   EXTENSION       = ".txt";
  public static final String   PLAIN_FILE      = RESOURCE_DIR + "/"
                                                   + PLAIN_NAME;
  public static final String   ENCRYPTED_FILE  = CRYPT_DIR + "/" + PLAIN_NAME;
  public static final String   DECRYPTED_FILE  = CRYPT_DIR + "/d";
  public static final String   KEY_FILE        = CRYPT_DIR + "/key.txt";
  public static final String   PREEXISTING_KEY = RESOURCE_DIR + "/key";
  
  public static final String[] PLAIN_FILES     = { PLAIN_FILE + EXTENSION,
      PLAIN_FILE + "2" + EXTENSION            };
  public static final String[] PLAIN_NAMES     = { PLAIN_NAME + EXTENSION,
      PLAIN_NAME + "2" + EXTENSION            };
  public static final String[] CRYPT_FILES     = { ENCRYPTED_FILE + EXTENSION,
      ENCRYPTED_FILE + "2" + EXTENSION        };
  public static final String[] DECRYPT_FILES   = { DECRYPTED_FILE + EXTENSION,
      DECRYPTED_FILE + "2" + EXTENSION        };
  public static final String[] RANDOM_FILES    = { PLAIN_NAME + EXTENSION,
      "bang", "arang", PLAIN_NAME + "2" + EXTENSION, "shambalulu" };
  
  public static final String   ALGORITHM       = "capt. crypto";
}
