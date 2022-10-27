package dashboard;

import java.util.ArrayList;
import java.util.Scanner;

public class Task {
	String name;
	String start_Date;
	String end_Date;
	ArrayList<String> tag = new ArrayList<>();
	StringBuilder content = new StringBuilder();

	void read(Scanner scan) {
		setName(scan);
		setDate(scan);
		while (setTag(scan)) {
		}
		setContent(scan);
	}

	private void setName(Scanner scan) {
		this.name = scan.nextLine();
	}

	private void setDate(Scanner scan) {
		this.start_Date = scan.next();
		if (start_Date.equals("-"))
			this.end_Date = "-";
		else
			this.end_Date = scan.next();
	}

	private boolean setTag(Scanner scan) {// #tag는 태그 추가 -tag는 태그 삭제
		String hashtag = scan.next();

		return switch (hashtag.charAt(0)) {
		case '#' -> {
			tag.add(hashtag);
			yield true;
		}
		case '-' -> false;
		default -> throw new IllegalArgumentException("Unexpected value: " + hashtag.charAt(0));
		};
	}

	private void setContent(Scanner scan) {
		String rewrite = scan.nextLine();
		if (rewrite.equals("다시쓰기")) {
			this.content.delete(0, content.length());
			this.content.append(scan.next());
		} else {
			this.content.append(rewrite);
		}
	}

	public void print() {
		System.out.format("||");
		printName();
		printDate();
		printTag();
		printContent();
		System.out.println();
	}

	void printName() {
		if (name.length() > 10) {
			System.out.format("*[%s...] | ", name.substring(0, 8));
		} else
			System.out.format("*[%s] | ", name);
	}

	void printDate() {
		System.out.format("%s-%s | ", start_Date, end_Date);
		// 출력 자릿수 맞추기
	}

	void printTag() {
		for (String hashtag : tag) {
			System.out.format("%s ", hashtag);
		}
		System.out.format("| ");
	}

	void printContent() {
		if (content.length() > 15) {
			System.out.format("%-12s... ", content.substring(0, 13));
		} else
			System.out.format("%-15s ", content);
	}

	int progressLvl() {
		if (start_Date.equals("-"))
			return 1;// waitingList
		if (start_Date.compareTo(Dashboard.today) > 0)
			return 2;// toDo
		else {
			if (end_Date.compareTo(Dashboard.today) >= 0)
				return 3;// inProgress
			else
				return 4;// done
		}
	}

	public void modify(int menuChk, Scanner scan) {
		scan.nextLine();
		switch (menuChk) {
		case 1 -> setName(scan);
		case 2 -> setDate(scan);
		case 3 -> setTag(scan);
		case 4 -> {
			System.out.println("\'다시쓰기\' 입력 시 삭제 후 입력");
			System.out.println("그 외 입력 시 덧붙이기");
			setContent(scan);
		}
		}
	}

	boolean matches(String kwd) {
		if (this.name.contains(kwd))
			return true;
		if (matchesTag(kwd))
			return true;
		if (this.content.toString().contains(kwd))
			return true;
		return false;
	}

	boolean matchesTag(String kwd) {
		for (String t : tag) {
			if (t.substring(1).equals(kwd))
				return true;
		}
		return false;
	}
}