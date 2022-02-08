import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Element;

public class CodeEditor {

	public JTextArea ta, lines;
	private JFrame frame;
	private HashSet<Integer> invalidline=new HashSet<Integer>();
	HashSet<Integer> emptyLines;

	public CodeEditor(JFrame frame, int x, int y, int w, int h) {
		this.frame = frame;
		ta = new JTextArea();
		lines = new JTextArea(" 1    ");
		lines.setBackground(Color.LIGHT_GRAY);
		lines.setEditable(false);
		JScrollPane js = new JScrollPane(ta);
		frame.addWindowListener(frameClose);

		ta.getDocument().addDocumentListener(new DocumentListener() {
			
			public String getText() {
				validate();
				int caretPosition = ta.getDocument().getLength();
				Element root = ta.getDocument().getDefaultRootElement();
				String text ;
				
				if(invalidline.contains(0))
					text = "*1    " + System.getProperty("line.separator");
				else
				     text = " 1    " + System.getProperty("line.separator");
				for (int i = 2; i < root.getElementIndex(caretPosition) + 2; i++) {
					if(invalidline.contains(i-1))
						text += "*" + i + System.getProperty("line.separator");
					else
					    text += " " + i + System.getProperty("line.separator");
				}
				return text;
			}

			@Override
			public void changedUpdate(DocumentEvent de) {
				Luncher.menuBar.fileHandler.saved=false;
				lines.setText(getText());
			}

			@Override
			public void insertUpdate(DocumentEvent de) {
				Luncher.menuBar.fileHandler.saved=false;
				lines.setText(getText());
			}

			@Override
			public void removeUpdate(DocumentEvent de) {
				Luncher.menuBar.fileHandler.saved=false;
				lines.setText(getText());
			}
		});
		js.getViewport().add(ta);
		js.setRowHeaderView(lines);

		js.setBounds(x, y, w, h);
		ta.setFont(ta.getFont().deriveFont(20f));
		lines.setFont(ta.getFont().deriveFont(20f));
		frame.add(js);

	}

	public String[] getCode() {
		;
		String code [] =validate() ;
		//System.out.println(Arrays.toString(code));
		if(code==null) {
		JOptionPane.showMessageDialog(Luncher.frame,
				"<html>"+"<br>Invalid Code.<br>"+
				"Please verify the code  file name was given.<html>",
				"Open",	JOptionPane.INFORMATION_MESSAGE);
		
		
		
		}
		return code;
	}

	public void disable() {
		this.ta.setEditable(false);
	}

	public void enable() {
		this.ta.setEditable(true);
	}

	public String [] validate() {
		String[] code = ta.getText().split("\n");
		ArrayList<String> validLines = new ArrayList<String>();
		boolean f = true;
		invalidline = new HashSet<Integer>();
		emptyLines = new HashSet<Integer>();
		for (int i = 0; i < code.length; i++) {
			try {
			if (code[i].length() != 0) {
				String[] insSplit = code[i].split(" ");
				if (insSplit.length != 2) {
					invalidline.add(i);
				} else {
					String op = insSplit[0];
					if (op.equals("ADDI.D") || op.equals("SUBI.D") || op.equals("MULI.D") || op.equals("DIVI.D")) {
						String regs[] = insSplit[1].split(",");
						if (regs.length != 3) {
							invalidline.add(i);

						} else {
							String reg1 = regs[0];
							String reg2 = regs[1];
							String reg3 = regs[2];
							if(reg1.charAt(0)=='F'&&Integer.parseInt(reg1.substring(1))>=0&&Integer.parseInt(reg1.substring(1))<31) {
								if((reg2.charAt(0)=='F'||reg2.charAt(0)=='R')&&Integer.parseInt(reg2.substring(1))>=0&&Integer.parseInt(reg2.substring(1))<31) {
									Double.parseDouble(reg3);
									validLines.add(code[i]);
								}
								else {
									invalidline.add(i);
								}
							}
							else {
								invalidline.add(i);
							}

						}

					}

					else if (op.equals("ADD.D") || op.equals("SUB.D") || op.equals("MUL.D") || op.equals("DIV.D")) {
						String regs[] = insSplit[1].split(",");
						if (regs.length != 3) {
							invalidline.add(i);
						} else {
							String reg1 = regs[0];
							String reg2 = regs[1];
							String reg3 = regs[2];
							if(reg1.charAt(0)=='F'&&Integer.parseInt(reg1.substring(1))>=0&&Integer.parseInt(reg1.substring(1))<31) {
								if((reg2.charAt(0)=='F'||reg2.charAt(0)=='R')&&Integer.parseInt(reg2.substring(1))>=0&&Integer.parseInt(reg2.substring(1))<31) {
									if((reg3.charAt(0)=='F'||reg3.charAt(0)=='R')&&Integer.parseInt(reg3.substring(1))>=0&&Integer.parseInt(reg3.substring(1))<31) {
										validLines.add(code[i]);
									}
									else {
										invalidline.add(i);
									}
								}
								else {
									invalidline.add(i);
								}
							}
							else {
								invalidline.add(i);
							}

						}
					} else if (op.equals("L.D") || op.equals("S.D")) {
						String regs[] = insSplit[1].split(",");
						if(regs.length==2) {
							String reg1 = regs[0];
							if(reg1.charAt(0)=='F'&&Integer.parseInt(reg1.substring(1))>=0&&Integer.parseInt(reg1.substring(1))<31) {
								Integer.parseInt(regs[1]);
								validLines.add(code[i]);
							}
							else {
								invalidline.add(i);
							}

						}
						else {
							invalidline.add(i);
						}
						
						
						
						
						
						

					} else {
						invalidline.add(i);
					}
				}
			} else {
				emptyLines.add(i);
			}
			}
			catch (Exception e) {
				invalidline.add(i);
			}
			
		}
		
		if(invalidline.size()==0) {
			String ans [] = new String[validLines.size()];
			for(int i = 0 ;i < ans.length;i++) {
				ans[i]=validLines.get(i);
			}
			return ans;
		}
		else {
			return null;
		}

		
	}

	WindowListener frameClose=new WindowAdapter()
	{
	public void windowClosing(WindowEvent we)
	{
	if(Luncher.menuBar.fileHandler.confirmSave())System.exit(0);
	}
	};
	//

}
