import java.util.LinkedList;
import java.util.List;

public class Observable {
	
	private List<Observer> observers;
	
	public Observable() {
		observers = new LinkedList<>();
	}
	
	public void addObserver(Observer ob) {
		observers.add(ob);
	}
	
	public void deleteObserver(Observer ob) {
		for(Observer o : observers) {
			if(o.equals(ob)) {
				observers.remove(o);
				break;
			}
		}
	}
	
	public void notifyObserver() {
		for(Observer o : observers) {
			o.update(this);
		}
	}

}
