import java.util.Arrays;

public class FloatMemory {
	private int size ;
	 double mem [];

	public FloatMemory() {
		this.size=38;
		mem = new double[size];
	}
	
	public double getValue(int address) {
		return this.mem[address];
	}
	
	public void setValue(int address,double value) {
		this.mem[address]=value;
	}

	@Override
	public String toString() {
		return "FloatMemory [size=" + size + ", mem=" + Arrays.toString(mem) + "]";
	}
	
	public FloatMemory copy() {
		FloatMemory f  = new FloatMemory();
		for(int i = 0 ; i < f.mem.length;i++) {
			f.mem[i]=this.mem[i];
		}
		return f;
	}
	
	

}
