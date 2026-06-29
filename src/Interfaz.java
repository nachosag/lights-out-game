package lightsOut;

import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import javax.swing.SwingConstants;

public class Interfaz {

	private static int anchoDePantalla;
	private static int largoDePantalla;

	private JLabel lbl_RecordClicks;
	private JLabel lbl_ContadorClicks;
	private JButton btn_ayuda;
	private JButton btn_Reinicio;
	private JButton btn_aumentarDificultad;
	private JButton btn_disminuirDificultad;
	private JFrame frame;
	private JPanel panel;
	private JButton[][] botones;
	private Juego juego;

	private CoordenadaRecomendada coorRecomendada;

	public static void main(String[] args) {

		// CODIGO CREADO DE FORMA PREDETERMINADA POR WINDOWBUILDER
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interfaz window = new Interfaz();
					window.frame.setSize(new Dimension(anchoDePantalla, largoDePantalla));
					window.frame.setLocationRelativeTo(null);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Interfaz() {
		tamañoDePantalla();
		inicializarVariables();
		inicializarPantalla();
		inicializarPanel();
		inicializarBotones();
		inicializarBotonesMenu();
	}

	private void tamañoDePantalla() {
		anchoDePantalla = 500;
		largoDePantalla = 700;
		juego = new Juego();
		coorRecomendada = null;
	}

	private void inicializarPantalla() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	}

	private void inicializarPanel() {
		int tamanio = juego.getTamañoMatriz();
		panel = new JPanel();
		panel.setBounds(0, 0, anchoDePantalla - 16, anchoDePantalla - 16);
//		panel.setBounds(0, 0, anchoDePantalla, anchoDePantalla);
		frame.getContentPane().add(panel);
		panel.setLayout(new GridLayout(tamanio, tamanio, 0, 0));
	}

	private void inicializarBotones() {
		int tamanio = juego.getTamañoMatriz();
		botones = new JButton[tamanio][tamanio];

		for (int f = 0; f < juego.getTamañoMatriz(); f++) {
			for (int c = 0; c < juego.getTamañoMatriz(); c++) {
				botones[f][c] = new JButton();
				botones[f][c].addActionListener(new Listener(f, c));
				panel.add(botones[f][c]);
			}
		}
		actualizarLuces();
	}

	private void inicializarVariables() {
		coorRecomendada = null;
	}

	private void inicializarBotonesMenu() {
		iniciarBotonDeReinicio();
		iniciarBotonDeDisminuirDificultar();
		iniciarBotonDeAumentarDificultad();
		iniciarLabelDeTurnos();
		iniciarLabelDeRecords();
		iniciarBotonDeAyuda();
	}

	private void iniciarBotonDeReinicio() {
		btn_Reinicio = new JButton("Reiniciar");
		accionReiniciar();
		propiedades_BotonDeReinicio();
		frame.getContentPane().add(btn_Reinicio);
	}

	private void propiedades_BotonDeReinicio() {
		btn_Reinicio.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		btn_Reinicio.setBounds(254, 495, 220, 43);
	}

