package Entity;
import java.util.HashMap;

import �ͻ��˽���.NoteUI;

public class NoticeList {
private static HashMap<String, NoteUI> map = new HashMap<String, NoteUI>();
	
	//��map���桰ע�ᡱ
	public static void addNoteUI(NoteEntity noteUIEntity) {
		map.put(noteUIEntity.getName(), noteUIEntity.getNoteUI());	
	}
	
	//�رմ��ں�Ҫɾ��
	public static void deletNoteUI(String noteUIName) {
		
		//ɾ��֮ǰ�鿴�Ƿ����������, ��ֹ����
		if(map.get(noteUIName) != null) {
			map.remove(noteUIName);
		}
		
	}
	
	//ͨ���������Ʒ��ش���
	public static NoteUI getNoteUI(String name) {
		return map.get(name);
	}
}
