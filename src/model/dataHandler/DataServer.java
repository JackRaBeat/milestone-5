package model.dataHandler;

public interface DataServer extends DataGetter {
	  void open(int port, int freq, String[] paths,Object lock);
	    void close();
}
