package chapter23;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer extends Thread{
	
	ServerSocket ss;
	ArrayList<CopyClient> list;
	
	public ChatServer() {
		
		try {
			
			list=new ArrayList<CopyClient>(); //����Ʈ ����
			ss=new ServerSocket(3300);
			
			System.out.println("Server Start!");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public static void main(String[] args) {
		new ChatServer().start();

	}

	@Override
	public void run() {
		
		while(true) {
            //���Ӵ�� ����
		try {
			Socket s=ss.accept();
			
			String ip=s.getInetAddress().getHostAddress();	
			
		    System.out.println(ip +"�� ���Ӽ���");	
		    //Ŭ���̾�Ʈ�� ������ ������ ���� CopyClient ����
		    CopyClient cc=new CopyClient(s, this);
		    list.add(cc); //list�� CopyClient�߰�
		    cc.start(); //�ش� Ŭ���̾�Ʈ ������ ������ ���� => InputStream�� ���� Ŭ���̾�Ʈ�κ��� �Ѿ�� �� ó��
		    
		} catch (Exception e) {
			// TODO: handle exception
		}
	 }
	}
	//�ۼ��� ������ Ŭ���̾�Ʈ���� ����
	public void sendMessage(String msg) {
		//�����ڵ��� CopyClient�� ��������� ArrayList�� ��� ����� ���´�
		try {
			
			for(CopyClient cc: list) {
				cc.out.println(msg);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	public void removeClient(CopyClient cc) {
		list.remove(cc); //�ް������� ���޹��� Ŭ���̾�Ʈ�� ArrayList���� ����
		sendMessage("�١ڡ١� "+cc.ip+"�� ����! �١ڡ١�");
		
	}

}