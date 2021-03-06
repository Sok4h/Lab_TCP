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

import model.Confirmation;
import model.User;
import processing.core.PApplet;
import processing.core.PShapeSVG.Text;

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
	int pantalla =0;

	public void settings() {

		size(600, 600);
		
		

	}

	public void setup() {

		InitConnection();
		usuarios = new ArrayList<User>();
		usuarios.add(new User("soka", "123"));
		usuarios.add(new User("camilo", "12345"));
		usuarios.add(new User("sokah", "pepito"));
		usuarios.add(new User("Sok4", "12345678"));

	}

	public void draw() {
		
		
		switch (pantalla) {
		case 0:
			
			background(0);
			textSize(20);
			text("Por favor ingrese los datos de acceso", width/2-150, height/2);
			
			break;

		case 1:
			
			background(0);
			textSize(20);
			text("Ha ingresado correctamente", width/2-150, height/2);
			
			break;
			
		case 2:
			
			background(0);
			textSize(20);
			text("Ha ingresado correctamente", width/2-150, height/2);
			
			break;
		}

		
	}

	private void InitConnection() {

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
					inputStreamReader = new InputStreamReader(inputStream);
					outputStreamWriter = new OutputStreamWriter(outputStream);
					bufferedReader = new BufferedReader(inputStreamReader);
					bufferedWriter = new BufferedWriter(outputStreamWriter);

					while (true) {

						
						String line = bufferedReader.readLine();
						System.out.println(line);

						Gson gson = new Gson();
						User tempUser = gson.fromJson(line, User.class);
						
						VerifyData(tempUser);

						

					}

				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		}).start();
	}

	private void SendMessage(Boolean resultado) {

		new Thread(

				new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub

						if (resultado) {

							try {
								Confirmation tempConfirmation = new Confirmation();
								tempConfirmation.setValid(true);
								Gson gson = new Gson();
								String confirmation = gson.toJson(tempConfirmation);
								bufferedWriter.write(confirmation+"\n");
								bufferedWriter.flush();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}

				else {

							try {
								
								Confirmation tempConfirmation = new Confirmation();
								tempConfirmation.setValid(false);
								Gson gson = new Gson();
								String confirmation = gson.toJson(tempConfirmation);
								bufferedWriter.write(confirmation+"\n");
								bufferedWriter.flush();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}

					}
				}

		).start();

	}
	
	private void VerifyData(User user) {
		
		Boolean logeado = false;
		
		System.out.println(user.getUserName());
		
		for (int i = 0; i < usuarios.size(); i++) {

			if (user.getUserName().equals(usuarios.get(i).getUserName())) {

				if (user.getPassword().equals(usuarios.get(i).getPassword())) {

					logeado = true;
					SendMessage(true);
					break;

				}

			}

		}
		

		if (!logeado) {
			SendMessage(false);

		}
		
	}
}
