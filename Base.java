package myPackage;

public class Base {
//comment by user 1    
	private String field;
	
	public Base() {
	    System.out.println("this is base constructor");
        setBaseField("Default base value");
        classMethod();
    }
    
	public void setBaseField(String str) {
//	    System.out.println("setBaseField with value = " + str);
		this.field = str;
	}
	
	public String getFieldValue(){
	    return field;
	}

	public void classMethod () {
		System.out.println("This is method of Base class.");
		System.out.println(getFieldValue());
	}
}