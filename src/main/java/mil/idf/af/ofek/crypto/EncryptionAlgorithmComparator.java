package mil.idf.af.ofek.crypto;

import java.util.Comparator;

public class EncryptionAlgorithmComparator implements Comparator<EncryptionAlgorithm> {

  @Override
  public int compare(EncryptionAlgorithm o1, EncryptionAlgorithm o2) {
    return o1.getKeyStrength() - o2.getKeyStrength();
  }
  
}