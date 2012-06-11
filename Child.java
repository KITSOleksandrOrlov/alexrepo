package myPackage;

public class Child extends Base {

    public String field = "child value"; 
    
    public Child() {
        System.out.println("this is child constructor");
        setBaseField("Default child value");
        classMethod();
    }
    
    @Override
	public void classMethod () {
		System.out.println("This is method of Child class.");
		System.out.println(getFieldValue());
	}
}
