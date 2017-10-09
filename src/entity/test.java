package entity;



/* this is for understanding builder pattern design */

public class test {

	int value;
	String name;
	private test(testbuilder builder) {
		this.value = builder.value;
		this.name = builder.name;
	}
	public String getname() {
		return name;
	}
	public Integer getvalue() {
		return value;
	}

	//builder class
	static class testbuilder {
		int value;
		String name;
		public testbuilder setvalue(int value) {
			this.value = value;
			return this;
		}
		public testbuilder setname(String name) {
			this.name = name;
			return this;
		}
		public test build() {
			return new test(this);//THIS is obj reference
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		testbuilder builder = new testbuilder();
		builder.setname("chentan");
		builder.setvalue(Integer.MAX_VALUE);
		//test testItem = new test();
		test testItem = builder.build();//pass builder instance variable to testItem reference
		System.out.println(testItem.getname());
		System.out.println(testItem.getvalue());
		
	}

}
