import java.awt.Color;
import java.util.Random;

public class process {
	int Id;
	int varisZamani;
	int patlamaZamani;
	int oncelik;
	int renk;
	boolean ilkDefaCalisti=true; //ilk defa çalıştığında true olacak diğer durumlarda false
	
	public boolean ilkDefaMiCalisti() {  //Ilk defa calisip calismadigi kontrolu yapildi.
		return ilkDefaCalisti;
	}
	

	public void ilkDefaMiCalistiAyarla(boolean ilkDefaCalisti) {  //Ilk defa calistigi bildirildi.
		this.ilkDefaCalisti = ilkDefaCalisti;
	}
		
	public int onceligiNe() {  //Oncelik donduren fonksiyon tanimlandi.
		return oncelik;
	}

	public void oncelikAyarla(int oncelik) {  //Oncelik ayarlayabilen fonksiyon tanimlandi.
		this.oncelik = oncelik;
	}

	process(int Id, int varisZamani, int patlamaZamani, int oncelik) {
		this.Id = Id;
		this.varisZamani = varisZamani;
		this.patlamaZamani = patlamaZamani;
		this.oncelik = oncelik;
		
		Random rand = new Random();
		renk = rand.nextInt(256);   //Rastgele renk secilmesi saglandi.
	}

	public int IdGetir() {  //Process ID donduren fonksiyon tanimlandi.
		return Id;
	}

	public int renkGetir() { //Renk donduren fonksiyon tanimlandi.
		return renk;
	}
	
	public void IdAyarla(int Id) { //Id degisikligi yapmaya yarayan fonksiyon tanimlandi.
		this.Id = Id;
	}

	public int varisZamaniGetir() {  //Varış zamanini donduren fonksiyon tanimlandi.
		return varisZamani;
	}

	public void varisZamaniAyarla(int varisZamani) { //Varis zamanini degistirmeye yarayan fonksiyon tanimlandi.
		this.varisZamani = varisZamani;
	}

	public int patlamaZamaniGetir() {  //Patlama zamanini donduren fonksiyon tanimlandi.
		return patlamaZamani;
	}

	public void patlamaZamaniAyarla(int patlamaZamani) { //Patlama zamaninin degistirilmesini saglayan fonksiyon tanimlandi.
		this.patlamaZamani = patlamaZamani;
	}
}
