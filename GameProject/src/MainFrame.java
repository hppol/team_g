import javax.swing.JFrame;

public class MainFrame extends JFrame {
	public MainFrame() {
		setTitle("Game");
		setSize(800, 600);
		setLocationRelativeTo(null);
		add(new Screen());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
}
