import javax.swing.JFrame;


public class Main {
	public static void main(String[] args){
		JFrame frame = new Client();
		frame.setTitle("Online Dictionary");
		frame.setSize(800, 800);
		//frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	} 
}
