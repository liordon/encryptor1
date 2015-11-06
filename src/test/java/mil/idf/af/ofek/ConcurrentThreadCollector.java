package mil.idf.af.ofek;

import java.util.Observable;
import java.util.Observer;

public class ConcurrentThreadCollector extends Observable implements Observer {
  private final int expectedUpdates;
  private int       updatesSoFar = 0;
  
  public ConcurrentThreadCollector(int eu) {
    expectedUpdates = eu;
  }
  
  @Override
  public void update(Observable o, Object arg) {
    if (expectedUpdates < ++updatesSoFar)
      notifyObservers();
  }
  
}
