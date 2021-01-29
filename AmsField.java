package ams;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
//간단한 공항 시스템 입력, 수정, 삭제 , 출력 등을 구현하기

public class AmsField {
	// 항공기번호, 항공사, 최대승객수, 출발지, 도착지
	String[][] arPlane = new String[100][5]; //글로벌 데이터 저장소
	
	int insertCnt; // 추가한 숫자의 플래그 역활
	int rowNum = -1; // 끝자리 표기한다. 
	final String title = "AMS"; //시스템 이름 설정

	// 중복 검사
	// 중보여부흫체크 하는 방법으로 항공기 번호가 똑같을 경우 거른다.
	String[] checkDup(String plane_num) {
		rowNum = -1;
		String[] plane = null;

		for (int i = 0; i < insertCnt; i++) { //추가된 횟수 만큼 반복 없으면 데이터가 없으면 시작도 안됨
			if (arPlane[i][0].equals(plane_num)) { // 입력된 항공기 번호가 똑같을 경우  절대 string 비교는 == 을 쓰지 않는다.
				plane = arPlane[i]; // 중복이 발생되면 이에 맞는 데이터를 내보냄
				rowNum = i; // 열 인덱스를 업데이트
			}
		}
		return plane;
	}

	// 추가
	//100개 까지만 추가 가능
	int insert(String[] plane) {
		int check = 0;
		if (insertCnt == 100) {
			check = -1;
		} else if (checkDup(plane[0]) == null) { // 중복 확인후 중복이 안되면 arPlane insertCnt 인데스 자리에 데이터를 넣는다.
			arPlane[insertCnt] = plane;
			insertCnt++; //추가되었스니 1을 더해준다.
			check = 1;
		}
		return check;
	}

	// 수정
	boolean update(String plane_num, int idx, String new_value) {
		boolean check = false;
		String[] plane = checkDup(plane_num); //기존에 존재하는 것을 가지고 오면 
		if (plane != null) { // 데이터가 존재할경우 
			plane[idx] = new_value; //그 지정한 인덱스 자리에 지정한 값을 넣는다.
			check = true; //성공 여부의 check 변환
		}
		return check;
	}

	// 삭제
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
		String result = "항공기번호 / 항공사 / 최대승객수/ 출발지 / 도착지\n";
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

	// 검색(항공기번호, 항공사, 최대승객수, 출발지, 도착지)
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

	// 목록
	String selectAll() {
		int[] arIdx = new int[insertCnt];
		for (int i = 0; i < arIdx.length; i++) {
			arIdx[i] = i;
		}
		return getFrontForm(arIdx);
	}

