package Entity;

import java.net.Socket;

public class SocketEntity {
		private Socket socket;
		private String name;
		public SocketEntity() {
			super();
		}
		
		public SocketEntity(Socket socket, String name) {
			super();
			this.socket = socket;
			this.name = name;
		}
		
		public Socket getSocket() {
			return socket;
		}
		
		public void setSocket(Socket socket) {
			this.socket = socket;
		}
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
}
