package mil.idf.af.ofek.io;

import java.io.IOException;

public interface IDataReader {
  public String readData(String fileName) throws IOException;
}