	// view
	//화면에 나타날 부분들을 메인에 정리 하기 보단 까끔하게 class 안에 정리하여 main 코두를 깔끔하게 한다.
	void view() {
		String[] menu = {"추가하기", "수정하기", "삭제하기", "검색하기", "목록보기"}; //사용자가 선택할 메뉴
		String[] search_category = {"항공기 번호", "항공사", "최대승객수", "출발지", "도착지"}; //검색을 골랐을 경우 나오는 메뉴
		//절대 경로 : 현재 어떤 위치에 있더라도 찾아갈 수 있는 경로
		//상대 경로 : 현재 위치에 따라서 변경되는 경로 .(현재폴더), ..(이전폴더)
		ImageIcon icon = new ImageIcon("src/img/main.gif"); //화면 꾸미기 용
		int choice = 0; //스위치문에 필요한 요소
		String[] plane = null; //plane 리스트 초기화
		int insert_result = 0;  // 추가 횟수 초기화
		boolean update_result = false;
		boolean delete_result = false;		
		String msg = ""; 
		String keyword = "";
		
		while(true) {
			choice = JOptionPane.showOptionDialog(null, "", title,
					JOptionPane.DEFAULT_OPTION,
					JOptionPane.PLAIN_MESSAGE, icon,
					menu, null); //초이스 참 메뉴와 이미지를 넣어 준다.
			
			if(choice == -1) {break;}
			//스위치 문을 사용해 여러 옵션응 다룰때 사용한다.
			switch(choice) {
			//추가
			case 0:
				plane = ("" + JOptionPane.showInputDialog(null, 
						"밑의 양식에 맞춰서 항공기를 추가해주세요\n"
						+ "항공기번호,항공사,최대승객수,출발지,도착지", title, JOptionPane.PLAIN_MESSAGE,
						icon, null, null)).split(","); //input dialog로 입력값 필요한 순서 대로 받는다.
				if(plane.length != 5) { // 받은 데이터가 5개가 아니면 오류 메세지
					JOptionPane.showMessageDialog(null, "잘못 입력하셨습니다.");
					continue;
				}
				insert_result = insert(plane);  // check값이 나온다. 
				if(insert_result == 1) {
					msg = "추가 성공"; 
				}else if(insert_result == 0){
					msg = "추가 실패 / 중복된 항공기 번호"; // 0 이면 중복을 말하는 것이고
				}else {
					msg = "추가 실패 / 더 이상 추가할 수 없음";  //100개 제한을 했기때문에 100이상 못 받는다.
				}
				JOptionPane.showMessageDialog(null, msg);
				break;
			//수정
			case 1:
				plane = ("" + JOptionPane.showInputDialog(null, 
						"밑의 양식에 맞춰서 항공기 데이터를 수정해주세요\n"
						+"항공 번호, 밑에서 바꾸고 싶은 값, 수정 값", title, JOptionPane.PLAIN_MESSAGE,
						icon, null, null)).split(","); //input dialog로 입력값 필요한 순서 대로 받는다.
				if(plane.length != 3) { // 받은 데이터가 5개가 아니면 오류 메세지
					JOptionPane.showMessageDialog(null, "존재 하지 않거나/잘못 입력하셨습니다.");
					continue;
				}else {
					update_result = update(plane[0],Integer.parseInt(plane[1]),plane[2]);  // check값이 나온다.
				}
				
				 
				if(update_result = true) {
					msg = "수정 성공"; 
				}else if(update_result = false){
					msg = "수정 실패 / 타입 에러"; // 0 이면 중복을 말하는 것이고
				}else {
					msg = "수정할수 없습니다.";  //100개 제한을 했기때문에 100이상 못 받는다.
				}
				JOptionPane.showMessageDialog(null, msg);
				break;
				
			//삭제
			case 2:
				plane = ("" + JOptionPane.showInputDialog(null, 
						"항공기 번호를 기입해주세요", title, JOptionPane.PLAIN_MESSAGE,
						icon, null, null)).split(","); //input dialog로 입력값 필요한 순서 대로 받는다.
				if(plane.length < 1) { // 받은 데이터가 5개가 아니면 오류 메세지
					JOptionPane.showMessageDialog(null, "존재 하지 않거나/잘못 입력하셨습니다.");
					continue;
				}else {
					delete_result = delete(plane[0]);  // check값이 나온다.
				}
				if(delete_result = true) {
					msg = "삭제 성공"; 
				}else if(delete_result = false){
					msg = "삭제 실패 / 타입 에러"; // 0 이면 중복을 말하는 것이고
				}else {
					msg = "삭제할수 없습니다.";  //100개 제한을 했기때문에 100이상 못 받는다.
				}
				JOptionPane.showMessageDialog(null, msg);
				break;
			//검색
			case 3:
				choice = JOptionPane.showOptionDialog(null, "", title,
						JOptionPane.DEFAULT_OPTION,
						JOptionPane.PLAIN_MESSAGE, icon,
						search_category, null);

				keyword = "" + JOptionPane.showInputDialog(null, 
						"검색하실 "+search_category[choice]+"을(를) 입력하세요", title, JOptionPane.PLAIN_MESSAGE,
						icon, null, null);
				
				msg = select(choice, keyword);
				JOptionPane.showMessageDialog(null, 
						msg.equals("") ? "검색 결과 없음" : msg);
				
				break;
			//목록
			case 4:
				msg = selectAll();
				JOptionPane.showMessageDialog(null,
						msg.equals("") ? "목록 없음" : msg);
				break;
			}
			
		}
	}
}






