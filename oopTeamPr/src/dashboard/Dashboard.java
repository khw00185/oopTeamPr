package dashboard;

import org.w3c.dom.ls.LSOutput;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Dashboard {
	public static String today;
	Scanner scan;
	String name;
	ArrayList<Task> taskList = new ArrayList<>();
	Schedule waitingList = new Schedule();
	Schedule toDo = new Schedule();
	Schedule inProgress = new Schedule();
	Schedule done = new Schedule();

	public Dashboard() {
		this.name = "default";
		waitingList.read("Waiting List");
		toDo.read("To-do List");
		inProgress.read("In progress List");
		done.read("Done List");
		getToday();
	}

	void run() {
		readAllTasks();
		print();
		menu();
	}

	void menu() {
		scan = new Scanner(System.in);
		
		int menuNum;
		while(true) {
			System.out.format("1. 전체 출력\n");
			System.out.format("2. 검색\n");
			System.out.format("3. 수정\n");
			menuNum = scan.nextInt();
			if(menuNum == 0)
				break;
			switch (menuNum) {
			case 1 -> print();
			case 2 -> search();
			case 3 -> modify();
			}	
		}
	}

	void getToday() {
		// 현재 날짜 구하기
		LocalDate now = LocalDate.now();
		// 포맷 정의
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd");
		// 포맷 적용
		Dashboard.today = now.format(formatter);
	}

	Scanner openfile(String filename) {
		Scanner scan = null;
		try {
			scan = new Scanner(new File(filename));
		} catch (Exception e) {
			System.out.printf("파일 오픈 실패: %s\n", filename);
			System.exit(0);
		}
		return scan;
	}

	void readAllTasks() {
		scan = openfile("task.txt");
		Task t;
		while (scan.hasNext()) {
			t = new Task();
			t.read(scan);
			classify(t);
			taskList.add(t);
		}
		scan.close();
	}

	void classify(Task task) {
		switch (task.progressLvl()) {
		case 1 -> waitingList.taskList.add(task);
		case 2 -> toDo.taskList.add(task);
		case 3 -> inProgress.taskList.add(task);
		case 4 -> done.taskList.add(task);
		}
	}

	void print() {
		waitingList.print();
		toDo.print();
		inProgress.print();
		done.print();
	}

	void search() {// 어느 Schedule인지 알기 위해 Schedule에서 검색
		String kwd = scan.next();
		System.out.println(kwd);
		waitingList.matches(kwd);
		toDo.matches(kwd);
		inProgress.matches(kwd);
		done.matches(kwd);
	}

	void modify() {
		for (int i = 0; i < taskList.size(); i++) {
			System.out.format("(%d) ", i + 1);
			taskList.get(i).print();
		}
		System.out.print("수정할 Task 번호 선택: ");
		int tskNum = scan.nextInt();
		taskList.get(tskNum-1).print();

		System.out.format("1. 이름 재설정\n");
		System.out.format("2. 날짜 변경\n");
		System.out.format("3. 태그 변경\n");
		System.out.format("4. 내용 변경\n");
		int menuNum = scan.nextInt();
		taskList.get(tskNum-1).modify(menuNum, scan);
	}

	public static void main(String[] args) {

		Dashboard d = new Dashboard();
		System.out.println(Dashboard.today);
		d.run();
	}

}