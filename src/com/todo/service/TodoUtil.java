package com.todo.service;

import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.io.FileWriter;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	static int count = 0;
	
	public static void createItem(TodoList list) {
		
		String title, desc, category, due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[�׸� �߰�]\n"
				+ "ī�װ� > ");
		category = sc.next();
		
		System.out.print("���� > ");
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.printf("�̹� �����ϴ� �����Դϴ�.");
			return;
		}
		
		System.out.print("���� > ");
		desc = sc.nextLine();
		desc = sc.nextLine();
		
		System.out.print("�������� > ");
		due_date = sc.next();
		
		TodoItem t = new TodoItem(title, desc, category, due_date);
		list.addItem(t);
		Setcount(Getcount()+1);
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[�׸� ����]\n"
				+ "������ �׸��� ��ȣ�� �Է��Ͻÿ� > ");
		
		int num = sc.nextInt();
		boolean del = false;
		
		int cnt = 1;
		for (TodoItem item : l.getList()) {
			if (cnt == num) {
				System.out.println(num + ". [" + item.getCategory() + "] " + item.getTitle() + " - " + item.getDesc() + " - " + item.getDue_date() + " = " + item.getTime());
			}
			cnt++;
		}
		System.out.print("�� �׸��� �����Ͻðڽ��ϱ�? (y/n) > ");
		String answer = sc.next();
		
		if(answer.equals("y")) {
		
		cnt = 1;
			for (TodoItem item : l.getList()) {
				if (num == cnt) {
					l.deleteItem(item);
					System.out.println("�ش� �׸��� ���� �Ǿ����ϴ�.");
					del = true;
					Setcount(Getcount()-1);
					break;
				}
				cnt++;
			}
			if(!del)
				System.out.println("�ش� �׸��� ���� ���� �ʽ��ϴ�.");
		
		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[�׸� ����]\n"
				+ "������ �׸��� ��ȣ�� �Է��Ͻÿ� > ");
		int num = sc.nextInt();
		if(num > Getcount() || num < 1) {
			System.out.println("�ùٸ� ��ȣ�� �Է��ϼ���. ");
			return;
		}
		
		int cnt = 1;
		for (TodoItem item : l.getList()) {
			if (cnt == num) {
				System.out.println(num + ". [" + item.getCategory() + "] " + item.getTitle() + " - " + item.getDesc() + " - " + item.getDue_date() + " = " + item.getTime());
			}
			cnt++;
		}

		System.out.print("�� ���� > ");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("������ �ߺ��˴ϴ�. �ߺ����� ���� ������ �Է����ּ���.");
			return;
		}
		
		System.out.print("�� ī�װ� > ");
		String new_category = sc.next().trim();
		
		System.out.print("�� ���� > ");
		String new_description = sc.nextLine().trim();
		new_description = sc.nextLine().trim();
		
		System.out.print("�� �������� > ");
		String new_due = sc.next().trim();
		
		cnt = 1;
		for (TodoItem item : l.getList()) {
			if (cnt == num) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description, new_category, new_due);
				l.addItem(t);
				System.out.println("�����Ǿ����ϴ�.");
			}
			cnt++;
		}

	}

	public static void listAll(TodoList l) {
		System.out.println("[ ��ü ���, �� " + Getcount() + "��]");
		int i = 1;
		for (TodoItem item : l.getList()) {
			System.out.println(i++ + ". [" + item.getCategory() + "] " + item.getTitle() + " - " + item.getDesc() + " - " + item.getDue_date() + " = " + item.getTime());
		}
	}
	
	public static void saveList(TodoList l, String filename) {
		try {
			Writer w = new FileWriter(filename);
			
			for(TodoItem item : l.getList()) {
				w.write(item.toSaveString());
			}
			
			System.out.println("��� �����Ͱ� ����Ǿ����ϴ�.");
			w.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void loadList(TodoList l, String filename) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
		
			String str;
			int cnt = 0;
			while((str = reader.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(str, "##");
				String category = st.nextToken();
				String title = st.nextToken();
				String desc = st.nextToken();
				String due = st.nextToken();
				String time = st.nextToken();
				TodoItem t = new TodoItem(title, desc, time, category, due);
				l.addItem(t);
				cnt++;
			}
			System.out.printf("%d���� �׸��� �о����ϴ�.\n", cnt);
			Setcount(cnt);
			reader.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void findList(TodoList l, String key) {
		int cnt = 0;
		int i = 1;
		for (TodoItem item : l.getList()) {
			if(item.getTitle().contains(key)) {
				System.out.println(i + ". [" + item.getCategory() + "] " + item.getTitle() + " - " + item.getDesc() + " - " + item.getDue_date() + " = " + item.getTime());
				cnt++;
			}
			else if(item.getDesc().contains(key)) {
				System.out.println(i + ". [" + item.getCategory() + "] " + item.getTitle() + " - " + item.getDesc() + " - " + item.getDue_date() + " = " + item.getTime());
				cnt++;
			}
			i++;
		}
		System.out.println("�� " + cnt + "���� �׸��� ã�ҽ��ϴ�.");
	}
	public static void findCate(TodoList l, String key) {
		int cnt = 0;
		int i = 1;
		for (TodoItem item : l.getList()) {
			if(item.getCategory().contains(key)) {
				System.out.println(i + ". [" + item.getCategory() + "] " + item.getTitle() + " - " + item.getDesc() + " - " + item.getDue_date() + " = " + item.getTime());
				cnt++;
			}
			i++;
		}
		System.out.println("�� " + cnt + "���� �׸��� ã�ҽ��ϴ�.");
	}
	public static void lsCate(TodoList l) {
		String cate = "";
		int cnt = 0;
		for(TodoItem item : l.getList()) {
			if(!cate.contains(item.getCategory())) {
				if(cate.equals("")) 
					cate = item.getCategory();
				else
					cate = cate + " / " + item.getCategory();
				cnt++;
			}
		}
		System.out.println(cate);
		System.out.println("�� " + cnt + "���� ī�װ��� ��ϵǾ� �ֽ��ϴ�.");
	}
	public static void Setcount(int cnt) {
		count = cnt;
	}
	public  static int Getcount() {
		return count;
	}
}
