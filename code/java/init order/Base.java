public class Base {

    private String mField = Log.fieldInitInBase();
    public static String sStaticField = Log.staticFieldInitInBase();

    static {
        System.out.println("static block code init in base");
    }

    {
        System.out.println("block code init in base");
    }

    public Base() {
        System.out.println("construct called in base");
    }
}