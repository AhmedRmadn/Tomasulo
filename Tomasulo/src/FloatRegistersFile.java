import java.util.Arrays;
import java.util.Map;

public class FloatRegistersFile {

	class FloatRegister {
		int position;
		String q;
		double value;
		String tag;

		public FloatRegister(int position) {
			this.position = position;
			this.q = null;

			this.value = 0;
			this.tag = "F" + position;
		}

		@Override
		public String toString() {
			return "FloatRegister [position=" + position + ", q=" + q + ", value=" + value + ", tag=" + tag + "]";
		}

		public FloatRegister copy() {
			FloatRegister f = new FloatRegister(this.position);
			f.position = this.position;
			f.q = this.q;
			f.value = this.value;
			f.tag = this.tag;
			return f;
		}

	}

	private int size;
	FloatRegister FloatRegFile[];

	public FloatRegistersFile(int size) {
		this.size = size;
		this.FloatRegFile = new FloatRegister[size];
		for (int i = 0; i < FloatRegFile.length; i++)
			FloatRegFile[i] = new FloatRegister(i);
	}

	public FloatRegistersFile() {
		this.size = 32;
		FloatRegFile = new FloatRegister[size];
		for (int i = 0; i < FloatRegFile.length; i++)
			FloatRegFile[i] = new FloatRegister(i);

	}

	public Object getValue(String tag) {
		int i = Integer.parseInt(tag.substring(1));
		if (FloatRegFile[i].q == null) {
			return FloatRegFile[i].value;
		} else {
			return FloatRegFile[i].q;
		}

	}

	public void updateBus(Map<String, Double> bus) {
		for (int i = 0; i < FloatRegFile.length; i++) {
			if (FloatRegFile[i].q != null) {
				if (bus.containsKey(FloatRegFile[i].q)) {
					FloatRegFile[i].value = bus.get(FloatRegFile[i].q);
					FloatRegFile[i].q = null;

				}
			}

		}

	}

	public void setInsTags(String reg, String tag) {
		int index = Integer.parseInt(reg.substring(1));
		this.FloatRegFile[index].q = tag;
	}

	@Override
	public String toString() {
		return "FloatRegistersFile [size=" + size + ", FloatRegFile=" + Arrays.toString(FloatRegFile) + "]";
	}

	public FloatRegistersFile copy() {
		FloatRegistersFile f = new FloatRegistersFile();
		this.size = size;
		for (int i = 0; i < f.FloatRegFile.length; i++) {
			f.FloatRegFile[i] = this.FloatRegFile[i].copy();
		}
		return f;
	}

}
