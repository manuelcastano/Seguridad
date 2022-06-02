import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {

	private static String pathToTheFile = ".//src//file//data.txt";
	private static File datos;

	public static void main(String[] args) {
		datos = new File(pathToTheFile);
		Scanner s = new Scanner(System.in);
		System.out.println("1. Ingresar");
		System.out.println("2. Registrarse");
		int option = s.nextInt();
		s.nextLine();
		switch(option) {
		case 1:
			System.out.println("Usuario:");
			String usuario = s.nextLine();
			System.out.println("Contraseña:");
			String contraseña = s.nextLine();
			if(usuario.equals("admin") && contraseña.equals("0123456789")) {
				admin();
			} else {
				try {
					if(Password.validatePassword(contraseña, usuario)) {
						usuario(usuario);
					} else {
						System.out.println("Usuario o contraseña incorrecta");
					}
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvalidKeySpecException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		case 2:
			System.out.println("Usuario:");
			String usuarioR = s.nextLine();
			System.out.println("Contraseña:");
			String contraseñaR = s.nextLine();
			registrarUsuario(usuarioR, contraseñaR);
			break;
		}
	}


	//Si es el usuario administrador
	public static void admin() {
		Scanner adminS = new Scanner(System.in);
		int option = -1;
		while(option != 0) {
			System.out.println("1. Consultar los nombres de los usuarios existentes");
			System.out.println("2. Eliminar un usuario");
			System.out.println("3. Poner en blanco la contraseña de un usuario");
			System.out.println("0. Salir");
			option = adminS.nextInt();
			adminS.nextLine();
			switch(option) {

			//consultamos los usuarios existentes
			case 1:
				try {
					FileReader f = new FileReader(datos);
					BufferedReader fileInput=new BufferedReader(f);
					while(fileInput.ready()) {
						String readLine=fileInput.readLine();
						System.out.println(readLine.split(",")[0]);
					}
					fileInput.close();
					f.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

				//Eliminamos un usuario
			case 2:
				try {
					System.out.println("Usuario a eliminar");
					String usuarioAEliminar = adminS.nextLine();
					File tempFile = new File(datos.getAbsolutePath() + ".tmp");

					BufferedReader br = new BufferedReader(new FileReader(pathToTheFile));
					PrintWriter pw = new PrintWriter(new FileWriter(tempFile));

					String line = null;

					//Read from the original file and write to the new
					//unless content matches data to be removed.
					while ((line = br.readLine()) != null) {
						if (!line.split(",")[0].equals(usuarioAEliminar)) {
							pw.println(line);
							pw.flush();
						}
					}
					pw.close();
					br.close();

					//Delete the original file
					if (!datos.delete()) {
						System.out.println("Could not delete file");
						return;
					}

					//Rename the new file to the filename the original file had.
					if (!tempFile.renameTo(datos)){
						System.out.println("Could not rename file");
					}
					break;
				} catch(SecurityException e) {
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				//Para poner en blanco la contraseña de un usuario
			case 3:
				try {
					System.out.println("Nombre del usuario");
					String usuario = adminS.nextLine();
					File tempFile = new File(datos.getAbsolutePath() + ".tmp");

					BufferedReader br = new BufferedReader(new FileReader(pathToTheFile));
					PrintWriter pw = new PrintWriter(new FileWriter(tempFile));

					String line = null;

					//Read from the original file and write to the new
					//unless content matches data to be removed.
					while ((line = br.readLine()) != null) {
						if (!line.split(",")[0].equals(usuario)) {
							pw.println(line);
							pw.flush();
						} else {
							String linea = line.split(",")[0] + ", ,";
							if(line.split("").length > 2) {
								linea += line.split(",")[2];
							}
							pw.println(linea);
							pw.flush();
						}
					}
					pw.close();
					br.close();

					//Delete the original file
					if (!datos.delete()) {
						System.out.println("Could not delete file");
					}

					//Rename the new file to the filename the original file had.
					if (!tempFile.renameTo(datos)){
						System.out.println("Could not rename file");
					}
				} catch(SecurityException e) {
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

			default:
				break;
			}
		}
	}


	//Si es un usuario común
	public static void usuario(String nombreUsuario) {
		Scanner usuarioS = new Scanner(System.in);
		int option = -1;
		while(option != 0) {
			System.out.println("1. Consultar su ultima fecha/hora de login");
			System.out.println("2. Cambiar la contraseña");
			System.out.println("0. Salir");
			option = usuarioS.nextInt();
			usuarioS.nextLine();
			switch(option) {
			//Consultar el ultimo login
			case 1:
				try {
					FileReader f = new FileReader(datos);
					BufferedReader fileInput=new BufferedReader(f);
					while(fileInput.ready()) {
						String readLine=fileInput.readLine();
						if(readLine.split(",")[0].equals(nombreUsuario)) {
							if(readLine.split(",").length > 2) {
								System.out.println(readLine.split(",")[2]);
							} else {
								System.out.println("Aun no tiene fecha y hora de su ultima conexion");
							}
							break;
						}
					}
					fileInput.close();
					f.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
				
			//Cuando nos salimos, guardamos la hora de la ultima conexion
			case 0:
				try {
					File tempFile = new File(datos.getAbsolutePath() + ".tmp");

					BufferedReader br = new BufferedReader(new FileReader(pathToTheFile));
					PrintWriter pw = new PrintWriter(new FileWriter(tempFile));

					String line = null;

					//Read from the original file and write to the new
					//unless content matches data to be removed.
					while ((line = br.readLine()) != null) {
						if (!line.split(",")[0].equals(nombreUsuario)) {
							pw.println(line);
							pw.flush();
						} else {
							DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
							String fecha = dtf.format(LocalDateTime.now());
							String[] linea = line.split(",");
							String lineaEntera = linea[0] + "," + linea[1] + "," + fecha;
							pw.println(lineaEntera);
							pw.flush();
						}
					}
					pw.close();
					br.close();

					//Delete the original file
					if (!datos.delete()) {
						System.out.println("Could not delete file");
					}

					//Rename the new file to the filename the original file had.
					if (!tempFile.renameTo(datos)){
						System.out.println("Could not rename file");
					}
				} catch(SecurityException e) {
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 2:
				try {
					System.out.println("Ingrese la nueva contraseña");
					File tempFile = new File(datos.getAbsolutePath() + ".tmp");

					BufferedReader br = new BufferedReader(new FileReader(pathToTheFile));
					PrintWriter pw = new PrintWriter(new FileWriter(tempFile));

					String line = null;

					//Read from the original file and write to the new
					//unless content matches data to be removed.
					while ((line = br.readLine()) != null) {
						if (!line.split(",")[0].equals(nombreUsuario)) {
							pw.println(line);
							pw.flush();
						} else {
							String newPassword = Password.generateStrongPasswordHash(usuarioS.nextLine());
							String[] linea = line.split(",");
							String lineaEntera = linea[0] + "," + newPassword + ",";
							if(linea.length > 2) {
								 lineaEntera += linea[2];
							}
							pw.println(lineaEntera);
							pw.flush();
						}
					}
					pw.close();
					br.close();

					//Delete the original file
					if (!datos.delete()) {
						System.out.println("Could not delete file");
					}

					//Rename the new file to the filename the original file had.
					if (!tempFile.renameTo(datos)){
						System.out.println("Could not rename file");
					}
				} catch(SecurityException e) {
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvalidKeySpecException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			default:
				break;
			}
		}
	}
	
	public static void registrarUsuario(String usuarioR, String contraseñaR) {
		try {
			String newPassword = Password.generateStrongPasswordHash(contraseñaR);
			String text = usuarioR + "," + newPassword + ",\n";
			FileOutputStream f = new FileOutputStream(pathToTheFile, true);
            byte[] byteArr = text.getBytes(); //converting string into byte array
            f.write(byteArr);
            f.close();
		} catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
