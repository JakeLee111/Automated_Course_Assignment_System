package CourseCheckerDuplicate;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import CourseChecker.Export;

public class ExportPage {
	
	public JFrame frame;
	public JButton button1;
	public JButton button2;
	public JTextField textField;
	
	private final LinkedHashMap<Long, List<String>> assignedCourseIdsResult;
	
	public ExportPage(ProcessData processData) {
        this.assignedCourseIdsResult = processData.getAssignedCourseIdsResult();
	}
		
	public void Frame() {
		// Add frame
		frame = new JFrame();
		frame.setSize(600, 600);
		frame.setResizable(false);
		frame.setTitle("Stamford");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panelTop = new JPanel();
		JPanel panelBottom = new JPanel();
		JPanel panelLeft = new JPanel();
		JPanel panelRight = new JPanel();
		JPanel panelCenter = new JPanel();
		
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
			    getClass().getResource("/week6/download.png")
			);
		frame.setIconImage(image.getImage());
		
		Image logo = image.getImage().getScaledInstance(
		        150,  // width
		        47,   // height
		        Image.SCALE_SMOOTH
		);

		ImageIcon smallIcon = new ImageIcon(logo);
		
		JLabel label = new JLabel();
		label.setText("Courses Checked");
		label.setIcon(smallIcon);
		label.setHorizontalTextPosition(JLabel.CENTER);
		label.setVerticalTextPosition(JLabel.BOTTOM);
		label.setForeground(Color.GREEN);
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
		JLabel option1 = new JLabel("Export File:");
		JLabel option2 = new JLabel("Insert Student ID:");
		panelCenter.setLayout(null);
		option1.setBounds(50, 50, 200, 30);
		option2.setBounds(50, 100, 200, 30);
		panelCenter.add(option1);
		panelCenter.add(option2);
		
		// Add button and text field
		button1 = new JButton("Download");
		button2 = new JButton("Search>>");
		textField = new JTextField();
		button1.setBounds(250, 50, 100, 30);
		button2.setBounds(250, 150, 100, 30);
		textField.setBounds(50, 150, 120, 30);
		panelCenter.add(button1);
		panelCenter.add(button2);
		panelCenter.add(textField);
		
		button1.addActionListener((e) -> {
		    JFileChooser fileChooser = new JFileChooser();
		    fileChooser.setDialogTitle("Save Excel File");
		    String timestamp = LocalDateTime.now()
		            .format(DateTimeFormatter.ofPattern("yyMMdd_HHmm"));

		    fileChooser.setSelectedFile(
		        new File("result_" + timestamp + ".xlsx"));

		    if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
		        File file = fileChooser.getSelectedFile();

		        // auto-add .xlsx
		        if (!file.getName().toLowerCase().endsWith(".xlsx")) {
		            file = new File(file.getAbsolutePath() + ".xlsx");
		        }

		        System.out.println("Saving to: " + file.getAbsolutePath());
		        System.out.println("Export rows: " + (assignedCourseIdsResult == null ? 0 : assignedCourseIdsResult.size()));
		        Export.exportResult(assignedCourseIdsResult, file);
		    }
		});
		
		
		frame.setVisible(true);
	}

}
