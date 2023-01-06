import java.awt.Color;
import java.util.Random;

public class process {
	int Id;
	int varisZamani;
	int patlamaZamani;
	int oncelik;
	int renk;
	boolean ilkDefaCalisti=true; //ilk defa çalıştığında true olacak diğer durumlarda false
	
	public boolean ilkDefaMiCalisti() {
		return ilkDefaCalisti;
	}
	

	public void ilkDefaMiCalistiAyarla(boolean ilkDefaCalisti) {
		this.ilkDefaCalisti = ilkDefaCalisti;
	}
		
	public int onceligiNe() {
		return oncelik;
	}

	public void oncelikAyarla(int oncelik) {
		this.oncelik = oncelik;
	}

	process(int Id, int varisZamani, int patlamaZamani, int oncelik) {
		this.Id = Id;
		this.varisZamani = varisZamani;
		this.patlamaZamani = patlamaZamani;
		this.oncelik = oncelik;
		
		Random rand = new Random();
		renk = rand.nextInt(256);
	}

	public int IdGetir() {
		return Id;
	}

	public int renkGetir() {
		return renk;
	}
	
	public void IdAyarla(int Id) {
		this.Id = Id;
	}

	public int varisZamaniGetir() {
		return varisZamani;
	}

	public void varisZamaniAyarla(int varisZamani) {
		this.varisZamani = varisZamani;
	}

	public int patlamaZamaniGetir() {
		return patlamaZamani;
	}

	public void patlamaZamaniAyarla(int patlamaZamani) {
		this.patlamaZamani = patlamaZamani;
	}

}
