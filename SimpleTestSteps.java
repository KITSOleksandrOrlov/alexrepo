//package kf.test.flow;
//
//import org.givwenzen.annotations.DomainStep;
//import org.givwenzen.annotations.DomainSteps;
//import org.junit.Before;
//import org.springframework.beans.factory.annotation.Autowired;
//
//@DomainSteps
//public class SimpleTestSteps {
//    private String data;
//    private TestBean bean;
//    
//    
//    public SimpleTestSteps() {
//        System.out.println("SimpleTestSteps");
//        this.data = "rtrt";
////        this.bean = bean;
//    }
//
//    @Before
//    public void setup() {
//        System.out.println("Setup");
//    }
//
//    @DomainStep("just return true")
//    public boolean createToDoWithDueDateOf() {
//        this.data = "opa";
//        return true;
//    }
//    
//    @DomainStep("just return false")
//    public String sddDDD() {
//        return bean.getBean();
//    }    
//    
//}
