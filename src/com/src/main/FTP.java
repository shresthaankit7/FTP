package com.src.main;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.Scanner;

import org.apache.commons.io.IOUtils;

/* Starts the ServerSocket */


public class FTP extends Thread{

	/**
	 * @param args
	 */
	public ServerSocket serverSocket;
	public Socket socketClient;
	public Window serverWindow;
	public DataInputStream dis;
	public FileOutputStream fos;
	public InputStream is;
	public File file;
	public int count = 0; // no of files received;
	public int current = 0; // current offset for file reading.

	
	public FTP() throws IOException{
		
			Scanner in = new Scanner(System.in);
			System.out.println("Enter the port to listen:");
			int port = in.nextInt();

			//InetAddress IP = InetAddress.getLocalHost(); //this is the actual code
			InetAddress IP = InetAddress.getByName("192.168.1.1");	//test
			
			
			this.serverSocket = new ServerSocket(port,50,IP);	//port and backlog=maximum queue //creating a server
	}
	
	public void run(){
		System.out.println("Waiting for connection at: " + this.serverSocket.getLocalSocketAddress());
		try {
			this.socketClient = this.serverSocket.accept();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	//client socket is copied like		
		System.out.println("Connected to client at: " + this.serverSocket.getLocalSocketAddress());
	
		try {
			this.dis = new DataInputStream(this.socketClient.getInputStream());
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		while(true){
			try{
				if(this.listenPort()!= 0){
					System.out.println("file received");
					this.count++;
					break; //
				}else{
				//	System.out.println("NO FILE");
				}
			}catch(IOException e){
					e.printStackTrace();
			}
		}
			try {
				this.socketClient.close();
				this.run();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
	}
	
	public int listenPort() throws IOException{
		//System.out.println("LISTENING");
			try{
					if( this.dis.available() != 0 ){
						String filename = this.dis.readUTF();
					 	this.fos = new FileOutputStream("/home/ankit07/" + filename);
						int bytesRead = (int) IOUtils.copyLarge(this.dis,this.fos);	//no of bytes copies
						System.out.println("byteRead :"  + bytesRead);
						return bytesRead;					
					}else{
						return 0;
					}
				}finally{
			
				}
			
			
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			try{
				Thread t = new FTP();
				t.start();
			}catch(IOException e){
				e.printStackTrace();
			}
	}
}
