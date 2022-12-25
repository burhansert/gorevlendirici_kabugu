//https://github.com/SadhuUjwal/mlfqs
import java.awt.Color;
import java.util.LinkedList;
import java.io.BufferedReader;  
import java.io.FileReader;  
import java.io.IOException;  

public class gorevlendirici {

	LinkedList<process> prosesler;
	LinkedList<process> kuyruk1;
	LinkedList<process> kuyruk2;
	LinkedList<process> kuyruk3;

	LinkedList<Integer> varisZamanlari;
	LinkedList<Integer> baslamaZamanlari;
	LinkedList<Integer> patlamaZamanlari;
	LinkedList<Integer> bitisZamanlari;

	gorevlendirici() {
		prosesler = new LinkedList<process>();
		kuyruk1 = new LinkedList<process>();
		kuyruk2 = new LinkedList<process>();
		kuyruk3 = new LinkedList<process>();
		varisZamanlari = new LinkedList<Integer>();
		baslamaZamanlari = new LinkedList<Integer>();
		patlamaZamanlari = new LinkedList<Integer>();
		bitisZamanlari = new LinkedList<Integer>();
	}

	/*public void renklendir(String metin)
	{
		System.out.print("\033[38;5;"+process.renkGetir()+"m");
		System.out.print( "P" + process.IdGetir()+ " oluşturuldu.");
		System.out.print(ANSI_RESET);
	}*/
	static String ANSI_RESET = "\u001B[0m";
	public static void main(String args[]) {
		gorevlendirici mlfq = new gorevlendirici();
		mlfq.initialize();
		int currentTime = 0;
		//int linePrint = 0;
		//int totalWaitingTime = 0, totalTurnAroundTime = 0, totalResponseTime = 0;

		System.out.println("------------------------------------------------");
		System.out.println("Zaman\t\tAçıklama");
		while (true) {
			if (mlfq.prosesler.size() == 0 
					&& mlfq.kuyruk1.size() == 0
					&& mlfq.kuyruk2.size() == 0
					&& mlfq.kuyruk3.size() == 0) {
				break;
			}
			
			//yeni oluşan prosesler: varış zamanı, şuanki zaman olan prosesler
			LinkedList<process> yeniOlusanProsesler = new LinkedList<process>();
			for (int i = 0; i < mlfq.prosesler.size(); i++) {
				if (mlfq.prosesler.get(i).varisZamaniGetir() == currentTime) {
					process process = mlfq.prosesler.get(i);
					mlfq.kuyruk1.add(process);
					yeniOlusanProsesler.add(process);
					/*if (linePrint == 0) {
						System.out.print("\033[38;5;"+process.renkGetir()+"m");
						System.out.print(currentTime + " s\t\t");
						System.out.print(ANSI_RESET);
						linePrint = 1;
					}
					
					System.out.print("\033[38;5;"+process.renkGetir()+"m");
					System.out.print( "P" + process.IdGetir()+ " oluşturuldu.");
					System.out.print(ANSI_RESET);*/
				}
			}

			for (int i = 0; i < yeniOlusanProsesler.size(); i++) mlfq.prosesler.remove(yeniOlusanProsesler.get(i));

			process currentProcess;
			if (mlfq.kuyruk1.size() != 0) {
				currentProcess = mlfq.kuyruk1.get(0);
				//if (linePrint == 0) {
					System.out.print("\033[38;5;"+currentProcess.renkGetir()+"m");
					System.out.print(currentTime + " sn\t\t");
					System.out.print(ANSI_RESET);
				//	linePrint = 1;
				//}
				if (currentProcess.onceligiNe() == 0)
					mlfq.baslamaZamanlari.set(
							currentProcess.IdGetir() - 1, currentTime);
				if (currentProcess.onceligiNe() < 8) {
					if (currentProcess.patlamaZamaniGetir() <= 1) {
						mlfq.kuyruk1.remove();
						mlfq.bitisZamanlari.set(currentProcess.IdGetir() - 1, currentTime);
						System.out.print("\033[38;5;"+currentProcess.renkGetir()+"m");
						System.out.print("proses sonlandı \t(id:"+currentProcess.IdGetir()+"\töncelik:"+currentProcess.onceligiNe()+"\tkalan süre:0 sn)");
						System.out.print(ANSI_RESET);
					} else {
						currentProcess.patlamaZamaniAyarla(currentProcess.patlamaZamaniGetir() - 1);
						System.out.print("\033[38;5;"+currentProcess.renkGetir()+"m");
						System.out.print("yürütülüyor \t\t(id:"+currentProcess.IdGetir()+"\töncelik:"+currentProcess.onceligiNe()+"\tkalan süre:"+currentProcess.patlamaZamaniGetir()+" sn)");
						System.out.print(ANSI_RESET);
						currentProcess.oncelikAyarla(currentProcess.onceligiNe() + 1);
						if (currentProcess.onceligiNe() == 8) {
							currentProcess.oncelikAyarla(0);
							mlfq.kuyruk2.add(currentProcess);
							mlfq.kuyruk1.remove(currentProcess);
							//System.out.print("\033[38;5;"+currentProcess.renkGetir()+"m");
							//System.out.print("P" + currentProcess.IdGetir()	+ " yaşlandı,Q1 den Q2 ye taşındı");
							//System.out.print(ANSI_RESET);
						}
					}
				}
			}
		
			else if (mlfq.kuyruk2.size() != 0) {
				currentProcess = mlfq.kuyruk2.get(0);
				//if (linePrint == 0) {
					System.out.print("\033[38;5;"+currentProcess.renkGetir()+"m");
					System.out.print(currentTime + " sn\t\t");
					System.out.print(ANSI_RESET);
					//linePrint = 1;
				//}
				if (currentProcess.onceligiNe() < 16) {
					if (currentProcess.patlamaZamaniGetir() <= 1) {
						mlfq.kuyruk2.remove();
						mlfq.bitisZamanlari.set(currentProcess.IdGetir() - 1, currentTime);
						System.out.print("\033[38;5;"+currentProcess.renkGetir()+"m");
						System.out.print("P" + currentProcess.IdGetir()	+ " çalışıyor,Q2 kuyruğunda. Sonlandı.");
						System.out.print(ANSI_RESET);
					} else {
						currentProcess.patlamaZamaniAyarla(currentProcess.patlamaZamaniGetir() - 1);
						currentProcess.oncelikAyarla(currentProcess.onceligiNe() + 1);
						System.out.print("\033[38;5;"+currentProcess.renkGetir()+"m");
						System.out.print("P" + currentProcess.IdGetir()	+ " çalışıyor,Q2 kuyruğunda. Askıya alındı.");
						System.out.print(ANSI_RESET);
						if (currentProcess.onceligiNe() == 16) {
							currentProcess.oncelikAyarla(0);
							mlfq.kuyruk3.add(currentProcess);
							mlfq.kuyruk2.remove(currentProcess);
							//System.out.print("\033[38;5;"+currentProcess.renkGetir()+"m");
							//System.out.print("P" + currentProcess.IdGetir()	+ " yaşlandı, Q2 den Q3 e taşındı");
							//System.out.print(ANSI_RESET);
						}
					}
				}
			}

			else if (mlfq.kuyruk3.size() != 0) {
				currentProcess = mlfq.kuyruk3.get(0);
				//if (linePrint == 0) {
					System.out.print("\033[38;5;"+currentProcess.renkGetir()+"m");
					System.out.print(currentTime + " sn\t\t");
					System.out.print(ANSI_RESET);
				//	linePrint = 1;
				//}
				System.out.print("\033[38;5;"+currentProcess.renkGetir()+"m");
				System.out.print("P" + currentProcess.IdGetir()	+ " çalışıyor,Q3 kuyruğunda. Askıya alındı.");
				System.out.print(ANSI_RESET);
				if (currentProcess.patlamaZamaniGetir() <= 1) {
					mlfq.kuyruk3.remove();
					mlfq.bitisZamanlari.set(currentProcess.IdGetir() - 1, currentTime);
					System.out.print("\033[38;5;"+currentProcess.renkGetir()+"m");
					System.out.print("P" + currentProcess.IdGetir()	+ " çalışıyor,Q3 kuyruğunda. Sonlandı");
					System.out.print(ANSI_RESET);
				} else
					currentProcess.patlamaZamaniAyarla(currentProcess.patlamaZamaniGetir() - 1);
			}
			currentTime++;
			//if (linePrint == 1)
				System.out.println();
			//linePrint = 0;
		}
	}

	public void initialize() {
		
		System.out.println("Process Id\tVarış Zamanı\tÖncelik\t\tPatlama Zamanı");
		
		/*
		varış zamanı/öncelik/patlama zamanı
		12,0,1
		12,1,2
		13,3,6
		*/
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
		baslamaZamanlari.add(-1);
		bitisZamanlari.add(-1);
		System.out.println(yeniProses1.IdGetir() + "\t\t"+ yeniProses1.varisZamaniGetir()+ "\t\t" +yeniProses1.onceligiNe() + "\t\t"+ yeniProses1.patlamaZamaniGetir());
		prosesno++;
		}  
		}   
		catch (IOException e)   
		{  
		e.printStackTrace();  
		} 
	
	}

}

