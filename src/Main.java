import Logic.Designs;
import java.io.File;

public class Main{
	public static void main(String[] args) {
		a a = new a();
		File designs = new File("C:/Users/andre/Documents/VS Code Programs/Random/Conways Art of War/src/Logic/designs.dat");
		Designs.loadDesigns(designs);
	}
}