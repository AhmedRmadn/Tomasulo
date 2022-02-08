import java.util.Map;




public class LSBuffer {

	class BufferCell {
		int position;
		String tag;
		boolean busy;
		String op;
		double VJ;
		boolean validVJ;
		String QJ;
		int time;
		boolean running;
		int address;
		boolean complete ;

		public BufferCell(int position) {
			this.position = position;
			this.tag = "L" + position;
			this.busy = false;
			this.op = null;
			this.VJ = 0;
			this.validVJ = false;
			this.QJ = null;
			this.time = 0;
			this.running = false;
			this.address = -1;
			this.complete = false;

		}
		
		public BufferCell copy() {
			BufferCell b =   new BufferCell(this.position);
			b.position= this.position;
			b.tag=this.tag;
			b.busy=this.busy;
			b.op=this.op;
			b.VJ=this.VJ;
			b.validVJ=this.validVJ;
			b.QJ = this.QJ;
			b.time=this.time;
			b.running=this.running;
			b.complete=this.complete ;
			b.address=this.address;
			return b ;

		}

	}

	private int size;
	public BufferCell buffer[];
	private boolean isFull = false;
	private int numOfIns = 0;
	private boolean isEmpty = true;

	public LSBuffer() {
		this.size = 5;
		buffer = new BufferCell[size];
		for (int i = 0; i < buffer.length; i++)
			buffer[i] = new BufferCell(i);

	}

	public boolean isFull() {
		return numOfIns == size;
	}

	public boolean isEmpty() {
		return numOfIns == 0;
	}

	public String issue(String op, double VJ, boolean validVJ, String QJ, int time, int address) {

		int loc = -1;
		for (int i = 0; i < buffer.length; i++) {
			if (!buffer[i].busy) {
				loc = i;
				buffer[i].busy = true;
				break;
			}
		}
		buffer[loc].op = op;
		buffer[loc].VJ = VJ;
		buffer[loc].validVJ = validVJ;

		buffer[loc].QJ = QJ;

		buffer[loc].time = time;
		buffer[loc].address = address;
		numOfIns++;
		return buffer[loc].tag;

	}

	public void update() {
		boolean f = false;
		for (int i = 0; i < buffer.length; i++) {
			if (buffer[i].busy&& !buffer[i].complete) {
				if (buffer[i].running) {
					buffer[i].time--;
				} else if (!f && (buffer[i].validVJ || buffer[i].QJ == null)) {
					buffer[i].running = true;
					buffer[i].time--;
					//f = true;
				}
				if(buffer[i].time==0) {
					buffer[i].complete=true;
				}
			}

		}
	}

	public boolean setCompleteInBus(Map<String, Double> bus) {

		for (int i = 0; i < buffer.length; i++) {
			if (buffer[i].complete) {
				if (buffer[i].op.equals("L.D")) {
					bus.put(buffer[i].tag, CPU.floatMemory.getValue(buffer[i].address));
					buffer[i].busy = false;
					buffer[i].running = false;
					buffer[i].complete=false;
					buffer[i].VJ = 0;
					buffer[i].validVJ = false;
					buffer[i].QJ = null;
					buffer[i].op = "";
					numOfIns--;
					return true;
				} else {
					CPU.floatMemory.setValue(buffer[i].address, buffer[i].VJ);
					buffer[i].busy = false;
					buffer[i].running = false;
					buffer[i].complete=false;
					buffer[i].VJ = 0;
					buffer[i].validVJ = false;
					buffer[i].QJ = null;
					buffer[i].op = "";
					numOfIns--;
				}


				
			}

		}
		return false;

	}

	public void updateBus(Map<String, Double> bus) {
		for (int i = 0; i < buffer.length; i++) {
			if (buffer[i].busy && !buffer[i].running) {
				if (!buffer[i].validVJ) {
					if (bus.containsKey(buffer[i].QJ)) {
						buffer[i].VJ = bus.get(buffer[i].QJ);
						buffer[i].validVJ = true;
						buffer[i].QJ = null;
						buffer[i].address = 0;
					}
				}

			}

		}
	}
	
	public LSBuffer copy() {
		LSBuffer c = new LSBuffer();
		c.size=this.size;
		for(int i = 0 ; i<c.buffer.length;i++) {
			c.buffer[i]=this.buffer[i].copy();
		}
		c.isFull=this.isFull;
		c.numOfIns=this.numOfIns;
		c.isEmpty=this.isEmpty;
		return c ;
				
	}

}
