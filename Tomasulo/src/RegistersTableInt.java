import java.awt.Color;
import java.awt.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

public class RegistersTableInt {
	JFrame frame;
	JTable jt;
	int x;
	int y;

	RegistersTableInt(JFrame frame, int x, int y) {
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
			data[i][0] = "R"+i;
			data[i][1] = 0+"" ;
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
		IntegerRegistersFile integerRegistersFile = ((IntegerRegistersFile)Luncher.c.hs.get(clk).get("IntegerRegistersFile"));
		IntegerRegistersFile.IntegerRegister [] integerRegFile = integerRegistersFile.integerRegFile; 
		//System.out.println(integerRegistersFile);
		for (int i = 0; i < integerRegFile.length; i++) {
			
			jt.setValueAt(integerRegFile[i].tag, i, 0);
			jt.setValueAt(integerRegFile[i].value+"", i, 1);
			jt.setValueAt(integerRegFile[i].q, i, 2);
		}
	}

		

		


	

}