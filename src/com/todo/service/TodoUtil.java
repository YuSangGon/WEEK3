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
	
	public static void createItem(TodoList list) {
		
		String title, desc;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[�׸� �߰�]\n"
				+ "���� > ");
		
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.printf("�̹� �����ϴ� �����Դϴ�.");
			return;
		}
		
		System.out.print("���� > ");
		desc = sc.nextLine();
		desc = sc.nextLine();
		
		TodoItem t = new TodoItem(title, desc);
		list.addItem(t);
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[�׸� ����]\n"
				+ "������ �׸��� ������ �Է��Ͻÿ� > ");
		
		String title = sc.next();
		boolean del = false;
		
		for (TodoItem item : l.getList()) {
			if (title.equals(item.getTitle())) {
				l.deleteItem(item);
				System.out.println("�ش� �׸��� ���� �Ǿ����ϴ�.");
				del = true;
				break;
			}
		}
		if(!del)
			System.out.println("�ش� �׸��� ���� ���� �ʽ��ϴ�.");
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[�׸� ����]\n"
				+ "������ �׸��� ������ �Է��Ͻÿ� > ");
		String title = sc.next().trim();
		if (!l.isDuplicate(title)) {
			System.out.println("������ �������� �ʽ��ϴ�.");
			return;
		}

		System.out.print("�� ���� > ");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("������ �ߺ��˴ϴ�. �ߺ����� ���� ������ �Է����ּ���.");
			return;
		}
		
		System.out.print("�� ���� > ");
		String new_description = sc.nextLine().trim();
		new_description = sc.nextLine().trim();
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description);
				l.addItem(t);
				System.out.println("�����Ǿ����ϴ�.");
			}
		}

	}

	public static void listAll(TodoList l) {
		System.out.println("[ ��ü ��� ]");
		for (TodoItem item : l.getList()) {
			System.out.println("[" + item.getTitle() + "] " + item.getDesc() + " - " + item.getTime());
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
			int count = 0;
			while((str = reader.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(str, "##");
				String title = st.nextToken();
				String desc = st.nextToken();
				String time = st.nextToken();
				TodoItem t = new TodoItem(title, desc, time);
				l.addItem(t);
				count++;
			}
			System.out.printf("%d���� �׸��� �о����ϴ�.\n", count);
			reader.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
