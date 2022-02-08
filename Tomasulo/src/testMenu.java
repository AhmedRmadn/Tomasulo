    import javax.swing.*;    
    import java.awt.event.*;    
    public class testMenu implements ActionListener{    
    JFrame frame;    
    JMenuBar mb;    
    JMenu file,edit,help;    
    //JMenuItem cut,copy,paste,selectAll;       
    testMenu(JFrame frame){    
    this.frame=frame;    
//    cut=new JMenuItem("cut");    
//    copy=new JMenuItem("copy");    
//    paste=new JMenuItem("paste");    
//    selectAll=new JMenuItem("selectAll");    
//    cut.addActionListener(this);    
//    copy.addActionListener(this);    
//    paste.addActionListener(this);    
//    selectAll.addActionListener(this);    
    mb=new JMenuBar();    
    file=new JMenu("File");    
    edit=new JMenu("Edit");    
    help=new JMenu("Help");     
    //edit.add(cut);edit.add(copy);edit.add(paste);edit.add(selectAll);    
    mb.add(file);mb.add(edit);mb.add(help);      
    frame.add(mb);   
    frame.setJMenuBar(mb);  
//    frame.setLayout(null);    
//    frame.setSize(400,400);    
//    frame.setVisible(true);    
    }     
    public void actionPerformed(ActionEvent e) {    
//    if(e.getSource()==cut)    
//    ta.cut();    
//    if(e.getSource()==paste)    
//    ta.paste();    
//    if(e.getSource()==copy)    
//    ta.copy();    
//    if(e.getSource()==selectAll)    
//    ta.selectAll();    
    }     
    public static void main(String[] args) {    
        new testMenu(null);    
    }    
    }    