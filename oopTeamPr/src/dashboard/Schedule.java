package dashboard;

import java.util.ArrayList;

import mgr.Manageable;

public class Schedule implements Manageable{
	String name;
	ArrayList<Task> taskList = new ArrayList<>();
	
	
	@Override
	public void read (String name) {
		this.name = name;
	}
	
	@Override
	public void print() {
		System.out.format("<%s>\n", name);
		System.out.println("-".repeat(80));
		for (Task t : taskList) {
			t.print();
		}
		System.out.println("-".repeat(80)+"\n");
	}
	
	@Override
	public void matches(String kwd) {
		System.out.format("<%s>\n", name);
		System.out.println("-".repeat(80));
		for (Task t : taskList) {
			
			if (t.matches(kwd)) {
				t.print();
			}
		}
		System.out.println("-".repeat(80));
	}

}