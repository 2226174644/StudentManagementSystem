package Entity;
import java.util.HashMap;

import 客户端界面.NoteUI;

public class NoticeList {
private static HashMap<String, NoteUI> map = new HashMap<String, NoteUI>();
	
	//向map里面“注册”
	public static void addNoteUI(NoteEntity noteUIEntity) {
		map.put(noteUIEntity.getName(), noteUIEntity.getNoteUI());	
	}
	
	//关闭窗口后要删除
	public static void deletNoteUI(String noteUIName) {
		
		//删除之前查看是否有这个窗口, 防止出错
		if(map.get(noteUIName) != null) {
			map.remove(noteUIName);
		}
		
	}
	
	//通过公告名称返回窗口
	public static NoteUI getNoteUI(String name) {
		return map.get(name);
	}
}