	private void accionReiniciar() {
		btn_Reinicio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reiniciarJuego();
			}
		});
	}

	private void iniciarBotonDeDisminuirDificultar() {
		btn_disminuirDificultad = new JButton("Disminuir dificultad");
		accionDisminuirDificultad();
		propiedades_botonDisminuirDificultad();
		frame.getContentPane().add(btn_disminuirDificultad);
	}

	private void propiedades_botonDisminuirDificultad() {
		btn_disminuirDificultad.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		btn_disminuirDificultad.setBounds(10, 603, 220, 43);
	}

	private void accionDisminuirDificultad() {
		btn_disminuirDificultad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().remove(panel); // Elimina el panel actual
				juego.disminuirDificultad();
				juego.reiniciarJuego(); // Comunica a juego que reinicie sus elementos
				inicializarVariables(); // Reinicia las variables
				inicializarPanel(); // Crea un nuevo panel
				inicializarBotones(); // Vuelve a crear los botones en el nuevo panel
				mostrarCantClicks(); // Muestra/Reinicia la cantidad de click a 0
				frame.revalidate(); // Vuelve a validar el contenido del frame
				frame.repaint(); // Repinta el frame para reflejar los cambios
			}

		});
	}

	private void iniciarBotonDeAumentarDificultad() {
		btn_aumentarDificultad = new JButton("Aumentar dificultad");
		accionAumentarDificultad();
		propiedades_botonDeAumentarDificultad();
		frame.getContentPane().add(btn_aumentarDificultad);
	}

	private void propiedades_botonDeAumentarDificultad() {
		btn_aumentarDificultad.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		btn_aumentarDificultad.setBounds(254, 603, 220, 43);
	}

	private void accionAumentarDificultad() {
		btn_aumentarDificultad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().remove(panel); // Elimina el panel actual
				juego.aumentarDificultad();
				juego.reiniciarJuego(); // Comunica a juego que reinicie sus elementos
				inicializarVariables(); // Reinicia las variables
				inicializarPanel(); // Crea un nuevo panel
				inicializarBotones(); // Vuelve a crear los botones en el nuevo panel
				mostrarCantClicks(); // Muestra/Reinicia la cantidad de click a 0
				frame.revalidate(); // Vuelve a validar el contenido del frame
				frame.repaint(); // Repinta el frame para reflejar los cambios
			}
		});
	}

	private void iniciarLabelDeTurnos() {
		lbl_ContadorClicks = new JLabel("Clicks:" + juego.getCantClicks());
		propiedades_labelDeTurnos();
		frame.getContentPane().add(lbl_ContadorClicks);
	}

	private void propiedades_labelDeTurnos() {
		lbl_ContadorClicks.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_ContadorClicks.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		lbl_ContadorClicks.setBackground(Color.GRAY);
		lbl_ContadorClicks.setBounds(10, 549, 220, 43);
	}

	private void iniciarLabelDeRecords() {
		lbl_RecordClicks = new JLabel("Record: " + juego.getRecordClicks());
		propiedades_labelDeRecords();
		frame.getContentPane().add(lbl_RecordClicks);
	}

	private void propiedades_labelDeRecords() {
		lbl_RecordClicks.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_RecordClicks.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		lbl_RecordClicks.setBackground(Color.GRAY);
		lbl_RecordClicks.setBounds(10, 495, 220, 43);
	}

	private void iniciarBotonDeAyuda() {
		btn_ayuda = new JButton("Pedir ayuda");
		accionPedirAyuda();
		propiedades_botonDeAyuda();
		frame.getContentPane().add(btn_ayuda);
	}

	private void propiedades_botonDeAyuda() {
		btn_ayuda.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		btn_ayuda.setBounds(254, 549, 220, 43);
	}

	private void accionPedirAyuda() {
		btn_ayuda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (coorRecomendada != null) { // Esto evita que haya varias variables pintadas
					reestablecerColorBordeRecomedado();
				}
				coorRecomendada = juego.getUnaCoordenadaRecomendada();
				botones[coorRecomendada.getF()][coorRecomendada.getC()].setBorder(new LineBorder(Color.MAGENTA, 3));
			}
		});
	}

	private void reestablecerColorBordeRecomedado() {
		botones[coorRecomendada.getF()][coorRecomendada.getC()].setBorder(new LineBorder(Color.DARK_GRAY));
		coorRecomendada = null;
	}

	// Va actualizando el estado de las luces cada vez que se presiona sobre una
	private void actualizarLuces() {
		for (int f = 0; f < juego.getTamañoMatriz(); f++) {
			for (int c = 0; c < juego.getTamañoMatriz(); c++) {
				if (juego.consultarEstadoDeLuz(f, c)) {
					botones[f][c].setBackground(Color.WHITE);
				} else {
					botones[f][c].setBackground(Color.BLACK);
				}
			}
		}
	}

	private void reiniciarJuego() {
		frame.getContentPane().remove(panel); // Elimina el panel actual
		juego.reiniciarJuego(); // Comunica a juego que reinicie sus elementos
		inicializarVariables(); // Reinicia las variables
		inicializarPanel(); // Crea un nuevo panel
		inicializarBotones(); // Vuelve a crear los botones en el nuevo panel
		mostrarCantClicks(); // Muestra/Reinicia la cantidad de click a 0
		frame.revalidate(); // Vuelve a validar el contenido del frame
		frame.repaint(); // Repinta el frame para reflejar los cambios
	}

	private void actualizarCantClicks() {
		juego.aumentarCantClicks();
		mostrarCantClicks();
	}

	private void mostrarCantClicks() {
		lbl_ContadorClicks.setText("Clicks:" + juego.getCantClicks());
	}

	private void actualizarRecord() {
		juego.actualizarRecord();
		lbl_RecordClicks.setText("Record:" + juego.getRecordClicks());
	}

	// Listener de la matriz de Luces
	private class Listener implements ActionListener {
		private int fila;
		private int col;

		public Listener(int f, int c) {
			this.fila = f;
			this.col = c;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			juego.actualizarRecomendaciones(fila, col);
			juego.alternarLuz(fila, col); // Cambia el estado de las luces de la clase Juego
			actualizarLuces(); // Actualiza el estado de las luces en la Iterfaz modificados en la clase Juego
			actualizarCantClicks();
			if (coorRecomendada != null) {
				reestablecerColorBordeRecomedado();
			}
			if (juego.verificarVictoria()) {
				actualizarRecord();
				juego.reiniciarCantClicks();
				JOptionPane.showMessageDialog(frame, "Felicidades, has ganado");
				reiniciarJuego();

			}
		}
	}
}
