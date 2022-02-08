import java.util.Arrays;
import java.util.Map;

public class IntegerRegistersFile {
	
	 class IntegerRegister {
		int position;
		String q;
		int value;
		String tag;

		public IntegerRegister(int position) {
			this.position = position;
			this.q = null;

			this.value = 10;
			this.tag = "R" + position;
		}

		@Override
		public String toString() {
			return "FloatRegister [position=" + position + ", q=" + q + ", value=" + value + ", tag=" + tag + "]";
		}
		
		public IntegerRegister copy() {
			IntegerRegister f = new IntegerRegister(this.position);
			f.position=this.position;
			f.q=this.q;
			f.value = this.value;
			f.tag = this.tag;
			return f;
		}
		
		
	}
	

	private int size;
    IntegerRegister integerRegFile[];



	public IntegerRegistersFile() {
		this.size = 32;
		integerRegFile = new IntegerRegister[size];
		for (int i = 0; i < integerRegFile.length; i++)
			integerRegFile[i] = new IntegerRegister(i);

	}

	public int getValue(String tag) {
		return integerRegFile[Integer.parseInt(tag.substring(1))].value ;

	}
	
	public void updateBus(String tag,int value) {
		integerRegFile[Integer.parseInt(tag.substring(1))].value = value;

	}
	
//	public void setInsTags(String reg ,String tag) {
//		int index = Integer.parseInt(reg.substring(1));
//		this.integerRegFile[index].q=tag;
//	}

	@Override
	public String toString() {
		return "FloatRegistersFile [size=" + size + ", FloatRegFile=" + Arrays.toString(integerRegFile) + "]";
	}
	
	public IntegerRegistersFile copy() {
		IntegerRegistersFile f  = new IntegerRegistersFile();
		this.size=size;
		for(int i = 0 ; i <f.integerRegFile.length;i++) {
			f.integerRegFile[i]=this.integerRegFile[i];
		}
		return f;
	}
	
	

     

}
