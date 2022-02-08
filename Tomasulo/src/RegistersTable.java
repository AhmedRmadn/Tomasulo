import java.awt.Color;
import java.awt.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

public class RegistersTable {
	JFrame frame;
	JTable jt;
	int x;
	int y;

	RegistersTable(JFrame frame, int x, int y) {
		this.x = x;
		this.y = y;
		this.frame = frame;
		String column[] = { "Register", "Value", "Q" };
		String data[][] = setValues();
		jt = new JTable(data, column);
		JScrollPane sp = new JScrollPane(jt);
		sp.setBounds(x, y, 200, 535);
		jt.getTableHeader().setOpaque(false);
		jt.getTableHeader().setBackground(Color.LIGHT_GRAY);
		jt.setDefaultEditor(Object.class, null);
		ren();
		frame.add(sp);
	}

	public String[][] setValues() {
		String data[][] = new String[32][3];
		for (int i = 0; i < 32; i++) {
			data[i][0] = "F"+i;
			data[i][1] = 0.0+"" ;
			data[i][2] = "";
		}
		return data;

	}

	public void ren() {

		jt.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
						column);
				c.setBackground(row % 2 == 0 ? Color.LIGHT_GRAY : Color.WHITE);
				return c;
			}
		});
	}

	public void update(int clk) {
		FloatRegistersFile floatRegistersFile = ((FloatRegistersFile)Luncher.c.hs.get(clk).get("FloatRegistersFile"));
		FloatRegistersFile.FloatRegister [] FloatRegFile = floatRegistersFile.FloatRegFile; 
		//System.out.println(floatRegistersFile);
		for (int i = 0; i < FloatRegFile.length; i++) {
			
			jt.setValueAt(FloatRegFile[i].tag, i, 0);
			jt.setValueAt(FloatRegFile[i].value+"", i, 1);
			jt.setValueAt(FloatRegFile[i].q, i, 2);
		}
	}

		

		


	

}