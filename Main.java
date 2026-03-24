package CourseCheckerDuplicate;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// Add frame
		ImportPage importpage = new ImportPage();					
		importpage.Frame();	
		
		// Add select file buttons
		importpage.SelectFile(importpage.button1, 1);
		importpage.SelectFile(importpage.button2, 2);
		
		// Add next button
		importpage.Next();
		
		
	}

}
