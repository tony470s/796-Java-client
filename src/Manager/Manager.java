package Manager;

public class Manager {

    private static Thread t1, t2;
    
    public static void main(String[] args) {
        t1 = new Thread(new Runnable(){
            public void run() {
                try { AutoOpen.run(); }
                catch (Exception e) {}
            }
        });
        t2 = new Thread(new Runnable(){
            public void run() {
                try { AutoClose.run(); }
                catch (Exception e) {}
            }
        });
        t1.start(); t2.start();
    }
}