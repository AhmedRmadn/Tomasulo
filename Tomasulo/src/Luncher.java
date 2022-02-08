import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Luncher implements ActionListener {
	static JFrame frame;
	static CodeEditor codeEditor;
	static MenuBar menuBar;
	static CPU c;
	static RegistersTable registersTable;
	static RegistersTableInt registersTable2;
	static MemoryTable memoryTable;
	static ReservationTable reservationTable;
	static ReservationTableMUL reservationTableMUL;
	static ReservationTableLS reservationTableLS;
	static InstructionQueue instructionQueue;
	static int clk = 0;
	static JTextField clockField;
	static JButton next;
	static JButton previous;
	static JTextField timeAdd,timeSub,timeMul,timeDiv,timeLD,timeSD;
	

	public Luncher() {

		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(118,90,67));
		//frame.setBackground();
		c = new CPU();
		frame1();
		startFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setSize(1350, 720);
		// frame.setLocation(10,10);
		frame.setLocationRelativeTo(null);
		frame.setLayout(null);
		frame.setVisible(true);

	

	}
	
	public void frame1() {
		//execution
		JLabel l1=new JLabel("Enter the Time for ADD and SUB Instructions");
		l1.setForeground(new Color(163,163,163));
		l1.setFont(l1.getFont().deriveFont(15f));
		timeAdd = new JTextField();timeAdd.setFont(timeAdd.getFont().deriveFont(25f));
		l1.setBounds(310, 480, 350, 50);timeAdd.setBounds(640, 480, 50, 50);
		timeSub = new JTextField();timeSub.setFont(timeSub.getFont().deriveFont(25f));
		timeSub.setBounds(700, 480, 50, 50);
		
		JLabel l3=new JLabel("Enter the Time for MUL and DIV Instructions");
		l3.setForeground(new Color(163,163,163));
		l3.setFont(l3.getFont().deriveFont(15f));
		timeMul = new JTextField();timeMul.setFont(timeMul.getFont().deriveFont(25f));
		l3.setBounds(310, 540, 350, 50);timeMul.setBounds(640, 540, 50, 50);
		timeDiv = new JTextField();timeDiv.setFont(timeDiv.getFont().deriveFont(25f));
		timeDiv.setBounds(700,540, 50, 50);
		
		
		JLabel l5=new JLabel("Enter the Time for LD and SD Instructions");
		l5.setForeground(new Color(163,163,163));
		l5.setFont(l5.getFont().deriveFont(15f));
		timeLD = new JTextField();timeLD.setFont(timeLD.getFont().deriveFont(25f));
		l5.setBounds(310, 600, 350, 50);timeLD.setBounds(640, 600, 50, 50);
		timeSD = new JTextField();timeSD.setFont(timeSD.getFont().deriveFont(25f));
		timeSD.setBounds(700, 600, 50, 50);
		
		frame.add(l1);frame.add(timeAdd);;frame.add(timeSub);
		frame.add(l3);frame.add(timeMul);;frame.add(timeDiv);
		frame.add(l5);frame.add(timeLD);;frame.add(timeSD);
//		JButton start = new JButton("Change");
//		start.setFont(start.getFont().deriveFont(20f));
//		start.setBounds(770, 600, 150, 50);
//		frame.add(start);

		
	}
	
	public void startFrame() {
		menuBar = new MenuBar(frame);
		codeEditor = new CodeEditor(frame, 0, 0, 300, 660);
		
		memoryTable = new MemoryTable(frame, 1205, 30);
		registersTable = new RegistersTable(frame, 995, 30);
		registersTable2 = new RegistersTableInt(frame, 785, 30);
		instructionQueue = new InstructionQueue(frame, 850, 580);
		JLabel l2=new JLabel("Control the Clock");
		l2.setForeground(new Color(163,163,163));
		l2.setFont(l2.getFont().deriveFont(12f));
		l2.setBounds(310, 10, 100, 45);
		clockField = new JTextField();
		clockField.setBounds(490, 10, 50, 50);
		clockField.setBorder(BorderFactory.createBevelBorder(3));
		clockField.setFont(clockField.getFont().deriveFont(20f));
		clockField.setEditable(false);
		clockField.setText("");
		clockField.setHorizontalAlignment(JTextField.CENTER);
		next = new JButton(new ImageIcon("res/n.jpg"));
		//next.setBorder(BorderFactory.createBevelBorder(2));
		next.setBounds(550, 10, 50, 50);
		next.addActionListener(this);
		

		previous = new JButton(new ImageIcon("res/p.jpg"));
		//previous.setBorder(BorderFactory.createBevelBorder(2));
		previous.setBounds(430, 10, 50, 50);
		previous.addActionListener(this);
		JLabel l=new JLabel("Reservation Stations and Buffers");
		l.setForeground(new Color(163,163,163));
		l.setFont(l.getFont().deriveFont(15f));
		l.setBounds(310, 80, 300, 20);

		frame.add(clockField);
		frame.add(next);
		frame.add(previous);
		frame.add(l);
		frame.add(l2);

		reservationTable = new ReservationTable(frame, 310, 110);
		reservationTableMUL = new ReservationTableMUL(frame, 310, 240);
		reservationTableLS = new ReservationTableLS(frame, 310, 370);




	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == next) {

			if (c.hs.size() - 1 > clk) {
				clk++;
				clockField.setText(clk + "");
				registersTable.update(clk);
				memoryTable.update(clk);
				reservationTable.update(clk);
				reservationTableMUL.update(clk);
				reservationTableLS.update(clk);
				instructionQueue.update(clk);

			}
		} else if (e.getSource() == previous) {
			if (clk > 0) {
				clk--;
				clockField.setText(clk + "");
				registersTable.update(clk);
				memoryTable.update(clk);
				reservationTable.update(clk);
				reservationTableMUL.update(clk);
				reservationTableLS.update(clk);
				instructionQueue.update(clk);

			}
		}

	}

	public static void runListener() {
		try {
			int a1 = Integer.parseInt(timeAdd.getText());
			int a2 = Integer.parseInt(timeSub.getText());
			int a3 = Integer.parseInt(timeMul.getText());
			int a4 = Integer.parseInt(timeDiv.getText());
			int a5 = Integer.parseInt(timeLD.getText());
			int a6 = Integer.parseInt(timeSD.getText());
			c.timeAdd=a1+1;
			c.timeSub=a2+1;
			c.timeMul=a3+1;
			c.timeDiv=a4+1;
			c.timeLD=a5+1;
			c.timeSD=a6+1;
			//frame.remove(comp);
			
			
		}
		catch (Exception e1) {

		}
		c.code = codeEditor.getCode();
		if(c.code==null)
			return;
		//codeEditor.disable();
		c.run();
		clk=0;
		clockField.setText(clk + "");
		registersTable.update(clk);
		memoryTable.update(clk);
		reservationTable.update(clk);
		reservationTableMUL.update(clk);
		reservationTableLS.update(clk);
		instructionQueue.update(clk);

	}
	
	

	public static void main(String[] args) {
		new Luncher();

	}
	
	

}


