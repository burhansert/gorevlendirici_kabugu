import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.awt.Color;

public class kuyruk {

	LinkedList<process> prosesler;
	LinkedList<process> kuyruk1;
	LinkedList<process> kuyruk2;
	LinkedList<process> kuyruk3;

	LinkedList<Integer> varisZamanlari;
	//LinkedList<Integer> baslamaZamanlari;
	LinkedList<Integer> patlamaZamanlari;
	//LinkedList<Integer> bitisZamanlari;
	
	kuyruk()
	{
		//System.out.print(ANSI_RESET);
		
		prosesler = new LinkedList<process>();
		kuyruk1 = new LinkedList<process>();
		kuyruk2 = new LinkedList<process>();
		kuyruk3 = new LinkedList<process>();
		varisZamanlari = new LinkedList<Integer>();
		//baslamaZamanlari = new LinkedList<Integer>();
		patlamaZamanlari = new LinkedList<Integer>();
		//bitisZamanlari = new LinkedList<Integer>();
		
		String ANSI_RESET = "\u001B[0m";
		
		System.out.println("Process Id\tVarış Zamanı\tÖncelik\t\tPatlama Zamanı");

		String line = "";  
		String splitBy = ",";  
		try   
		{  
		BufferedReader br = new BufferedReader(new FileReader("giriş.txt"));  
		int prosesno =1;
		while ((line = br.readLine()) != null)   //returns a Boolean value  
		{  
		String[] proses = line.split(splitBy);    // use comma as separator  
		process yeniProses1 = new process(prosesno,Integer.parseInt(proses[0].trim()) ,Integer.parseInt(proses[2].trim()), Integer.parseInt(proses[1].trim()));
		prosesler.add(yeniProses1);
		varisZamanlari.add(yeniProses1.varisZamani);
		patlamaZamanlari.add(yeniProses1.patlamaZamani);
		//baslamaZamanlari.add(-1);
		//bitisZamanlari.add(-1);
		System.out.println(yeniProses1.IdGetir() + "\t\t"+ yeniProses1.varisZamaniGetir()+ "\t\t" +yeniProses1.onceligiNe() + "\t\t"+ yeniProses1.patlamaZamaniGetir());
		prosesno++;
		}  
		}   
		catch (IOException e)   
		{  
		e.printStackTrace();  
		} 
		
		int currentTime = 0;
		//int linePrint = 0;
		//int totalWaitingTime = 0, totalTurnAroundTime = 0, totalResponseTime = 0;

		System.out.println("------------------------------------------------");
		System.out.println("Zaman\t\tAçıklama");
		while (true) {
			if (prosesler.size() == 0 
					&& kuyruk1.size() == 0
					&& kuyruk2.size() == 0
					&& kuyruk3.size() == 0) {
				break;
			}
			
			//yeni oluşan prosesler: varış zamanı, şuanki zaman olan prosesler
			LinkedList<process> yeniOlusanProsesler = new LinkedList<process>();
			for (int i = 0; i < prosesler.size(); i++) {
				if (prosesler.get(i).varisZamaniGetir() == currentTime) //şuanki zamanda gelen prosesleri seç
				{
					process process = prosesler.get(i);
					
					if(prosesler.get(i).onceligiNe() == 0) //öncelik 0 ise önce onu işleme alıyor
						kuyruk1.add(0,process);
					else
						kuyruk1.add(process);
						
					yeniOlusanProsesler.add(process);
				}
			}

			for (int i = 0; i < yeniOlusanProsesler.size(); i++) prosesler.remove(yeniOlusanProsesler.get(i));

			process currentProcess;

			if (kuyruk1.size() != 0) {
				currentProcess = kuyruk1.get(0);
				System.out.print("\033[38;5;"+currentProcess.renkGetir()+"m");
				System.out.print(currentTime + " sn\t\t");
				System.out.print(ANSI_RESET);

				//if (currentProcess.onceligiNe() == 0) mlfq.baslamaZamanlari.set(currentProcess.IdGetir() - 1, currentTime); //önceliği 0 olan proses geldiyse hemen başlat
				
				//if (currentProcess.onceligiNe() < 8) 
				//{
				if (currentProcess.patlamaZamaniGetir() <= 1)
					{
						kuyruk1.remove();
						System.out.print("\033[38;5;"+currentProcess.renkGetir()+"m");
						System.out.print("proses sonlandı \t\t(id:"+currentProcess.IdGetir()+"\töncelik:"+currentProcess.onceligiNe()+"\tkalan süre:0 sn)");
						System.out.print(ANSI_RESET);
					} else {
						
						currentProcess.patlamaZamaniAyarla(currentProcess.patlamaZamaniGetir() - 1);
						System.out.print("\033[38;5;"+currentProcess.renkGetir()+"m");
						if(currentProcess.ilkDefaMiCalisti())
						{
							System.out.print("proses başladı \t\t\t(id:"+currentProcess.IdGetir()+"\töncelik:"+currentProcess.onceligiNe()+"\tkalan süre:"+currentProcess.patlamaZamaniGetir()+" sn)");
							currentProcess.ilkDefaMiCalistiAyarla(false);
						}
						else {
							System.out.print("proses yürütülüyor \t\t(id:"+currentProcess.IdGetir()+"\töncelik:"+currentProcess.onceligiNe()+"\tkalan süre:"+currentProcess.patlamaZamaniGetir()+" sn)");
							
						}
						System.out.print(ANSI_RESET);
		
						if (currentProcess.onceligiNe() != 0)
						{
							currentProcess.oncelikAyarla(currentProcess.onceligiNe() + 1);
							kuyruk2.add(currentProcess);
							kuyruk1.remove(currentProcess);
						}
					}
			}
		
			else if (kuyruk2.size() != 0) {
				currentProcess = kuyruk2.get(0);
				System.out.print("\033[38;5;"+currentProcess.renkGetir()+"m");
				System.out.print(currentTime + " sn\t\t");
				System.out.print(ANSI_RESET);

				//if (currentProcess.onceligiNe() < 16) {
					if (currentProcess.patlamaZamaniGetir() <= 1) {
						kuyruk2.remove();
						//mlfq.bitisZamanlari.set(currentProcess.IdGetir() - 1, currentTime);
						System.out.print("\033[38;5;"+currentProcess.renkGetir()+"m");
						System.out.print("proses sonlandı \t\t(id:"+currentProcess.IdGetir()+"\töncelik:"+currentProcess.onceligiNe()+"\tkalan süre:0 sn)");
						System.out.print(ANSI_RESET);
					} else {
						currentProcess.patlamaZamaniAyarla(currentProcess.patlamaZamaniGetir() - 1);
		
						System.out.print("\033[38;5;"+currentProcess.renkGetir()+"m");
						System.out.print("proses yürütülüyor \t\t(id:"+currentProcess.IdGetir()+"\töncelik:"+currentProcess.onceligiNe()+"\tkalan süre:"+currentProcess.patlamaZamaniGetir()+" sn)");
						System.out.print(ANSI_RESET);
						currentProcess.oncelikAyarla(currentProcess.onceligiNe() + 1);
						
						
						kuyruk3.add(currentProcess);
						kuyruk2.remove(currentProcess);

					}
				//}
			}

			else if (kuyruk3.size() != 0) {
				currentProcess = kuyruk3.get(0);
				System.out.print("\033[38;5;"+currentProcess.renkGetir()+"m");
				System.out.print(currentTime + " sn\t\t");
				System.out.print(ANSI_RESET);
		

				if (currentProcess.patlamaZamaniGetir() <= 1) {
					kuyruk3.remove();
					//mlfq.bitisZamanlari.set(currentProcess.IdGetir() - 1, currentTime);
					System.out.print("\033[38;5;"+currentProcess.renkGetir()+"m");
					System.out.print("proses sonlandı \t\t(id:"+currentProcess.IdGetir()+"\töncelik:"+currentProcess.onceligiNe()+"\tkalan süre:0 sn)");
					System.out.print(ANSI_RESET);
				} else
				{
					currentProcess.patlamaZamaniAyarla(currentProcess.patlamaZamaniGetir() - 1);
					System.out.print("\033[38;5;"+currentProcess.renkGetir()+"m");
					System.out.print("proses yürütülüyor \t\t(id:"+currentProcess.IdGetir()+"\töncelik:"+currentProcess.onceligiNe()+"\tkalan süre:"+currentProcess.patlamaZamaniGetir()+" sn)");
					System.out.print(ANSI_RESET);
					
					kuyruk3.removeFirst();
					kuyruk3.add(currentProcess);
				}
			}
			
			currentTime++;
			System.out.println();
		}
	}
}
