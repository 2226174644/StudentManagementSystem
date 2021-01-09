package Util;
import java.net.Socket;
import java.util.HashMap;

import Entity.SocketEntity;
public class SocketList {
	private static HashMap<String, Socket> map = new HashMap<String, Socket>();
		public static void addSocket(SocketEntity socketEntity) {
			map.put(socketEntity.getName(),socketEntity.getSocket());
		}
		//Í¨¹ýêÇ³Æ·µ»Øsocket
		public static Socket getSocket(String name) {
			return map.get(name);
		}
		public static HashMap<String,Socket>getMap(){
			return map;
		}
		public static void deletSocket(String name) {
			if(map.get(name)!=null) {
				map.remove(name);
			}
			return;
		}
}
