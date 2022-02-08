import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class CPU {
	String[] code;
	public static FloatRegistersFile floatRegistersFile = new FloatRegistersFile();
	public static IntegerRegistersFile integerRegistersFile = new IntegerRegistersFile();
	public static FloatMemory floatMemory = new FloatMemory();
	public static ArrayList<HashMap<String, Object>> hs = new ArrayList<HashMap<String, Object>>();
	reservationStationA aBuffer = new reservationStationA();
	reservationStationM mBuffer = new reservationStationM();
	LSBuffer lsBuffer = new LSBuffer();
	// int clk = 0;
	int pc = 0;
	int timeAdd = 2;
	int timeSub = 2;
	int timeMul = 2;
	int timeDiv = 2;
	int timeLD = 2;
	int timeSD = 2;

	// Queue<String> issuedIns = new
	public CPU() {
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("FloatRegistersFile", this.floatRegistersFile.copy());
		data.put("IntegerRegistersFile", this.integerRegistersFile.copy());
		data.put("FloatMemory", this.floatMemory.copy());
		data.put("ABuffer", this.aBuffer.copy());
		data.put("MBuffer", this.mBuffer.copy());
		data.put("LSBuffer", this.lsBuffer.copy());
		//data.put("InsQueue", insQueue());
		hs.add(data);

		// ABuffer test = aBuffer.
	}

	public void run() {
		pc = 0;
		hs = new ArrayList<HashMap<String, Object>>();
		aBuffer = new reservationStationA();
		mBuffer = new reservationStationM();
		lsBuffer = new LSBuffer();
		HashMap<String, Object> d = new HashMap<String, Object>();
		d.put("FloatRegistersFile", this.floatRegistersFile.copy());
		d.put("IntegerRegistersFile", this.integerRegistersFile.copy());
		d.put("FloatMemory", this.floatMemory.copy());
		d.put("ABuffer", this.aBuffer.copy());
		d.put("MBuffer", this.mBuffer.copy());
		d.put("LSBuffer", this.lsBuffer.copy());
		d.put("InsQueue", insQueue());
		hs.add(d);
		// System.out.println(code.length);
		while (pc < code.length || !this.aBuffer.isEmpty() || !this.mBuffer.isEmpty() || !this.lsBuffer.isEmpty()) {

			if (!this.aBuffer.isEmpty() || !this.mBuffer.isEmpty() || !this.lsBuffer.isEmpty()) {
				update();
			}
			if (pc < code.length) {
				issue();
			}
			if (!this.aBuffer.isEmpty() || !this.mBuffer.isEmpty() || !this.lsBuffer.isEmpty()) {
				writeBack();
			}
			HashMap<String, Object> data = new HashMap<String, Object>();
			data.put("FloatRegistersFile", this.floatRegistersFile.copy());
			data.put("IntegerRegistersFile", this.integerRegistersFile.copy());
			data.put("FloatMemory", this.floatMemory.copy());
			data.put("ABuffer", this.aBuffer.copy());
			data.put("MBuffer", this.mBuffer.copy());
			data.put("LSBuffer", this.lsBuffer.copy());
			data.put("InsQueue", insQueue());
			hs.add(data);
			 //System.out.println(data.get("ABuffer"));

			// System.out.println(aBuffer);
			// System.out.println(floatRegistersFile);

		}
		//System.out.println(hs.size());
//		System.out.println(hs.get(0).get("FloatRegistersFile"));
//		System.out.println(hs.get(1).get("FloatRegistersFile"));
//		System.out.println(hs.get(2).get("FloatRegistersFile"));

	}
	
	public String [] insQueue() {
		String iQ [] = new String[3];
		for(int i = 0 ; i< 3 ; i++) {
			if(pc+i<code.length)
				iQ[i]=code[pc+i];
			else
				iQ[i]="";
		}
		//System.out.println(Arrays.toString(iQ));
		return iQ;
	}

	public void update() {
		this.aBuffer.update();
		this.mBuffer.update();
		this.lsBuffer.update();
	}

	public void writeBack() {
		Map<String, Double> bus = new HashMap<String, Double>();
		boolean a = aBuffer.setCompleteInBus(bus);
		if (!a) {
			a = mBuffer.setCompleteInBus(bus);
			if (!a) {
				lsBuffer.setCompleteInBus(bus);
			}

		}

		aBuffer.updateBus(bus);
		mBuffer.updateBus(bus);
		lsBuffer.updateBus(bus);
		floatRegistersFile.updateBus(bus);

	}

	public void issue() {
		String ins = code[pc];
		boolean f = true;
		if (f && !aBuffer.isFull()) {

			String[] insSplit = ins.split(" ");
			String op = insSplit[0];

			if (op.equals("ADD.D") || op.equals("SUB.D") || op.equals("ADDI.D") || op.equals("SUBI.D")) {
				double VJ;
				boolean validVJ;
				double VK;
				boolean validVK;
				String QJ;
				String QK;
				String regs[] = insSplit[1].split(",");
				String reg1 = regs[0];
				String reg2 = regs[1];
				String reg3 = regs[2];
				int time = timeAdd;
				if (op.equals("SUB.D"))
					time = timeSub;
				Object r1;
				if (reg2.charAt(0) == 'R')
					r1 = integerRegistersFile.getValue(reg2);
				else
					r1 = floatRegistersFile.getValue(reg2);
				if (r1 instanceof String) {
					VJ = 0;
					validVJ = false;
					QJ = (String) r1;
				} else {
					if (reg2.charAt(0) == 'R')
						VJ = (int) r1;
					else
						VJ = (double) r1;
					validVJ = true;
					QJ = null;
				}

				if (op.equals("ADDI.D") || op.equals("SUBI.D")) {
					VK = Double.parseDouble(reg3);
					validVK = true;
					QK = null;
				} else {

					Object r2;
					if (reg3.charAt(0) == 'R')
						r2 = integerRegistersFile.getValue(reg3);
					else
						r2 = floatRegistersFile.getValue(reg3);
					if (r2 instanceof String) {
						VK = 0;
						validVK = false;
						QK = (String) r2;
					} else {
						if (reg3.charAt(0) == 'R')
							VK = (int) r2;
						else
							VK = (double) r2;

						validVK = true;
						QK = null;
					}
				}
				f = false;
				pc++;
				String tag = aBuffer.issue(op, VJ, validVJ, VK, validVK, QJ, QK, time);
				this.floatRegistersFile.setInsTags(reg1, tag);

			}
		}
		if (f && !this.mBuffer.isFull()) {

			String[] insSplit = ins.split(" ");
			String op = insSplit[0];
			if (op.equals("MUL.D") || op.equals("DIV.D") || op.equals("MULI.D") || op.equals("DIVI.D")) {
				double VJ;
				boolean validVJ;
				double VK;
				boolean validVK;
				String QJ;
				String QK;
				String regs[] = insSplit[1].split(",");
				String reg1 = regs[0];
				String reg2 = regs[1];
				String reg3 = regs[2];
				int time = timeMul;
				if (op.equals("DIV.D"))
					time = timeDiv;
				Object r1;
				if (reg2.charAt(0) == 'R')
					r1 = integerRegistersFile.getValue(reg2);
				else
					r1 = floatRegistersFile.getValue(reg2);

				if (r1 instanceof String) {
					VJ = 0;
					validVJ = false;
					QJ = (String) r1;
				} else {
					if (reg2.charAt(0) == 'R')
						VJ = (int) r1;
					else
						VJ = (double) r1;
					validVJ = true;
					QJ = null;
				}

				if (op.equals("MULI.D") || op.equals("DIVI.D")) {
					VK = Double.parseDouble(reg3);
					validVK = true;
					QK = null;
				} else {

					Object r2;
					if (reg3.charAt(0) == 'R')
						r2 = integerRegistersFile.getValue(reg3);
					else
						r2 = floatRegistersFile.getValue(reg3);

					if (r2 instanceof String) {
						VK = 0;
						validVK = false;
						QK = (String) r2;
					} else {
						if (reg3.charAt(0) == 'R')
							VK = (int) r2;
						else	
						    VK = (double) r2;
						validVK = true;
						QK = null;
					}
				}
				f = false;
				pc++;
				String tag = mBuffer.issue(op, VJ, validVJ, VK, validVK, QJ, QK, time);
				this.floatRegistersFile.setInsTags(reg1, tag);

			}

		}

		if (f && !this.lsBuffer.isFull()) {

			String[] insSplit = ins.split(" ");
			String op = insSplit[0];
			String regs[] = insSplit[1].split(",");
			String reg1 = regs[0];

			if (op.equals("L.D")) {
				int address = Integer.parseInt(regs[1]);
				int time = timeLD;
				f = false;
				pc++;
				String tag = lsBuffer.issue(op, 0, false, null, time, address);
				this.floatRegistersFile.setInsTags(reg1, tag);

			} else if (op.equals("S.D")) {

				int address = Integer.parseInt(regs[1]);
				double VJ;
				boolean validVJ;
				String QJ;
				int time = timeSD;
				Object r2;
				if (reg1.charAt(0) == 'R')
					r2 = integerRegistersFile.getValue(reg1);
				else
					r2 = floatRegistersFile.getValue(reg1);
				if (r2 instanceof String) {
					VJ = 0;
					validVJ = false;
					QJ = (String) r2;
				} else {
					if (reg1.charAt(0) == 'R')
						VJ = (int) r2;
					else
					    VJ = (double) r2;
					validVJ = true;
					QJ = null;
				}
				f = false;
				pc++;
				String tag = lsBuffer.issue(op, VJ, validVJ, QJ, time, address);

			}

		}
	}

//	public static void main(String[] args) {
//		CPU c =new CPU();
//		System.out.println(c.floatRegistersFile);
//	}

}
