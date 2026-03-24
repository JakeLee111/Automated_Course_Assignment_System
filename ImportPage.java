package CourseCheckerDuplicate;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ImportPage {
	
	public JFrame frame = new JFrame();
	public JButton button1 = new JButton("Select File");
	public JButton button2 = new JButton("Select File");
	public JButton button3 = new JButton("Next>>");
	public JPanel panelCenter = new JPanel();
	
	public boolean studentFileLoaded = false;
	public boolean coursesFileLoaded = false;
	
	public File studentFile;
	public File coursesFile;
	public StoreData storeData = new StoreData();
	

	public void Frame() {
		// Add frame
		frame.setSize(600, 600);
		frame.setResizable(false);
		frame.setTitle("Stamford RegSystem");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panelTop = new JPanel();
		JPanel panelBottom = new JPanel();
		JPanel panelLeft = new JPanel();
		JPanel panelRight = new JPanel();
		
		// Add panels
		panelTop.setPreferredSize(new Dimension(150,150));
		panelBottom.setPreferredSize(new Dimension(150,150));
		panelLeft.setPreferredSize(new Dimension(100,100));
		panelRight.setPreferredSize(new Dimension(100,100));
		panelCenter.setPreferredSize(new Dimension(100,100));
		
		frame.add(panelTop, BorderLayout.NORTH);
		frame.add(panelBottom, BorderLayout.SOUTH);
		frame.add(panelLeft, BorderLayout.WEST);
		frame.add(panelRight, BorderLayout.EAST);
		frame.add(panelCenter, BorderLayout.CENTER);
		
		// Add logo
		ImageIcon image = new ImageIcon(
			    getClass().getResource("/download.png")
			);
		frame.setIconImage(image.getImage());
		
		Image logo = image.getImage().getScaledInstance(
		        150,  // width
		        47,   // height
		        Image.SCALE_SMOOTH
		);

		ImageIcon smallIcon = new ImageIcon(logo);
		
		JLabel label = new JLabel();
		label.setText("Registration System");
		label.setIcon(smallIcon);
		label.setHorizontalTextPosition(JLabel.CENTER);
		label.setVerticalTextPosition(JLabel.BOTTOM);
		label.setForeground(Color.BLUE);
		label.setFont(new Font("MV Boli", Font.BOLD, 20));
		label.setIconTextGap(10);
		label.setBorder(BorderFactory.createEmptyBorder(
		        50,  // top padding
		        0,
		        0,
		        0
		));
		panelTop.add(label);
		
		// Add instructions
		JLabel option1 = new JLabel("Import Student Data:");
		JLabel option2 = new JLabel("Import Courses Data:");
		panelCenter.setLayout(null);
		option1.setBounds(50, 50, 200, 30);
		option2.setBounds(50, 150, 200, 30);
		panelCenter.add(option1);
		panelCenter.add(option2);
		
		// Add button
		button1.setBounds(250, 50, 100, 30);
		button2.setBounds(250, 150, 100, 30);
		button3.setBounds(150, 220, 100, 30);
		panelCenter.add(button1);
		panelCenter.add(button2);
		panelCenter.add(button3);
		frame.setVisible(true);
	}
	
	// Select file button
			public void SelectFile(JButton button, int type) {
				button.addActionListener((e) -> {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setDialogTitle("Select Excel File");
					int result = fileChooser.showOpenDialog(null);
					if (result == JFileChooser.APPROVE_OPTION) {
						File selectedFile = fileChooser.getSelectedFile();
						System.out.println("Selected file: " + selectedFile.getAbsolutePath());
						System.out.println("---------");
						if (type == 1) { 
							studentFileLoaded = true; 
							studentFile = selectedFile; 
							storeData.ReadStudentFile(studentFile);}
		                if (type == 2) { 
		                	coursesFileLoaded = true; 
		                	coursesFile = selectedFile; 
		                	storeData.ReadCoursesFile(coursesFile);}
					 }
			    });
			}
			
	// Next	button
			public void Next() {
				button3.addActionListener((e)-> {
				    if (studentFileLoaded && coursesFileLoaded) {
				    	frame.dispose();  
				    	StoreData storeData = new StoreData();
				        storeData.ReadStudentFile(studentFile);
				        storeData.ReadCoursesFile(coursesFile);

				        ProcessData processData = new ProcessData(storeData);
				        processData.Processing();
				        ExportPage exportpage = new ExportPage(processData);
				    	exportpage.Frame();
				        				       
				    } else {
				    	JLabel messageLabel = new JLabel("");
				    	messageLabel.setBounds(85, 250, 400, 30);
				    	messageLabel.setForeground(Color.RED);
				    	panelCenter.add(messageLabel);
				        messageLabel.setText("Please complete previous steps first.");
				    }
				});
			}
		
}


