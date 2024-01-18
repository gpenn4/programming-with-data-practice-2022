public class CheckSQLiteJAR {
    public static void main(String[] args) throws ClassNotFoundException{
        Class c = Class.forName("org.sqlite.JDBC");
        System.out.println("loaded the class" + c.getName() + " correctly."); 
    }
    
}
