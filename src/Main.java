import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.jws.soap.SOAPBinding.Use;

import com.google.gson.Gson;

import model.User;
import processing.core.PApplet;

public class Main extends PApplet {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main("Main");
	
	}
	
	ServerSocket server;
	Socket socket;
    InputStream inputStream;
    OutputStream outputStream;
    BufferedReader bufferedReader;
    BufferedWriter bufferedWriter;
    InputStreamReader inputStreamReader;
    OutputStreamWriter outputStreamWriter;
    ArrayList<User> usuarios;
	
	
	public void settings() {
		
		size(600,600);
		
	}
	
	public void setup() {
	
	    InitConnection();	
	    usuarios = new ArrayList<User>();
	    usuarios.add(new User("soka","123"));
	    
	    }
	
	public void draw() {
		
		background(0);
	}
	
	private void InitConnection()  {
		
        new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				

                try {
                    server = new ServerSocket(500);
                    
                    System.out.println("cargando");
                    
                    socket = server.accept();
                    
                    System.out.println("conectado");
                    

                    inputStream = socket.getInputStream();
                    outputStream = socket.getOutputStream();
                     inputStreamReader= new InputStreamReader(inputStream);
                    outputStreamWriter = new OutputStreamWriter(outputStream);
                    bufferedReader = new BufferedReader(inputStreamReader);
                    bufferedWriter = new BufferedWriter(outputStreamWriter);


                    while (true){

                        String line = bufferedReader.readLine();
                        System.out.println(line);
                        
                        Gson gson = new Gson();
                        User tempUser = gson.fromJson(line, User.class);
                        
                        if(tempUser.getUserName().equals(usuarios.get(0).getUserName())) {
                        	
                        	bufferedWriter.write("ok\n");
                        	bufferedWriter.flush();
                        	
                        }
                        
                        else {
                        	bufferedWriter.write("nel\n");
                        	bufferedWriter.flush();
                        	
                        }
                        


                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
   
				
        }
		).start();}

	



	}
	



