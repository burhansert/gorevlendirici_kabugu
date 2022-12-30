package gorevlendirici_kabugu_v2_process_builder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;



public class process_builder {
	public static void main(String args[]) throws InterruptedException, Exception {
		String output = get_output_string();
		System.out.println(output);
	}
	
	public static String get_output_string() throws InterruptedException, Exception {
		StringBuilder sb = new StringBuilder();
		try {
			  ProcessBuilder pb = new ProcessBuilder("java", "-jar", "C:\\Users\\Veysel\\Desktop\\gorevlendirici_kabugu_v2/gorevlendirici_kabugu.jar");
			  final Process p=pb.start();
			  BufferedReader br=new BufferedReader(new InputStreamReader(p.getInputStream()));
			  String line;
			  int status = p.waitFor();
			  System.out.println("Process'in tamamlandi, return code: " + status);
			  
			  while((line=br.readLine())!=null) {
				  sb.append(line);
			  }
		}
		catch(IOException e) {
		    e.printStackTrace();
		}
		return sb.toString();
	}
}
