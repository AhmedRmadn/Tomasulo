    import java.awt.Color;
import java.awt.Component;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;    
    public class MemoryTable {    
        JFrame frame;   
        JTable jt;
        MemoryTable(JFrame frame , int x , int y){    
        this.frame=frame;
        String column[]={"Location","Value"}; 
        String data[][] = setValues();
        jt=new JTable(data,column);            
        JScrollPane sp=new JScrollPane(jt);   
        sp.setBounds(x,y,135,631);
        jt.getTableHeader().setOpaque(false);
        jt.getTableHeader().setBackground(Color.LIGHT_GRAY);
        jt.setDefaultEditor(Object.class, null);
        ren();
        frame.add(sp);      
    } 
        
        public String [][]  setValues() {
            String data[][]=new String[38][2]; 
            //((IntegerRegistersFile)Luncher.c.hs.get(0).get("IntegerRegistersFile"));
            double mem [] =  ((FloatMemory)Luncher.c.hs.get(0).get("FloatMemory")).mem;
            for(int i = 0 ; i<mem.length;i++) {
            	data [i][0]="Mem"+i;
            	data [i][1]=""+mem[i];
            }
            return data;
        	
        }
        
        public void ren(){
        
        jt.setDefaultRenderer(Object.class, new DefaultTableCellRenderer()
        {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
            {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row % 2 == 0 ? Color.LIGHT_GRAY : Color.WHITE);
                return c;
            }
        });
        }
        
        public void update(int clk) {
        	
        	double mem [] =  ((FloatMemory)Luncher.c.hs.get(clk).get("FloatMemory")).mem;
        	//System.out.println(Arrays.toString(mem));
    		for (int i = 0; i < mem.length; i++) {
    			
    			jt.setValueAt("Mem"+i, i, 0);
    			jt.setValueAt(mem[i]+"", i, 1);
    		}
        }



    }  