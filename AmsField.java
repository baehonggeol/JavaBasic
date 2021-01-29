package ams;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
//������ ���� �ý��� �Է�, ����, ���� , ��� ���� �����ϱ�

public class AmsField {
	// �װ����ȣ, �װ���, �ִ�°���, �����, ������
	String[][] arPlane = new String[100][5]; //�۷ι� ������ �����
	
	int insertCnt; // �߰��� ������ �÷��� ��Ȱ
	int rowNum = -1; // ���ڸ� ǥ���Ѵ�. 
	final String title = "AMS"; //�ý��� �̸� ����

	// �ߺ� �˻�
	// �ߺ�����ňüũ �ϴ� ������� �װ��� ��ȣ�� �Ȱ��� ��� �Ÿ���.
	String[] checkDup(String plane_num) {
		rowNum = -1;
		String[] plane = null;

		for (int i = 0; i < insertCnt; i++) { //�߰��� Ƚ�� ��ŭ �ݺ� ������ �����Ͱ� ������ ���۵� �ȵ�
			if (arPlane[i][0].equals(plane_num)) { // �Էµ� �װ��� ��ȣ�� �Ȱ��� ���  ���� string �񱳴� == �� ���� �ʴ´�.
				plane = arPlane[i]; // �ߺ��� �߻��Ǹ� �̿� �´� �����͸� ������
				rowNum = i; // �� �ε����� ������Ʈ
			}
		}
		return plane;
	}

	// �߰�
	//100�� ������ �߰� ����
	int insert(String[] plane) {
		int check = 0;
		if (insertCnt == 100) {
			check = -1;
		} else if (checkDup(plane[0]) == null) { // �ߺ� Ȯ���� �ߺ��� �ȵǸ� arPlane insertCnt �ε��� �ڸ��� �����͸� �ִ´�.
			arPlane[insertCnt] = plane;
			insertCnt++; //�߰��Ǿ����� 1�� �����ش�.
			check = 1;
		}
		return check;
	}

	// ����
	boolean update(String plane_num, int idx, String new_value) {
		boolean check = false;
		String[] plane = checkDup(plane_num); //������ �����ϴ� ���� ������ ���� 
		if (plane != null) { // �����Ͱ� �����Ұ�� 
			plane[idx] = new_value; //�� ������ �ε��� �ڸ��� ������ ���� �ִ´�.
			check = true; //���� ������ check ��ȯ
		}
		return check;
	}

	// ����
	boolean delete(String plane_num) {
		String[] plane = checkDup(plane_num);
		boolean check = false;
		if (plane != null) {
			for (int i = rowNum; i < insertCnt; i++) {
				if (i == 99) {
					arPlane[i] = null;
					break;
				}
				arPlane[i] = arPlane[i + 1];

			}
			insertCnt--;
			check = true;
		}
		return check;
	}

	String getFrontForm(int[] arIdx) {
		int cnt = 0;
		String result = "�װ����ȣ / �װ��� / �ִ�°���/ ����� / ������\n";
		if(arIdx != null) {
			for (int i = 0; i < arIdx.length; i++) {
				++cnt;
				result += cnt + ". ";
				for (int j = 0; j < arPlane[i].length; j++) {
					result += arPlane[arIdx[i]][j];
					result += j == arPlane[i].length - 1 ? "\n" : " / ";
				}
			}
			return result;
		}
		return "";
	}

	// �˻�(�װ����ȣ, �װ���, �ִ�°���, �����, ������)
	String select(int idx, String keyword) {
		boolean check = false;
		boolean flag = false;
		int[] arIdx = null;
		String temp = "";
		for (int i = 0; i < insertCnt; i++) {
			check = idx == 2 ? arPlane[i][idx].equals(keyword) : arPlane[i][idx].contains(keyword);
			if (check) {
				temp += i + ",";
				flag = true;
			}
		}
		if(flag) {
			String[] arTemp = temp.split(",");
			arIdx = new int[arTemp.length];
			for (int i = 0; i < arTemp.length; i++) {
				arIdx[i] = Integer.parseInt(arTemp[i]);
			}
		}
		return getFrontForm(arIdx);
	}

	// ���
	String selectAll() {
		int[] arIdx = new int[insertCnt];
		for (int i = 0; i < arIdx.length; i++) {
			arIdx[i] = i;
		}
		return getFrontForm(arIdx);
	}

