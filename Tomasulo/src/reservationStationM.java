import java.util.Map;

//// Buffer for multi and div
public class reservationStationM {

	class BufferCell {
		int position;
		String tag;
		boolean busy;
		String op;
		double VJ;
		boolean validVJ;
		double VK;
		boolean validVK;
		String QJ;
		String QK;
		int time;
		boolean running;
		boolean complete;

		public BufferCell(int position) {
			this.position = position;
			this.tag = "M" + position;
			this.busy = false;
			this.op = null;
			this.VJ = 0;
			this.validVJ = false;
			this.VK = 0;
			this.validVK = false;
			this.QJ = null;
			this.QK = null;
			this.time = 0;
			this.running = false;
			this.complete = false;

		}

		public BufferCell copy() {
			BufferCell b = new BufferCell(this.position);
			b.position = this.position;
			b.tag = this.tag;
			b.busy = this.busy;
			b.op = this.op;
			b.VJ = this.VJ;
			b.validVJ = this.validVJ;
			b.VK = this.VK;
			b.validVK = this.validVK;
			b.QJ = this.QJ;
			b.QK = this.QK;
			b.time = this.time;
			b.running = this.running;
			b.complete = this.complete;
			return b;

		}

	}

	private int size;
    public BufferCell buffer[];
	private boolean isFull = false;
	private int numOfIns = 0;
	private boolean isEmpty = true;

	public reservationStationM() {
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

	public String issue(String op, double VJ, boolean validVJ, double VK, boolean validVK, String QJ, String QK,
			int time) {
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
		buffer[loc].VK = VK;
		buffer[loc].validVK = validVK;
		buffer[loc].QJ = QJ;
		buffer[loc].QK = QK;
		buffer[loc].time = time;
		numOfIns++;
		// System.out.println(buffer[loc].tag);
		return buffer[loc].tag;

	}

	public void update() {
		boolean f = false;
		for (int i = 0; i < buffer.length; i++) {
			if (buffer[i].busy && !buffer[i].complete) {
				if (buffer[i].running) {
					buffer[i].time--;
				} else if (!f && buffer[i].validVK && buffer[i].validVJ) {
					buffer[i].running = true;
					buffer[i].time--;
					//f = true;
				}
				if (buffer[i].time == 0) {
					buffer[i].complete = true;
				}
			}

		}
	}

	public boolean setCompleteInBus(Map<String, Double> bus) {

		for (int i = 0; i < buffer.length; i++) {
			if (buffer[i].complete) {
				// System.out.println(buffer[i].tag+" "+excute(i));
				bus.put(buffer[i].tag, excute(i));
				buffer[i].busy = false;
				buffer[i].running = false;
				buffer[i].complete = false;
				buffer[i].VJ = 0;
				buffer[i].validVJ = false;
				buffer[i].VK = 0;
				buffer[i].validVK = false;
				buffer[i].QJ = null;
				buffer[i].QK = null;
				buffer[i].op = "";
				numOfIns--;
				return true;
			}

		}
		return false;

	}

	private double excute(int i) {

		if (buffer[i].op.equals("MUL.D") || buffer[i].op.equals("MULI.D")) {
			// System.out.println(buffer[i].VJ +" "+ buffer[i].VK);
			return buffer[i].VJ * buffer[i].VK;
		} else {
			return buffer[i].VJ / buffer[i].VK;
		}
	}

	public void updateBus(Map<String, Double> bus) {
		for (int i = 0; i < buffer.length; i++) {
			if (buffer[i].busy && !buffer[i].running) {
				if (!buffer[i].validVJ) {
					if (bus.containsKey(buffer[i].QJ)) {
						buffer[i].VJ = bus.get(buffer[i].QJ);
						buffer[i].validVJ = true;
						buffer[i].QJ = null;
					}
				}
				if (!buffer[i].validVK) {
					if (bus.containsKey(buffer[i].QK)) {
						buffer[i].VK = bus.get(buffer[i].QK);
						buffer[i].validVK = true;
						buffer[i].QK = null;
					}
				}

			}

		}
	}

	public reservationStationM copy() {
		reservationStationM c = new reservationStationM();
		c.size = this.size;
		for (int i = 0; i < c.buffer.length; i++) {
			c.buffer[i] = this.buffer[i].copy();
		}
		c.isFull = this.isFull;
		c.numOfIns = this.numOfIns;
		c.isEmpty = this.isEmpty;
		return c;

	}

}
