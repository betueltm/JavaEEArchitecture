package betueltm.javatests;

public class Draft {
	public static void main(String[] args) {
		Operation sumOperation = (a,b) -> { return a + b; };
		Operation subtractOperation = (a,b) -> { return a - b; };
		
		int sum = sumOperation.calculate(1, 3);
		int subtraction = subtractOperation.calculate(3, 2);
		
		System.out.println("[" + sum + "," + subtraction + "]");
	}
	
	@FunctionalInterface
	public interface Operation {
		public int calculate(int a, int b);
	}
}
