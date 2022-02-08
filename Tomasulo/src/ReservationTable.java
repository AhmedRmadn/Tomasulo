    import java.awt.Color;
import java.awt.Component;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;    
    public class ReservationTable {    
        JFrame frame;   
        JTable jt;
        ReservationTable(JFrame frame , int x , int y){    
        this.frame=frame;
        String column[]={"Tag","OP","VJ","VK","QJ","QK"}; 
        String data[][] = setValues();
        jt=new JTable(data,column);            
        JScrollPane sp=new JScrollPane(jt);   
        sp.setBounds(x,y,410,103);
        jt.getTableHeader().setOpaque(false);
        jt.getTableHeader().setBackground(new Color (233,104,82));
        jt.setDefaultEditor(Object.class, null);

        //ren(1,Color.blue);
        frame.add(sp);      
    } 
        
        public String [][]  setValues() {
            String data[][]=new String[5][6]; 
            for(int i = 0 ; i<5;i++) {
            	data [i][0]="A"+i;
            	data [i][1]="";
            	data [i][2]="";
            	data [i][3]="";
            	data [i][4]="";
            	data [i][5]="";
            }
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
    		reservationStationA aBuffer = ((reservationStationA)Luncher.c.hs.get(clk).get("ABuffer"));
    		reservationStationA.BufferCell [] buffer = aBuffer.buffer; 
    		HashMap<Integer, Color> colors = new HashMap<Integer, Color>();
    		//System.out.println(buffer);
    		for (int i = 0; i < buffer.length; i++) {
    			
    			jt.setValueAt(buffer[i].tag, i, 0);
    			jt.setValueAt(buffer[i].busy?buffer[i].op:"", i, 1);
    			jt.setValueAt((buffer[i].validVJ?buffer[i].VJ:"")+"", i, 2);
    			jt.setValueAt((buffer[i].validVK?buffer[i].VK:"")+"", i, 3);
    			jt.setValueAt(buffer[i].QJ==null?"":buffer[i].QJ, i, 4);
    			jt.setValueAt(buffer[i].QK==null?"":buffer[i].QK, i, 5);
    			
    			if(buffer[i].busy) {
    				if(buffer[i].complete) {
    					colors.put(i, new Color(0,255,64));
    				}
    				else if(buffer[i].running) {
    					colors.put(i, new Color(128,128,225));
    					
    				}
    				else {
    					colors.put(i, new Color(239,228,176));
    					
    				}
    				
    			}
    			else {
    				colors.put(i,  Color.WHITE);
    				
    			}
    		}
    		ren(colors);
        	
        	
        	
        	
        }

    }  