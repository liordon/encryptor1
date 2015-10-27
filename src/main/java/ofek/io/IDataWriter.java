package ofek.io;

import java.io.FileNotFoundException;

interface IDataWriter {
  public void writeData(String data, String fileName) throws FileNotFoundException;
	
}
