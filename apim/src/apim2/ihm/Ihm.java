package apim2.ihm;

import java.awt.Dimension;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Ihm {

	private static final Dimension dimensionPane = new Dimension(0, 10);
	private static final String TITLE = "Montée de version API GATEWAY";
	private static final Dimension FIELD_DIMENSION = new Dimension(200, 20); // Specify your desired dimensions here
	private static final String[] LABELS = { "Chemin absolu à la racine du produit :", "Nom produit :",
			"Nom de l' Api :", "Version :" };

	public static Map<String, String> start() {
		final Ihm ihm = new Ihm();
		final List<String> params = ihm.readValues();
		final HashMap<String, String> map = new HashMap<>();

		;
		if (params.size() == 4) {
			map.put("path", params.get(0));
			map.put("product", params.get(1));
			map.put("api", params.get(2));
			map.put("version", params.get(3));
		} else {
			alert("il manque des champs");
			System.exit(0);
		}

		return map;
	}

	public static String read() {
		return Optional.ofNullable(JOptionPane.showInputDialog(null, "projet:", "apim")).orElse("");
	}

	public static void info(String msg) {
		JOptionPane.showMessageDialog(null, msg, "Info", JOptionPane.INFORMATION_MESSAGE);
	}

	public List<String> readValues() {
		final List<String> results = new ArrayList<>();
		final List<JTextField> fields = new ArrayList<>();
		final JPanel panel = new JPanel();

		for (String label : LABELS) {
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			JTextField field = getTextField();
			fields.add(field);

			panel.add(getLabel(label));
			panel.add(field);
			panel.add(Box.createRigidArea(dimensionPane));
		}
		
		final URL url = getClass().getResource("/lbp.png");
		final ImageIcon icon = new ImageIcon(url);
		
		final int result = JOptionPane.showConfirmDialog(null, panel, TITLE, JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE, icon);

		if (result == JOptionPane.OK_OPTION) {
			for (JTextField field : fields) {
				results.add(field.getText());
				if (field.getText() == null || field.getText().trim().isEmpty()) {
					alert("Veuillez remplir tous les champs.");
					System.exit(0);
				}
			}
		} else {
			System.exit(0);
		}

		return results;
	}

	private static void alert(String msg) {
		JOptionPane.showMessageDialog(null, msg, "Erreur", JOptionPane.ERROR_MESSAGE);

	}

	private JLabel getLabel(String msg) {
		return new JLabel(msg);
	}

	private JTextField getTextField() {
		JTextField field = new JTextField();
		field.setPreferredSize(FIELD_DIMENSION);
		return field;
	}
}
