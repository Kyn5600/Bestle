package weka.api;
 
import java.util.Scanner;

public class controller{
	public static void main(String[] args) throws Exception {
		while(true) {
		System.out.println("Enter program choice: \n1. Base\n2. Training\n3. Bestle\n4. CSVtoArff");
		int choice = 0;
		try {
			Scanner sc = new Scanner(System.in);
			System.out.print("\nChoice (1-4): ");
			choice = sc.nextInt();
		} catch(Exception e) {
			System.out.println("\nPlease Enter a number.\n");
			choice = 0;
		}
		switch(choice) {
			case 1:
				textbasedai runner = new textbasedai();
				System.out.println("Running base...");
				runner.runner();
				continue;
			case 2:
				newUserInput runner1 = new newUserInput();
				System.out.println("Running training...");
				runner1.runner();
				continue;
			case 3:
				System.out.println("Running Bestle...");
				response runner2 = new response();
				runner2.runner();
				continue;
			case 4:
				CSVToArff runner3 = new CSVToArff();
				runner3.runner();
				System.out.println("CSV to Arff completed.");
				continue;
			case 5:
				System.out.println("Printing dataset. Secret unlocked.");
				textbasedai runner4 = new textbasedai();
				runner4.printCSV();
				continue;
		}
		}
	}
}