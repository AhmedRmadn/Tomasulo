    import javax.swing.*;    
    import java.awt.event.*;    
    public class MenuBar implements ActionListener{    
    JFrame frame;    
    MenuFile fileHandler;
    MenuFind findReplaceDialog=null; 
    JMenuBar mb;    
    JMenu file,edit,help,run;    
    JMenuItem cut,copy,paste,selectAll,runFile,save,saveAs,open,newFile,exit,find,findNext,replace,goTo,helpIns;    
    //JTextArea ta;    
    MenuBar(JFrame frame){    
    this.frame=frame;
    fileHandler=new MenuFile();
    mb=new JMenuBar(); 
    
    run = new JMenu("Run");
    runFile=new JMenuItem("Run",KeyEvent.VK_R); 
    runFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,ActionEvent.CTRL_MASK));
    runFile.addActionListener(this);
    run.add(runFile);
    
    help = new JMenu("help");
    helpIns = new JMenuItem("help",KeyEvent.VK_I);
    helpIns.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,ActionEvent.CTRL_MASK));
    helpIns.addActionListener(this);
    help.add(helpIns);
    
    file=new JMenu("File");
    newFile = new JMenuItem("New",KeyEvent.VK_N);
    newFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,ActionEvent.CTRL_MASK));
    newFile.addActionListener(this);
    
    
    open = new JMenuItem("Open",KeyEvent.VK_O);
    open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,ActionEvent.CTRL_MASK));
    open.addActionListener(this);
    
    
    save = new JMenuItem("Save",KeyEvent.VK_S);
    save.addActionListener(this);
    save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK));
    //createMenuItem(fileSave,KeyEvent.VK_S,fileMenu,KeyEvent.VK_S,this);
    
    
    saveAs = new JMenuItem("Save As");
    saveAs.addActionListener(this);
    exit = new JMenuItem("Exit");
    exit.addActionListener(this);
    file.add(newFile);file.add(open);;file.add(save);file.add(saveAs);file.add(exit);
   
    
    
    edit=new JMenu("Edit"); 
    find = new JMenuItem("Find");
    find.addActionListener(this);
    
    findNext = new JMenuItem("Find Next");
    findNext.addActionListener(this);
    
    replace = new JMenuItem("Replace");
    replace.addActionListener(this);
    
    goTo = new JMenuItem("Go To");
    goTo.addActionListener(this);
    
    edit.add(find);edit.add(findNext);edit.add(replace);edit.add(goTo);
    
    //cut=new JMenuItem("cut");    
//    copy=new JMenuItem("copy");    
//    paste=new JMenuItem("paste");    
//    selectAll=new JMenuItem("selectAll");    
//    cut.addActionListener(this);    
//    copy.addActionListener(this);    
//    paste.addActionListener(this);    
//    selectAll.addActionListener(this);  
    
      
       


    
   
   // edit.add(cut);edit.add(copy);edit.add(paste);edit.add(selectAll);    
    mb.add(file);mb.add(edit);  mb.add(run); mb.add(help);
    frame.add(mb); 
    frame.setJMenuBar(mb);
    
    }     
    public void actionPerformed(ActionEvent e) {    
    if(e.getSource()==runFile)    
       Luncher.runListener(); 
    else if(e.getSource()==newFile) {
    	fileHandler.newFile();
    }
    else if(e.getSource()==open) {
    	fileHandler.openFile();
    }
    else if(e.getSource()==save) {   
    	fileHandler.saveThisFile();;
    }
    else if(e.getSource()==saveAs)    
    	fileHandler.saveAsFile();
    else if(e.getSource()==exit) {
    	{if(fileHandler.confirmSave())System.exit(0);}
    }
    else if(e.getSource()==find) {
    	if(Luncher.codeEditor.ta.getText().length()==0)
    		return;	// text box have no text
    	if(findReplaceDialog==null)
    		findReplaceDialog=new MenuFind(Luncher.codeEditor.ta);
    	findReplaceDialog.showDialog(Luncher.frame,true);//find
    }
    else if (e.getSource()==findNext) {
    	if(Luncher.codeEditor.ta.getText().length()==0)
    		return;	// text box have no text

    	if(findReplaceDialog!=null)
    	   findReplaceDialog.findNextWithSelection();
    }
    else if (e.getSource()==replace) {
    	if(Luncher.codeEditor.ta.getText().length()==0)
    		return;	// text box have no text

    	if(findReplaceDialog==null)
    		findReplaceDialog=new MenuFind(Luncher.codeEditor.ta);
    	findReplaceDialog.showDialog(Luncher.frame,false);//replace
    }
    else if(e.getSource()==goTo) {
    	if(Luncher.codeEditor.ta.getText().length()==0)
    		return;	// text box have no text
    	goTo();
    }
    else if(e.getSource()==helpIns) {
		JOptionPane.showMessageDialog(Luncher.frame,
				"<html>"+"<br> Tomasulo Algorithm Simulator.<br>"+
						"<br> This  algorithm allows for out-of-order execution.<br>"+
						
						"<br> Screen's sections .<br>"+
						"<br> section 1 for writing your Instructions  with number of lines and * to indicate syntax error.<br>"+
						"<br> section 2 for Reservation stations and Buffers all with size 5 .<br>"+
						"<br> section 3 for Memory and Reisters files for double and integer 32 size each  .<br>"+
						"<br> section 4 for controling the clock  textField two show current clock cycle and 2 buttons to go next or previous cycle  .<br>"+
						"<br> section 5 for controling the Execution time for all Instrucions  textField for each type.<br>"+
						
						
						
						"<br> Example of Instructions .<br>"+
						"<div style=\\\"width:100\\\"><p>  ADD.D F0,F0,F0 , ADDI.D F0,F1,100."+
						"    SUB.D F0,F0,F0 , SUBI.D F0,F1,100."+
						"    MUL.D F0,F0,F0 , MULI.D F0,F1,100."+
						"    DIV.D F0,F0,F0 , DIVI.D F0,F1,100."+
						"    LD.D F0,12 , SD.D F0,12.<p><div>"+
						
						"<br>How to use the Simulator<br>"+
						"<div style=\\\"width:500\\\"><p>write your code in section 1 with no syntax errors and then you can enter execution times for all instructions and from menubar run ."
						+ "     you will see in section 4 current clock control it as yo like and you will see the reflections in section 2 and 3 ."
						+ "      Section 2 has different color for 3 stages to indicate the state <p> <div>"+
				          "<p style=\"color:red\">Thank you</p>"+ "<html>",
				"Help",	JOptionPane.INFORMATION_MESSAGE);
    }
//    if(e.getSource()==paste)    
//    ta.paste();    
//    if(e.getSource()==copy)    
//    ta.copy();    
//    if(e.getSource()==selectAll)    
//    ta.selectAll();    
    }  
    
    void goTo()
    {
    int lineNumber=0;
    try
    {
    lineNumber=Luncher.codeEditor.ta.getLineOfOffset(Luncher.codeEditor.ta.getCaretPosition())+1;
    String tempStr=JOptionPane.showInputDialog(Luncher.frame,"Enter Line Number:",""+lineNumber);
    if(tempStr==null)
    	{return;}
    lineNumber=Integer.parseInt(tempStr);
    Luncher.codeEditor.ta.setCaretPosition(Luncher.codeEditor.ta.getLineStartOffset(lineNumber-1));
    }catch(Exception e){}
    }
  
    }    