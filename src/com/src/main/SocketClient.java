package com.src.main;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import org.apache.commons.io.IOUtils;


public class SocketClient{

	/**
	 * @param args
	 */
	
	public Socket socketClient;
	public Window window;
	public File file;
	public FileInputStream fis;
	public OutputStream os;
	public int port; //connected port
	public InetAddress IP;
	public int getPort(){
		return this.port;
	}
	
	public int setSocketClient(InetAddress IP,int port) throws IOException{
			try{
				this.socketClient = new Socket(IP,port);
				this.window = new Window(this);
				this.window.setVisible(true);
				return 1;	// connection established
			}catch(IOException e){
				return 0;
			}
	}
	


	public void getFile(String filename) throws IOException{
				try{
					this.file = this.window.file;
					DataOutputStream dos = new DataOutputStream(this.socketClient.getOutputStream());
					dos.writeUTF(filename);
					FileInputStream fis = new FileInputStream(this.file);
					int readByte = (int) IOUtils.copyLarge(fis, dos);
					System.out.println("FILE SENT : " + filename + "  Bytes :" + readByte);
			
					if( this.file != null) this.file = null;
					fis.close();
					dos.flush();
				}finally{				
					//if( this.os!=null)	this.os.close();
					if( this.window.file != null) this.window.file = null;
					this.window.setVisible(false); //test
					if( this.file != null) this.file = null;
					if( this.socketClient!=null) this.socketClient.close();
				}
				
				this.setSocketClient(this.IP, this.port); //test
				
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		SocketClient sc = new SocketClient();
		//sc.window = new Window();
		//sc.window.setVisible(true);
		
		Scanner in = new Scanner(System.in);
		System.out.println("Enter the port no you want to connect: ");
		int port = in.nextInt();
		sc.port = port;

		//InetAddress IP = InetAddress.getLocalHost();	//use getLocalHost for localhost
														//getByName("127.0.1.1")
			
		InetAddress IP = InetAddress.getByName("192.168.1.1");
		
		sc.IP = IP;//test
		
		//test, change argument
			if( sc.setSocketClient(sc.IP,sc.port) == 1 ){ // Socket established
				System.out.println("Connected to server at port :"+ sc.socketClient.getLocalPort() );
			}else{
					System.out.println("ERROR IN SETTING CONNECTION");
			}
	}

}
