public class Extend extends Base {

    private String mField = Log.fieldInitInExtend();
    public static String sStaticField = Log.staticFieldInitInExtend();

    static {
        System.out.println("static block code init in extend");
    }

    {
        System.out.println("block code init in extend");
    }

    public Extend() {
        System.out.println("construct called in extend");
    }

    public static void main(String[] args) {
        new Extend();
    }
}