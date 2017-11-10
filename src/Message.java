import java.io.Serializable;

public class Message implements Serializable{
	
	//ERR = error message
	//MSG = standard message
	private String type;
	
	private String userEmitter;
	private String userRecevier = null;
	private String room;
	private String text;
	
	//Broadcast message
	public Message(String type, String userEmitter, String room, String text) {
		this.type = type;
		this.userEmitter = userEmitter;
		this.room = room;
		this.text = text;
	}
	
	//Private message
	public Message(String type, String userEmitter, String userRecevier, String room, String text) {
		this.type = type;
		this.userEmitter = userEmitter;
		this.userRecevier = userRecevier;
		this.room = room;
		this.text = text;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUserEmitter() {
		return userEmitter;
	}

	public void setUserEmitter(String userEmitter) {
		this.userEmitter = userEmitter;
	}

	public String getUserRecevier() {
		return userRecevier;
	}

	public void setUserRecevier(String userRecevier) {
		this.userRecevier = userRecevier;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
