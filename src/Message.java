
public class Message {
	
	int type;
	//1 = request name
	//2 = standard message
	String userEmitter;
	String userRecevier = null;
	String room = null;
	
	//Broadcast message
	public Message(int type, String userEmitter, String room) {
		this.type = type;
		this.userEmitter = userEmitter;
		this.room = room;
	}
	
	//Private message
	public Message(int type, String userEmitter, String userRecevier, String room) {
		this.type = type;
		this.userEmitter = userEmitter;
		this.userRecevier = userRecevier;
		this.room = room;
	}
	
}
