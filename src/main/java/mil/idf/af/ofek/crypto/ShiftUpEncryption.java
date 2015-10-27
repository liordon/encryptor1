package mil.idf.af.ofek.crypto;

public class ShiftUpEncryption extends CharToCharEncryptor {
  @Override
  protected int encryptionMorph(int charVal, int key) {
    int $ = (charVal + (key % NUM_CHARS)) % NUM_CHARS;
    if (0 > $)
      $ += NUM_CHARS;
    return $;
  }
  
  @Override
  protected int decryptionMorph(int charVal, int key) {
    int $ = (charVal - (key % NUM_CHARS)) % NUM_CHARS;
    if (0 > $)
      $ += NUM_CHARS;
    return $;
  }
  
  @Override
  public String getName() {
    return "shift up encryption";
  }
}
