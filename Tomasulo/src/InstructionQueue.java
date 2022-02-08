    import java.awt.Color;
import java.awt.Component;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;    
    public class InstructionQueue {    
        JFrame frame;   
        JTable jt;
        InstructionQueue(JFrame frame , int x , int y){    
        this.frame=frame;
        String column[]={"Instructions Queue"}; 
        String data[][] = setValues();
        jt=new JTable(data,column);            
        JScrollPane sp=new JScrollPane(jt);   
        sp.setBounds(x,y,250,72);
        jt.getTableHeader().setOpaque(false);
        jt.getTableHeader().setBackground(Color.LIGHT_GRAY);
        jt.setDefaultEditor(Object.class, null);

        //ren(1,Color.blue);
        frame.add(sp);      
    } 
        
        public String [][]  setValues() {
            String data[][]=new String[3][1]; 
             

            return data;
        	
        }
        
        public void ren(HashMap<Integer, Color> colors){
        
        jt.setDefaultRenderer(Object.class, new DefaultTableCellRenderer()
        {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
            {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                   c.setBackground(colors.get(row));

                return c;
            }
        });
        }
        
        public void update(int clk) {
        	String [] insQueue =(String []) Luncher.c.hs.get(clk).get("InsQueue");
            
    		HashMap<Integer, Color> colors = new HashMap<Integer, Color>();
    		//System.out.println(buffer);
    		for (int i = 0; i < insQueue.length; i++) {
    			
    			jt.setValueAt(insQueue[i], i, 0);

    			String op = (insQueue[i].split(" "))[0];
    			if(op.length()>0) {
    				if(op.equals("ADD.D")||op.equals("ADDI.D")||op.equals("SUB.D")||op.equals("SUBI.D")) {
    					colors.put(i, new Color (233,104,82));
    				}
    				else if(op.equals("MUL.D")||op.equals("MULI.D")||op.equals("DIV.D")||op.equals("DIVI.D")) {
    					colors.put(i, new Color (64,128,128));
    					
    				}
    				else {
    					colors.put(i, new Color (75,239,108));
    					
    				}
    				
    			}
    			else {
    				colors.put(i,  Color.WHITE);
    				
    			}
    		}
    		ren(colors);
        	
        	
        	
        	
        }

    }  