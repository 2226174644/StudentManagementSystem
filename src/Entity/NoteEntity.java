package Entity;

import �ͻ��˽���.NoteUI;

public class NoteEntity {
	private NoteUI noteUI;
	private String name;
	public NoteEntity() {
		super();
	}
	public NoteEntity(NoteUI NoteUI,String name) {
		super();
		this.noteUI=NoteUI;
		this.name=name;
	}
	public NoteUI getNoteUI() {
		return noteUI;
		
	}
	public void setNoteUI(NoteUI noteUI) {
		this.noteUI=noteUI;
	}
	public String getName() {
		return name;
		
	}
	public void setName(String name) {
	this.name=name;
	}
}
	
