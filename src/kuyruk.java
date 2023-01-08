import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.awt.Color;

public class kuyruk {

	LinkedList<process> prosesler;			//Okunan prosesleri tutacak olan degiskenler tanimlandi.
	LinkedList<process> kuyruk1;
	LinkedList<process> kuyruk2;
	LinkedList<process> kuyruk3;

	LinkedList<Integer> varisZamanlari;   //Proseslerin varis zamanlarini tutacak liste olusturuldu.
	
	LinkedList<Integer> patlamaZamanlari;   //Proseslerin patlama zamanlarini tutacak liste olusturuldu.
	
	
	kuyruk()
	{
		prosesler = new LinkedList<process>();
		kuyruk1 = new LinkedList<process>();
		kuyruk2 = new LinkedList<process>();
		kuyruk3 = new LinkedList<process>();
		varisZamanlari = new LinkedList<Integer>();
		
		patlamaZamanlari = new LinkedList<Integer>();
		
		
		String ANSI_RESET = "\u001B[0m";
		
		System.out.println("Process Id\tVarış Zamanı\tÖncelik\t\tPatlama Zamanı");

		String line = "";  
		String splitBy = ",";  
		try   
		{  
		BufferedReader br = new BufferedReader(new FileReader("giriş.txt"));  //Txt dosyasinin okunmasi saglandi.
		int prosesno =1;
		while ((line = br.readLine()) != null)   //Txt dosyasinin son satirina gelene kadar okunmasi saglandi.
		{  
		String[] proses = line.split(splitBy);    //Belirlenen karaktere gore okunan degerler bolundu ve diziye yazildi.
		process yeniProses1 = new process(prosesno,Integer.parseInt(proses[0].trim()) ,Integer.parseInt(proses[2].trim()), Integer.parseInt(proses[1].trim())); //Gerekli degerler kurucu fonksiyona verilerek process sinifindan nesne türetildi.
		prosesler.add(yeniProses1); //Olusturulan proses listeye eklendi.
		varisZamanlari.add(yeniProses1.varisZamani); //Olusturulan prosesin varis zamani atandi.
		patlamaZamanlari.add(yeniProses1.patlamaZamani); //Olusturulan prosesin patlama zamani atandi.
		
		System.out.println(yeniProses1.IdGetir() + "\t\t"+ yeniProses1.varisZamaniGetir()+ "\t\t" +yeniProses1.onceligiNe() + "\t\t"+ yeniProses1.patlamaZamaniGetir()); //Proses bilgileri yazdirildi.
		prosesno++;
		}  
		}   
		catch (IOException e)   
		{  
		e.printStackTrace();  
		} 
		
		int currentTime = 0;
	

		System.out.println("------------------------------------------------");
		System.out.println("Zaman\t\tAçıklama");
		while (true) {
			//Donguden cikilabilmesi icin gerekli kosul saglandi.
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
				currentProcess = kuyruk1.get(0);  //Kuyruktan proses alindi.
				System.out.print("\033[38;5;"+currentProcess.renkGetir()+"m"); //Cikti renklendirildi.
				System.out.print(currentTime + " sn\t\t");
				System.out.print(ANSI_RESET);

	
				if (currentProcess.patlamaZamaniGetir() <= 1)
					{
						kuyruk1.remove(); //Sonlanan proses kuyruktan cikarildi.
						System.out.print("\033[38;5;"+currentProcess.renkGetir()+"m"); //Cikti renklendirildi.
						System.out.print("proses sonlandı \t\t(id:"+currentProcess.IdGetir()+"\töncelik:"+currentProcess.onceligiNe()+"\tkalan süre:0 sn)"); // Sonlanan prosesin bilgileri yazdirilir.
						System.out.print(ANSI_RESET);
					} else {
						
						currentProcess.patlamaZamaniAyarla(currentProcess.patlamaZamaniGetir() - 1);
						System.out.print("\033[38;5;"+currentProcess.renkGetir()+"m"); //Cikti renklendirildi.
						if(currentProcess.ilkDefaMiCalisti())
						{
							System.out.print("proses başladı \t\t\t(id:"+currentProcess.IdGetir()+"\töncelik:"+currentProcess.onceligiNe()+"\tkalan süre:"+currentProcess.patlamaZamaniGetir()+" sn)"); // Baslayan prosesin bilgileri yazdirilir.
							currentProcess.ilkDefaMiCalistiAyarla(false); //Proses calismaya basladigi icin ilk defa calisti bilgisi degistirilir.
						}
						else {
							System.out.print("proses yürütülüyor \t\t(id:"+currentProcess.IdGetir()+"\töncelik:"+currentProcess.onceligiNe()+"\tkalan süre:"+currentProcess.patlamaZamaniGetir()+" sn)"); //Henuz bitmemis olan prosesin bilgileri yazdirilir.
							
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
				currentProcess = kuyruk2.get(0); //Kuyruktan proses alindi.
				System.out.print("\033[38;5;"+currentProcess.renkGetir()+"m"); //Cikti renklendirildi.
				System.out.print(currentTime + " sn\t\t");
				System.out.print(ANSI_RESET);

				
					if (currentProcess.patlamaZamaniGetir() <= 1) {
						kuyruk2.remove(); //Sonlanan proses kuyruktan cikarildi.
						
						System.out.print("\033[38;5;"+currentProcess.renkGetir()+"m"); //Cikti renklendirildi.
						System.out.print("proses sonlandı \t\t(id:"+currentProcess.IdGetir()+"\töncelik:"+currentProcess.onceligiNe()+"\tkalan süre:0 sn)"); // Sonlanan prosesin bilgileri yazdirilir.
						System.out.print(ANSI_RESET);
					} else {
						currentProcess.patlamaZamaniAyarla(currentProcess.patlamaZamaniGetir() - 1);
		
						System.out.print("\033[38;5;"+currentProcess.renkGetir()+"m"); //Cikti renklendirildi.
						System.out.print("proses yürütülüyor \t\t(id:"+currentProcess.IdGetir()+"\töncelik:"+currentProcess.onceligiNe()+"\tkalan süre:"+currentProcess.patlamaZamaniGetir()+" sn)"); //Henuz bitmemis olan prosesin bilgileri yazdirilir.
						System.out.print(ANSI_RESET);
						currentProcess.oncelikAyarla(currentProcess.onceligiNe() + 1); //Oncelik degeri arttirildi.
						
						
						kuyruk3.add(currentProcess);  //Devam edecek proses kuyruga eklendi.
						kuyruk2.remove(currentProcess);

					}
				
			}

			else if (kuyruk3.size() != 0) {
				currentProcess = kuyruk3.get(0); //Kuyruktan proses alindi.
				System.out.print("\033[38;5;"+currentProcess.renkGetir()+"m"); //Cikti renklendirildi.
				System.out.print(currentTime + " sn\t\t");
				System.out.print(ANSI_RESET);
		

				if (currentProcess.patlamaZamaniGetir() <= 1) { //Sart saglandiginda proses sonlanmis olur.
					kuyruk3.remove(); //Sonlanan proses kuyruktan cikarildi.
					
					System.out.print("\033[38;5;"+currentProcess.renkGetir()+"m"); //Cikti renklendirildi.
					System.out.print("proses sonlandı \t\t(id:"+currentProcess.IdGetir()+"\töncelik:"+currentProcess.onceligiNe()+"\tkalan süre:0 sn)"); //Sonlana proses bilgileri yazdirildi.
					System.out.print(ANSI_RESET);
				} else
				{
					currentProcess.patlamaZamaniAyarla(currentProcess.patlamaZamaniGetir() - 1); //
					System.out.print("\033[38;5;"+currentProcess.renkGetir()+"m"); //Cikti renklendirildi.
					System.out.print("proses yürütülüyor \t\t(id:"+currentProcess.IdGetir()+"\töncelik:"+currentProcess.onceligiNe()+"\tkalan süre:"+currentProcess.patlamaZamaniGetir()+" sn)"); //Yurutulen proses bilgileri yazdirildi.
					System.out.print(ANSI_RESET);
					
					kuyruk3.removeFirst();
					kuyruk3.add(currentProcess); //Devam edecek proses kuyruga eklendi.
				}
			}
			
			currentTime++;
			System.out.println();
		}
	}
}