	// view
	//ȭ�鿡 ��Ÿ�� �κе��� ���ο� ���� �ϱ� ���� ����ϰ� class �ȿ� �����Ͽ� main �ڵθ� ����ϰ� �Ѵ�.
	void view() {
		String[] menu = {"�߰��ϱ�", "�����ϱ�", "�����ϱ�", "�˻��ϱ�", "��Ϻ���"}; //����ڰ� ������ �޴�
		String[] search_category = {"�װ��� ��ȣ", "�װ���", "�ִ�°���", "�����", "������"}; //�˻��� ����� ��� ������ �޴�
		//���� ��� : ���� � ��ġ�� �ִ��� ã�ư� �� �ִ� ���
		//��� ��� : ���� ��ġ�� ���� ����Ǵ� ��� .(��������), ..(��������)
		ImageIcon icon = new ImageIcon("src/img/main.gif"); //ȭ�� �ٹ̱� ��
		int choice = 0; //����ġ���� �ʿ��� ���
		String[] plane = null; //plane ����Ʈ �ʱ�ȭ
		int insert_result = 0;  // �߰� Ƚ�� �ʱ�ȭ
		boolean update_result = false;
		boolean delete_result = false;		
		String msg = ""; 
		String keyword = "";
		
		while(true) {
			choice = JOptionPane.showOptionDialog(null, "", title,
					JOptionPane.DEFAULT_OPTION,
					JOptionPane.PLAIN_MESSAGE, icon,
					menu, null); //���̽� �� �޴��� �̹����� �־� �ش�.
			
			if(choice == -1) {break;}
			//����ġ ���� ����� ���� �ɼ��� �ٷ궧 ����Ѵ�.
			switch(choice) {
			//�߰�
			case 0:
				plane = ("" + JOptionPane.showInputDialog(null, 
						"���� ��Ŀ� ���缭 �װ��⸦ �߰����ּ���\n"
						+ "�װ����ȣ,�װ���,�ִ�°���,�����,������", title, JOptionPane.PLAIN_MESSAGE,
						icon, null, null)).split(","); //input dialog�� �Է°� �ʿ��� ���� ��� �޴´�.
				if(plane.length != 5) { // ���� �����Ͱ� 5���� �ƴϸ� ���� �޼���
					JOptionPane.showMessageDialog(null, "�߸� �Է��ϼ̽��ϴ�.");
					continue;
				}
				insert_result = insert(plane);  // check���� ���´�. 
				if(insert_result == 1) {
					msg = "�߰� ����"; 
				}else if(insert_result == 0){
					msg = "�߰� ���� / �ߺ��� �װ��� ��ȣ"; // 0 �̸� �ߺ��� ���ϴ� ���̰�
				}else {
					msg = "�߰� ���� / �� �̻� �߰��� �� ����";  //100�� ������ �߱⶧���� 100�̻� �� �޴´�.
				}
				JOptionPane.showMessageDialog(null, msg);
				break;
			//����
			case 1:
				plane = ("" + JOptionPane.showInputDialog(null, 
						"���� ��Ŀ� ���缭 �װ��� �����͸� �������ּ���\n"
						+"�װ� ��ȣ, �ؿ��� �ٲٰ� ���� ��, ���� ��", title, JOptionPane.PLAIN_MESSAGE,
						icon, null, null)).split(","); //input dialog�� �Է°� �ʿ��� ���� ��� �޴´�.
				if(plane.length != 3) { // ���� �����Ͱ� 5���� �ƴϸ� ���� �޼���
					JOptionPane.showMessageDialog(null, "���� ���� �ʰų�/�߸� �Է��ϼ̽��ϴ�.");
					continue;
				}else {
					update_result = update(plane[0],Integer.parseInt(plane[1]),plane[2]);  // check���� ���´�.
				}
				
				 
				if(update_result = true) {
					msg = "���� ����"; 
				}else if(update_result = false){
					msg = "���� ���� / Ÿ�� ����"; // 0 �̸� �ߺ��� ���ϴ� ���̰�
				}else {
					msg = "�����Ҽ� �����ϴ�.";  //100�� ������ �߱⶧���� 100�̻� �� �޴´�.
				}
				JOptionPane.showMessageDialog(null, msg);
				break;
				
			//����
			case 2:
				plane = ("" + JOptionPane.showInputDialog(null, 
						"�װ��� ��ȣ�� �������ּ���", title, JOptionPane.PLAIN_MESSAGE,
						icon, null, null)).split(","); //input dialog�� �Է°� �ʿ��� ���� ��� �޴´�.
				if(plane.length < 1) { // ���� �����Ͱ� 5���� �ƴϸ� ���� �޼���
					JOptionPane.showMessageDialog(null, "���� ���� �ʰų�/�߸� �Է��ϼ̽��ϴ�.");
					continue;
				}else {
					delete_result = delete(plane[0]);  // check���� ���´�.
				}
				if(delete_result = true) {
					msg = "���� ����"; 
				}else if(delete_result = false){
					msg = "���� ���� / Ÿ�� ����"; // 0 �̸� �ߺ��� ���ϴ� ���̰�
				}else {
					msg = "�����Ҽ� �����ϴ�.";  //100�� ������ �߱⶧���� 100�̻� �� �޴´�.
				}
				JOptionPane.showMessageDialog(null, msg);
				break;
			//�˻�
			case 3:
				choice = JOptionPane.showOptionDialog(null, "", title,
						JOptionPane.DEFAULT_OPTION,
						JOptionPane.PLAIN_MESSAGE, icon,
						search_category, null);

				keyword = "" + JOptionPane.showInputDialog(null, 
						"�˻��Ͻ� "+search_category[choice]+"��(��) �Է��ϼ���", title, JOptionPane.PLAIN_MESSAGE,
						icon, null, null);
				
				msg = select(choice, keyword);
				JOptionPane.showMessageDialog(null, 
						msg.equals("") ? "�˻� ��� ����" : msg);
				
				break;
			//���
			case 4:
				msg = selectAll();
				JOptionPane.showMessageDialog(null,
						msg.equals("") ? "��� ����" : msg);
				break;
			}
			
		}
	}
}






