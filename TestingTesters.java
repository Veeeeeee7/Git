public class TestingTesters {
    public static void main(String[] args) throws Exception {
        Utils.writeStringToFile("testv", "test file contents");
        Utils.writeStringToFile("testv2", "hello");
        Index ind = new Index();
        ind.init();
        ind.add("testv");
        ind.add("testv2");
        ind.remove("testv");
    }
}
